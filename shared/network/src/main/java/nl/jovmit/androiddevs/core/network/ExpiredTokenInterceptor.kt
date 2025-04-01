package nl.jovmit.androiddevs.core.network

import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class ExpiredTokenInterceptor(
    private val logoutSignal: LogoutSignal,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            logoutSignal.onLoggedOut()
        }
        return response
    }
}