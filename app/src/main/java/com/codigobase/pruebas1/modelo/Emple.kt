package com.codigobase.pruebas1.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Depart::class,
        parentColumns = ["id_depart"],
        childColumns = ["depart_id"]
    )]
)
data class Emple(
    @PrimaryKey var id_emple: Int,
    @ColumnInfo(name = "nombre_emple") var nombre_emple: String,
    @ColumnInfo var depart_id: Int
) {
    override fun toString(): String {
        return "$id_emple $nombre_emple"
    }
}