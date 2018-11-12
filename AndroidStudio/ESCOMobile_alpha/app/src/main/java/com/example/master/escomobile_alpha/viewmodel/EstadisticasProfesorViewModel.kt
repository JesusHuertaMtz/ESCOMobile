package com.example.master.escomobile_alpha.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.master.escomobile_alpha.modelo.entidad.Comentario
import com.example.master.escomobile_alpha.util.request.RequestManager
import com.example.master.escomobile_alpha.viewholder.adapter.EstadisticasProfesorAdapter
import org.json.JSONArray

class EstadisticasProfesorViewModel(): ViewModel() {
	lateinit var estadisticasProfesorAdapter: EstadisticasProfesorAdapter

	fun init( context:Context ) {
		estadisticasProfesorAdapter = EstadisticasProfesorAdapter(context)
	}

	fun getComentariosBy( nombreProfesor: String ) {
		val url = RequestManager.URL_COMENTARIOS
		val params = hashMapOf<String, Any>( "request" to "getComentarios", "nombre_prof" to nombreProfesor )

		RequestManager().postRequest( url, params ) { jsonResponse ->
			if( jsonResponse.optInt("code") == 200 ) {
				val json = jsonResponse.optJSONObject("data" )
				val jsonComentarios = json.optJSONArray("comentarios" )

				if( jsonComentarios != null ) {
					val comentarios = parseJSONComentarios( jsonComentarios )
					estadisticasProfesorAdapter.updateComentario( comentarios )

				} else {
					val comentario = Comentario()
					comentario.comentario = json.optJSONObject("comentarios" ).optString("mensaje" )
					estadisticasProfesorAdapter.updateComentario( mutableListOf( comentario ) )
				}

			} else {
				//Hubo error
				println("ERROR ${ jsonResponse.opt("description") }")
			}
		}
	}

	private fun parseJSONComentarios( jsonArray: JSONArray ): MutableList<Comentario> {
		val comentarios = mutableListOf<Comentario>()

		for( index in 0..jsonArray.length() -1 ) {
			val comentario = Comentario( jsonArray.optJSONObject( index ) )
			comentarios.add( comentario )
		}

		return comentarios
	}
}










