package com.codigobase.pruebas1.modelo

import androidx.room.*

@Entity
data class Emple(
    @PrimaryKey var id_emple: Int,
    @ColumnInfo(name = "nombre_emple") var nombre_emple: String,
    @ColumnInfo var depart_id: Int
) {
    override fun toString(): String {
        return "Emple:\n" +
                " - id_emple $id_emple\n" +
                " - nombre_emple $nombre_emple\n" +
                " - depart_id $depart_id"
    }
}