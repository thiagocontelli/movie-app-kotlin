package com.contelli.jetmovie.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.contelli.jetmovie.model.Movie
import com.contelli.jetmovie.model.getMovies
import com.contelli.jetmovie.navigation.MovieScreens

@Composable
fun HomeScreen(navController: NavController) {
    val movieList = getMovies()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movieList) {
                MovieCard(
                    navController = navController,
                    movie = it
                )
            }
        }
    }
}

@Composable
fun MovieCard(
    navController: NavController, movie: Movie
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(movie.images[0])
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build(),
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(route = MovieScreens.DetailsScreen.name + "/${movie.id}")
            }, backgroundColor = Color.Green.copy(alpha = 0.5f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            if (painter.state is AsyncImagePainter.State.Loading ||
                painter.state is AsyncImagePainter.State.Error
            ) {
                CircularProgressIndicator()
            }

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(80.dp)
                    .aspectRatio(1f)
            )
            Column() {
                Text(text = "${movie.title} - ${movie.year}")
                Text(text = "${movie.genre}")
            }
        }
    }
}