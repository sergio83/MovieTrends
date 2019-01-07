package com.billboard.movies.data.remote.api.utils

import com.billboard.movies.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class ServiceGenerator {

    companion object {

        private val mLogging = HttpLoggingInterceptor()
        private var mGsonFactory: GsonConverterFactory? = null
        private val mHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        private var mBuilder: Retrofit.Builder? = null

        fun <S> createService(serviceClass: Class<S>): S {

            mHttpClient.let {
                if (BuildConfig.DEBUG) {
                    mLogging.level = HttpLoggingInterceptor.Level.BODY
                    mHttpClient.addInterceptor(mLogging)
                    mHttpClient.addNetworkInterceptor(StethoInterceptor())
                }

                mHttpClient.addInterceptor { chain ->

                    //val language = AppPreferences.language ?: "en"
                    val language = Locale.getDefault().language
                    val url = chain.request().url().newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .addQueryParameter("language", language)
                        .build()

                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-type", "application/json")
                        .url(url)
                        .build()
                    chain.proceed(request)
                }
            }

            if (mGsonFactory == null) {
                mGsonFactory = GsonConverterFactory.create()
            }

            mBuilder = Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(mGsonFactory!!)


            val client = mHttpClient.build()
            val retrofit = mBuilder!!.client(client).build()
            return retrofit.create(serviceClass)
        }
    }


}