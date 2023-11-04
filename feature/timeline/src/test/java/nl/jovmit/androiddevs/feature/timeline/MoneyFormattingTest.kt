package nl.jovmit.androiddevs.feature.timeline

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MoneyFormattingTest {

    @Test
    fun testFlow() = runBlocking {
        val result = flowOf(1, 2, 3, 4, 5).limit(3).toList()

        assertThat(listOf(1, 2, 3)).isEqualTo(result)
    }

    fun <T> Flow<T>.limit(limit: Int): Flow<T> {
        var count = 0
        return flow {
            this@limit.collect { item ->
                if (count < limit) {
                    count++
                    emit(item)
                }
            }
        }
    }

    @Test
    fun testFlowTransform() = runBlocking {
        val result = flowOf(1, 2, 3, 4, 5, 6, 7).limited(3).toList()
        assertThat(listOf(1, 2, 3)).isEqualTo(result)
    }

    fun <T> Flow<T>.limited(limit: Int): Flow<T> {
        var count = 0
        return this.transform { item ->
            if (count < limit) {
                count++
                emit(item)
            }
            return@transform
        }
    }
}