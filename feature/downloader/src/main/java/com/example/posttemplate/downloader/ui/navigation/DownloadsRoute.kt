package com.example.posttemplate.downloader.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.posttemplate.downloader.ui.Download
import com.example.posttemplate.downloader.ui.FileDownloadScreen
import com.example.posttemplate.ui.navigation.Route


fun NavGraphBuilder.downloadsRoute(onDownloadClick: (Download) -> Unit) {

    composable(route = Route.Downloads.route) {
        FileDownloadScreen(
            onDownloadClick = onDownloadClick
        )
    }
}
