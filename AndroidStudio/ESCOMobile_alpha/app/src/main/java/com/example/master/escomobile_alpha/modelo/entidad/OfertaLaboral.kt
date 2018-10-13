package com.example.master.escomobile_alpha.modelo.entidad

import org.json.JSONObject
import java.io.Serializable

class OfertaLaboral(): Serializable {
    var idOferta : Int? = null
    var puesto: String? = null
    var requisitos: String? = null
    var sueldo : String? = null
    var horario: String? = null
    var fecha_publicacion : String? = null

    constructor( idOferta: Int, puesto: String, requisitos: String, sueldo: String, horario: String, fecha_publicacion: String ) : this() {
        this.idOferta = idOferta
        this.puesto = puesto
        this.requisitos = requisitos
        this.sueldo = sueldo
        this.horario = horario
        this.fecha_publicacion = fecha_publicacion
    }

    constructor( json: JSONObject ): this() {
        this.idOferta = json.optInt("id" )
        this.puesto = json.optString("puesto" )
        this.requisitos = json.optString("requisitos" )
        this.sueldo = json.optString("sueldo" )
        this.horario = json.optString("horario" )
        this.fecha_publicacion = json.optString("fecha_pub" )
    }
}