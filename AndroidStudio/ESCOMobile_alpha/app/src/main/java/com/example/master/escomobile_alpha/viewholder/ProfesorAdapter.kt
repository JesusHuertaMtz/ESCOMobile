package com.example.master.escomobile_alpha.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.master.escomobile_alpha.databinding.ProfesorRecyclerViewItemBinding
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import com.example.master.escomobile_alpha.modelo.logica_negocio.BSBusquedaEscom
import com.example.master.escomobile_alpha.util.CustomProgressBar
import kotlinx.android.synthetic.main.profesor_recycler_view_item.view.*

class ProfesorAdapter( val context: Context): RecyclerView.Adapter<ProfesorViewHolder>() {
	var data = mutableListOf<Profesor>()
	private var layoutInflater: LayoutInflater
	private var listener: OnProfesorSelected
	
	init {
		layoutInflater = LayoutInflater.from( context )
		
		if( context is OnProfesorSelected ) {
			listener = context
		} else {
			throw ClassCastException( context.toString() + "must implement OnProfesorSelected" )
		}
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorViewHolder {
		val profesorRecyclerViewItemBinding = ProfesorRecyclerViewItemBinding.inflate( layoutInflater, parent, false )
		
		return ProfesorViewHolder( profesorRecyclerViewItemBinding.root )
	}
	
	override fun onBindViewHolder(holder: ProfesorViewHolder, position: Int) {
		//holder.itemView.profesor_foto
		holder.itemView.nombre_profesor.text = data[position].nombre
		
		holder.itemView.setOnClickListener {
			CustomProgressBar.show( layoutInflater.context )
			BSBusquedaEscom().searchProfesorByName( data[position].nombre ) { profesor ->
				CustomProgressBar.getDialog()?.dismiss()

				profesor?.let { p ->
					p.cubiculo?.id = "Cubiculo: ${p.cubiculo?.id}"
					p.cubiculo?.academia = "Academia: ${p.cubiculo?.academia}"

					listener.onProfesorSelected( p )
				}
			}
		}
	}
	
	override fun getItemCount(): Int {
		return data.size
	}
	
	fun updateProfesores( profesores: MutableList<Profesor> ) {
		this.data = profesores
		notifyDataSetChanged()
	}
	
	interface OnProfesorSelected {
		fun onProfesorSelected( profesor: Profesor )
	}
}