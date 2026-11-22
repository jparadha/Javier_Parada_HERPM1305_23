
package com.example.javier_parada_herpm1305_23

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listaCamaras: ListView
    private lateinit var gridCamaras: GridView
    private lateinit var adaptador: CameraAdapter

    private val camaras = mutableListOf<Camera>()

    companion object {
        private const val MENU_ELIMINAR_CAMARA = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listaCamaras = findViewById(R.id.listaCamaras)
        gridCamaras = findViewById(R.id.gridCamaras)

        cargarCamaras()

        adaptador = CameraAdapter(this, camaras) { camara ->

            val intent = Intent(
                this,
                DetalleCamaraActivity::class.java
            )

            intent.putExtra("marca", camara.marca)
            intent.putExtra("modelo", camara.modelo)
            intent.putExtra("precio", camara.precio)
            intent.putExtra("imagen", camara.imagenResId)

            startActivity(intent)
        }

        listaCamaras.adapter = adaptador
        gridCamaras.adapter = adaptador

        // Registrar menú contextual en ambas vistas
        registerForContextMenu(listaCamaras)
        registerForContextMenu(gridCamaras)
    }

    // Crear Option Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Gestionar opciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_vista_lista -> {
                mostrarVistaLista()
                return true
            }

            R.id.menu_vista_cuadricula -> {
                mostrarVistaCuadricula()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Mostrar ListView
    private fun mostrarVistaLista() {

        adaptador.cambiarModoCuadricula(false)

        gridCamaras.visibility = View.GONE
        listaCamaras.visibility = View.VISIBLE

        Toast.makeText(
            this,
            "Vista de lista",
            Toast.LENGTH_SHORT
        ).show()
    }

    // Mostrar GridView
    private fun mostrarVistaCuadricula() {

        adaptador.cambiarModoCuadricula(true)

        listaCamaras.visibility = View.GONE
        gridCamaras.visibility = View.VISIBLE

        Toast.makeText(
            this,
            "Vista de cuadrícula",
            Toast.LENGTH_SHORT
        ).show()
    }

    // Crear menú contextual
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val camaraSeleccionada = camaras[info.position]

        menu.setHeaderTitle(camaraSeleccionada.marca)

        menu.add(
            Menu.NONE,
            MENU_ELIMINAR_CAMARA,
            Menu.NONE,
            "Eliminar cámara"
        )
    }

    // Ejecutar acción del menú contextual
    override fun onContextItemSelected(item: MenuItem): Boolean {

        if (item.itemId == MENU_ELIMINAR_CAMARA) {

            val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicion = info.position

            val camaraEliminada = camaras.removeAt(posicion)

            adaptador.notifyDataSetChanged()

            Toast.makeText(
                this,
                "${camaraEliminada.marca} eliminada del catálogo",
                Toast.LENGTH_SHORT
            ).show()

            return true
        }

        return super.onContextItemSelected(item)
    }

    // Cargar cámaras iniciales
    private fun cargarCamaras() {

        camaras.add(
            Camera(
                marca = "Dahua",
                modelo = "Domo interior",
                precio = 29990,
                imagenResId = R.drawable.cam_dahua
            )
        )

        camaras.add(
            Camera(
                marca = "Hikvision",
                modelo = "Bullet exterior",
                precio = 49990,
                imagenResId = R.drawable.cam_hikvision
            )
        )

        camaras.add(
            Camera(
                marca = "EZVIZ",
                modelo = "Cámara exterior",
                precio = 39990,
                imagenResId = R.drawable.cam_ezviz
            )
        )

        camaras.add(
            Camera(
                marca = "Reolink",
                modelo = "Cámara PTZ",
                precio = 89990,
                imagenResId = R.drawable.cam_reolink
            )
        )

        camaras.add(
            Camera(
                marca = "Tapo",
                modelo = "Cámara motorizada interior",
                precio = 34990,
                imagenResId = R.drawable.cam_tapo
            )
        )
    }
}

