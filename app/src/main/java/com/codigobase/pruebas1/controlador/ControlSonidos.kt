package com.codigobase.pruebas1.controlador

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.codigobase.pruebas1.R

/**
 * Esta clase controla los sonidos cortos. Quita redundancia de codigo.
 * IMPORTANTE El audio esta por defecto deshabilitado, para habilitarlo usa el metodo setActivo().
 * IMPORTANTE 2 En onDestroy() llama al metodo liberar memoria. SoundPool carga los audios cortos en la RAM.
 * Los sonidos se guardan en el paquete "raw" dentro de "resources".
 */
class ControlSonidos(contexto: Context) {

    // ################ CAMPOS ################
    private val soundPool: SoundPool
    private val coleccionAudios: IntArray
    private var habilitado: Boolean = false


    // ################ AL INICIAR ################
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
            soundPool.load(contexto,
                R.raw.snd_seleccion, 1),
            soundPool.load(contexto, R.raw.snd_borrar, 1),
            soundPool.load(contexto,
                R.raw.snd_interruptor, 1)
        )
    }


    // ################ METODOS ################
    /**
     * Permite definir si esta activa o no la reproduccion de audio.
     * Por defecto esta desactivado.
     */
    fun setActivo(activarAudio: Boolean) {
        habilitado = activarAudio
    }

    /**
     * Permite ejecutar un audio corto, para ello hay que especificar el indice del audio, este se
     * puede obtener poniendo "Constantes.sonidoNombreSonido".
     * IMPORTANTE Ha de estar habilitado, usa el metodo "setActivo()" para habilitarlo.
     * Tambien y de manera opcional se puede especificar el volumen del canal izquierdo y el del derecho.
     */
    fun playAudio(indiceAudio: Int, audioLeft: Float = 1f, audioRight: Float = 1f) {
        // Toca el sonido especificado (por el ID de las Constantes), solo si existe y si esta habilitada la musica
        if (indiceAudio < coleccionAudios.size && habilitado) {

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
