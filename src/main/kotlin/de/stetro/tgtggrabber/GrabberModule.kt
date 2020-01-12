package de.stetro.tgtggrabber

import de.stetro.tgtggrabber.api.IFTTTRepository
import de.stetro.tgtggrabber.api.TgtgRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val grabberModule = module {
    single { provideOkHttp() }
    single(named("tgtgRetrofit")) { provideTgtgRetrofit(get()) }
    single(named("iftttRetrofit")) { provideIftttRetrofit(get()) }
    single { TgtgRepository(get(named("tgtgRetrofit"))) }
    single { IFTTTRepository(get(named("iftttRetrofit"))) }
}

fun provideIftttRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .baseUrl("https://maker.ifttt.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

private fun provideOkHttp(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.NONE
    interceptor.redactHeader("")
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}

private fun provideTgtgRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .baseUrl("https://apptoogoodtogo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}
