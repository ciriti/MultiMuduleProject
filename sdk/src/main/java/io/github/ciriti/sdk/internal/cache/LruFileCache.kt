package io.github.ciriti.sdk.internal.cache

import androidx.collection.LruCache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

internal class LruFileCache(
    private val cacheDir: File,
    private val maxSize: Int,
) : FileCache {

    private val lruCache: LruCache<String, ByteArray> by lazy {
        object : LruCache<String, ByteArray>(maxSize) {
            override fun sizeOf(key: String, value: ByteArray): Int {
                return value.size
            }
        }
    }

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        loadStoredFiles()
    }

    private val mutex by lazy { Mutex() }

    override fun loadFiles(): List<ByteArray> {
        return lruCache.snapshot().values.toList()
    }

    override suspend fun saveFile(name: String, data: ByteArray) {
        mutex.withLock {
            val file = File(cacheDir, name)
            file.writeBytes(data)
            lruCache.put(name, data)
        }
    }

    override fun getFile(name: String): ByteArray? {
        return lruCache[name]
    }

    override suspend fun clear() {
        mutex.withLock {
            lruCache.evictAll()
            cacheDir.deleteRecursively()
        }
    }

    private fun loadStoredFiles() {
        cacheDir.listFiles()?.forEach { file ->
            val fileName = file.name
            val fileContent = file.readBytes()
            lruCache.put(fileName, fileContent)
        }
    }

    override fun getFilesCount(): Int {
        return lruCache.snapshot().size
    }
}
