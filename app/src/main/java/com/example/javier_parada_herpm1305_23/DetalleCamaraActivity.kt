package com.example.javier_parada_herpm1305_23

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.NumberFormat
import java.util.Locale

class DetalleCamaraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_camara)

        // Configurar Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarDetalle)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalle de cámara"

        // Referencias a los elementos visuales
        val imagen = findViewById<ImageView>(R.id.imgDetalleCamara)
        val marca = findViewById<TextView>(R.id.txtDetalleMarca)
        val modelo = findViewById<TextView>(R.id.txtDetalleModelo)
        val precio = findViewById<TextView>(R.id.txtDetallePrecio)

        // Recibir datos enviados desde MainActivity
        val marcaRecibida = intent.getStringExtra("marca") ?: ""
        val modeloRecibido = intent.getStringExtra("modelo") ?: ""
        val precioRecibido = intent.getIntExtra("precio", 0)
        val imagenRecibida = intent.getIntExtra("imagen", 0)

        // Mostrar información de la cámara
        marca.text = marcaRecibida
        modelo.text = modeloRecibido

        val formatoPrecio =
            NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CL"))

        precio.text = getString(
            R.string.precio_camara,
            formatoPrecio.format(precioRecibido)
        )

        if (imagenRecibida != 0) {
            imagen.setImageResource(imagenRecibida)
        }
    }

    // Acción del botón Up
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}