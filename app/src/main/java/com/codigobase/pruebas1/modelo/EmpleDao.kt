package com.codigobase.pruebas1.modelo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmpleDao {
    @Query("SELECT * FROM emple")
    fun getEmpleados(): List<Emple>

    @Query(value = "SELECT * FROM emple WHERE id_emple = :id_emple")
    fun getEmple(id_emple: Int): Emple

    @Insert
    fun addEmple(emple: Emple)
}