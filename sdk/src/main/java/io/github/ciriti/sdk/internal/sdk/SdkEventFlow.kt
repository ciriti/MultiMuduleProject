package io.github.ciriti.sdk.internal.sdk

import io.github.ciriti.sdk.api.SdkEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal interface SdkEventFlow {
    val events: Flow<SdkEvent>
    suspend fun emitEvent(event: SdkEvent)

    companion object
}

internal fun SdkEventFlow.Companion.create(): SdkEventFlow = SdkEventFlowImpl()

private class SdkEventFlowImpl : SdkEventFlow {

    private val _events = MutableSharedFlow<SdkEvent>()

    override val events: Flow<SdkEvent>
        get() = _events

    override suspend fun emitEvent(event: SdkEvent) {
        _events.emit(event)
    }
}
