package nl.jovmit.androiddevs.testutils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch

inline fun <T> CoroutineScope.collectSharedFlow(
    flow: SharedFlow<T>,
    block: () -> Unit
): T {
    val values = mutableListOf<T>()
    val job = launch(Dispatchers.Unconfined) {
        flow.toCollection(values)
    }
    block()
    job.cancel()
    return values.first()
}

inline fun <T> CoroutineScope.collectStateFlow(
    stateFlow: StateFlow<T>,
    dropInitialValue: Boolean = true,
    action: () -> Unit
): List<T> {
    val results = arrayListOf<T>()
    val collectJob = launch(Dispatchers.Unconfined) {
        stateFlow.toCollection(results)
    }
    action()
    collectJob.cancel()
    return if (dropInitialValue) results.drop(1) else results
}