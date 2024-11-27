package com.example.tvplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util

class MainActivity : ComponentActivity() {
    private lateinit var player: ExoPlayer

    private val songs = listOf(
        R.raw.rosapastel,
        R.raw.yoguapo
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        player = ExoPlayer.Builder(this).build()

        playSong(songs[currentIndex])

        setContent {
            MusicPlayerUI(
                onPlayPause = { togglePlayPause() },
                onNext = { playNextSong() },
                onPrevious = { playPreviousSong() },
                currentSongTitle = "Song ${currentIndex + 1}"
            )
        }
    }
    private fun playSong(songResId: Int) {
        val uri = "android.resource://$packageName/$songResId"
        val mediaItem: MediaItem = MediaItem.Builder()
            .setUri(uri)
            .build()
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }
    //private fun playSong(songResId: Int) {
      //  val mediaItem: MediaItem = MediaItem.Builder()
         //   .setUri(Util.getUriForResourceId(this, songResId))
        //    .build()
     //   player.setMediaItem(mediaItem)
      //  player.prepare()
      //  player.play()
  //  }

    private fun togglePlayPause() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    private fun playNextSong() {
        currentIndex = (currentIndex + 1) % songs.size
        playSong(songs[currentIndex])
    }

    private fun playPreviousSong() {
        currentIndex = if (currentIndex == 0) songs.size - 1 else currentIndex - 1
        playSong(songs[currentIndex])
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}


@Composable
fun MusicPlayerUI(
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    currentSongTitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Song Title: $currentSongTitle")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onPrevious() }) {
                Text(text = "Previous")
            }
            Button(onClick = { onPlayPause() }) {
                Text(text = "Play/Pause")
            }
            Button(onClick = { onNext() }) {
                Text(text = "Next")
            }
        }
    }
}

// Función para agregar Card en Compose
fun Card(modifier: Modifier, elevation: Dp, content: @Composable ColumnScope.() -> Unit) {
}
