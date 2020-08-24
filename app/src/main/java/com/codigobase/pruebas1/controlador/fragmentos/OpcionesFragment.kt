package com.codigobase.pruebas1.controlador.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.controlador.ControlSonidos
import com.codigobase.pruebas1.modelo.Constantes
import com.codigobase.pruebas1.modelo.GuardarCargar
import kotlinx.android.synthetic.main.barra_titulo.view.*
import kotlinx.android.synthetic.main.fragment_opciones.view.*
import java.io.File


/**
 * Este fragmento muestra las opciones de la aplicacion.
 */
class OpcionesFragment : Fragment() {

    // ########################### CAMPOS ###########################
    private lateinit var guardarCargar: GuardarCargar
    private lateinit var filesDir: File
    private lateinit var root: View
    private lateinit var controlSonidos: ControlSonidos


    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Como no puedo acceder desde aqui a "filesDir" lo obtengo mediante parametros
        arguments?.let { filesDir = it.getSerializable("rutaGuardado") as File }
    }


    // ########################### AL CREAR LA VISTA ###########################
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        root = inflater.inflate(R.layout.fragment_opciones, container, false)

        // Poner titulo a las opciones
        root.lblTituloBarra.text = getString(R.string.opciones)

        // Crear una instancia de GuardarCargar para poder trabajar con ella
        guardarCargar = GuardarCargar(filesDir)

        // Refrescar elementos del fragmento acorde a los datos de guardado
        refrescarOpciones()

        // Crear el controlador de sonidos
        controlSonidos = ControlSonidos(root.context)

        // Controlar los eventos de boton
        eventosBoton()

        return root
    }


    // ########################### METODO ON DESTROY ###########################
    override fun onDestroy() {
        super.onDestroy()

        // Guardar cambios al destruir este fragmento (hay muchas mas formas de hacerlo)
        if (!guardarCargar.guardar()) mostrarToast(getString(R.string.guardadoFallido))
    }


    // ########################### METODO ESTATICO ###########################
    companion object {
        /**
         * Este metodo se emplea para crear una nueva instancia de
         * este fragmento.
         *
         * @return Una nueva instancia del fragmento OpcionesFragment.
         */
        @JvmStatic
        fun newInstance(rutaGuardado: File) = OpcionesFragment().apply {
            arguments = Bundle().apply { putSerializable("rutaGuardado", rutaGuardado) }
        }
    }


    // ########################### METODOS ###########################
    /** Lo que sucedera al pulsar algun boton. */
    private fun eventosBoton() {
        root.swEfectos.setOnClickListener {
            GuardarCargar.datos.efectosSonido = root.swEfectos.isChecked
            controlSonidos.playAudio(Constantes.SND_TIC)
        }
        root.btnRestablecer.setOnClickListener {
            guardarCargar.restablecerDatos()
            refrescarOpciones()
            controlSonidos.playAudio(Constantes.SND_BORRAR)
        }
    }

    /** Refresca las opciones acorde a lo que esta guardado. */
    private fun refrescarOpciones() {
        root.swEfectos.isChecked = GuardarCargar.datos.efectosSonido
    }

    /** Muestra un mensaje Toast. */
    private fun mostrarToast(mensaje: String) {
        Toast.makeText(root.context, mensaje, Toast.LENGTH_SHORT).show()
    }

}