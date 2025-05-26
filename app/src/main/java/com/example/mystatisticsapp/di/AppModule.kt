package com.example.mystatisticsapp.di


import com.example.mystatisticsapp.data.remote.ApiService
import com.example.mystatisticsapp.data.repository.RikmastersRepositoryImpl
import com.example.mystatisticsapp.domain.repository.RikmastersRepository
import com.example.mystatisticsapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

         @Provides
         fun provideBaseUrl(): String {
             return Constants.baseUrl
         }

         @Provides
         @Singleton
         fun provideLoggingInterceptor(): HttpLoggingInterceptor {
             return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
         }

         @Provides
         @Singleton
         fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
             val okHttpClient = OkHttpClient.Builder()
             okHttpClient.addInterceptor(logger)
             okHttpClient.callTimeout(30, TimeUnit.SECONDS)
             okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
             okHttpClient.writeTimeout(30, TimeUnit.SECONDS)
             okHttpClient.readTimeout(30, TimeUnit.SECONDS)
             val okHttp = okHttpClient.build()
             return okHttp
         }

         @Provides
         @Singleton
         fun provideConvectorFactory(): GsonConverterFactory {
             return GsonConverterFactory.create()
         }

         @Provides
         @Singleton
         fun provideRetrofit(
                baseUrl: String,
                gsonFactory: GsonConverterFactory,
                okHttpClient: OkHttpClient
         ): Retrofit {
             return Retrofit.Builder()
                 .baseUrl(baseUrl)
                 .addConverterFactory(gsonFactory)
                 .client(okHttpClient)
                 .build()
         }

         @Provides
         @Singleton
         fun provideApiService(retrofit: Retrofit): ApiService {
             return retrofit.create(ApiService::class.java)
         }

         @Provides
         @Singleton
         fun provideRepository(
                apiService: ApiService,
         ): RikmastersRepository {
             return RikmastersRepositoryImpl(
                    apiService = apiService,
             )
         }
}