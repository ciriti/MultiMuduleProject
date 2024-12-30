package io.github.ciriti.sdk.internal.sdk

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import io.github.ciriti.sdk.api.FileDownloaderSdk
import io.github.ciriti.sdk.config.FileDownloaderConfig
import io.github.ciriti.sdk.internal.cache.FileCache
import io.github.ciriti.sdk.internal.download.FileDownloader
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FileDownloaderSdkImplTest {

    private lateinit var config: FileDownloaderConfig
    private lateinit var fileCache: FileCache
    private lateinit var fileDownloader: FileDownloader
    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var sdkEventFlow: SdkEventFlow
    private lateinit var lifecycle: LifecycleRegistry
    private lateinit var fileDownloaderSdk: FileDownloaderSdk
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        config = mockk()
        fileCache = mockk()
        fileDownloader = mockk()
        lifecycleOwner = mockk()
        sdkEventFlow = mockk()
        lifecycle = LifecycleRegistry(lifecycleOwner)
        every { lifecycleOwner.lifecycle } returns lifecycle
//        fileDownloaderSdk = FileDownloaderSdkImpl(
//            config,
//            fileCache,
//            fileDownloader,
//            lifecycleOwner,
//            CoroutineScope(testDispatcher),
//            sdkEventFlow,
//            testDispatcher
//        )
    }

    @Test
    fun `test loadFiles`() = runTest(testDispatcher) {
        val urls = listOf("http://example.com/file1", "http://example.com/file2")
        coEvery { fileDownloader.downloadFile(any()) } returns Result.success(ByteArray(0))

        fileDownloaderSdk.loadFiles(urls)

        coVerify(exactly = 1) { fileDownloader.downloadFile("http://example.com/file1") }
        coVerify(exactly = 1) { fileDownloader.downloadFile("http://example.com/file2") }
    }
}
