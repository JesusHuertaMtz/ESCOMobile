package com.example.master.escomobile_alpha.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.master.escomobile_alpha.util.request.RequestManager
import com.example.master.escomobile_alpha.viewholder.EstadisticasProfesorAdapter

class EstadisticasProfesorViewModel(): ViewModel() {
	lateinit var estadisticasProfesorAdapter: EstadisticasProfesorAdapter

	fun init( context:Context ) {
		estadisticasProfesorAdapter = EstadisticasProfesorAdapter( context )
	}

	fun getComentariosBy( nombreProfesor: String ) {
		val url = RequestManager.URL_SQL_SERVER
		val params = hashMapOf<String, Any>( "request" to "getComentarios", "nombre_prof" to nombreProfesor )

		RequestManager().postRequest( url, params ) { jsonResponse ->
			if( jsonResponse.optInt("code") == 200 ) {


			} else {
				//Hubo error
				println("ERROR ${ jsonResponse.opt("description") }")
			}
		}
	}

}