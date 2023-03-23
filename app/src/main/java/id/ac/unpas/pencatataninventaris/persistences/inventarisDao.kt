package id.ac.unpas.pencatataninventaris.persistences

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.unpas.pencatataninventaris.model.Inventaris

@Dao
interface inventarisDao {
    @Query("SELECT * FROM Inventaris")
    fun loadAll(): LiveData<List<Inventaris>>
    @Query("SELECT * FROM Inventaris WHERE id = :id")
    fun find(id: String): Inventaris?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg items: Inventaris)
    @Delete
    fun delete(item: Inventaris)
}