package ru.netology.mymusicplayer.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.netology.mymusicplayer.BuildConfig
import ru.netology.mymusicplayer.BuildConfig.BASE_URL
import ru.netology.mymusicplayer.dto.Album
import java.util.concurrent.TimeUnit

interface AlbumApi {
    @GET("album.json")
    suspend fun getAlbum(): Response<Album>

    companion object {

        private val logging = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        private val okhttp = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okhttp)
            .build()

        val service: AlbumApi by lazy {
            retrofit.create(AlbumApi::class.java)
        }
    }
}

