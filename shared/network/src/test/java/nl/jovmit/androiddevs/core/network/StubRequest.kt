package nl.jovmit.androiddevs.core.network

import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

class StubRequest(
    private val code: Int
) : Interceptor.Chain {

    private val request = Request.Builder()
        .url("https://dummy.url/")
        .build()

    override fun call(): Call {
        TODO("Not yet implemented")
    }

    override fun connectTimeoutMillis(): Int {
        TODO("Not yet implemented")
    }

    override fun connection(): Connection? {
        TODO("Not yet implemented")
    }

    override fun proceed(request: Request): Response {
        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_2)
            .code(code)
            .message("")
            .build()
    }

    override fun readTimeoutMillis(): Int {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        return request
    }

    override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        TODO("Not yet implemented")
    }

    override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        TODO("Not yet implemented")
    }

    override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        TODO("Not yet implemented")
    }

    override fun writeTimeoutMillis(): Int {
        TODO("Not yet implemented")
    }
}