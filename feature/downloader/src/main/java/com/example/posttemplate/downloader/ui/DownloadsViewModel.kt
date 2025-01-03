package com.example.posttemplate.downloader.ui

import androidx.lifecycle.viewModelScope
import com.example.posttemplate.ui.components.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random


class DownloadsViewModel : BaseViewModel<DownloadsState, DownloadsEffect, DownloadsIntent>() {
    private val _state = MutableStateFlow(DownloadsState())
    override val state: StateFlow<DownloadsState> = _state

    private val _effect = MutableSharedFlow<DownloadsEffect>()
    override val effect: SharedFlow<DownloadsEffect>
        get() = _effect

    private var updateJob: Job? = null

    init {
        createDownloads()
        startHighFrequencyUpdates()
    }

    private fun createDownloads(){
        val downloads = (1 .. 10)
            .map { id -> Download(id, "File $id", 0f) }
        _state.value = state.value.copy(downloads = downloads.associateBy { it.id })
    }

    fun startHighFrequencyUpdates() {
        val files = _state.value.downloads.values.toList()
        updateJob = viewModelScope.generateHighFrequencyUpdates(files) { updatedFile ->
            updateFileProgress(updatedFile.id, updatedFile.progress)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopHighFrequencyUpdates()
    }

    fun stopHighFrequencyUpdates() {
        updateJob?.cancel()
    }

    private fun updateFileProgress(fileId: Int, progress: Float) {
        viewModelScope.launch {
            val updatedFiles = _state.value.downloads.toMutableMap().apply {
                this[fileId]?.let { file ->
                    this[fileId] = file.copy(progress = progress)
                }
            }
            _state.value = _state.value.copy(downloads = updatedFiles)
        }
    }

    private fun CoroutineScope.generateHighFrequencyUpdates(
        downloads: List<Download>,
        updateIntervalMillis: Long = 100L,
        onUpdate: (Download) -> Unit
    ): Job {
        return launch {
            while (isActive) {
                val filesToUpdate = downloads.shuffled().take(Random.nextInt(1, downloads.size + 1))
                filesToUpdate.forEach { file ->
                    val newProgress = Random.nextFloat() * 100
                    onUpdate(file.copy(progress = newProgress))
                }
                delay(updateIntervalMillis)
            }
        }
    }


    override fun handleIntent(intent: DownloadsIntent) {
        when (intent) {
            DownloadsIntent.LoadDownloads -> startHighFrequencyUpdates()
        }
    }
}
