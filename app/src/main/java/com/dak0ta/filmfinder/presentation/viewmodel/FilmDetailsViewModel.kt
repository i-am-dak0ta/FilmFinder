package com.dak0ta.filmfinder.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dak0ta.domain.usecase.GetFilmDetailsUseCase
import com.dak0ta.filmfinder.presentation.entity.FilmDetailsDisplay
import com.dak0ta.filmfinder.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _filmDetails = MutableStateFlow<FilmDetailsDisplay?>(null)
    val filmDetails: StateFlow<FilmDetailsDisplay?> = _filmDetails

    private val _state = MutableStateFlow<FilmDetailsState>(FilmDetailsState.Loading)
    val state: StateFlow<FilmDetailsState> = _state

    private val supervisorJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(supervisorJob + Dispatchers.Main)

    init {
        val filmId: Int = savedStateHandle["filmId"] ?: error("filmId is missing")
        loadFilmDetails(filmId)
    }

    fun loadFilmDetails(filmId: Int) {
        viewModelScope.launch {
            _state.value = FilmDetailsState.Loading
            try {
                val result = withContext(Dispatchers.IO) {
                    getFilmDetailsUseCase(filmId)
                }

                result.onSuccess { filmDetails ->
                    _filmDetails.value = filmDetails.toPresentation()
                    _state.value = FilmDetailsState.Success
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
        _state.value = FilmDetailsState.Error(e.message.orEmpty())
    }


    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancel()
    }

    sealed class FilmDetailsState {
        object Loading : FilmDetailsState()
        object Success : FilmDetailsState()
        data class Error(val message: String) : FilmDetailsState()
    }
}