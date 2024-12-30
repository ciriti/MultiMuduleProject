package io.github.ciriti.sdk.internal.sdk

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

internal interface LifecycleHandler : LifecycleEventObserver {
    // this is the Fluent Interface pattern
    fun onDispose(block: () -> Unit) : LifecycleHandler

    companion object
}

internal fun LifecycleHandler.Companion.create(lifecycleOwner: LifecycleOwner): LifecycleHandler {
    return LifecycleHandlerImpl(lifecycleOwner)
}

internal class LifecycleHandlerImpl(
    private val lifecycleOwner: LifecycleOwner,
) : LifecycleHandler {

    private var onDispose: (() -> Unit)? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        println("SDK lifecycleHandler registered")
    }

    override fun onDispose(block: () -> Unit) : LifecycleHandler = apply { onDispose = block }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            println("SDK lifecycleHandler disposed")
            requireNotNull(onDispose) { "onDispose must be set" }
            onDispose!!.invoke()
            dispose()
        }
    }

    private fun dispose() {
        lifecycleOwner.lifecycle.removeObserver(this)
    }

}
