package com.example.posttemplate.downloader.ui

data class DownloadsState(
    val isLoading: Boolean = false,
    val downloads: Map<Int, Download> = emptyMap(),
    val errorMessage: String? = null
)

sealed class DownloadsIntent {
    data object LoadDownloads : DownloadsIntent()
}

sealed class DownloadsEffect {
    data class ShowError(val message: String) : DownloadsEffect()
}
