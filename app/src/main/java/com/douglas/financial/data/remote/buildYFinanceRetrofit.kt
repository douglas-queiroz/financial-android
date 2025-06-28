package com.douglas.financial.data.remote

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.douglas.financial.BuildConfig.YFINANCE_KEY

fun buildYFinanceRetrofit(): Retrofit {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(AuthInterceptor(YFINANCE_KEY))
        .build()

    return Retrofit.Builder()
        .baseUrl("https://apidojo-yahoo-finance-v1.p.rapidapi.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
}

class AuthInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("x-rapidapi-key", authToken)
            .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
            .build()
        return chain.proceed(newRequest)
    }
}