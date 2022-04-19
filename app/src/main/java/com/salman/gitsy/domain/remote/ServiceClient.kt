package com.salman.gitsy.domain.remote

import com.salman.gitsy.BuildConfig
import com.salman.gitsy.utility.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Salman Saifi on 17/04/22.
 * Email - zach.salmansaifi@gmail.com
 */

object ServiceClient {

    operator fun invoke(): GitsyApis {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }


        val headerInterceptorChain = Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder().getHeader()
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            addInterceptor(headerInterceptorChain)
            addInterceptor(loggingInterceptor)
        }.build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GitsyApis::class.java)
    }

    private fun Request.Builder.getHeader(): Request.Builder {
        header("Authorization", "e3de16779a65d135a0bf3ea630bf11dc5207fde1")
        header("Content-Type", "application/json")
        return this
    }

}
