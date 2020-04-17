package com.codigobase.pruebas1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_luz_sensor.*
import kotlinx.android.synthetic.main.barra_superior.*
import java.lang.Exception

class LuzSensorActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    var valorLimite: Int = 300
    var porcentajeLuz: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luz_sensor)

        // Obtener elementos sensor manager y el sensor de luz
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Ocultar barra superior, si no da null
        supportActionBar?.hide()

        // Definir titulo
        lblTituloBarra.text = "Sensor de luz"

        // Mostrar mensaje de como se usa
        mostrarMensaje(
            "Instrucciones",
            "Para esta prueba se usa el sensor de luz, conforme más luz haya, más cargado estará la barra de progreso."
        )

        // Cargar los eventos de boton
        eventosBoton()
    }

    override fun onResume() {
        super.onResume()

        // Definir la frecuencia con la que quiero que se actualice
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        // Anular el listener de la luz para que no este a cada rato detectando
        sensorManager.unregisterListener(this)
    }

    // Metodos del sensor x2
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            if (event != null) {
                mostrarValorLuz(event.values[0])
            }
        } catch (ex: Exception) {
            Log.e("ERROR", "Fallo con el sensor de la luz.")
        }
    }

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

        cadena = "Valor del sensor $cantidadLuz"
        txtTextoSensorLuz.text = cadena
    }

    // Otros metodos
    private fun eventosBoton() {
        btnRef.setOnClickListener {
            mostrarMensaje(
                "Información",
                "Clase: LuzSensorActivity\nLayer: activity_luz_sensor\nManifest: android.hardware.sensor.acelerometer"
            )
        }
        btnVolver.setOnClickListener { finish() }
    }

    private fun mostrarMensaje(titulo: String, mensaje: String) {
        var cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton("Aceptar", null)
        cuadroDialogo.show()
    }


}
