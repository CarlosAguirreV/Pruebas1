package com.codigobase.pruebas1.controlador.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codigobase.pruebas1.R
import kotlinx.android.synthetic.main.barra_superior.*

class SonidosMusicaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sonidos_musica)

        // Ocultar barra superior, si no da null.
        supportActionBar?.hide()

        // Definir titulo.
        lblTituloBarra.text = getString(R.string.sonidoMusica)
    }
}