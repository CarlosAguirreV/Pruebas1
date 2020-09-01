package com.codigobase.pruebas1.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Depart(
    @PrimaryKey var id_depart: Int,
    @ColumnInfo(name = "nombre_depart") var nombre_depart: String
) {
    override fun toString(): String {
        return "Depart:\n" +
                " - id_depart $id_depart\n" +
                " - nombre_depart $nombre_depart"
    }
}