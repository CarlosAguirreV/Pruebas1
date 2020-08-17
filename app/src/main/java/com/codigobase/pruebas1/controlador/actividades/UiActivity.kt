package com.codigobase.pruebas1.controlador.actividades

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.controlador.fragmentos.BotonesUiFragment
import com.codigobase.pruebas1.controlador.fragmentos.DialogoUiFragment
import kotlinx.android.synthetic.main.activity_ui.*
import kotlinx.android.synthetic.main.barra_superior.*
import kotlinx.android.synthetic.main.dialog_accept.view.*

class UiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui)

        // Ocultar barra superior, si no da null
        super.getSupportActionBar()?.hide()

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

    private fun eventosBoton() {
        btnVolver.setOnClickListener { finish() }

        btnRef.setOnClickListener {
            mostrarMensaje(
                getString(R.string.ref),
                getString(R.string.ref_ui)
            )
        }

//        btnDialogo1.setOnClickListener {
//            mostrarCuadroDialogo(
//                "Cuadro de diálogo",
//                "Este es el mensaje del cuadro de dialogo",
//                "Aceptar"
//            )
//        }

        menu_inferior_ui.setOnNavigationItemSelectedListener { elementoSeleccionado: MenuItem ->

            if (elementoSeleccionado.itemId == R.id.menu_botones) {
                mostrarFragmentoSeleccionado(BotonesUiFragment())
            } else if (elementoSeleccionado.itemId == R.id.menu_dialogos) {
                mostrarFragmentoSeleccionado(DialogoUiFragment())
            }

            true

        }
    }

    private fun mostrarFragmentoSeleccionado(fragmento: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.contenidoFragmento, fragmento)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarMensaje(titulo: String, mensaje: String) {
        val cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton(getString(R.string.aceptar), null)
        cuadroDialogo.show()
    }

    private fun mostrarCuadroDialogo(titulo: String, mensaje: String, boton: String) {
        // Construir el cuadro de dialogo
        val constructor: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val vistaDialogo: View = inflater.inflate(R.layout.dialog_accept, null)
        constructor.setView(vistaDialogo)
        val cuadroDialogo: AlertDialog = constructor.create()

        // Hacer que el fondo del layout sea invisible
        cuadroDialogo.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Definir los textos
        vistaDialogo.lblDialogTitulo.text = titulo
        vistaDialogo.lblDialogMensaje.text = mensaje
        val btnDialogoAceptar: Button = vistaDialogo.btnDialogAceptar
        btnDialogoAceptar.text = boton

        // Controlar los eventos de boton
        btnDialogoAceptar.setOnClickListener {
            mostrarToast("Pulsado $boton")
            cuadroDialogo.dismiss()
        }

        // Mostrar el cuadro de dialogo
        cuadroDialogo.show()
    }
}

















