package com.contelli.jetmovie.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.contelli.jetmovie.model.Movie
import com.contelli.jetmovie.model.getMovies

@Composable
fun DetailsScreen(navController: NavController, movieData: String?) {
    val movies: List<Movie> = getMovies()
    val movie: Movie? = movies.find {
        it.id == movieData
    }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(movie!!.images[0])
            .size(Size.ORIGINAL).crossfade(true).build(),
    )
    Scaffold(topBar = {
        TopAppBar() {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
                Text(text = "Voltar", style = MaterialTheme.typography.h6)
            }
        }
    }) {
        it
        Column() {
            if (movie != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                        CircularProgressIndicator()
                    }

                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(80.dp)
                            .height(160.dp)
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "${movie.title} - ${movie.year}")
                        Text(text = movie.genre)
                        Text(text = movie.director)
                        Text(text = movie.actors)
                        Text(text = movie.rating)
                    }

                }
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = movie.plot)
                Spacer(modifier = Modifier.padding(16.dp))
                LazyRow {
                    items(movie.images) {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                    }
                }

            }
        }
    }
}

fun openDialog(id: String, isOpen: Boolean) {

}