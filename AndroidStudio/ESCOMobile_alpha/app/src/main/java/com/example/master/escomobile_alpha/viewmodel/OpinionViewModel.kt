package com.example.master.escomobile_alpha.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.master.escomobile_alpha.modelo.entidad.Comentario
import com.example.master.escomobile_alpha.util.request.RequestManager

class OpinionViewModel: ViewModel() {

	companion object {
		val OPINION_PUBLICADA : String = "¡Tu opinión ha sido publicada!"
		val OPINION_NO_PUBLICADA : String = "Tu opinión no pudo ser publicada"
	}

	fun sendComments( comentario: Comentario, completionHandler: ( msg: String ) -> Unit ) {
		val url = RequestManager.URL_COMENTARIOS
		val params = hashMapOf<String, Any>( "request" to "setComentario", "boleta" to comentario.boleta,
				"correo" to comentario.correo, "nombre_prof" to comentario.nombreProfesor,
				"puntuacion" to comentario.puntuacion, "comentario" to comentario.comentario,
				"modelo" to comentario.getModelo(), "SO" to comentario.getSistemaOp() )

		RequestManager().postRequest( url, params ) { jsonResponse ->
			if( jsonResponse.optInt("code") == 200 ) {
				val status = jsonResponse.optJSONObject("data" ).optJSONObject( "comentario" ).optBoolean("status" )
				if( status ) {
					//Bien
					completionHandler( OPINION_PUBLICADA )

				} else {
					//Error
					completionHandler( OPINION_NO_PUBLICADA )
				}

			} else {
				//Hubo error
				completionHandler( OPINION_NO_PUBLICADA )
				println("ERROR ${ jsonResponse.opt("description") }")
			}
		}
	}
}