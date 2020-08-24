package com.codigobase.pruebas1.controlador.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codigobase.pruebas1.R

/**
 * Fragmento 1 de UIActivity.
 * Es llamado desde UIActivity.
 */
class BotonesUiFragment : Fragment() {

    // ########################### AL CREAR LA VISTA ###########################
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_botones_ui, container, false)
    }

}
