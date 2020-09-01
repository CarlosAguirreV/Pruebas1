package com.codigobase.pruebas1.modelo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Clase encargada de controlar la BD.
 * Para poder crear una instancia hay que llamar al metodo "getInstancia()" de esta clase.
 * Si quieres aniadir una nueva entidad tienes que meter la clase en el array de "entities" y
 * despues aniadir el metodo abstacto abajo para poder llamar a sus metodos.
 */
@Database(
    entities = [Emple::class, Depart::class],
    version = 1
)
abstract class BD_emple_depart : RoomDatabase() {

    /** Metodos abstractos para manipular las entidades de la BD. */
    abstract fun empleDao(): EmpleDao
    abstract fun departDao(): DepartDao

    /**
     * Este fragmento de codigo construye y retorna una instancia de este objeto.
     * Solo habra una instancia para toda la aplicacion.
     *
     * Companion obliga a que los metodos y campos sean propios de la clase(static)
     */
    companion object {
        @Volatile
        private var instanciaBD: BD_emple_depart? = null

        /** Este es el metodo que construye y devuelve una sola instancia de esta clase. */
        fun getInstancia(context: Context): BD_emple_depart {
            val instanciaAuxUno = instanciaBD

            if (instanciaAuxUno != null) {
                return instanciaAuxUno
            }

            // Si no existe una instancia de la BD, se creara.
            synchronized(this) {
                val instanciaAux = Room.databaseBuilder(
                    context,
                    BD_emple_depart::class.java, Constantes.NOMBRE_BD
                ).createFromAsset("bd/emple_depart.bd").allowMainThreadQueries().build()

                //.allowMainThreadQueries().build()
                instanciaBD = instanciaAux
                return instanciaAux
            }

        }
    }

}