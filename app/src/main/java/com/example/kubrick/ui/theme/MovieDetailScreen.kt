package com.example.kubrick.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.kubrick.data.model.MovieDetails

private const val DETAIL_IMAGE =
    "https://image.tmdb.org/t/p/"

@Composable
fun MovieDetailScreen(
    movieId: Int,
    onBack: () -> Unit,
    vm: MovieDetailViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(movieId) {
        vm.loadMovie(movieId)
    }

    val movie = state.movie

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
    ) {
        when {
            state.loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Accent
                )
            }

            state.error != null -> {
                Text(
                    text = state.error ?: "Something went wrong",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            movie != null -> {
                MovieDetailContent(
                    movie = movie,
                    cast = state.cast,
                    onBack = onBack
                )
            }
        }
    }
}

@Composable
private fun MovieDetailContent(
    movie: MovieDetails,
    cast: List<com.example.kubrick.data.model.CastMember>,
    onBack: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {

            // HERO BACKDROP
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(440.dp)
            ) {
                AsyncImage(
                    model = movie.backdropPath?.let {
                        DETAIL_IMAGE + "w1280" + it
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
                                    Color.Black.copy(alpha = 0.15f),
                                    Color.Transparent,
                                    Dark
                                )
                            )
                        )
                )

                // BACK BUTTON
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(18.dp)
                        .align(Alignment.TopStart)
                        .clip(CircleShape)
                        .background(Color(0xAA101719))
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                // BOOKMARK BUTTON
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(18.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(Color(0xAA101719))
                ) {
                    Icon(
                        Icons.Default.BookmarkBorder,
                        contentDescription = "Add to watchlist",
                        tint = Color.White
                    )
                }
            }

            // MOVIE INFORMATION
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    movie.displayTitle,
                    color = Color.White,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        movie.releaseDate?.take(4) ?: "Release date unavailable",
                        color = Accent,
                        fontSize = 13.sp
                    )

                    Spacer(Modifier.width(14.dp))

                    Text(
                        "★ ${"%.1f".format(movie.rating)}",
                        color = Accent,
                        fontSize = 13.sp
                    )

                    movie.runtime?.let {
                        Spacer(Modifier.width(14.dp))
                        Text(
                            "${it / 60}h ${it % 60}m",
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                // GENRES
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    items(movie.genres) { genre ->
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(0xFF263235))
                                .border(
                                    1.dp,
                                    Accent.copy(alpha = 0.25f),
                                    CircleShape
                                )
                                .padding(
                                    horizontal = 15.dp,
                                    vertical = 9.dp
                                )
                        ) {
                            Text(
                                genre.name,
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(32.dp))

                // SYNOPSIS
                Text(
                    "Synopsis",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    movie.overview?.ifBlank {
                        "No synopsis available."
                    } ?: "No synopsis available.",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    lineHeight = 23.sp
                )

                Spacer(Modifier.height(32.dp))

                // CAST
                Text(
                    "Cast",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(16.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    items(cast) { actor ->
                        Column(
                            modifier = Modifier.width(100.dp)
                        ) {
                            AsyncImage(
                                model = actor.profilePath?.let {
                                    DETAIL_IMAGE + "w185" + it
                                },
                                contentDescription = actor.name,
                                modifier = Modifier
                                    .size(75.dp)
                                    .clip(CircleShape)
                                    .background(CardColor),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                actor.name,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1
                            )

                            Text(
                                actor.character ?: "",
                                color = Color.LightGray,
                                fontSize = 11.sp,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // WATCH BUTTON
        Button(
            onClick = {
                // Streaming availability will be implemented later.
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 22.dp)
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF258F99)
            ),
            shape = CircleShape
        ) {
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White
            )

            Spacer(Modifier.width(8.dp))

            Text(
                "Watch Now",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}