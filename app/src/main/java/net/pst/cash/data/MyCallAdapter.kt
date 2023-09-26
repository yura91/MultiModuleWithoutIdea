package net.pst.cash.data

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class MyCallAdapter(private val responseType: Type) :
    CallAdapter<GoogleSignInResponse, MyCall<GoogleSignInResponse>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<GoogleSignInResponse>): MyCall<GoogleSignInResponse> =
        MyCall(call)

    class Factory : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ): CallAdapter<*, *>? {
            val rawType = getRawType(returnType)

            if (rawType != MyCall::class.java) {
                return null
            }

            if (returnType !is ParameterizedType) {
                throw IllegalStateException("MyCall must have generic type (e.g., MyCall<ResponseBody>)")
            }

            val responseType = getParameterUpperBound(0, returnType)

            return MyCallAdapter(responseType)
        }
    }
}

