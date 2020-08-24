package com.codigobase.pruebas1.controlador

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.codigobase.pruebas1.R
import com.codigobase.pruebas1.modelo.GuardarCargar

/**
 * Esta clase controla los sonidos cortos. Quita redundancia de codigo.
 * IMPORTANTE Para que suene el audio el valor GuardarCargar.datos.efectosSonido ha de estar a true.
 * IMPORTANTE 2 En onDestroy() llama al metodo liberar memoria. SoundPool carga los audios cortos en la RAM.
 * IMPORTANTE 3 Para cargar cualquier sonido tan solo pasale su ID el cual puedes obtener desde Constantes.SND_
 */
class ControlSonidos(contexto: Context) {

    // ########################### CAMPOS ###########################
    private val soundPool: SoundPool
    private val coleccionAudios: IntArray


    // ########################### AL INICIAR ###########################
    /** Inicializa los elementos para poder usarlos */
    init {
        // Crear los atributos del SoundPool
        val atributosAudio: AudioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        // Crear el Sound Pool para poder ejecutar los sonidos
        soundPool = SoundPool.Builder().setMaxStreams(20).setAudioAttributes(atributosAudio).build()

        // Crear y llenar la coleccion de sonidos para poder reproducirlos posteriormente
        coleccionAudios = intArrayOf(
            soundPool.load(contexto, R.raw.snd_borrar, 1),
            soundPool.load(contexto, R.raw.snd_interruptor, 1),
            soundPool.load(contexto, R.raw.snd_tic, 1),
            soundPool.load(contexto, R.raw.snd_maleta, 1)
        )
    }


    // ########################### METODOS ###########################
    /**
     * Permite ejecutar un audio corto, para ello hay que especificar el indice del audio, este se
     * puede obtener poniendo "Constantes.sonidoNombreSonido".
     * IMPORTANTE Ha de estar habilitado, usa el metodo "setActivo()" para habilitarlo.
     * Tambien y de manera opcional se puede especificar el volumen del canal izquierdo y el del derecho.
     */
    fun playAudio(indiceAudio: Int, audioLeft: Float = 1f, audioRight: Float = 1f) {
        // Toca el sonido especificado (por el ID de las Constantes), solo si existe y si esta habilitada la musica
        if (indiceAudio < coleccionAudios.size && GuardarCargar.datos.efectosSonido) {

            soundPool.play(
                coleccionAudios[indiceAudio],
                audioLeft,
                audioRight,
                0,
                0,
                1f
            )
        }
    }

    /**
     * Este metodo libera la memoria RAM ocupada por el soundPool.
     * Lo suyo es llamar a este metodo en el metodo "onDestroy()" de cada actividad.
     */
    fun liberarMemoria() {
        this.soundPool.release()
    }

}
