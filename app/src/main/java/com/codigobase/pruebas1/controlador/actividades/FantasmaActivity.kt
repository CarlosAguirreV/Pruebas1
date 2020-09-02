package com.codigobase.pruebas1.controlador.actividades

import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.codigobase.pruebas1.R
import kotlinx.android.synthetic.main.activity_fantasma.*
import kotlinx.android.synthetic.main.barra_superior.*

/**
 * Esta actividad trata de utilizar el sensor "OnTouchListener" el cual detectara la posicion en
 * la cual se esta tocando la pantalla. Sobre ese punto se dibujara una vela. Al desplazar el dedo
 * el fantasma se movera en la direccion contraria, como si tratase de evitarlo.
 */
class FantasmaActivity : AppCompatActivity(), View.OnTouchListener {

    // ########################### CAMPOS ###########################
    private val velocidadFantasma = 15
    private lateinit var coordenadasVela: Point
    private lateinit var coordenadasFantasma: Point
    private lateinit var areaMovimiento: Point


    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fantasma)

        // Ocultar barra superior, si no da null.
        supportActionBar?.hide()

        // Crear elementos.
        coordenadasVela = Point()
        coordenadasFantasma = Point()
        areaMovimiento = Point()

        // Refrescar coordenadas fantasma y vela.
        refrescarCoordenadas()

        // Definir el listener de toques al elemento de la GUI que hara de sensor.
        sensorToques.setOnTouchListener(this)

        // Definir titulo.
        lblTituloBarra.text = getString(R.string.fantasma)

        // Mostrar mensaje de como se usa.
        mostrarMensaje(
            getString(R.string.instruc),
            getString(R.string.ins_fantasma)
        )

        // Cargar los eventos de boton.
        eventosBoton()
    }


    // ########################### AL TOCAR LA PANTALLA ###########################
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        // Si el area no tiene valor hay que obtenerlo antes de nada.
        if(areaMovimiento.x == 0){ refrescarAreaMovimiento() }

        if (v?.id == sensorToques.id) {
            // Obtener los valores X e Y del punto tocado en la pantalla.
            val valorX = event?.getAxisValue(MotionEvent.AXIS_X)
            val valorY = event?.getAxisValue(MotionEvent.AXIS_Y)

            // Si se ha tocado algo se refresca la posicion de la vela y del fantasma.
            if (valorX != null && valorY != null) {
                refrescarCoordenadas()
                posicionarVela(valorX.toInt(), valorY.toInt())
                alejarFantasma()
            }
        }
        return true
    }


    // ########################### METODOS ###########################
    /** Lo que sucedera al pulsar algun boton. */
    private fun eventosBoton() {
        btnInfo.setOnClickListener {
            mostrarMensaje(
                getString(R.string.info),
                getString(R.string.ref_fantasma)
            )
        }
        btnVolver.setOnClickListener { finish() }
    }

    /** Almacena las coordenadas de la imagen de la vela y del fantasma y mostrarlas por pantalla. */
    private fun refrescarCoordenadas() {
        coordenadasVela.x = imgVela.x.toInt()
        coordenadasVela.y = imgVela.y.toInt()

        coordenadasFantasma.x = imgFantasma.x.toInt()
        coordenadasFantasma.y = imgFantasma.y.toInt()

        mostrarValores(coordenadasFantasma)
    }

    /** Posiciona la imagen de la vela en las coordenadas X e Y. */
    private fun posicionarVela(x: Int, y: Int) {
        val mitadImagen = imgVela.width / 2
        imgVela.x = x.toFloat() - 30
        imgVela.y = y.toFloat() + mitadImagen
    }

    /** Muesta los valores X e Y en pantalla. */
    private fun mostrarValores(coordenadas: Point) {
        val cadenaX = "X: " + coordenadas.x
        val cadenaY = "Y: " + coordenadas.y

        lblX.text = cadenaX
        lblY.text = cadenaY
    }

    /** Aleja al fantasma respecto a la imagen de la vela. */
    private fun alejarFantasma() {
        // Para las coordenadas X.
        if (coordenadasFantasma.x < coordenadasVela.x && coordenadasFantasma.x > velocidadFantasma) {
            imgFantasma.x += velocidadFantasma * -1
        } else if (coordenadasFantasma.x > coordenadasVela.x && coordenadasFantasma.x < areaMovimiento.x - imgFantasma.width) {
            imgFantasma.x += velocidadFantasma
        }

        // Para las coordenadas Y.
        if (coordenadasFantasma.y < coordenadasVela.y && coordenadasFantasma.y > velocidadFantasma) {
            imgFantasma.y += velocidadFantasma * -1
        } else if (coordenadasFantasma.y > coordenadasVela.y && coordenadasFantasma.y < areaMovimiento.y - imgFantasma.width) {
            imgFantasma.y += velocidadFantasma
        }
    }

    /** Obtener y almacenar el tamanio de la pantalla. */
    private fun refrescarAreaMovimiento() {
        areaMovimiento.x = sensorToques.width
        areaMovimiento.y = sensorToques.height
    }

    /** Muestra un mensaje con informacion. */
    private fun mostrarMensaje(titulo: String, mensaje: String) {
        val cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton("Aceptar", null)
        cuadroDialogo.show()
    }
}
