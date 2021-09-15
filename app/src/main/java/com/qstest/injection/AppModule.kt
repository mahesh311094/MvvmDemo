package com.qstest.injection

import com.qstest.utils.CoroutineDispatchersProvider
import okhttp3.OkHttpClient
import org.koin.dsl.module

val appModule = module {

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }

    single { provideHttpClient() }
    single { CoroutineDispatchersProvider() }
}