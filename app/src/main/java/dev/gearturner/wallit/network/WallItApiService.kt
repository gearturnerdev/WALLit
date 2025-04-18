package dev.gearturner.wallit.network

import dev.gearturner.wallit.model.Wallpaper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://picsum.photos/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WallItApiService {
    @GET("id/{imageId}/info")
    suspend fun getWallpaperInfo(@Path("imageId") imageId: Int): Wallpaper
}

object WallItApi {
    val retrofitService: WallItApiService by lazy {
        retrofit.create(WallItApiService::class.java)
    }
}