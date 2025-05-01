package dev.gearturner.wallit.model

//Represents a wallpaper item with metadata typically retrieved from an API or local source
data class Wallpaper(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)