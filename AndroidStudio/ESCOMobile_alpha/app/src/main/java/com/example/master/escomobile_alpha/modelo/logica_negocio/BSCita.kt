package com.example.master.escomobile_alpha.modelo.logica_negocio

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import com.example.master.escomobile_alpha.modelo.entidad.Cita
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.util.dialog.DialogUtil
import com.example.master.escomobile_alpha.util.request.RequestManager

class BSCita {
	val cita = MutableLiveData<Cita>()

	fun getProfesoresRegistrados( completionHandler: ( MutableList<Usuario>? ) -> Unit ) {
		val url = RequestManager.URL_CITA
		val params = hashMapOf<String, Any>( "request" to "getProfesoresRegistrados" )

		RequestManager().postRequest( url, params ) { jsonResponse ->
			if( jsonResponse.optInt("code" )  == 200 ) {
				val jsonProfesores = jsonResponse.optJSONObject("data" )
									.optJSONArray("profesores_registrados" )
				val profesores = mutableListOf<Usuario>()

				for( index in 0..jsonProfesores.length() - 1 ) {
					val json = jsonProfesores.optJSONObject( index )
					val profesor = Usuario( json )

					profesores.add( profesor )
				}

				completionHandler( profesores )

			} else {
				println("ERROR ${ jsonResponse.opt("description") }")
				completionHandler( null )
			}
		}
	}

	fun validarCita( cita: Cita ) {
		val isFullFormIncomplete = cita.tipo.isEmpty() || cita.idProfesor.isEmpty()
								|| cita.idAlumno.isEmpty() || cita.fecha.isEmpty()
								|| cita.hora.isEmpty() || cita.motivo.isEmpty()

		if( isFullFormIncomplete ) {
			cita.tipo = "CAMPO_VACIO"

		} else {
			//TODO: Validar a fecha y hora de la cita
		}

		this.cita.value = cita
	}

	fun agendarCita( activity: Activity, cita: Cita, completionHandler: (Cita?) -> Unit ) {
		val rm = RequestManager()
		val title = "Agendar cita"
		val msg = "¿Estás seguro de que deseas aceptar la solicitud de cita?"
		DialogUtil.createAlertPositive( activity, title, msg, DialogInterface.OnClickListener {
			dialog, which ->

			val url = RequestManager.URL_CITA
			val msgNotification = "Tu solicitud de cita con ${cita.idProfesor} del día ${cita.fecha} a las ${cita.hora} hrs, ha sido aceptada"
			val params = hashMapOf<String, Any>("id_cita" to cita.idCita,  "request" to "cambiarEstadoCita",
					"estado_cita" to "Agendada", "id_usuario_notificar" to cita.idAlumno,
					"msg_notificacion" to msgNotification, "es_profesor" to true ) //el profesor es el único que puede aceptar

			rm.postRequest( url, params ) { jsonResponse ->
				if( jsonResponse.optInt("code" ) == 200 ) {
					val msgSuccess = "La cita fue agendada correctamente."
					Toast.makeText( activity, msgSuccess, Toast.LENGTH_SHORT ).show()
					completionHandler( cita )

				} else {
					val msgError = "La cita no pudo ser agendada. Verifica la información."
					Toast.makeText( activity, msgError, Toast.LENGTH_SHORT ).show()
					completionHandler( null )
				}
			}
		}).show()
	}

	fun cancelarCita( activity: Activity, cita: Cita, esProfesor: Boolean, completionHandler: (Cita?) -> Unit ) {
		val rm = RequestManager()
		val title = "Cancelar cita"
		val msg = "¿Estás seguro de que deseas cancelar la cita?"
		val usuarioANotificar = if( esProfesor ) cita.idAlumno else cita.idProfesor
		val usuarioQueCancela = if( !esProfesor ) cita.idAlumno else cita.idProfesor

		DialogUtil.createAlertPositive( activity, title, msg, DialogInterface.OnClickListener {
			dialog, which ->

			val url = RequestManager.URL_CITA
			val msgNotification = "Tu solicitud de cita con $usuarioQueCancela del día ${cita.fecha} a las ${cita.hora} hrs, ha sido cancelada"
			val params = hashMapOf<String, Any>("id_cita" to cita.idCita,  "request" to "cambiarEstadoCita",
					"estado_cita" to "Cancelada", "id_usuario_notificar" to usuarioANotificar,
					"msg_notificacion" to msgNotification, "es_profesor" to esProfesor )

			rm.postRequest( url, params ) { jsonResponse ->
				if( jsonResponse.optInt("code" ) == 200 ) {
					val msgSuccess = "La cita fue cancelada correctamente."
					Toast.makeText( activity, msgSuccess, Toast.LENGTH_SHORT ).show()
					completionHandler( cita )

				} else {
					val msgError = "La cita no pudo ser cancelada."
					Toast.makeText( activity, msgError, Toast.LENGTH_SHORT ).show()
					completionHandler( null )
				}
			}
		}).show()
	}
}