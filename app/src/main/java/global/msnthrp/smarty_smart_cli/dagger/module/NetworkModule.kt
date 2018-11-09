package global.msnthrp.smarty_smart_cli.dagger.module

import dagger.Module
import dagger.Provides
import global.msnthrp.smarty_smart_cli.BuildConfig
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.storage.Prefs
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by msnthrp on 22/01/18.
 */

@Module
class NetworkModule {

    private val timeout = 45L

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val log = HttpLoggingInterceptor()
        log.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return log
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(prefs: Prefs): AuthInterceptor = AuthInterceptor(prefs)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                            authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideNetwork(client: OkHttpClient, myIp: MyIp): Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(myIp.value)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideMyIp(prefs: Prefs): MyIp = MyIp(prefs)


    inner class AuthInterceptor(private val prefs: Prefs) : Interceptor {

        private val authToken = "Auth"

        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val url = request.url()
                    .newBuilder()
                    .build()

            request = request.newBuilder()
                    .url(url)
                    .addHeader(authToken, prefs.token)
                    .build()
            return chain.proceed(request)
        }
    }

    inner class MyIp(prefs: Prefs) {
        val value: String = "http://${prefs.ip}:1753"
    }
}