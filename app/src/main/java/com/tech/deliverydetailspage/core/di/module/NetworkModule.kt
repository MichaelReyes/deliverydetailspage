package com.tech.deliverydetailspage.core.di.module

import androidx.annotation.NonNull
import com.tech.deliverydetailspage.BuildConfig
import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

const val BACKEND_BASE_URL = "https://mock-api-mobile.dev.lalamove.com/"

@Module
object NetworkModule {

    internal val loggingInterceptor: HttpLoggingInterceptor
        @Provides
        get() = HttpLoggingInterceptor().setLevel(
            if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )

    @Provides
    @Named("Backend")
    fun getBackendApiEndpoint(): String {
        return BACKEND_BASE_URL
    }

    @Provides
    @Named("Backend")
    @Singleton
    internal fun provideApiRetrofit(@Named("Backend") @NonNull baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    internal fun getHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        builder.addInterceptor(interceptor)

        return builder.build()
    }
}
