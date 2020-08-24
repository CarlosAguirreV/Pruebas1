package com.codigobase.pruebas1.modelo

import android.util.Log
import java.io.*

/**
 * Clase encargada de guardar, cargar y borrar (sobreescribir) los datos.
 * Es necesaria la ruta donde se guardaran los datos, esta se obtiene mediante getFilesDir() (filesDir)
 * y getExternalFilesDir() en la clase de una actividad.
 * Se puede acceder a los datos directamente haciendo mencion a "GuardarCargar.datos" para no estar creando instancias cada vez.
 */
class GuardarCargar(ruta: File) {

    // ################ CAMPOS Y METODOS ESTATICOS ################
    companion object {
        var datos: DatosGuardado = getDatosPorDefecto()
        private fun getDatosPorDefecto(): DatosGuardado = DatosGuardado(true, false, 0)
    }

    // ################ CAMPOS ################
    val rutaArchivo = File(ruta, Constantes.NOMBRE_ARCHIVO)

    // ################ METODOS ################
    /**
     * Guarda los datos.
     * Guarda los datos que estan en la variable "datos".
     * Retorna true si lo ha hecho bien y false si ha tenido problemas.
     */
    fun guardar(): Boolean {
        var todoBien = true

        try {
            val escritor = ObjectOutputStream(FileOutputStream(rutaArchivo))
            escritor.writeObject(datos)
            escritor.close()
        } catch (ex: IOException) {
            todoBien = false
        }

        return todoBien
    }

    /**
     * Carga los datos y los almacena en la variable datos.
     * Si no encuentra nada, guarda datos nuevos.
     */
    fun cargar() {
        try {
            val lector = ObjectInputStream(FileInputStream(rutaArchivo))
            datos = lector.readObject() as DatosGuardado
            lector.close()

        } catch (ex: IOException) {
            datos = getDatosPorDefecto()

            if (!guardar()) {
                Log.e("ERROR", "No se han podido guardar los datos.")
            }
        }
    }

    /**
     * Sobreescribe los datos con los valores por defecto, no guarda.
     */
    fun restablecerDatos() {
        datos = getDatosPorDefecto()
    }
}