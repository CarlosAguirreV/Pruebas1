package com.codigobase.pruebas1.controlador.actividades

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.controlador.fragmentos.BotonesUiFragment
import com.codigobase.pruebas1.controlador.fragmentos.DialogoUiFragment
import kotlinx.android.synthetic.main.activity_ui.*
import kotlinx.android.synthetic.main.barra_superior.*

/**
 * Actividad que muestra el uso de diferentes elementos de la "User Interface".
 * Se emplean multiples elementos del directorio "res > drawable" hechale un ojo.
 */
class UiActivity : AppCompatActivity() {

    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui)

        // Ocultar barra superior, si no da null
        supportActionBar?.hide()

        // Definir titulo
        lblTituloBarra.text = getString(R.string.ui)

        // Mostrar mensaje de como se usa
        mostrarMensaje(
            getString(R.string.instruc),
            getString(R.string.ins_ui)
        )

        // Cargar los eventos de boton
        eventosBoton()

        // Mostrar el primer fragmento
        mostrarFragmentoSeleccionado(BotonesUiFragment())
    }

    // ########################### METODOS ###########################
    /** Lo que sucedera al pulsar algun boton. */
    private fun eventosBoton() {
        btnVolver.setOnClickListener { finish() }

        btnInfo.setOnClickListener {
            mostrarMensaje(
                getString(R.string.info),
                getString(R.string.ref_ui)
            )
        }

        menu_inferior_ui.setOnNavigationItemSelectedListener { elementoSeleccionado: MenuItem ->

            if (elementoSeleccionado.itemId == R.id.menu_botones) {
                mostrarFragmentoSeleccionado(BotonesUiFragment())
            } else if (elementoSeleccionado.itemId == R.id.menu_dialogos) {
                mostrarFragmentoSeleccionado(DialogoUiFragment())
            }

            true
        }
    }

    /** Muestra el fragmento pasado por parametro. */
    private fun mostrarFragmentoSeleccionado(fragmento: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.contenidoFragmento, fragmento)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    /** Muestra un mensaje con informacion. */
    private fun mostrarMensaje(titulo: String, mensaje: String) {
        val cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton(getString(R.string.aceptar), null)
        cuadroDialogo.show()
    }
}

















