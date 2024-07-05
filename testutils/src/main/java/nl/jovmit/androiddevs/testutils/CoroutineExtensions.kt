package nl.jovmit.androiddevs.testutils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch

fun <T> CoroutineScope.collectStateFlow(
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