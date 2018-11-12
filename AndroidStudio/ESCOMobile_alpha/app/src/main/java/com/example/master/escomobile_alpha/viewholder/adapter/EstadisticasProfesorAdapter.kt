package com.example.master.escomobile_alpha.viewholder.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.master.escomobile_alpha.databinding.ComentarioRecyclerViewBinding
import com.example.master.escomobile_alpha.modelo.entidad.Comentario
import com.example.master.escomobile_alpha.viewholder.EstadisticasProfesorViewHolder

class EstadisticasProfesorAdapter( contex: Context ): RecyclerView.Adapter<EstadisticasProfesorViewHolder>() {
	private var comentarios = mutableListOf<Comentario>()
	private var layoutInflater: LayoutInflater

	init {
		layoutInflater = LayoutInflater.from( contex )
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadisticasProfesorViewHolder {
		val recyclerView = ComentarioRecyclerViewBinding.inflate( layoutInflater, parent, false )

		return EstadisticasProfesorViewHolder(recyclerView.root, recyclerView)
	}

	override fun onBindViewHolder(holder: EstadisticasProfesorViewHolder, position: Int) {
		holder.comentarioRecyclerViewBinding.txtComentario.text = comentarios[position].comentario
	}

	override fun getItemCount(): Int {
		return comentarios.size
	}

	fun updateComentario( comentarios: MutableList<Comentario> ) {
		this.comentarios = comentarios
		notifyDataSetChanged()
	}
}