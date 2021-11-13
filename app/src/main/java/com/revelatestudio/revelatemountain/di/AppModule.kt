package com.revelatestudio.revelatemountain.di

import com.revelatestudio.revelatemountain.data.remote.PixabayApi
import com.revelatestudio.revelatemountain.data.repository.IRepository
import com.revelatestudio.revelatemountain.data.repository.MainRepository
import com.revelatestudio.revelatemountain.util.DisptacherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://pixabay.com/api/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePixabayApi() : PixabayApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PixabayApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api : PixabayApi) : MainRepository = MainRepository(api)

    @Singleton
    @Provides
    fun provideDispatcher() : DisptacherProvider = object : DisptacherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }

}