package com.example.master.escomobile_alpha.modelo.entidad

import org.json.JSONObject
import java.io.Serializable

class Cita(): Serializable {
	var idCita : String = ""
	var tipo : String = ""
	var idProfesor : String = ""
	var idAlumno : String = ""
	var fecha : String = ""
	var hora : String = ""
	var motivo : String = ""

	constructor( jsonObject: JSONObject ): this() {
		this.idCita = jsonObject.optString("idCita" )
		this.tipo   = jsonObject.optString("tipoCita" )
		this.idProfesor = jsonObject.optString("nombre_prof" )
		this.idAlumno = jsonObject.optString("nombre_alumno" )
		//this.fecha =
		this.hora = jsonObject.optString("hora" )
		this.motivo = jsonObject.optString("motivo" )
	}
}

/*private class Cita {
	var nombreProfesor = ObservableField<String>()
	var fecha = ""
	var hora = ""
	var tipo = ""
	var motivo = ""
}*/

/*
data class Cita(
		private var _nombreProfesor: String,
		private var _fecha : String,
		private var _hora: String,
		private var _tipo: String,
		private var _motivo: String ): BaseObservable() {

	var nombreProfesor: String
	@Bindable get() = _nombreProfesor
	set(value) {
		_nombreProfesor = value
		notifyPropertyChanged( BR.nombreProfesor )
	}

	var fecha: String
		@Bindable get() = _fecha
		set(value) {
			_fecha = value
			notifyPropertyChanged( BR.nombreProfesor )
		}

	var hora: String
		@Bindable get() = _hora
		set(value) {
			_hora = value
			notifyPropertyChanged( BR.nombreProfesor )
		}

	var tipo: String
		@Bindable get() = _tipo
		set(value) {
			_tipo = value
			notifyPropertyChanged( BR.nombreProfesor )
		}

	var motivo: String
		@Bindable get() = _motivo
		set(value) {
			_motivo = value
			notifyPropertyChanged( BR.nombreProfesor )
		}
}*/