package io.github.ciriti.sdk.internal.cache

internal interface FileCache {
    fun loadFiles(): List<ByteArray>
    suspend fun saveFile(name: String, data: ByteArray)
    fun getFile(name: String): ByteArray?
    suspend fun clear()
    fun getFilesCount(): Int
}
