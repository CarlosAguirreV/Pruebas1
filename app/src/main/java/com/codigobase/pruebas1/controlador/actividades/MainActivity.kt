package com.codigobase.pruebas1.controlador.actividades

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.controlador.ControlSonidos
import com.codigobase.pruebas1.controlador.Corrutinas
import com.codigobase.pruebas1.modelo.Constantes
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.elementos_menu.*

class MainActivity : AppCompatActivity() {

    private var bloquear: Boolean = false
    private lateinit var controlSonidos: ControlSonidos

    override fun onCreate(savedInstanceState: Bundle?) {
        // En cuanto cargue esperar un poco y quitar el Splash Screen
        Thread.sleep(100)
        setTheme(R.style.AppTheme)

        // Del onCreate
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ocultar barra superior, si no da null
        super.getSupportActionBar()?.hide()

        // Crear controlador de sonidos
        controlSonidos =
            ControlSonidos(this)
        controlSonidos.setActivo(true)

        // Eventos de boton
        eventosDeBoton()
    }

    override fun onResume() {
        super.onResume()
        bloquear = false
    }

    override fun onDestroy() {
        super.onDestroy()
        controlSonidos.liberarMemoria()
    }

    private fun eventosDeBoton() {
        btnFantasma.setOnClickListener { showActividad(FantasmaActivity::class.java) }
        btnLuzSensor.setOnClickListener { showActividad(LuzSensorActivity::class.java) }
        btnUI.setOnClickListener { showActividad(UiActivity::class.java) }
        btnCorrutinas.setOnClickListener { showActividad(Corrutinas::class.java) }
        btnCodigo.setOnClickListener { mostrarWeb() }
        btnAcerca.setOnClickListener { showActividad(AcercaDeActivity::class.java) }
        btnCerrar.setOnClickListener { finish() }
    }

    private fun showActividad(clase: Class<*>) {
        controlSonidos.playAudio(Constantes.SND_INTERRUPTOR)

        if (!bloquear) {
            val intent = Intent(this, clase)
            startActivity(intent)

            // Animaciones de Atif Pervaiz - https://github.com/AtifSayings/Animatoo
            Animatoo.animateSlideLeft(this)
        }
    }

    private fun mostrarWeb() {
        val paginaWeb = "https://github.com/CarlosAguirreV/Pruebas1.git"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paginaWeb))
        startActivity(intent)
    }
}
