package com.codigobase.pruebas1

import android.content.DialogInterface
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_fantasma.*
import kotlinx.android.synthetic.main.barra_superior.*

/**
 * https://medium.com/kotlin-en-android/coroutines-con-kotlin-introducci%C3%B3n-a68f5eeee6a8
 */
class FantasmaActivity : AppCompatActivity(), View.OnTouchListener {

    private val velocidadFantasma = 15
    private lateinit var coordenadasVela: Point
    private lateinit var coordenadasFantasma: Point
    private lateinit var areaMovimiento: Point

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fantasma)

        // Ocultar barra superior, si no da null
        super.getSupportActionBar()?.hide()

        // Crear elementos
        coordenadasVela = Point()
        coordenadasFantasma = Point()
        areaMovimiento = Point()

        // Refrescar coordenadas fantasma y vela
        refrescarCoordenadas()

        // Definir el listener de toques al elemento de la GUI que hara de sensor
        sensorToques.setOnTouchListener(this)

        // Definir titulo
        lblTituloBarra.text = "Fantasma"

        // Mostrar mensaje de como se usa
        mostrarMensaje(
            "Instrucciones",
            "Al arrastrar el dedo por la pantalla se moverá la vela y el fantasma huirá.\nO al menos eso es lo que debería suceder."
        )

        // Cargar los eventos de boton
        eventosBoton()
    }

    private fun eventosBoton() {
        btnRef.setOnClickListener {
            mostrarMensaje(
                "Información",
                "Nombre actividad: FantasmaActivity\nNombre layer: activity_fantasma"
            )
        }
        btnVolver.setOnClickListener { finish() }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v?.id == sensorToques.id) {
            var valorX = event?.getAxisValue(MotionEvent.AXIS_X)
            var valorY = event?.getAxisValue(MotionEvent.AXIS_Y)

            if (valorX != null && valorY != null) {
                refrescarCoordenadas()
                posicionarVela(valorX.toInt(), valorY.toInt())
                alejarFantasma()
            }
        }
        return true
    }

    private fun refrescarCoordenadas() {
        coordenadasVela.x = imgVela.x.toInt()
        coordenadasVela.y = imgVela.y.toInt()

        coordenadasFantasma.x = imgFantasma.x.toInt()
        coordenadasFantasma.y = imgFantasma.y.toInt()

        mostrarValores(coordenadasFantasma)
    }

    private fun posicionarVela(x: Int, y: Int) {
        var mitadImagen = imgVela.width / 2
        imgVela.x = x.toFloat() - 30
        imgVela.y = y.toFloat() + mitadImagen
    }

    private fun mostrarValores(coordenadas: Point) {
        var cadenaX = "X: " + coordenadas.x
        var cadenaY = "Y: " + coordenadas.y

        lblX.text = cadenaX
        lblY.text = cadenaY
    }

    private fun alejarFantasma() {
        refrescarAreaMovimiento() // SOLO TIENE QUE HACERLO UNA SOLA VEZ

        // Para las coordenadas X
        if (coordenadasFantasma.x < coordenadasVela.x && coordenadasFantasma.x > velocidadFantasma) {
            imgFantasma.x += velocidadFantasma * -1
        } else if (coordenadasFantasma.x > coordenadasVela.x && coordenadasFantasma.x < areaMovimiento.x - imgFantasma.width) {
            imgFantasma.x += velocidadFantasma
        }

        // Para las coordenadas Y
        if (coordenadasFantasma.y < coordenadasVela.y && coordenadasFantasma.y > velocidadFantasma) {
            imgFantasma.y += velocidadFantasma * -1
        } else if (coordenadasFantasma.y > coordenadasVela.y && coordenadasFantasma.y < areaMovimiento.y - imgFantasma.width) {
            imgFantasma.y += velocidadFantasma
        }
    }

    private fun refrescarAreaMovimiento() {
        areaMovimiento.x = sensorToques.width
        areaMovimiento.y = sensorToques.height

        Log.e("ALGO X", "${areaMovimiento.x}")
        Log.e("ALGO Y", "${areaMovimiento.y}")
    }

    private fun mostrarMensaje(titulo: String, mensaje: String) {
        var cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton("Aceptar", null)
        cuadroDialogo.show()
    }
}
