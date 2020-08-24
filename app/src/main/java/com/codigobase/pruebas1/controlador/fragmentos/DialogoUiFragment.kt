package com.codigobase.pruebas1.controlador.fragmentos

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.codigobase.pruebas1.R
import kotlinx.android.synthetic.main.dialog_accept.view.*
import kotlinx.android.synthetic.main.fragment_dialogo_ui.view.*

/**
 * Fragmento 2 de UIActivity.
 * Es llamado desde UIActivity.
 */
class DialogoUiFragment : Fragment() {

    // ########################### AL CREAR LA VISTA ###########################
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dialogo_ui, container, false)

        root.btnDialogo1.setOnClickListener {
            mostrarCuadroDialogo(
                getString(R.string.titulo_cuadro),
                getString(R.string.mensaje_cuadro),
                getString(R.string.aceptar),
                root.context
            )
        }

        // Inflate the layout for this fragment
        return root
    }


    // ########################### METODOS ###########################
    /** Muestra un cuadro de dialogo normal. */
    private fun mostrarCuadroDialogo(
        titulo: String,
        mensaje: String,
        boton: String,
        contexto: Context
    ) {
        // Construir el cuadro de dialogo
        val constructor: AlertDialog.Builder = AlertDialog.Builder(contexto)
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
            mostrarToast(contexto, getString(R.string.has_pulsado_btn))
            cuadroDialogo.dismiss()
        }

        // Mostrar el cuadro de dialogo
        cuadroDialogo.show()
    }

    /** Muestra un mensaje Toast. */
    private fun mostrarToast(contexto: Context, mensaje: String) {
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show()
    }


}
