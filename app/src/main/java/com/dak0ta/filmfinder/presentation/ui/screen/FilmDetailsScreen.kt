package com.dak0ta.filmfinder.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.dak0ta.filmfinder.R
import com.dak0ta.filmfinder.presentation.entity.FilmDetailsDisplay
import com.dak0ta.filmfinder.presentation.entity.ReviewDisplay
import com.dak0ta.filmfinder.presentation.ui.components.ErrorContent
import com.dak0ta.filmfinder.presentation.ui.components.InfoRow
import com.dak0ta.filmfinder.presentation.ui.components.LoadingIndicator
import com.dak0ta.filmfinder.presentation.ui.theme.Neutral1000
import com.dak0ta.filmfinder.presentation.viewmodel.FilmDetailsViewModel

@Composable
fun FilmDetailsScreen(
    viewModel: FilmDetailsViewModel = hiltViewModel(),
    filmId: Int
) {
    val state by viewModel.state.collectAsState()
    val filmDetails by viewModel.filmDetails.collectAsState()

    when (state) {
        is FilmDetailsViewModel.FilmDetailsState.Loading -> LoadingIndicator()

        is FilmDetailsViewModel.FilmDetailsState.Success -> {
            filmDetails?.let { film ->
                FilmDetailsContent(film)
            }
        }

        is FilmDetailsViewModel.FilmDetailsState.Error -> {
            ErrorContent(
                message = (state as FilmDetailsViewModel.FilmDetailsState.Error).message,
                onRetry = { viewModel.loadFilmDetails(filmId) }
            )
        }
    }
}

@Composable
fun FilmDetailsContent(
    film: FilmDetailsDisplay
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Poster
        AsyncImage(
            model = film.posterUrl,
            contentDescription = stringResource(R.string.poster),
            error = painterResource(R.drawable.emptyposter),
            placeholder = painterResource(R.drawable.emptyposter),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(264.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally)
        )

        HorizontalDivider(
            modifier = Modifier.padding(16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )

        // Film info
        FilmDetailInfo(film)

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )

        // List of images
        if (film.filmImageUrls.isNotEmpty()) {
            FilmImageList(
                film.filmImageUrls,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            InfoRow(
                iconResId = R.drawable.circle_x,
                contentDescription = stringResource(R.string.error_no_images),
                text = stringResource(R.string.error_no_images),
                modifier = Modifier.padding(16.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )

        // List of reviews
        if (film.reviews.isNotEmpty()) {
            ReviewList(
                film.reviews,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            InfoRow(
                iconResId = R.drawable.circle_x,
                contentDescription = stringResource(R.string.error_no_reviews),
                text = stringResource(R.string.error_no_reviews),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun FilmImageList(
    filmImageUrls: List<String>,
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = modifier
            .height(300.dp)
    ) {
        items(filmImageUrls.size) { index ->
            val filmImageUrl = filmImageUrls[index]
            FilmImageItem(filmImageUrl)
        }
    }
}

@Composable
fun FilmImageItem(filmImageUrl: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(

            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        AsyncImage(
            model = filmImageUrl,
            contentDescription = stringResource(R.string.image),
            error = painterResource(id = R.drawable.emptyposter),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(132.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun ReviewList(
    reviews: List<ReviewDisplay>,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(reviews.size) { index ->
            val review = reviews[index]
            ReviewItem(review)
        }
    }
}

@Composable
fun ReviewItem(review: ReviewDisplay) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Card(
        modifier = Modifier
            .widthIn(max = 0.9f * screenWidth)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = review.typeColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Author
            Text(
                text = review.displayAuthor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Neutral1000
            )

            // User rating
            if (review.displayUserRating.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = review.displayUserRating,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Neutral1000
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Review title
            if (review.displayTitle.isNotEmpty()) {
                Text(
                    text = review.displayTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Neutral1000
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Review text
            Text(
                text = review.displayReview,
                style = MaterialTheme.typography.bodyMedium,
                color = Neutral1000,
                maxLines = 64,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun FilmDetailInfo(
    film: FilmDetailsDisplay
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Name
        Text(
            text = film.displayName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Alternative name
        if (film.displayAlternativeName.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = film.displayAlternativeName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Rating
        InfoRow(
            iconResId = R.drawable.star,
            contentDescription = stringResource(R.string.rating),
            text = film.displayRating
        )

        // Release years
        if (film.displayYear.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(
                iconResId = R.drawable.calendar,
                contentDescription = stringResource(R.string.release_years),
                text = film.displayYear
            )
        }

        // Genres
        if (film.displayGenres.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(
                iconResId = R.drawable.film,
                contentDescription = stringResource(R.string.genres),
                text = film.displayGenres
            )
        }

        // Countries
        if (film.displayCountries.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(
                iconResId = R.drawable.map_pin,
                contentDescription = stringResource(R.string.countries),
                text = film.displayCountries
            )
        }

        // Description
        if (film.displayDescription.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(
                iconResId = R.drawable.text,
                contentDescription = stringResource(R.string.description),
                text = film.displayDescription
            )
        }
    }
}