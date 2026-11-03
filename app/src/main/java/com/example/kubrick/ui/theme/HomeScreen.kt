
package com.example.kubrick.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.kubrick.data.model.Movie
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import androidx.compose.foundation.clickable


private const val IMAGE_URL =
    "https://image.tmdb.org/t/p/"

val Dark = Color(0xFF101719)
val CardColor = Color(0xFF202B2F)
val Accent = Color(0xFFB8D9D4)
@Composable
fun HomeTabScreen(

    vm: HomeViewModel = viewModel()
) {
    val state by vm.state.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 110.dp)
    ) {

        // PROFILE HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(CardColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = Accent
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(
                        "Welcome back,",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )

                    Text(
                        "Guest",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.NotificationsNone,
                    contentDescription = "Notifications",
                    tint = Color.White
                )
            }
        }

        // SEARCH BAR
        OutlinedTextField(
            value = state.searchText,
            onValueChange = vm::updateSearch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            placeholder = {
                Text(
                    "Movies, series, shows...",
                    color = Color.LightGray
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            },
            trailingIcon = {
                IconButton(onClick = vm::search) {
                    Icon(
                        Icons.Default.Tune,
                        contentDescription = "Search",
                        tint = Accent
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { vm.search() }
            ),
            shape = RoundedCornerShape(30.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Accent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = CardColor,
                unfocusedContainerColor = CardColor
            )
        )

        Spacer(Modifier.height(20.dp))

        // GENRE FILTERS
        LazyRow(
            contentPadding = PaddingValues(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item {
                GenreChip(
                    "All",
                    state.selectedGenre == null
                ) {
                    vm.selectGenre(null)
                }
            }

            items(state.genres) { genre ->
                GenreChip(
                    genre.name,
                    state.selectedGenre == genre.id
                ) {
                    vm.selectGenre(genre.id)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // FEATURED BANNER
        if (state.movies.isNotEmpty()) {
            FeaturedBanner(state.movies.first())
        }

        Spacer(Modifier.height(26.dp))

        // TRENDING MOVIES
        MovieSliderSection(
            title = "Trending",
            movies = state.movies
        )

        // TOP RATED MOVIES
        MovieSliderSection(
            title = "Top Rated",
            movies = state.topRatedMovies
        )

        // HOTSTAR
        MovieSliderSection(
            title = "Hotstar",
            movies = state.topRatedMovies
        )
    }
}


@Composable
fun GenreChip(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                if (selected) Accent else CardColor
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            name,
            color = if (selected) Dark else Color.White,
            fontSize = 13.sp
        )
    }
}

@Composable
fun FeaturedBanner(movie: Movie) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .height(205.dp)
            .clip(RoundedCornerShape(22.dp))
    ) {
        AsyncImage(
            model = movie.backdropPath?.let {
                IMAGE_URL + "w1280" + it
            },
            contentDescription = movie.displayTitle,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.85f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(18.dp)
        ) {
            Text(
                "FEATURED",
                color = Accent,
                fontSize = 10.sp,
                letterSpacing = 3.sp
            )

            Text(
                movie.displayTitle,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "★ ${"%.1f".format(movie.rating)} / 10",
                color = Accent,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Column(
        modifier = Modifier.width(150.dp)
    )   {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(CardColor)
        ) {
            AsyncImage(
                model = movie.posterPath?.let {
                    IMAGE_URL + "w500" + it
                },
                contentDescription = movie.displayTitle,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(Color(0xCC101719))
                    .padding(horizontal = 9.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(13.dp)
                )

                Spacer(Modifier.width(3.dp))

                Text(
                    "%.1f".format(movie.rating),
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(9.dp))

        Text(
            movie.displayTitle,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            maxLines = 1
        )


    }


}

@Composable
fun MovieSliderSection(
    title: String,
    movies: List<Movie>
) {
    if (movies.isEmpty()) return

    Spacer(Modifier.height(30.dp))

    Text(
        text = title,
        color = Color.White,
        fontSize = 21.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 18.dp)
    )

    Spacer(Modifier.height(16.dp))

    LazyRow(
        contentPadding = PaddingValues(horizontal = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(
            items = movies,
            key = { movie -> movie.id }
        ) { movie ->
            MovieCard(movie)
        }
    }
}


