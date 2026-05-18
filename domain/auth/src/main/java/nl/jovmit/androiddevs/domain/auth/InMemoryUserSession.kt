package nl.jovmit.androiddevs.domain.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import nl.jovmit.androiddevs.domain.auth.data.User
import javax.inject.Inject

class InMemoryUserSession @Inject constructor() : UserSession {

  private val _sessionUser = MutableStateFlow<User?>(null)
  override val sessionUser: StateFlow<User?> = _sessionUser

  override fun setSessionUser(user: User) {
    _sessionUser.value = user
  }

  override fun clear() {
    _sessionUser.value = null
  }
}
