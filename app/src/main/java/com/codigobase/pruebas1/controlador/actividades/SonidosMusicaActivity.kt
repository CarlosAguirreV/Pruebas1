package com.codigobase.pruebas1.controlador.actividades

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.controlador.ControlMusica
import com.codigobase.pruebas1.controlador.ControlSonidos
import com.codigobase.pruebas1.modelo.Constantes
import com.codigobase.pruebas1.modelo.GuardarCargar
import kotlinx.android.synthetic.main.activity_sonidos_musica.*
import kotlinx.android.synthetic.main.barra_superior.*

/**
 * Esta actividad permite probar los controles de musica y sonido.
 * Se emplean las clases ControlMusica y ControlSonido.
 * Esta actividad no funcionara si en las opciones de la aplicacion no se ha marcado los
 * efectos de sonido, en cuyo caso mostrara un mensaje.
 */
class SonidosMusicaActivity : AppCompatActivity() {

    // ########################### CAMPOS ###########################
    private lateinit var controlSonidos: ControlSonidos
    private lateinit var controlMusica: ControlMusica
    private val volumenMaximo = 1f
    private var volumenIzq = volumenMaximo
    private var volumenDcho = volumenMaximo


    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sonidos_musica)

        // Ocultar barra superior, si no da null.
        supportActionBar?.hide()

        // Definir titulo.
        lblTituloBarra.text = getString(R.string.sonidoMusica)

        // Mostrar mensaje de como se usa
        mostrarMensaje(
            getString(R.string.instruc),
            getString(R.string.ins_musica_efectos)
        )

        // Mostrar u ocultar el mensaje de aviso (solo si esta deshabilitada la opcion de efectos).
        if (!GuardarCargar.datos.efectosSonido) {
            mensajeAlerta.visibility = View.VISIBLE
            lyAviso.visibility = View.VISIBLE
            lyScrollMusica.visibility = View.GONE
        }

        // Actualizar barras de progreso al mismo nivel que el volumen.
        sbVolIzq.progress = (volumenIzq * 100).toInt()
        sbVolDcho.progress = (volumenDcho * 100).toInt()

        // Crear elementos de sonido y musica.
        controlSonidos = ControlSonidos(this)
        controlMusica = ControlMusica(this, R.raw.msc_animada)

        // Eventos de boton.
        eventosBoton()
    }


    // ########################### METODO ON DESTROY ###########################
    override fun onDestroy() {
        super.onDestroy()
        controlSonidos.liberarMemoria()
        controlMusica.liberarMemoria()
    }


    // ########################### METODOS ###########################
    /** Lo que sucedera al pulsar algun boton. */
    private fun eventosBoton() {
        btnVolver.setOnClickListener { finish() }

        btnInfo.setOnClickListener {
            mostrarMensaje(
                getString(R.string.info),
                getString(R.string.ref_musica_efectos)
            )
        }

        btnPlaySonido.setOnClickListener {
            controlSonidos.playAudio(
                Constantes.SND_MALETA,
                volumenIzq,
                volumenDcho
            )
        }

        btnPlayMusica.setOnClickListener {
            if (controlMusica.isPlaying()) {
                controlMusica.pause()
                btnPlayMusica.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play))
            } else {
                controlMusica.play()
                btnPlayMusica.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause))
            }
        }

        sbVolIzq.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                volumenIzq = progress / 100f
                refrescarValoresMusica()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        sbVolDcho.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                volumenDcho = progress / 100f
                refrescarValoresMusica()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    /** Refresca el volumen de la musica. */
    private fun refrescarValoresMusica() {
        controlMusica.controlVolumen(volumenIzq, volumenDcho)
    }

    /** Muestra un mensaje con informacion. */
    private fun mostrarMensaje(titulo: String, mensaje: String) {
        val cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton(getString(R.string.aceptar), null)
        cuadroDialogo.show()
    }

}