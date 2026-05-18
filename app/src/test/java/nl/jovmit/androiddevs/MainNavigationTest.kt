package nl.jovmit.androiddevs

import com.google.common.truth.Truth.assertThat
import androidx.navigation3.runtime.NavKey
import nl.jovmit.androiddevs.feature.login.LoginRoute
import nl.jovmit.androiddevs.feature.postcomposer.PostComposerRoute
import nl.jovmit.androiddevs.feature.postdetails.PostDetailsRoute
import nl.jovmit.androiddevs.feature.timeline.TimelineRoute
import nl.jovmit.androiddevs.feature.welcome.WelcomeRoute
import org.junit.jupiter.api.Test

class MainNavigationTest {

    @Test
    fun successfulLoginClearsBackStackToTimeline() {
        val backStack = mutableListOf<NavKey>(WelcomeRoute, LoginRoute)

        backStack.replaceWith(TimelineRoute)

        assertThat(backStack).containsExactly(TimelineRoute).inOrder()
    }

    @Test
    fun forcedLogoutClearsBackStackToLogin() {
        val backStack = mutableListOf<NavKey>(
            TimelineRoute,
            PostDetailsRoute("post-id"),
            PostComposerRoute
        )

        backStack.replaceWith(LoginRoute)

        assertThat(backStack).containsExactly(LoginRoute).inOrder()
    }

    @Test
    fun postRemovalClearsBackStackToTimeline() {
        val backStack = mutableListOf<NavKey>(TimelineRoute, PostDetailsRoute("post-id"))

        backStack.replaceWith(TimelineRoute)

        assertThat(backStack).containsExactly(TimelineRoute).inOrder()
    }

    @Test
    fun navigateUpPopsOneDestination() {
        val backStack = mutableListOf<NavKey>(TimelineRoute, PostDetailsRoute("post-id"))

        backStack.popDestination()

        assertThat(backStack).containsExactly(TimelineRoute).inOrder()
    }

    @Test
    fun navigateUpKeepsRootDestination() {
        val backStack = mutableListOf<NavKey>(WelcomeRoute)

        backStack.popDestination()

        assertThat(backStack).containsExactly(WelcomeRoute).inOrder()
    }
}
