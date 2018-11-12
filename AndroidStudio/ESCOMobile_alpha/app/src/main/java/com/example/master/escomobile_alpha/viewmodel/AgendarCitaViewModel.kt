package com.example.master.escomobile_alpha.viewmodel

import android.R
import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.master.escomobile_alpha.modelo.entidad.Cita
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.modelo.logica_negocio.BSCita
import com.example.master.escomobile_alpha.util.request.RequestManager

class AgendarCitaViewModel: ViewModel() {
	private var bsCita: BSCita

	init {
		bsCita = BSCita()
	}

	fun setupSpinner( activity: Activity, spinner: Spinner, nombreProfesor: String, completionHandler: (status: Boolean) -> Unit ) {
		bsCita.getProfesoresRegistrados { profesores ->
			if( profesores != null ) {
				if( nombreProfesor.isEmpty() ) {
					val adapter = ArrayAdapter<Usuario>( activity, R.layout.simple_spinner_dropdown_item, profesores )
					spinner.adapter = adapter

				} else {
					//Se selecciono a un profesor y se paso como parametro al fragment
					val filtroProfesores = filtrarProfesores( profesores, nombreProfesor )

					if( filtroProfesores.isEmpty() ) {
						//El profesor no esta registrado. HAY QUE TENER EN CUENTA QUE PUEDE
						//QUE EL NOMBRE CON EL QUE SE REGISTRE EL PROFESOR AFECTA LA BUSQUEDA
						Toast.makeText( activity, "El profesor no está registrado", Toast.LENGTH_LONG ).show()
						completionHandler( false )

					} else {
						val adapter = ArrayAdapter<Usuario>( activity, R.layout.simple_spinner_dropdown_item, filtroProfesores )
						spinner.adapter = adapter
					}
				}

			} else {
				val adapter = ArrayAdapter<Usuario>( activity, R.layout.simple_spinner_dropdown_item, mutableListOf<Usuario>() )
				spinner.adapter = adapter
				Toast.makeText( activity, "No hay profesores registrados", Toast.LENGTH_LONG ).show()
			}

			completionHandler( true )
		}
	}

	private fun filtrarProfesores( profesores: MutableList<Usuario>, nombreProfesor: String ): MutableList<Usuario> {
		val filtroProfesores = mutableListOf<Usuario>()
		val name = removeAcentos( nombreProfesor )

		for( profesor in profesores ) {
			val nombre = removeAcentos( profesor.primerAp + " " + profesor.segundoAp + " " + profesor.nombre )

			println("$nombre == $name")
			if( nombre.equals( name ) ) {
				filtroProfesores.add( profesor )
			}
		}

		return filtroProfesores
	}

	private fun removeAcentos( cadena: String ): String {
		val name = cadena.toLowerCase().replace( Regex("[aeiouáéíóúñ ]"), "" )

		return name
	}

	fun agendarCita( cita: Cita, completionHandler: ( status: Boolean ) -> Unit ) {
		val params = hashMapOf<String, Any>( "request" to "solicitarAgendarCita", "tipo_cita" to cita.tipo,
									"id_profesor" to cita.idProfesor,
									"id_alumno" to cita.idAlumno, "fecha" to cita.fecha,
									"hora" to cita.hora, "motivo" to cita.motivo )
		val rm = RequestManager()

		rm.postRequest( RequestManager.URL_CITA, params ) { jsonResponse ->
			if( jsonResponse.optInt("code") == 200 ) {
				//Cita registrada
				completionHandler( true )

			} else {
				//Error al registrar la cita
				completionHandler( false )
			}
		}
	}

	fun getCita(): MutableLiveData<Cita> {
		return bsCita.cita
	}

	fun updateCita( cita: Cita ) {
		bsCita.validarCita( cita )
	}
}