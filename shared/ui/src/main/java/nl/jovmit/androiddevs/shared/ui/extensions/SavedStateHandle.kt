package nl.jovmit.androiddevs.shared.ui.extensions

import androidx.lifecycle.SavedStateHandle

inline fun <T> SavedStateHandle.update(
    key: String,
    block: (T) -> T
) {
    requireNotNull(get<T>(key)).let(block).also { set(key, it) }
}