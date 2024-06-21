package nl.jovmit.androiddevs.core.netowrk

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.Interceptor
import okhttp3.Response
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class AuthTokenExpiryTest {

    private val logoutSignal = LogoutSignal()

    @Test
    fun callResponseWith401() {
        val request = StubRequest(401)
        val interceptor = ExpiredTokenInterceptor(logoutSignal)

        interceptor.intercept(request)

        assertThat(logoutSignal.forcedLogout.value).isEqualTo(Unit)
    }

    @Test
    fun callResponseWith200() {
        val request = StubRequest(200)
        val interceptor = ExpiredTokenInterceptor(logoutSignal)

        interceptor.intercept(request)

        assertThat(logoutSignal.forcedLogout.value).isNull()
    }

    class LogoutSignal {

        private val _forcedLogout = MutableStateFlow<Unit?>(null)
        val forcedLogout: StateFlow<Unit?> = _forcedLogout.asStateFlow()

        fun onLoggedOut() {
            _forcedLogout.update { Unit }
        }
    }

    class ExpiredTokenInterceptor(
        private val logoutSignal: LogoutSignal,
    ) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            if (response.code == HTTP_UNAUTHORIZED) {
                logoutSignal.onLoggedOut()
            }
            return response
        }
    }
}