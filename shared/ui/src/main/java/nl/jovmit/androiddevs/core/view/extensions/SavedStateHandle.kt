package nl.jovmit.androiddevs.core.view.extensions

import androidx.lifecycle.SavedStateHandle

inline fun <T> SavedStateHandle.update(
    key: String,
    block: (T) -> T
) {
    requireNotNull(get<T>(key)).let(block).also { set(key, it) }
}