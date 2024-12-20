package io.github.ciriti.sdk.internal

import androidx.collection.LruCache
import java.io.File

internal class LruFileCache(private val cacheDir: File) : FileCache {
    private val lruCache: LruCache<String, ByteArray> = LruCache(50)

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        loadStoredFiles()
    }

    override fun loadFiles(): List<ByteArray> {
        return lruCache.snapshot().values.toList()
    }

    override fun saveFile(name: String, data: ByteArray) {
        val file = File(cacheDir, name)
        file.writeBytes(data)
        lruCache.put(name, data)
    }

    override fun getFile(name: String): ByteArray? {
        return lruCache.get(name)
    }

    override fun clear() {
        lruCache.evictAll()
        cacheDir.deleteRecursively()
    }

    private fun loadStoredFiles() {
        cacheDir.listFiles()?.forEach { file ->
            val fileName = file.name
            val fileContent = file.readBytes()
            lruCache.put(fileName, fileContent)
        }
    }

    override fun getFilesCount(): Int {
        TODO("Not yet implemented")
    }
}
