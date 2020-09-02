package com.codigobase.pruebas1.controlador.actividades

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.codigobase.pruebas1.R
import kotlinx.android.synthetic.main.activity_luz_sensor.*
import kotlinx.android.synthetic.main.barra_superior.*

/**
 * Actividad que trata de medir la cantidad de luz que entra por el sensor.
 * A mas luz mayor sera el progreso de la barra, es un valor orientativo, por ello se pone
 * abajo a la izquierda el valor real del sensor.
 */
class LuzSensorActivity : AppCompatActivity(), SensorEventListener {

    // ########################### CAMPOS ###########################
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    var valorLimite: Int = 300
    var porcentajeLuz: Int = 0


    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luz_sensor)

        // Obtener elementos sensor manager y el sensor de luz.
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Ocultar barra superior, si no da null.
        supportActionBar?.hide()

        // Definir titulo
        lblTituloBarra.text = getString(R.string.luz)

        // Mostrar mensaje de como se usa.
        mostrarMensaje(
            getString(R.string.instruc),
            getString(R.string.ins_luz)
        )

        // Cargar los eventos de boton.
        eventosBoton()
    }


    // ########################### METODO ON RESUME ###########################
    override fun onResume() {
        super.onResume()

        // Definir la frecuencia con la que quiero que se actualice.
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }


    // ########################### METODO ON PAUSE ###########################
    override fun onPause() {
        super.onPause()
        // Anular el listener de la luz para que no este a cada rato detectando
        sensorManager.unregisterListener(this)
    }


    // ########################### METODOS DEL SENSOR x2 ###########################
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            if (event != null) {
                mostrarValorLuz(event.values[0])
            }
        } catch (ex: Exception) {
            Log.e(
                getString(R.string.error), getString(
                    R.string.fallo_luz
                )
            )
        }
    }


    // ########################### METODOS ###########################
    /** Mostrar tanto el porcentaje de luz como el valor exacto aportado por el sensor. */
    private fun mostrarValorLuz(cantidadLuz: Float) {
        if (cantidadLuz < valorLimite) {
            porcentajeLuz = (cantidadLuz * 100 / valorLimite).toInt()
            progressBar.progress = porcentajeLuz
        } else {
            porcentajeLuz = 100
            progressBar.progress = 100
        }

        var cadena = "$porcentajeLuz%"
        txtTextoPorcentajeLuz.text = cadena

        cadena = getString(R.string.valor_luz) + " $cantidadLuz"
        txtTextoSensorLuz.text = cadena
    }

    /** Lo que sucedera al pulsar algun boton. */
    private fun eventosBoton() {
        btnInfo.setOnClickListener {
            mostrarMensaje(
                getString(R.string.info),
                getString(R.string.ref_luz)
            )
        }
        btnVolver.setOnClickListener { finish() }
    }

    /** Muestra un mensaje con informacion. */
    private fun mostrarMensaje(titulo: String, mensaje: String) {
        val cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton(getString(R.string.aceptar), null)
        cuadroDialogo.show()
    }


}
