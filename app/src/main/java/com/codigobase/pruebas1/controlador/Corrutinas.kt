package com.codigobase.pruebas1.controlador

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.codigobase.pruebas1.R
import kotlinx.android.synthetic.main.activity_corrutinas.*
import kotlinx.android.synthetic.main.barra_superior.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Una corrutina es un subproceso que se realiza en el mismo hilo.
 * Documentacion: https://codelabs.developers.google.com/codelabs/kotlin-coroutines/#0
 * Video: https://www.youtube.com/watch?v=F63mhZk-1-Y
 */
class Corrutinas : AppCompatActivity() {

    private var pulsacionesBoton = 0
    private lateinit var toastActual: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corrutinas)

        // Ocultar barra superior, si no da null
        super.getSupportActionBar()?.hide()

        // Definir titulo
        lblTituloBarra.text = getString(R.string.corrutinas)

        // Mostrar mensaje de como se usa
        mostrarMensaje(
            getString(R.string.instruc),
            getString(R.string.ins_corrutinas)
        )

        // Crear un toast vacio para poder cancelarlo en caso de que se creen varios a la vez
        toastActual = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        // Cargar los eventos de boton
        eventosBoton()
    }

    override fun onDestroy() {
        super.onDestroy()
        toastActual.cancel()
    }

    // Otros metodos
    private fun eventosBoton() {
        btnRef.setOnClickListener {
            mostrarMensaje(
                getString(R.string.info),
                getString(R.string.ref_corrutinas)
            )
        }
        btnVolver.setOnClickListener { finish() }

        btnCuentaAtras.setOnClickListener {
            // Ambito de la corrutina: IO, Main, Default
            // Iniciar una corutina
            CoroutineScope(IO).launch { iniciarCuentaAtras() }
        }

        btnToast.setOnClickListener { mostrarToast(getString(R.string.mensajeCorrutinas) + " ${++pulsacionesBoton}") }
    }

    /** Corrutina - Metodo que trabajan en paralelo */
    private suspend fun iniciarCuentaAtras() {
        mostrarBoton(false)
        setTextoHiloPrincipal("3")
        delay(1000)
        setTextoHiloPrincipal("2")
        delay(1000)
        setTextoHiloPrincipal("1")
        delay(1000)
        setTextoHiloPrincipal(getString(R.string.fin))
        delay(500)
        mostrarBoton(true)
    }

    /** Corrutina - Metodos que trabajan con el hilo principal */
    private suspend fun setTextoHiloPrincipal(mensaje: String) {
        withContext(Main) {
            lblCuentaAtras.text = mensaje
        }
    }

    private suspend fun mostrarBoton(mostrar: Boolean) {
        withContext(Main) {
            if (mostrar) {
                btnCuentaAtras.visibility = View.VISIBLE
                lblCuentaAtras.visibility = View.GONE
            } else {
                btnCuentaAtras.visibility = View.GONE
                lblCuentaAtras.visibility = View.VISIBLE
            }
        }
    }

    /** Metodo que muestra un mensaje */
    private fun mostrarMensaje(titulo: String, mensaje: String) {
        val cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton(getString(R.string.aceptar), null)
        cuadroDialogo.show()
    }

    private fun mostrarToast(mensaje: String) {
        toastActual.cancel()
        toastActual = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT)
        toastActual.show()
    }


}