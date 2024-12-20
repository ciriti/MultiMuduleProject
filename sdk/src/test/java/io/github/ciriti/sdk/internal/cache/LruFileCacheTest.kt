package io.github.ciriti.sdk.internal.cache

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

class LruFileCacheTest {

    private lateinit var cacheDir: File
    private lateinit var fileCache: LruFileCache

    @Before
    fun setUp() {
        cacheDir = File("test_cache")
        fileCache = LruFileCache(cacheDir, 50)
    }

    @Test
    fun `test Save And Get File`() = runBlocking {
        val fileName = "testFile"
        val fileData = "testData".toByteArray()

        fileCache.saveFile(fileName, fileData)
        val retrievedData = fileCache.getFile(fileName)

        assertNotNull(retrievedData)
        assertArrayEquals(fileData, retrievedData)
    }

    @Test
    fun `test Clear Cache`() = runBlocking {
        val fileName = "testFile"
        val fileData = "testData".toByteArray()

        fileCache.saveFile(fileName, fileData)
        fileCache.clear()
        val retrievedData = fileCache.getFile(fileName)

        assertNull(retrievedData)
    }

    @Test
    fun `test eviction`() = runBlocking {
        val fileName = "testFile"
        val fileData = ByteArray(1024)

        fileCache = LruFileCache(cacheDir, 2 * 1024)

        repeat(3) {
            fileCache.saveFile(fileName + it, fileData)
        }

        assertEquals(2, fileCache.getFilesCount())
    }
}
