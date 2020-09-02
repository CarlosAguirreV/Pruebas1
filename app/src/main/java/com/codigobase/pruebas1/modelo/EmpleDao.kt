package com.codigobase.pruebas1.modelo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmpleDao {
    @Query("SELECT * FROM emple")
    fun getEmpleados(): List<Emple>

    @Query(value = "SELECT * FROM emple WHERE id_emple = :id_emple")
    fun getEmple(id_emple: Int): Emple

    @Query(value = "SELECT * FROM emple WHERE nombre_emple = :nombre")
    fun getEmplePorNombre(nombre: String): Emple

    @Query(value = "SELECT * FROM emple WHERE depart_id = :depart_id")
    fun getEmplePorDepart(depart_id: Int): List<Emple>

    @Query(value = "SELECT MAX(id_emple) FROM emple")
    fun getMaximoId(): Int

    @Insert
    fun addEmple(emple: Emple)

    @Delete
    fun deleteEmple(emple: Emple)
}