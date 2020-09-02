package com.codigobase.pruebas1.controlador.actividades

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.modelo.BD_emple_depart
import com.codigobase.pruebas1.modelo.Depart
import com.codigobase.pruebas1.modelo.Emple
import kotlinx.android.synthetic.main.activity_bd.*
import kotlinx.android.synthetic.main.barra_superior.*

class BdActivity : AppCompatActivity() {

    // ########################### CAMPOS ###########################
    private lateinit var bdEmpleDepart: BD_emple_depart
    private lateinit var coleccionEmpleados: List<Emple>
    private lateinit var coleccionDepartamentos: List<Depart>
    private lateinit var adaptadorDepart: ArrayAdapter<String>
    private lateinit var adaptadorEmple: ArrayAdapter<String>


    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bd)

        // Ocultar barra superior, si no da null
        super.getSupportActionBar()?.hide()

        // Definir titulo.
        lblTituloBarra.text = getString(R.string.bdConRoom)

        // Obtener una instancia de la BD
        bdEmpleDepart = BD_emple_depart.getInstancia(this)

        // Rellenar el spinner con los departamentos.
        rellenarSpinner()

        // Mostrar mensaje de como se usa.
        mostrarMensaje(
            getString(R.string.instruc),
            getString(R.string.ins_bd)
        )

        // Controlar los eventos de boton.
        eventosBoton()
    }


    // ########################### METODOS ###########################
    /** Controla los eventos de boton. */
    private fun eventosBoton() {
        btnVolver.setOnClickListener { finish() }
        btnInfo.setOnClickListener {
            mostrarMensaje(
                getString(R.string.info),
                getString(R.string.ref_bd)
            )
        }

        // Si se selecciona algo en el spinner se realiza la consulta.
        spnDepartamento?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                consultar()
            }
        }

        btnInsertar.setOnClickListener { insertar() }
        btnEliminar.setOnClickListener { eliminar() }

        // Al hacer clic sobre algun elemento de la lista mostrara el nombre seleccionado.
        listEmpleados.setOnItemClickListener { parent, view, position, id ->
            txtNombreEmple.setText(coleccionEmpleados[position].nombre_emple)
        }
    }

    /** Realiza una consulta sobre los empleados. */
    private fun consultar() {
        // Obtener el id del departamento seleccionado en el spinner.
        val idDepartSeleccionado =
            coleccionDepartamentos[spnDepartamento.selectedItemPosition].id_depart

        // Intentar obtener una coleccion de empleados, de lo contrario mostrar un toast con el error.
        try {
            coleccionEmpleados = bdEmpleDepart.empleDao().getEmplePorDepart(idDepartSeleccionado)
        } catch (ex: Exception) {
            mostrarToast(ex.message)
        }

        // Listar todos los empleados.
        listarEmpleados()
    }

    /** Inserta un nuevo empleado. */
    private fun insertar() {
        if (getCadenaIntroducida().isNotEmpty()) {
            if (existeEmple()) {
                mostrarToast(getString(R.string.existe_emple))
            } else {
                // Crear un id nuevo para el empleado.
                val idNuevo = bdEmpleDepart.empleDao().getMaximoId() + 1

                // Obtener el id del departamento seleccionado.
                val idDepart =
                    coleccionDepartamentos[spnDepartamento.selectedItemId.toInt()].id_depart

                // Realizar la insercion en la BD.
                bdEmpleDepart.empleDao()
                    .addEmple(Emple(idNuevo, getCadenaIntroducida(), idDepart))

                // Consultar de nuevo los empleados y refrescar la lista.
                consultar()
            }
        } else {
            mostrarToast(getString(R.string.esta_vacio))
        }
    }

    /** Elimina el empleado que tenga el nombre especificado en el TextView. */
    private fun eliminar() {
        val emple = bdEmpleDepart.empleDao().getEmplePorNombre(getCadenaIntroducida())
        if (emple == null) {
            mostrarToast(getString(R.string.no_existe_emple))
        } else {
            // Borrar empleado.
            bdEmpleDepart.empleDao().deleteEmple(emple)

            // Consultar de nuevo los empleados y refrescar la lista.
            consultar()
        }
    }


    // ########################### METODOS AUXILIARES ###########################
    /** Rellena el Spinner con los departamentos. */
    private fun rellenarSpinner() {
        // Obtener los departamentos.
        coleccionDepartamentos = bdEmpleDepart.departDao().getDepartamentos()

        // Crear y rellenar el adaptador del spinner con los departamentos.
        adaptadorDepart = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item)
        for (departamento in coleccionDepartamentos) {
            adaptadorDepart.add(departamento.nombre_depart)
        }

        // Asignar el adaptador al spinner.
        spnDepartamento.adapter = adaptadorDepart
    }

    /** Lista los empleados que estan en la List coleccion empleados. */
    private fun listarEmpleados() {
        // Crear y rellenar una lista con las cadenas de los empleados.
        val cadenasEmple: ArrayList<String> = ArrayList()
        for (empleado in coleccionEmpleados) {
            cadenasEmple.add(empleado.toString())
        }

        // Crear y rellenar el adaptador del spinner con los empleados.
        adaptadorEmple = ArrayAdapter(this, android.R.layout.simple_list_item_1, cadenasEmple)

        // Asignar el adaptador a la lista.
        listEmpleados.adapter = adaptadorEmple
    }

    /** Comprueba si existe el empleado cuyo nombre esta en el TextView. */
    private fun existeEmple(): Boolean {
        val empleado = bdEmpleDepart.empleDao().getEmplePorNombre(getCadenaIntroducida())
        return empleado != null
    }

    /** Muestra un Toast con el mensaje especificado. */
    private fun mostrarToast(mensaje: String?) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    /** Muestra un mensaje con informacion. */
    private fun mostrarMensaje(titulo: String, mensaje: String) {
        val cuadroDialogo: AlertDialog.Builder = AlertDialog.Builder(this)
        cuadroDialogo.setTitle(titulo).setMessage(mensaje)
        cuadroDialogo.setPositiveButton("Aceptar", null)
        cuadroDialogo.show()
    }

    /** Obtiene la cadena introducida en el EditText sin espacios ni saltos de linea. */
    private fun getCadenaIntroducida(): String {
        val cadena = txtNombreEmple.text.toString().trim()
        val cadenaFinal = cadena.replace("\n", "")
        return cadenaFinal
    }

}
