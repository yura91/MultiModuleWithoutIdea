package net.pst.cash.data

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class MyCall<T>(private val delegate: Call<T>) {

    fun cancel() {
        delegate.cancel()
    }

    fun enqueue(callback: Callback<T>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(call, HttpException(response))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }

    fun clone(): MyCall<T> {
        return MyCall(delegate.clone())
    }

    fun execute(): Response<T> {
        return delegate.execute()
    }

    fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    fun request(): Request {
        return delegate.request()
    }
}
