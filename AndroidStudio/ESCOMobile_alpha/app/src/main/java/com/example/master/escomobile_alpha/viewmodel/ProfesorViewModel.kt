package com.example.master.escomobile_alpha.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.widget.Toast
import com.example.master.escomobile_alpha.databinding.FragmentProfesorBinding
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import com.example.master.escomobile_alpha.modelo.logica_negocio.BSBusquedaEscom
import com.example.master.escomobile_alpha.util.CustomProgressBar
import com.example.master.escomobile_alpha.util.request.RequestManager
import com.example.master.escomobile_alpha.viewholder.adapter.ProfesorAdapter
import com.reddit.indicatorfastscroll.FastScrollItemIndicator

class ProfesorViewModel: ViewModel() {
	lateinit var profesorAdapter: ProfesorAdapter
	private lateinit var profesores: MutableList<Profesor>

	fun init( context: Context) {
		profesorAdapter = ProfesorAdapter(context)
	}
	
	fun setupRecyclerViewFastScroller( profesorBinding: FragmentProfesorBinding ) {
		profesorBinding.fastscroller.apply {
			setupWithRecyclerView( profesorBinding.recyclerViewProfesores, { position ->
				val item = profesorAdapter.data.get( position ).nombre
				
				FastScrollItemIndicator.Text( item.substring( 0, 1 ).toUpperCase() )
			})
		}
		
		profesorBinding.fastscrollerThumb.apply {
			setupWithFastScroller( profesorBinding.fastscroller )
		}
	}
	
	fun getProfesores() {
		val params = hashMapOf<String, Any>( "request" to "getProfesores" )
		CustomProgressBar.show( profesorAdapter.context )
		RequestManager().postRequest( RequestManager.URL_SQL_SERVER, params ) { jsonResponse ->
			if( jsonResponse.optInt("code") == 200 ) {
				val jsonProfesores = jsonResponse.optJSONObject("data" ).optJSONArray("profesores" )
				profesores = BSBusquedaEscom().parseJSONProfesores( jsonProfesores )
				showAllProfesores()
				
			} else {
				Toast.makeText( profesorAdapter.context, "${ jsonResponse.optString("description")}", Toast.LENGTH_SHORT ).show()
			}

			CustomProgressBar.getDialog()?.dismiss()
		}
	}

	fun filterProfesor( query: String ) {
		val filterProfesores = mutableListOf<Profesor>()

		for( profesor in profesores ) {
			if( profesor.nombre.toLowerCase().contains( query.toLowerCase() ) ) {
				filterProfesores.add( profesor )
			}
		}

		profesorAdapter.updateProfesores( filterProfesores )
	}

	fun showAllProfesores() {
		profesorAdapter.updateProfesores( profesores )
	}
}






