package com.example.master.escomobile_alpha.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.master.escomobile_alpha.databinding.ComentarioRecyclerViewBinding

class EstadisticasProfesorAdapter( contex: Context ): RecyclerView.Adapter<EstadisticasProfesorViewHolder>() {
	var comentarios = mutableListOf( "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc. ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc. ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc. ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer suscipit rhoncus sollicitudin. Praesent vel porttitor purus, id massa nunc. ")
	private var layoutInflater: LayoutInflater

	init {
		layoutInflater = LayoutInflater.from( contex )
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadisticasProfesorViewHolder {
		val recyclerView = ComentarioRecyclerViewBinding.inflate( layoutInflater, parent, false )

		return EstadisticasProfesorViewHolder( recyclerView.root, recyclerView )
	}

	override fun onBindViewHolder(holder: EstadisticasProfesorViewHolder, position: Int) {
		holder.comentarioRecyclerViewBinding.txtComentario.text = comentarios[position]
	}

	override fun getItemCount(): Int {
		return comentarios.size
	}

	fun updateComentario( comentarios: MutableList<String> ) {
		this.comentarios = comentarios
		notifyDataSetChanged()
	}
}