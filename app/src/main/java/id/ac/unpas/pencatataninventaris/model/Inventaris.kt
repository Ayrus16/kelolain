package id.ac.unpas.pencatataninventaris.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Inventaris(
    @PrimaryKey val id: String,
    val namaBarang: String,
    val jumlahBarang: String,
    val jenisBarang: String,
    val keterangan: String
)
