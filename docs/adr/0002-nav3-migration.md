# ADR 0002: Migrate Compose Navigation to Navigation 3

## Status

Accepted

## Context

The app currently uses Navigation 2 Compose APIs from the `app` module while feature modules expose navigation registration and navigation helper functions. The navigation surface is small: one linear back stack, Compose destinations, no deep links, no bottom navigation, no nested graphs, and no dialog destinations.

Navigation 3 is the Compose-first AndroidX navigation API where the app owns a list-like back stack of typed keys and renders entries through `NavDisplay`.

## Decision

Migrate from Navigation 2 to Navigation 3 in one atomic change.

Use the latest stable Navigation 3 release line available during the migration, `androidx.navigation3:navigation3-*:1.1.1`.

Keep destination key ownership in feature modules. Each feature module owns the typed navigation key for its destination and exposes an entry-provider function. The `app` module owns the back stack and cross-feature navigation policies.

Expose destination keys as feature module API. Do not expose `navigateToX` helpers from feature modules after the migration; the `app` module mutates the back stack explicitly.

Use Navigation 3 saveable back stack support. All navigation keys implement `NavKey` and are annotated with Kotlin serialization annotations so the back stack can survive configuration changes and process death.

Pass route identity from the Nav3 key into the destination composable. For post details, the entry key carries the post id, the screen receives that id, and a `LaunchedEffect` calls the ViewModel loading function with the id. Do not recreate Navigation 2 route argument reads through `SavedStateHandle`.

Cover app-level stack mutation policy with focused tests. Navigation flows that clear or pop the stack should be tested outside feature UI rendering where possible.

## Consequences

- Navigation 2 can be removed after the migration instead of coexisting with Navigation 3.
- Feature modules keep their existing responsibility for rendering their own destinations.
- The `app` module remains the place where cross-feature flows are coordinated.
- Navigation behavior should preserve the current process death and configuration change expectations.
