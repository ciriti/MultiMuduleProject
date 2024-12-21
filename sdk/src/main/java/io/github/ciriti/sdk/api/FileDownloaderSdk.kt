package io.github.ciriti.sdk.api

interface FileDownloaderSdk {
    fun loadFiles(urls: List<String>)
    fun clearFiles()
    fun getFiles(): List<ByteArray>
    fun getFile(name: String): ByteArray?
    fun getFilesCount(): Int
    fun getFilesSize(): Int
    fun setClient(client: SdkClient)

    companion object
}
