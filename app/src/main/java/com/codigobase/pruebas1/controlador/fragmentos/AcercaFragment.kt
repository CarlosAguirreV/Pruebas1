package com.codigobase.pruebas1.controlador.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codigobase.pruebas1.R
import kotlinx.android.synthetic.main.barra_titulo.view.*

/**
 * Fragmento que muestra informacion acerca de esta aplicacion.
 * Es llamado desde MainActiviy.
 */
class AcercaFragment : Fragment() {

    // ########################### AL CREAR LA VISTA ###########################
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_acerca, container, false)

        // Cambiar titulo
        root.lblTituloBarra.text = getString(R.string.acerca)

        return root
    }


    // ########################### METODO ESTATICO ###########################
    companion object {
        /**
         * Este metodo se emplea para crear una nueva instancia de
         * este fragmento.
         *
         * @return Una nueva instancia del fragmento AcercaFragment.
         */
        @JvmStatic
        fun newInstance() = AcercaFragment()
    }
}