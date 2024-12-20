package io.github.ciriti.sdk.internal

internal interface FileCache {
    fun loadFiles(): List<ByteArray>
    fun saveFile(name: String, data: ByteArray)
    fun getFile(name: String): ByteArray?
    fun clear()
    fun getFilesCount(): Int
}
