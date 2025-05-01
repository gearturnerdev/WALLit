/*
authors: Hunter Pageau and MD Fayed bin Salim
version: 1 May 2025
interface and functions to work with Picsum API
 */

package dev.gearturner.wallit.network

import dev.gearturner.wallit.model.Wallpaper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//Base URL for accessing the wallpaper API (Lorem Picsum)
private const val BASE_URL = "https://picsum.photos/"

//Retrofit instance configured with Gson for JSON parsing
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())  //Converts JSON response into Kotlin objects
    .baseUrl(BASE_URL)  //Sets the base URL for all API endpoints
    .build()

//Retrofit service
interface WallItApiService {
    //Returns detailed info about a wallpaper with the given ID
    @GET("id/{imageId}/info")
    suspend fun getWallpaperInfo(@Path("imageId") imageId: Int): Wallpaper
}

//object exposing the API service
object WallItApi {
    val retrofitService: WallItApiService by lazy {
        retrofit.create(WallItApiService::class.java)
    }
}