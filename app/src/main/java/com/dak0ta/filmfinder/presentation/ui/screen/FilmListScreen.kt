package com.dak0ta.filmfinder.presentation.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.dak0ta.filmfinder.R
import com.dak0ta.filmfinder.presentation.entity.FilmDisplay
import com.dak0ta.filmfinder.presentation.ui.components.ErrorContent
import com.dak0ta.filmfinder.presentation.ui.components.LoadingIndicator
import com.dak0ta.filmfinder.presentation.viewmodel.FilmListViewModel

@Composable
fun FilmListScreen(
    viewModel: FilmListViewModel = hiltViewModel(),
    onFilmClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val films by viewModel.films.collectAsState()
    val currentPage = viewModel.currentPage.collectAsState()
    val totalPages = viewModel.totalPages.collectAsState()

    when (state) {
        is FilmListViewModel.FilmListState.Loading -> {
            LoadingIndicator()
        }

        is FilmListViewModel.FilmListState.Success -> {
            FilmListContent(
                films = films,
                currentPage = currentPage.value,
                totalPages = totalPages.value,
                onFilmClick = onFilmClick,
                onPageChange = { page -> viewModel.loadFilms(page) }
            )
        }

        is FilmListViewModel.FilmListState.Error -> {
            ErrorContent(
                message = (state as FilmListViewModel.FilmListState.Error).message,
                onRetry = { viewModel.loadFilms(currentPage.value) }
            )
        }
    }
}

@Composable
fun FilmListContent(
    films: List<FilmDisplay>,
    currentPage: Int,
    totalPages: Int,
    onFilmClick: (Int) -> Unit,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        FilmList(
            films = films,
            onFilmClick = onFilmClick,
            modifier = Modifier.weight(1f)
        )

        Pagination(
            currentPage = currentPage,
            totalPages = totalPages,
            onPageChange = onPageChange,
            modifier = Modifier
        )
    }
}


@Composable
fun FilmList(
    films: List<FilmDisplay>,
    onFilmClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(films.size) { index ->
            val film = films[index]
            FilmItem(film = film, onClick = { onFilmClick(film.id) })
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
        }
    }
}

@Composable
fun FilmItem(film: FilmDisplay, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Poster
            AsyncImage(
                model = film.posterUrl,
                contentDescription = stringResource(R.string.poster),
                error = painterResource(id = R.drawable.emptyposter),
                placeholder = painterResource(id = R.drawable.emptyposter),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(132.dp)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Film info
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Name
                Text(
                    text = film.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Alternative name and year
                if (film.displayAlternativeNameAndYear.isNotEmpty()) {
                    Text(
                        text = film.displayAlternativeNameAndYear,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Countries and genres
                if (film.displayCountriesAndGenres.isNotEmpty()) {
                    Text(
                        text = film.displayCountriesAndGenres,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun Pagination(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Go to the first page
        PageNavigationButton(
            enabled = currentPage > 1,
            onClick = { onPageChange(1) },
            icon = R.drawable.chevron_first,
            contentDescription = stringResource(R.string.go_to_first_page)
        )

        // Go to the previous page
        PageNavigationButton(
            enabled = currentPage > 1,
            onClick = { onPageChange(currentPage - 1) },
            icon = R.drawable.chevron_left,
            contentDescription = stringResource(R.string.go_to_previous_page)
        )

        // Page numbers
        Pages(
            currentPage = currentPage,
            totalPages = totalPages,
            onPageChange = onPageChange
        )

        // Go to the next page
        PageNavigationButton(
            enabled = currentPage < totalPages,
            onClick = { onPageChange(currentPage + 1) },
            icon = R.drawable.chevron_right,
            contentDescription = stringResource(R.string.go_to_next_page)
        )

        // Go to the last page
        PageNavigationButton(
            enabled = currentPage < totalPages,
            onClick = { onPageChange(totalPages) },
            icon = R.drawable.chevron_last,
            contentDescription = stringResource(R.string.go_to_last_page)
        )
    }
}

@Composable
fun PageNavigationButton(
    enabled: Boolean,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = if (enabled) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun Pages(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        val pagesToShow = when (currentPage) {
            1 -> listOf(1, 2, 3).filter { it <= totalPages }
            totalPages -> listOf(totalPages - 2, totalPages - 1, totalPages).filter { it > 0 }
            else -> listOf(currentPage - 1, currentPage, currentPage + 1)
        }

        pagesToShow.forEach { page ->
            PageButton(
                page = page,
                isCurrent = (page == currentPage),
                onClick = { onPageChange(page) }
            )
        }
    }
}

@Composable
fun PageButton(page: Int, isCurrent: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isCurrent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .wrapContentWidth(),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
    ) {
        Text(
            text = page.toString()
        )
    }
}