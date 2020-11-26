package com.codigobase.pruebas1.controlador.actividades

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.controlador.ControlSonidos
import com.codigobase.pruebas1.controlador.fragmentos.AcercaFragment
import com.codigobase.pruebas1.controlador.fragmentos.MenuPrincipalFragment
import com.codigobase.pruebas1.controlador.fragmentos.OpcionesFragment
import com.codigobase.pruebas1.modelo.Constantes
import com.codigobase.pruebas1.modelo.GuardarCargar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Actividad que no tiene mas que una barra inferior de menu.
 * Va mostrando los fragmentos conforme se selecciona alguna opcion.
 */
class MainActivity : AppCompatActivity() {

    // ########################### CAMPOS ###########################
    private var bloquear: Boolean = false
    private lateinit var controlSonidos: ControlSonidos


    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        // En cuanto cargue esperar un poco y quitar el Splash Screen.
        Thread.sleep(100)
        setTheme(R.style.AppTheme)

        // Del onCreate.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ocultar barra superior, si no da null.
        supportActionBar?.hide()

        // Cargar los datos guardados.
        GuardarCargar(filesDir).cargar()

        // Crear controlador de sonidos.
        controlSonidos =
            ControlSonidos(this)

        // Eventos de boton.
        eventosDeBoton()

        // Mostrar el menu principal.
        mostrarFragmento(MenuPrincipalFragment.newInstance())
    }


    // ########################### METODO ON RESUME ###########################
    override fun onResume() {
        super.onResume()
        bloquear = false
    }


    // ########################### METODO ON DESTROY ###########################
    override fun onDestroy() {
        super.onDestroy()
        controlSonidos.liberarMemoria()
    }


    // ########################### METODOS ###########################
    /** Lo que sucedera al pulsar algun boton. */
    private fun eventosDeBoton() {
        // Definir las opciones de la barra de navegacion
        // Link: https://code.tutsplus.com/es/tutorials/how-to-code-a-bottom-navigation-bar-for-an-android-app--cms-30305
        val listenerNavigation =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                controlSonidos.playAudio(Constantes.SND_INTERRUPTOR)

                when (item.itemId) {
                    R.id.menuPrincipal -> mostrarFragmento(MenuPrincipalFragment.newInstance())
                    R.id.menuCodigo -> mostrarWeb()
                    R.id.menuOpciones -> mostrarFragmento(OpcionesFragment.newInstance(filesDir))
                    R.id.menuAcerca -> mostrarFragmento(AcercaFragment.newInstance())
                    R.id.menuCerrar -> finish()
                }
                true
            }

        // Asignar las acciones a la barra de navegacion inferior
        navigationView?.setOnNavigationItemSelectedListener(listenerNavigation)
    }

    /** Muestra un fragmento. */
    private fun mostrarFragmento(fragmento: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragmento).commit()
    }

    /** Hace un intent implicito a la pagina de GitHub donde esta este proyecto. */
    private fun mostrarWeb() {
        val paginaWeb = "https://github.com/CarlosAguirreV/Pruebas1.git"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paginaWeb))
        startActivity(intent)
    }

}
