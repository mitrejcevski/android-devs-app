# Android Devs App

The Android Devs App connects Android developers through shared posts and future interest-based discovery.

## Language

**Timeline**:
A global stream of **Posts** visible to every signed-in **User**.
_Avoid_: Feed, home feed

**Offline Timeline**:
A **Timeline** state where **Posts** cannot be reached because the app has no connection.
_Avoid_: Network error

**Unavailable Timeline**:
A **Timeline** state where **Posts** cannot be reached because the service is not available.
_Avoid_: Backend error, server error

**Post**:
A piece of content shared with Android developers by one **User**.
_Avoid_: Item, timeline item

**Post Details**:
The view of a single **Post** in full.
_Avoid_: Details page, post page

**Post Composer**:
The screen where a **User** creates a new **Post**.
_Avoid_: Add post form, create post page

**Authoring User**:
The **User** who created a **Post** or **Comment**.
_Avoid_: Owner, creator

**Session**:
The app's current signed-in state for one **User**.
_Avoid_: Login state, auth state

**Session User**:
The **User** represented by the current **Session**.
_Avoid_: Current user, logged-in user

**Comment**:
A text response added to a **Post**.
_Avoid_: Reply

**Reaction**:
A lightweight response from a **User** to a **Post**, chosen from a fixed set of reaction types.
_Avoid_: Like

**Reaction Type**:
One of the allowed ways a **User** can react to a **Post**.
_Avoid_: Emoji, reaction kind

**Tag**:
A topic label associated with a **Post**.
_Avoid_: Category

**Interest**:
A topic a **User** cares about.
_Avoid_: Preference

**Image**:
Visual media attached to a **Post**.
_Avoid_: Picture, photo

## Relationships

- A **Timeline** contains many **Posts**
- A **Timeline** can be offline or unavailable
- Offline or unavailable **Timeline** states affect all post operations
- Post operation failures are prioritized as offline or unavailable, then missing **Session**, then missing **Post**, then wrong **Authoring User**
- A **Post** belongs to exactly one **Authoring User**
- A **Session** has exactly one **Session User**
- New **Posts** are authored by the **Session User**
- A **Post** can have many **Comments**
- A **Post** can have many **Reactions**
- A **Post** can have many **Tags**
- A **Post** can have many **Images**
- A **User** can have many **Interests**
- A future personalized **Timeline** matches **Posts** to **Users** by comparing **Tags** with **Interests**
- A **Comment** belongs to exactly one **Post**
- A **Comment** is authored by exactly one **Authoring User**
- A **User** can have at most one **Reaction** per **Post**
- Only the **Authoring User** of a **Post** can remove that **Post**
- A **User** is only offered post removal for **Posts** they authored
- A **User** can remove their own **Post** from the **Timeline** or **Post Details**
- Removing a **Post** removes its **Comments**, **Reactions**, **Tags**, and **Images**
- After removing a **Post** from **Post Details**, the **User** returns to the **Timeline**
- Every signed-in **User** sees the same **Timeline** until interest-based personalization is introduced
- The **Timeline** presents **Posts** newest first
- A **Timeline** with no **Posts** is empty, not failed
- The **Timeline** shows a scannable summary of each **Post**
- **Post Details** shows the full **Post**, including all **Images** and **Comments**
- **Post Details** shows one **Post** selected from the **Timeline**
- The **Timeline** shows total **Reaction** count and reveals a read-only per-type **Reaction** summary on demand
- The **Timeline** shows **Comment** count only
- **Tags** are hidden from users in the first UI
- The first **Timeline** experience allows **Users** to add and remove **Posts**, but only displays existing **Comments** and **Reactions**
- The **Timeline** provides access to the **Post Composer**
- The first **Timeline** experience starts with seed **Posts** that reset when the app restarts
- Seed **Posts** have seed **Users** as their **Authoring Users**
- Seed **Posts** represent realistic Android developer community content
- **Posts** added or removed during one app run are visible to all signed-in **Users** during that run
- Ending a **Session** does not reset the **Timeline**
- Ending a **Session** returns the **User** to authentication
- Adding or removing **Posts** requires an active **Session**
- A new **Post** requires a non-blank title and non-blank body
- A new **Post** can be created without **Images** or **Tags**
- A new **Post** starts without **Comments** or **Reactions**
- The first add-post UI only accepts title and body
- Seed **Posts** can include **Images**, **Tags**, **Comments**, and **Reactions**
- A **Post** remains valid when an **Image** cannot be displayed
- After adding a **Post**, the **User** returns to the **Timeline**

## Example dialogue

> **Dev:** "Should the **Timeline** show different **Posts** for different **Users**?"
> **Domain expert:** "Not yet — the **Timeline** is global for now, and personalization by interests comes later."
> **Dev:** "Is a **Post** just a title and body?"
> **Domain expert:** "No — a **Post** can also carry **Comments**, **Tags**, **Images**, and reactions, though **Tags** are not visible in the first UI."
> **Dev:** "What happens if a **User** reacts with one **Reaction Type** and then chooses another?"
> **Domain expert:** "The latest **Reaction Type** replaces the previous **Reaction** for that **Post**."
> **Dev:** "If a **Post** is removed, do its **Comments** survive?"
> **Domain expert:** "No — removing a **Post** removes all of its associated content."
> **Dev:** "Can any **User** remove a **Post** from the **Timeline**?"
> **Domain expert:** "No — only the **Authoring User** can remove their own **Post**."
> **Dev:** "Where should a newly created **Post** appear?"
> **Domain expert:** "At the top of the **Timeline**, because **Posts** are shown newest first."
> **Dev:** "Why do **Tags** exist if they are hidden in the first UI?"
> **Domain expert:** "**Tags** describe **Posts** now and will later support matching **Posts** to **Users** through **Interests**."
> **Dev:** "Should the **Timeline** show every **Comment** and **Image**?"
> **Domain expert:** "No — the **Timeline** is for scanning; **Post Details** shows the full **Post**."
> **Dev:** "Can **Users** add **Comments** or change **Reactions** in the first **Timeline** experience?"
> **Domain expert:** "No — the first experience only displays existing **Comments** and **Reactions** while allowing **Users** to add and remove **Posts**."
> **Dev:** "If Alice adds a **Post** and Bob signs in during the same app run, does Bob see it?"
> **Domain expert:** "Yes — first-stage **Timeline** changes are shared during one app run."
> **Dev:** "What if Bob tries to remove Alice's **Post** directly?"
> **Domain expert:** "That is rejected because Bob is not the **Authoring User**; attempting to remove a missing **Post** is a separate case."
> **Dev:** "Can Alice remove seed **Posts**?"
> **Domain expert:** "No — seed **Posts** have their own **Authoring Users**."
> **Dev:** "Can Alice create a **Post** with only a title and body?"
> **Domain expert:** "Yes — **Images** and **Tags** are optional, and new **Posts** start without **Comments** or **Reactions**."
> **Dev:** "Can a **Post** be created with a blank title?"
> **Domain expert:** "No — a new **Post** requires non-blank title and body."
> **Dev:** "Can the first add-post UI add **Images** or **Tags**?"
> **Domain expert:** "No — those can appear in seed **Posts**, but the first add-post UI only accepts title and body."
> **Dev:** "What happens if an **Image** cannot be displayed?"
> **Domain expert:** "The **Post** still appears, and the UI shows an image placeholder."
> **Dev:** "Can Alice remove her own **Post** from **Post Details**?"
> **Domain expert:** "Yes — removal is available anywhere Alice is viewing her own **Post**."
> **Dev:** "What happens after Alice removes a **Post** from **Post Details**?"
> **Domain expert:** "Alice returns to the **Timeline**, where the removed **Post** no longer appears."
> **Dev:** "What happens after Alice adds a **Post**?"
> **Domain expert:** "Alice returns to the **Timeline**, where the new **Post** appears at the top."
> **Dev:** "Who is the **Authoring User** when someone creates a **Post**?"
> **Domain expert:** "The **Authoring User** is the **Session User**."
> **Dev:** "Does ending Alice's **Session** remove **Posts** she added?"
> **Domain expert:** "No — ending a **Session** does not reset the shared **Timeline**."
> **Dev:** "Can a stale screen add a **Post** after the **Session** ends?"
> **Domain expert:** "No — adding or removing **Posts** requires an active **Session**."
> **Dev:** "Should **Post Details** invent content from a post id?"
> **Domain expert:** "No — **Post Details** shows the selected **Post** from the **Timeline**."
> **Dev:** "What if every **Post** is removed from the **Timeline**?"
> **Domain expert:** "The **Timeline** is empty and invites the **Session User** to add a **Post**."
> **Dev:** "Should the **Timeline** show every **Reaction Type** immediately?"
> **Domain expert:** "No — show total **Reaction** count first, with read-only per-type summary available on demand."
> **Dev:** "Should the **Timeline** show comment previews?"
> **Domain expert:** "No — it shows **Comment** count only; full **Comments** are shown in **Post Details**."
> **Dev:** "Where does Alice create a **Post**?"
> **Domain expert:** "Alice opens the **Post Composer** from the **Timeline**."
> **Dev:** "What should seed **Posts** feel like?"
> **Domain expert:** "They should read like realistic Android developer community content."
> **Dev:** "Can the first in-memory **Timeline** still show loading?"
> **Domain expert:** "Yes — loading is a presentation state that prepares the experience for later data sources."
> **Dev:** "Can the first **Timeline** experience show offline or unavailable states?"
> **Domain expert:** "Yes — **Offline Timeline** and **Unavailable Timeline** are valid states even before a remote source exists."
> **Dev:** "Can Alice add or remove **Posts** while the **Timeline** is offline?"
> **Domain expert:** "No — offline and unavailable states affect all post operations."
> **Dev:** "If Bob tries to remove Alice's **Post** while the **Timeline** is offline, which failure applies?"
> **Domain expert:** "The offline state applies first because post operations cannot proceed while the **Timeline** is offline."

## Flagged ambiguities

- "timeline" could mean a global stream or a personalized feed — resolved: **Timeline** is global for this stage.
- "post" could mean a simple list item or a richer social object — resolved: **Post** is the main shared content object.
- "like" could mean any reaction or one specific reaction — resolved: **Reaction** is the general concept, and "like" is one **Reaction Type**.
- "context" was used to mean a post's associated content — resolved: use **Comments**, **Reactions**, **Tags**, and **Images** when naming those concepts.
- "tag" and "interest" are related but distinct — resolved: **Tags** describe **Posts**; **Interests** describe **Users**.
- "post preview" or "timeline item" should not become domain concepts — resolved: the **Timeline** shows a summarized presentation of a **Post**.
- "current user" and "logged-in user" should be expressed as **Session User** when referring to the user represented by the current **Session**.
- "offline" and "unavailable" should not be collapsed — resolved: **Offline Timeline** means connection is absent; **Unavailable Timeline** means the service cannot provide **Posts**.
