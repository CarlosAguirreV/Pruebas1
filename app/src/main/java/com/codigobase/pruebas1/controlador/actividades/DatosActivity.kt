package com.codigobase.pruebas1.controlador.actividades

import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.modelo.DatosGuardado
import com.codigobase.pruebas1.modelo.GuardarCargar
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_datos.*
import kotlinx.android.synthetic.main.barra_superior.*

class DatosActivity : AppCompatActivity() {

    private lateinit var guardarCargar: GuardarCargar
    private lateinit var toastAnterior: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos)

        // Ocultar barra superior, si no da null.
        supportActionBar?.hide()

        // Definir titulo.
        lblTituloBarra.text = getString(R.string.guardarCargar)

        // Crear los elementos necesarios.
        guardarCargar = GuardarCargar(filesDir)
        toastAnterior = Toast(this)

        // Cargar datos y mostrarlos al empezar.
        cargarDatos()

        // Mostrar mensaje de como se usa.
        mostrarMensaje(
            getString(R.string.instruc),
            getString(R.string.ins_guarda_carga)
        )

        // Cargar los eventos de boton.
        eventosBoton()
    }

    private fun eventosBoton() {
        btnGuardar.setOnClickListener { guardarDatos() }
        btnCargar.setOnClickListener { cargarDatos() }
        btnInfo.setOnClickListener {
            mostrarMensaje(
                getString(R.string.info),
                getString(R.string.ref_guarda_carga)
            )
        }
        btnVolver.setOnClickListener { finish() }

        seekNumEntero.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                lblValorSeek.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }


    private fun cargarDatos() {
        // Primero se cargan los datos.
        // Estos se guardan en un objeto almacenado como campo estatico dentro de la clase GuardarCargar.
        // Si por lo que sea da un error al cargar se cargaran los datos por defecto automaticamente.
        if (guardarCargar.cargar()) {
            mostrarToast(getString(R.string.datosCargados))
        } else {
            mostrarToast(getString(R.string.errorAlCargar))
        }

        // A continuacion se accede a los datos y se muestran en la interfaz.
        chkGuardado.isChecked = GuardarCargar.datos.opcion1
        seekNumEntero.progress = GuardarCargar.datos.opcion2
        lblValorSeek.text = GuardarCargar.datos.opcion2.toString()

        // Informar de que se han cargado los datos.
        mostrarToast(getString(R.string.datosCargados))
    }

    private fun guardarDatos() {
        // Antes de nada hay que modificar los datos de guardado, recuerda que estan referenciados en
        // un campo estatico dentro de la clase GuardarCargar.
        GuardarCargar.datos.opcion1 = chkGuardado.isChecked
        GuardarCargar.datos.opcion2 = seekNumEntero.progress

        // Una vez almacenados se procede a guardarlos en memoria.
        if (guardarCargar.guardar()) {
            mostrarToast(getString(R.string.datosGuardados))
        } else {
            mostrarToast(getString(R.string.errorAlGuardar))
        }
    }

    /** Muestra un Toast con el mensaje especificado. */
    private fun mostrarToast(mensaje: String?) {
        // Cancelar el toast anterior.
        toastAnterior.cancel()

        // Mostrar el siguiente mensaje Toast.
        toastAnterior = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT)
        toastAnterior.show()
    }

    /** Muestra un mensaje con informacion. */
    private fun mostrarMensaje(titulo: String, mensaje: String) {
        val cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton(getString(R.string.aceptar), null)
        cuadroDialogo.show()
    }

}