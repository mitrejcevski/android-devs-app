package nl.jovmit.androiddevs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import nl.jovmit.androiddevs.feature.login.LoginRoute
import nl.jovmit.androiddevs.feature.login.loginEntry
import nl.jovmit.androiddevs.feature.postcomposer.PostComposerRoute
import nl.jovmit.androiddevs.feature.postcomposer.postComposerEntry
import nl.jovmit.androiddevs.feature.postdetails.PostDetailsRoute
import nl.jovmit.androiddevs.feature.postdetails.postDetailsEntry
import nl.jovmit.androiddevs.feature.signup.SignUpRoute
import nl.jovmit.androiddevs.feature.signup.signUpEntry
import nl.jovmit.androiddevs.feature.timeline.TimelineRoute
import nl.jovmit.androiddevs.feature.timeline.timelineEntry
import nl.jovmit.androiddevs.feature.welcome.WelcomeRoute
import nl.jovmit.androiddevs.feature.welcome.welcomeEntry

@Composable
fun MainApp(
    mainViewModel: MainAppViewModel = hiltViewModel()
) {

    val backStack = rememberNavBackStack(WelcomeRoute)

    LaunchedEffect(Unit) {
        mainViewModel.observeLoggedOut()
        mainViewModel.loggedOut.collect {
            backStack.replaceWith(LoginRoute)
        }
    }

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        onBack = { backStack.popDestination() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween()
            ) togetherWith scaleOut(targetScale = .9f, animationSpec = tween())
        },
        popTransitionSpec = {
            scaleIn(initialScale = .9f, animationSpec = tween()) togetherWith slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween()
            )
        },
        entryProvider = entryProvider {
            welcomeEntry(
                onLogin = { backStack.add(LoginRoute) },
                onSignUp = { backStack.add(SignUpRoute) }
            )

            signUpEntry(
                onNavigateUp = { backStack.popDestination() }
            )

            loginEntry(
                onLoggedIn = { backStack.replaceWith(TimelineRoute) },
                onNavigateUp = { backStack.popDestination() }
            )

            timelineEntry(
                onItemClicked = { itemId ->
                    backStack.add(PostDetailsRoute(itemId))
                },
                onAddPost = { backStack.add(PostComposerRoute) }
            )

            postDetailsEntry(
                onNavigateUp = { backStack.popDestination() },
                onPostRemoved = { backStack.replaceWith(TimelineRoute) }
            )

            postComposerEntry(
                onNavigateUp = { backStack.popDestination() },
                onPostAdded = { backStack.popDestination() }
            )
        }
    )
}
