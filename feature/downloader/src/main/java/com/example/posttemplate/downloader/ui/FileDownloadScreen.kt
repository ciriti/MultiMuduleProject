package com.example.posttemplate.downloader.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.posttemplate.ui.components.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.compose.koinInject

data class Download(val id: Int, val name: String, val progress: Float)

@Composable
fun FileDownloadScreen(
    viewModel: BaseViewModel<DownloadsState, DownloadsEffect, DownloadsIntent> = koinInject(),
    onDownloadClick: (Download) -> Unit
) {
    val downloads = viewModel.state.collectAsState().value.downloads.values.toList()
    LazyColumn {
        items(downloads, key = { file -> file.id }) { file ->
            fileDownload(file, file.progress, true, {}, {})
        }
    }
}

@Composable
fun fileDownload(
    download: Download,
    progress: Float,
    isDownloading: Boolean,
    onStopClick: () -> Unit,
    onStartClick: () -> Unit,
) {

    var debouncedProgress by remember { mutableFloatStateOf(progress) }
    val animatedProgress by animateFloatAsState(targetValue = debouncedProgress, label = "")

    LaunchedEffect(progress) {
        delay(200) // Adjust the delay as needed
        debouncedProgress = progress
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // File name on the left
            Text(
                text = download.name,
                modifier = Modifier.weight(1f),
            )

            // Stop download button on the right
            Button(
                onClick = onStopClick,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(if (isDownloading) "Stop" else "Start")
            }
        }

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FileDownloadScreenPreview() {
    FileDownloadScreen(
        viewModel = DownloadsMockVM(),
        onDownloadClick = {}
    )
}

class DownloadsMockVM : BaseViewModel<DownloadsState, DownloadsEffect, DownloadsIntent>() {
    private val _state = MutableStateFlow(
        DownloadsState(downloads = listOf(
            Download(1, "First File", 5f),
            Download(2, "Second File", 90f)
        ).associateBy { it.id })
    )
    override val state: StateFlow<DownloadsState> = _state

    private val _effect = MutableSharedFlow<DownloadsEffect>()
    override val effect: SharedFlow<DownloadsEffect>
        get() = _effect

    override fun handleIntent(intent: DownloadsIntent) {}
}
