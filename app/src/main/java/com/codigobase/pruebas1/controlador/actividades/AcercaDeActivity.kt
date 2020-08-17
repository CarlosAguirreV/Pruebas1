package com.codigobase.pruebas1.controlador.actividades

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codigobase.pruebas1.R
import kotlinx.android.synthetic.main.barra_superior.*

class AcercaDeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de)

        // Ocultar barra superior, si no da null
        super.getSupportActionBar()?.hide()

        // Ocultar el boton referencia
        btnRef.visibility = View.GONE

        // Definir titulo
        lblTituloBarra.text = getString(R.string.acerca)

        // Evento del boton volver
        btnVolver.setOnClickListener { finish() }
    }
}