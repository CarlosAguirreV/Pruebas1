package com.codigobase.pruebas1.controlador.actividades

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codigobase.pruebas1.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_anuncios.*
import kotlinx.android.synthetic.main.barra_superior.*

class AnunciosActivity : AppCompatActivity(), RewardedVideoAdListener {

    // ########################### CAMPOS ###########################
    private lateinit var videoBonificadoAd: RewardedVideoAd
    private var monedas: Int = 0
    private val bonificacionBannerMostrado = 10
    private val bonificacionBannerAbierto = 50
    private val bonificacionVideo = 100
    private var cadenaConsola: String = ""


    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anuncios)

        // Ocultar barra superior, si no da null.
        supportActionBar?.hide()

        // Definir titulo
        lblTituloBarra.text = getString(R.string.publicidad)

        // -- Google AdMob (publicidad) --
        // IMPORTANTE - Indica previamente en el archivo Manifest el ID de la aplicacion.
        // Recuerda que para hacer pruebas has de usar el ID que aporta Google, de lo contrario
        // podrian suspenderte la cuenta.
        // Para el banner: ca-app-pub-3940256099942544/6300978111
        // Para el bonificado: ca-app-pub-3940256099942544/5224354917
        MobileAds.initialize(this) {}

        // Anuncio bonificado.
        videoBonificadoAd = MobileAds.getRewardedVideoAdInstance(this)
        videoBonificadoAd.rewardedVideoAdListener = this
        cargarAnuncioBonificado()

        // Banner inferior.
        val adRequest = AdRequest.Builder().build()
        bannerInferior.loadAd(adRequest)

        // Refrescar el texto con las 'monedas' ganadas.
        addMonedas(0)

        // Controlar los eventos de boton.
        eventosBoton()
    }


    // ########################### METODO ON PAUSE ###########################
    override fun onPause() {
        super.onPause()
        videoBonificadoAd.pause(this)
    }


    // ########################### METODO ON RESUME ###########################
    override fun onResume() {
        super.onResume()
        videoBonificadoAd.resume(this)
    }


    // ########################### METODO ON DESTROY ###########################
    override fun onDestroy() {
        super.onDestroy()
        videoBonificadoAd.destroy(this)
    }


    /** Carga el anuncio bonificado, asegurate de usar el ID del bloque de anuncios correspondiente. */
    private fun cargarAnuncioBonificado() {
        videoBonificadoAd.loadAd(
            "ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build()
        )
    }


    /** Eventos de boton. */
    private fun eventosBoton() {
        // Al pulsar en el boton volver.
        btnVolver.setOnClickListener { finish() }

        // Al pulsar en el boton que cargara el anuncio de bonificacion.
        btnMostrarAnuncioBonif.setOnClickListener {
            if (videoBonificadoAd.isLoaded) {
                videoBonificadoAd.show()
            }
        }

        // Banner inferior.
        bannerInferior.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                addCadenaConsola(getString(R.string.bnMostrado) + bonificacionBannerMostrado.toString())
                addMonedas(bonificacionBannerMostrado)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                addCadenaConsola(getString(R.string.bnFallido))
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                addCadenaConsola(getString(R.string.bnAbierto) + bonificacionBannerAbierto.toString())
                addMonedas(bonificacionBannerAbierto)
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                addCadenaConsola(getString(R.string.bnTocado))
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                addCadenaConsola(getString(R.string.bnCancelado))
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                addCadenaConsola(getString(R.string.bnCerrado))
            }
        }

    }


    // Metodos del anuncio por bonificacion (video omitible).
    override fun onRewardedVideoAdLoaded() {
        addCadenaConsola(getString(R.string.bfCargado))
        btnMostrarAnuncioBonif.isEnabled = true
    }

    override fun onRewardedVideoAdOpened() {
        addCadenaConsola(getString(R.string.bfAbierto))
    }

    override fun onRewardedVideoStarted() {
        addCadenaConsola(getString(R.string.bfComenzado))
    }

    override fun onRewardedVideoAdClosed() {
        addCadenaConsola(getString(R.string.bfCerrado))
        btnMostrarAnuncioBonif.isEnabled = false
        cargarAnuncioBonificado()
    }

    override fun onRewarded(p0: com.google.android.gms.ads.reward.RewardItem?) {
        addCadenaConsola(getString(R.string.bfVisto) + bonificacionVideo.toString())
        addMonedas(bonificacionVideo)
    }

    override fun onRewardedVideoAdLeftApplication() {
        addCadenaConsola(getString(R.string.bfDejado))
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        addCadenaConsola(getString(R.string.bfNoCargado))
    }

    override fun onRewardedVideoCompleted() {
        addCadenaConsola(getString(R.string.bfCompletado))
    }

    private fun addMonedas(monedasGanadas: Int) {
        monedas += monedasGanadas
        lblMonedas.text = monedas.toString()
    }

    private fun addCadenaConsola(cadena: String) {
        cadenaConsola += "- $cadena \n"
        lblConsola.text = cadenaConsola
    }

    /** Muestra un Toast con el mensaje especificado. */
    private fun mostrarToast(mensaje: String?) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

}
