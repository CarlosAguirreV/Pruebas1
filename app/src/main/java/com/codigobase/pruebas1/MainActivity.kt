package com.codigobase.pruebas1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ocultar barra superior, si no da null
        super.getSupportActionBar()?.hide()

        // Eventos de boton
        eventosDeBoton()
    }

    override fun onResume() {
        super.onResume()
        bloquearBotones(false)
    }

    private fun bloquearBotones(bloquear: Boolean) {
        btnFantasma.isEnabled = true
    }

    private fun eventosDeBoton() {
        btnFantasma.setOnClickListener(View.OnClickListener { bloquearBotones(true); showActividadFantasma() })
        btnCerrar.setOnClickListener(View.OnClickListener { finish() })
    }

    private fun showActividadFantasma() {
        val intent = Intent(this, FantasmaActivity::class.java)
        startActivity(intent)
    }
}
