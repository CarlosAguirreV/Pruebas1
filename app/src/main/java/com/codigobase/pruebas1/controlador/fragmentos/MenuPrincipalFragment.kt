package com.codigobase.pruebas1.controlador.fragmentos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.controlador.ControlSonidos
import com.codigobase.pruebas1.controlador.actividades.*
import com.codigobase.pruebas1.modelo.Constantes
import com.codigobase.pruebas1.modelo.GuardarCargar
import kotlinx.android.synthetic.main.elementos_menu.view.*
import kotlinx.android.synthetic.main.fragment_menu_principal.view.*

/**
 * Este fragmento muestra las areas de esta aplicacion.
 */
class MenuPrincipalFragment : Fragment() {

    // ########################### CAMPOS ###########################
    private var bloquear: Boolean = false
    private lateinit var coleccionMarcadores: Array<TextView>
    private lateinit var root: View
    private lateinit var controlSonidos: ControlSonidos


    // ########################### AL CREAR LA VISTA ###########################
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento.
        root = inflater.inflate(R.layout.fragment_menu_principal, container, false)

        // Obtener los elementos para poder dirigirme a ellos.
        coleccionMarcadores = arrayOf(
            root.mSensores,
            root.mUI,
            root.mHilos,
            root.mMultimedia,
            root.mBD,
            root.mExternos
        )

        // Crear el controlador de sonidos.
        controlSonidos = ControlSonidos(root.context)

        // Eventos de boton.
        eventosDeBoton()

        return root
    }


    // ########################### METODO ON RESUME ###########################
    override fun onResume() {
        super.onResume()
        bloquear = false
    }


    // ########################### METODO ESTATICO ###########################
    companion object {
        /**
         * Este metodo se emplea para crear una nueva instancia de
         * este fragmento.
         *
         * @return Una nueva instancia del fragmento MenuPrincipalFragment.
         */
        @JvmStatic
        fun newInstance() = MenuPrincipalFragment()
    }

    // ########################### METODOS ###########################
    /** Lo que sucedera al pulsar algun boton. */
    private fun eventosDeBoton() {
        // Al seleccionar algun elemento del spinner
        root.spnIr?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Quitar el fondo a todos los marcadores
                for (marcador in coleccionMarcadores) {
                    marcador.background = null
                }

                // Si se ha seleccionado algo
                if (position != 0) {
                    controlSonidos.playAudio(Constantes.SND_TIC)

                    // Dirigir el scrollbar al marcador seleccionado
                    root.scrMenu?.scrollTo(0, coleccionMarcadores[position - 1].top)

                    // Cambiar el color del marcador seleccionado
                    coleccionMarcadores[position - 1].background =
                        ContextCompat.getDrawable(root.context, R.color.colorMarcador)
                }
            }
        }

        root.btnFantasma.setOnClickListener { showActividad(FantasmaActivity::class.java) }
        root.btnLuzSensor.setOnClickListener { showActividad(LuzSensorActivity::class.java) }
        root.btnUI.setOnClickListener { showActividad(UiActivity::class.java) }
        root.btnCorrutinas.setOnClickListener { showActividad(CorrutinasActivity::class.java) }
        root.btnSndMsc.setOnClickListener { showActividad(SonidosMusicaActivity::class.java) }
        root.btnGuardarCargar.setOnClickListener { showActividad(DatosActivity::class.java) }
        root.btnBd.setOnClickListener { showActividad(BdActivity::class.java) }
    }

    private fun showActividad(clase: Class<*>) {
        if (!bloquear) {
            controlSonidos.playAudio(Constantes.SND_INTERRUPTOR)

            val intent = Intent(root.context, clase)
            startActivity(intent)

            // Animaciones de Atif Pervaiz - https://github.com/AtifSayings/Animatoo
            Animatoo.animateSlideLeft(root.context)
        }
    }

}