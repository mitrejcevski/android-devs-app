package nl.jovmit.androiddevs.domain.auth

import kotlinx.coroutines.flow.StateFlow
import nl.jovmit.androiddevs.domain.auth.data.User

interface UserSession {

  val sessionUser: StateFlow<User?>

  fun setSessionUser(user: User)

  fun clear()
}
