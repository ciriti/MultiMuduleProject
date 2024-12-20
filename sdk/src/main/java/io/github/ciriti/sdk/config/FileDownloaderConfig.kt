package io.github.ciriti.sdk.config

/*
    An enum (enumeration) is used to define a set of named constants. 
    It is ideal for representing a fixed set of options or categories 
    that a variable can hold. In this case, FileDownloaderOption is 
    used to represent different configuration options or modes for the 
    file downloader. Benefit:
    - Type Safety
    - Readability
    - Maintainability: Adding or removing options is straightforward and centralized.

*/
enum class FileDownloaderOption {
    Option1,
    Option2,
    Option3
}

/*
    A data class in Kotlin is used to hold data. It is particularly useful 
    for classes that are meant to store state and have little to no behavior 
    beyond holding data. Benefit:
    - Convenience: Provides a concise way to define classes that are primarily 
    used for holding data.
    - Data Equality: Provides built-in implementations of equals(), hashCode(), 
    and toString() methods.
*/
data class FileDownloaderConfig(
    val options: List<FileDownloaderOption>,
    val fileUrls: List<String>,
    val maxCacheSize: Int
) 
