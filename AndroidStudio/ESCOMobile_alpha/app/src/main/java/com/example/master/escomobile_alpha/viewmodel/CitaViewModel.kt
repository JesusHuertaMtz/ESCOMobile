package com.example.master.escomobile_alpha.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.master.escomobile_alpha.modelo.entidad.Cita
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.util.request.RequestManager

class CitaViewModel: ViewModel() {

	fun getCitasPendientes( user: Usuario, completionHandler: ( List<String>, HashMap<String, MutableList<Cita>> ) -> Unit ) {
		getCitas( "Pendiente", user.boleta ?: "", user.esProfesor ?: false ) {
			fechas, citas ->

			completionHandler( fechas, citas )
		}
	}

	fun getCitasAgendadas( user: Usuario, completionHandler: ( List<String>, HashMap<String, MutableList<Cita>> ) -> Unit ) {
		getCitas( "Agendada", user.boleta ?: "", user.esProfesor ?: false ) {
			fechas, citas ->

			completionHandler( fechas, citas )
		}
	}

	fun getCitasPasadas( user: Usuario, completionHandler: ( List<String>, HashMap<String, MutableList<Cita>> ) -> Unit ) {
		getCitas( "Pasada", user.boleta ?: "", user.esProfesor ?: false ) {
			fechas, citas ->

			completionHandler( fechas, citas )
		}
	}

	fun getCitasCanceladas( user: Usuario, completionHandler: ( List<String>, HashMap<String, MutableList<Cita>> ) -> Unit ) {
		getCitas( "Cancelada", user.boleta ?: "", user.esProfesor ?: false ) {
			fechas, citas ->

			completionHandler( fechas, citas )
		}
	}

	private fun getCitas( estado: String, id: String, esProfesor: Boolean,
	                      completionHandler: ( List<String>, HashMap<String, MutableList<Cita>> ) -> Unit ) {
		val url = RequestManager.URL_CITA
		val params = hashMapOf<String, Any>( "request" to "getCitas",
					"id" to id, "estado" to estado, "esProfesor" to esProfesor )
		val rm = RequestManager()

		rm.postRequest( url, params ) { jsonResponse ->
			if( jsonResponse.optInt("code" ) == 200 ) {
				val citas = jsonResponse.optJSONObject("data" ).optJSONArray("citas" )
				val diasDeCita = mutableListOf<String>()
				val citasPorFecha = hashMapOf<String, MutableList<Cita>>()

				for( index in 0..citas.length() - 1 ) {
					val jsonCita = citas.optJSONObject( index )
					val fecha = jsonCita.optString("fecha" )
					val citasFecha = jsonCita.optJSONArray( "citas_fecha" )
					val citas = mutableListOf<Cita>()
					diasDeCita.add( fecha )

					for( citaIndex in 0..citasFecha.length() - 1 ) {
						val jsonCitaFecha = citasFecha.optJSONObject( citaIndex )

						val cita = Cita( jsonCitaFecha )
						cita.fecha = fecha
						////TODO: Añadir las citas a un hashmap y las fecha a un listof
						citas.add( cita )
					}

					citasPorFecha.put( fecha, citas )
				}

				completionHandler( diasDeCita, citasPorFecha )

			} else {
				//hubo un error.
				completionHandler(listOf(), hashMapOf() )
			}
		}
	}
}