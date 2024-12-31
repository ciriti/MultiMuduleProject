package io.github.ciriti.sdk.messagequeue

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// 1. Define a Task interface
interface Task {
    suspend fun execute()
}

interface TaskQueue {
    fun add(task: Task): Job
    suspend fun shutdown()
}

internal class TaskQueueImpl(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) : TaskQueue {

    private val taskChannel by lazy { Channel<Task>() }
    private val activeTasks = mutableListOf<Job>()
    private val mutex = Mutex()

    init {
        // Start a coroutine to process tasks sequentially
        scope.launch {
            for (task in taskChannel) {
                try {
                    val job = launch {
                        task.execute()
                    }
                    mutex.withLock { activeTasks.add(job) }
                    job.join()
//                    mutex.withLock { activeTasks.remove(job) }
                } catch (e: Exception) {
                    // Handle task execution errors
                    println("Task execution failed: ${e.message}")
                }
            }
        }
    }

    override fun add(task: Task): Job {
        return scope.launch {
            taskChannel.send(task)
        }
    }

    override suspend fun shutdown() {
//        runBlocking {
            mutex.withLock {
                activeTasks.joinAll()
                activeTasks.clear()
            }
            println("shutdown")
            scope.cancel()
            taskChannel.cancel()
//        }

    }
}

class ExampleTask(
    private val name: String,
    private val delay: Long
) : Task {
    override suspend fun execute() {
        println("Executing task: $name")
        delay(delay) // Simulate task work
        println("Task $name completed")
    }
}

fun main() = runBlocking {
    val taskQueue = TaskQueueImpl()

    (1..5).map { i ->
        taskQueue.add(ExampleTask("Task $i", (6-i) * 1000L))
    }.joinAll()

    taskQueue.shutdown()
}
