package id.ac.unpas.pencatataninventaris.persistences

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.unpas.pencatataninventaris.model.Inventaris

@Database(entities = [Inventaris::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventarisDao(): inventarisDao
}