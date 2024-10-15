package com.dak0ta.filmfinder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.dak0ta.domain.usecase.GetFilmsUseCase
import com.dak0ta.domain.usecase.GetTotalPagesUseCase
import com.dak0ta.filmfinder.presentation.entity.FilmDisplay
import com.dak0ta.filmfinder.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val getTotalPagesUseCase: GetTotalPagesUseCase
) : ViewModel() {

    private val _films = MutableStateFlow<List<FilmDisplay>>(emptyList())
    val films: StateFlow<List<FilmDisplay>> = _films

    private val _state = MutableStateFlow<FilmListState>(FilmListState.Loading)
    val state: StateFlow<FilmListState> = _state

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    private val _totalPages = MutableStateFlow(1)
    val totalPages: StateFlow<Int> = _totalPages

    private val supervisorJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(supervisorJob + Dispatchers.Main)

    companion object {
        private const val MAX_TOTAL_PAGES = 47591
    }

    init {
        loadTotalPages()
        loadFilms(_currentPage.value)
    }

    fun loadFilms(page: Int = 1) {
        viewModelScope.launch {
            _state.value = FilmListState.Loading
            try {
                val result = withContext(Dispatchers.IO) {
                    getFilmsUseCase(page)
                }

                result.onSuccess { films ->
                    _films.value = films.map { it.toPresentation() }
                    _currentPage.value = page
                    _state.value = FilmListState.Success
                }.onFailure {
                    handleError(it)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun loadTotalPages() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    getTotalPagesUseCase()
                }

                result.onSuccess { pages ->
                    _totalPages.value = min(pages, MAX_TOTAL_PAGES)
                }.onFailure {
                    handleError(it)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(e: Throwable) {
        _state.value = FilmListState.Error(e.message.orEmpty())
    }

    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancel()
    }

    sealed class FilmListState {
        object Loading : FilmListState()
        object Success : FilmListState()
        data class Error(val message: String) : FilmListState()
    }
}
