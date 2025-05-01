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

//Retrofit service interface defining the API endpoints
interface WallItApiService {

    //GET request to "https://picsum.photos/id/{imageId}/info"
    //Returns detailed info about a wallpaper with the given ID
    @GET("id/{imageId}/info")
    suspend fun getWallpaperInfo(@Path("imageId") imageId: Int): Wallpaper
}

//Singleton object exposing the API service
//'retrofitService' is lazily initialized the first time it is accessed
object WallItApi {
    val retrofitService: WallItApiService by lazy {
        retrofit.create(WallItApiService::class.java)
    }
}