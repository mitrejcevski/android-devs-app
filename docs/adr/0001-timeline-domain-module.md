# ADR 0001: Create a Timeline Domain Module

## Status

Accepted

## Context

The timeline feature lists posts, while post details displays one selected post. Both features need the same post model, post lookup behavior, and post mutation rules. The project already keeps authentication contracts in a dedicated `domain:auth` module.

## Decision

Create a dedicated `domain:timeline` module that owns shared post concepts and operations used by both `feature:timeline` and `feature:postdetails`.

Name the primary domain contract `TimelineRepository`, because it represents the timeline experience rather than generic post storage.

Model expected timeline outcomes with sealed result types, following the existing auth domain style instead of throwing for normal domain failures.

Expose the timeline as a reactive result-state stream so loading, success, offline, unavailable, additions, and removals are reflected automatically. Keep mutations as explicit operations that return sealed results. Treat an empty success list as a UI state rather than a distinct domain result.

Load post details with an explicit one-shot operation for now. A details screen can reload later through pull-to-refresh rather than observing a selected post continuously.

Represent comments and reactions as full domain objects rather than counts. Timeline UI can derive summaries, while post details can display the full comment list and reaction information.

Add a separate `feature:postcomposer` module with a general navigation destination. The first composer accepts title and body only, is launched from the timeline initially, and returns to the timeline after a post is added.

After successful post creation, the composer only navigates back. The timeline observes the shared result-state stream and updates from the domain source.

## Consequences

- Timeline and post details can depend on the same post contract instead of duplicating models.
- Post ownership, removal, empty, offline, unavailable, and not-found outcomes can be tested outside the UI features.
- The domain module can start with an in-memory implementation and later gain a remote or persistent implementation without moving feature-level code.
