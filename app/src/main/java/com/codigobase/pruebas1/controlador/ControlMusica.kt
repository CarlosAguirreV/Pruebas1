package com.codigobase.pruebas1.controlador

import android.content.Context
import android.media.MediaPlayer

/**
 * Esta clase controla los audios mas largos (musica). Quita redundancia de codigo.
 * IMPORTANTE El audio esta por defecto deshabilitado, para habilitarlo usa el metodo setActivo().
 * IMPORTANTE 2 En onDestroy() llama al metodo liberar memoria. MediaPlayer carga los audios en la memoria ROM (no volatil).
 * La musica se guarda en el paquete "raw" dentro de "resources".
 */
class ControlMusica(contexto: Context, idRecursoMusica: Int) {

    // ########################### CAMPOS ###########################
    private val mediaPlayer: MediaPlayer = MediaPlayer.create(contexto, idRecursoMusica)
    private var habilitado: Boolean = false


    // ########################### AL INICIAR ###########################
    /** Inicializa los elementos para poder usarlos */
    init {
        mediaPlayer.isLooping = true
        mediaPlayer.setVolume(100f, 100f)
    }


    // ########################### METODOS ###########################
    /**
     * Permite definir si esta activa o no la reproduccion de musica.
     * Por defecto esta desactivado.
     */
    fun setActivo(activarAudio: Boolean) {
        habilitado = activarAudio
    }

    /** Reproduce la musica. */
    fun play() {
        if (habilitado) {
            mediaPlayer.start()
        }
    }

    /** Para la musica solo si se estaba ejecutando. */
    fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    /**
     * Vacia la memoria ROM (no volatil).
     * Lo suyo es llamar a este metodo en el metodo "onDestroy()" de cada actividad.
     */
    fun liberarMemoria() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

}
