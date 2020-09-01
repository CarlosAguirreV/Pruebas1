package com.codigobase.pruebas1.modelo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DepartDao {
    @Query("SELECT * FROM depart")
    fun getDepartamentos(): List<Depart>

    @Query(value = "SELECT * FROM depart WHERE id_depart = :id_depart")
    fun getDepart(id_depart: Int): Depart

    @Insert
    fun addDepart(depart: Depart)
}