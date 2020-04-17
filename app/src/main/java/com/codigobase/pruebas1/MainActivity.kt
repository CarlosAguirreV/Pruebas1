package com.codigobase.pruebas1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.elementos_menu.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // En cuanto cargue esperar un poco y quitar el Splash Screen
        Thread.sleep(100)
        setTheme(R.style.AppTheme)

        // Del onCreate
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

    // Esto evitara que se puedan abrir dos actividades a la vez
    private fun bloquearBotones(bloquear: Boolean) {
        var bloqueo: Boolean = !bloquear
        btnFantasma.isEnabled = bloqueo
        btnLuzSensor.isEnabled = bloqueo
    }

    private fun eventosDeBoton() {
        btnFantasma.setOnClickListener { bloquearBotones(true); showActividadFantasma() }
        btnLuzSensor.setOnClickListener { bloquearBotones(true); showActividadLuz() }
        btnCodigo.setOnClickListener { mostrarWeb() }
        btnCerrar.setOnClickListener { finish() }
    }

    private fun showActividadFantasma() {
        val intent = Intent(this, FantasmaActivity::class.java)
        startActivity(intent)
    }

    private fun showActividadLuz() {
        val intent = Intent(this, LuzSensorActivity::class.java)
        startActivity(intent)
    }

    private fun mostrarWeb() {
        var paginaWeb = "https://github.com/CodigoBase2018/Pruebas1.git"
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(paginaWeb))
        startActivity(intent)
    }
}
