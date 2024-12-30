package io.github.ciriti.sdk.api

import kotlinx.coroutines.flow.Flow

interface FileDownloaderSdk {

    val eventFlow: Flow<SdkEvent>

    fun loadFiles()
    fun loadFiles(urls: List<String>)
    fun clearFiles()
    fun getFiles(): List<ByteArray>
    fun getFile(name: String): ByteArray?
    fun getFilesCount(): Int
    fun getFilesSize(): Int
    fun setClient(client: SdkClient)
    fun cancelDownload(name: String)
    fun cancelAllDownloads()

    companion object
}
