package nl.jovmit.androiddevs

import androidx.navigation3.runtime.NavKey

internal fun MutableList<NavKey>.replaceWith(route: NavKey) {
    clear()
    add(route)
}

internal fun MutableList<NavKey>.popDestination() {
    if (size > 1) {
        removeAt(lastIndex)
    }
}
