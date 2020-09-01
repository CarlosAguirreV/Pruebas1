package com.codigobase.pruebas1.controlador.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.modelo.BD_emple_depart
import kotlinx.android.synthetic.main.activity_bd.*

class BdActivity : AppCompatActivity() {

    private lateinit var bdEmpleDepart: BD_emple_depart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bd)

        // Ocultar barra superior, si no da null
        super.getSupportActionBar()?.hide()

        // Obtener una instancia de la BD
        bdEmpleDepart = BD_emple_depart.getInstancia(this)

        try {
            val coleccionDepartamentos = bdEmpleDepart.departDao().getDepartamentos()

            var cadena = ""
            for (departamento in coleccionDepartamentos) {
                cadena += departamento.toString() + "\n\n"
            }

            txtDatos.text = cadena

        } catch (ex: Exception) {
            Log.e("ERROR", ex.message)
        }
    }
}