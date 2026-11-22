
package com.example.javier_parada_herpm1305_23

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CameraAdapter(
    private val contexto: Context,
    private val listaCamaras: MutableList<Camera>,
    private val alSeleccionarCamara: (Camera) -> Unit
) : BaseAdapter() {

    private var modoCuadricula = false

    override fun getCount(): Int {
        return listaCamaras.size
    }

    override fun getItem(position: Int): Camera {
        return listaCamaras[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Permite alternar entre lista y cuadrícula
    fun cambiarModoCuadricula(activar: Boolean) {
        modoCuadricula = activar
        notifyDataSetChanged()
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (modoCuadricula) 1 else 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val tipoLayout = if (modoCuadricula) {
            R.layout.item_camera_grid
        } else {
            R.layout.item_camera
        }

        val vista: View
        val holder: ViewHolder

        if (convertView == null) {

            vista = LayoutInflater.from(contexto)
                .inflate(tipoLayout, parent, false)

            holder = ViewHolder(
                vista.findViewById(R.id.imgCamara),
                vista.findViewById(R.id.txtMarca),
                vista.findViewById(R.id.txtModelo)
            )

            vista.tag = holder

        } else {

            vista = convertView
            holder = vista.tag as ViewHolder
        }

        val camara = getItem(position)

        holder.imagen.setImageResource(camara.imagenResId)
        holder.marca.text = camara.marca
        holder.modelo.text = camara.modelo

        holder.imagen.contentDescription =
            "${camara.marca} ${camara.modelo}"

        holder.imagen.setOnClickListener {
            alSeleccionarCamara(camara)
        }

        return vista
    }

    private class ViewHolder(
        val imagen: ImageView,
        val marca: TextView,
        val modelo: TextView
    )
}
