package com.codigobase.pruebas1.modelo

import java.io.Serializable

data class DatosGuardado(
    // De las opciones
    var efectosSonido: Boolean,

    // De la actividad DatosGuardadoActivity
    var opcion1: Boolean,
    var opcion2: Int
) : Serializable