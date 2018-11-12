package com.example.master.escomobile_alpha.modelo.entidad

import com.example.master.escomobile_alpha.util.model_name.DeviceModel
import org.json.JSONObject

class Comentario() {
	var boleta : String = ""
	var correo : String = ""
	var nombreProfesor : String = ""
	var puntuacion : String = ""
	var comentario : String = ""
	var nombreAlumno : String = ""
	var fecha : String = ""

	constructor( jsonObject: JSONObject ): this() {
		this.nombreAlumno = jsonObject.optString("nombre_alumno" )
		this.comentario = jsonObject.optString("comentario" )
		this.fecha = jsonObject.optString("fecha_pub" )
	}

	fun getModelo(): String {
		return DeviceModel.getModel()
	}

	fun getSistemaOp(): String {
		return DeviceModel.getDeviceModelName()
	}
}