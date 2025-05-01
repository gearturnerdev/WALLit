/*
authors: Hunter Pageau and MD Fayed bin Salim
version: 1 May 2025
JUnit test for database
 */

package dev.gearturner.wallit

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.gearturner.wallit.data.FavoriteDao
import dev.gearturner.wallit.data.WallItDatabase
import dev.gearturner.wallit.model.Favorite
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WallItDatabaseTest {
    private lateinit var db: WallItDatabase
    private lateinit var favoriteDao: FavoriteDao

    //initialize database in memory
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WallItDatabase::class.java).build()
        favoriteDao = db.favoriteDao()
    }

    //close the database
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //adds wallpaper with id 123 as favorite, retrieves favorite with id 123, compares to the original object's id, test passes if they match
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
