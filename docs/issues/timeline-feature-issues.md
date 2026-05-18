# Timeline Feature Issues

Published to GitHub as issues #7 through #14.

## 1. [Persist the Session User after authentication](https://github.com/mitrejcevski/android-devs-app/issues/7)

## What to build

Persist the Session User after authentication so post mutations can be attributed to the current Session and guarded when no Session is active. Logout should end the Session without resetting the shared Timeline state.

## Acceptance criteria

- [ ] Successful login and sign-up establish a Session User that can be read by timeline-related features.
- [ ] Logout clears the Session User.
- [ ] The Session model remains in memory for now and is shaped so it can later be backed by persistent storage.
- [ ] Tests cover establishing, reading, and clearing the Session User.

## Blocked by

None - can start immediately

## 2. [Show seed Posts on the global Timeline](https://github.com/mitrejcevski/android-devs-app/issues/8)

## What to build

Show realistic seed Posts on the global Timeline from a shared in-memory Timeline source. The Timeline presents Posts newest first and uses the same shared domain contract that Post Details and Post Composer will use.

## Acceptance criteria

- [ ] Signed-in Users see the same global Timeline.
- [ ] Seed Posts appear newest first.
- [ ] Timeline rows show author, title, body summary, first image or placeholder, comment count, and total Reaction count.
- [ ] Tests cover seed ordering and shared in-memory Timeline behavior.

## Blocked by

- #7

## 3. [Model Timeline loading, offline, and unavailable states](https://github.com/mitrejcevski/android-devs-app/issues/9)

## What to build

Represent loading, Offline Timeline, and Unavailable Timeline states through the Timeline result-state stream and post operation results. The in-memory source can simulate offline and unavailable states.

## Acceptance criteria

- [ ] Timeline can emit loading, success, offline, and unavailable states.
- [ ] Offline and unavailable states affect loading, adding, and removing Posts.
- [ ] Failure priority is offline or unavailable, then missing Session, then missing Post, then wrong Authoring User.
- [ ] Tests cover each state and failure priority.

## Blocked by

- #8

## 4. [Show read-only Reaction summary](https://github.com/mitrejcevski/android-devs-app/issues/10)

## What to build

Show total Reaction count on each Timeline Post and open a read-only bottom sheet with per-type Reaction counts when the total is tapped.

## Acceptance criteria

- [ ] Timeline shows only total Reaction count by default.
- [ ] Tapping the Reaction count opens a read-only per-type summary.
- [ ] The first implementation does not allow adding, removing, or changing Reactions.
- [ ] Tests or previews cover Posts with and without Reactions.

## Blocked by

- #8

## 5. [Load Post Details from the shared Timeline source](https://github.com/mitrejcevski/android-devs-app/issues/11)

## What to build

Load Post Details with a one-shot lookup from the shared Timeline source. Details shows the full selected Post and reports a missing-post state instead of fabricating placeholder content.

## Acceptance criteria

- [ ] Post Details loads a Post by id from the shared Timeline source.
- [ ] Details shows full body, all Images, full Comments, and Reaction information.
- [ ] Missing Posts show a missing-post state.
- [ ] Details loading is one-shot for now, with room for pull-to-refresh later.

## Blocked by

- #8

## 6. [Compose a new Post from a separate Post Composer](https://github.com/mitrejcevski/android-devs-app/issues/12)

## What to build

Add a separate Post Composer feature destination launched from the Timeline FAB. The first composer accepts title and body only, validates both as non-blank, adds the Post for the Session User, and navigates back to Timeline.

## Acceptance criteria

- [ ] Timeline provides a FAB that navigates to Post Composer.
- [ ] Composer accepts title and body only.
- [ ] Blank title or body is rejected.
- [ ] Successful creation returns to Timeline, where the new Post appears at the top.

## Blocked by

- #7
- #8

## 7. [Remove own Posts from Timeline and Post Details](https://github.com/mitrejcevski/android-devs-app/issues/13)

## What to build

Allow Users to remove Posts they authored from both Timeline and Post Details. Removal deletes the whole Post and its associated Comments, Reactions, Tags, and Images.

## Acceptance criteria

- [ ] Delete is only offered for Posts authored by the Session User.
- [ ] Removing a Post updates the Timeline stream.
- [ ] Removing from Post Details returns to Timeline.
- [ ] Domain results distinguish not signed in, post not found, and not author.

## Blocked by

- #7
- #8
- #11

## 8. [Show Empty Timeline invitation](https://github.com/mitrejcevski/android-devs-app/issues/14)

## What to build

Show an Empty Timeline state when the Timeline result is successful but has no Posts. The empty state invites the Session User to add a Post.

## Acceptance criteria

- [ ] Empty success lists are treated as an empty UI state, not a domain failure.
- [ ] Empty Timeline invites the Session User to add a Post.
- [ ] The add action opens Post Composer.
- [ ] Tests or previews cover the empty state.

## Blocked by

- #12
- #13
