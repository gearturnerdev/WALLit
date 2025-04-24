package dev.gearturner.wallit

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.gearturner.wallit.data.FavoriteDao
import dev.gearturner.wallit.data.WallItDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import android.content.Context
import androidx.room.Room
import dev.gearturner.wallit.model.Favorite
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Test
import org.junit.Assert.*
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class WallItDatabaseTest {
    private lateinit var db: WallItDatabase
    private lateinit var favoriteDao: FavoriteDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WallItDatabase::class.java).build()
        favoriteDao = db.favoriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadFavorite() = runBlocking {
        val favorite = Favorite(api_id = 123)
        favoriteDao.insertFavorite(favorite)
        val retrieved = favoriteDao.getFavorite(123)
        assertNotNull(retrieved)
        assertEquals(123, retrieved?.api_id)
    }
}
