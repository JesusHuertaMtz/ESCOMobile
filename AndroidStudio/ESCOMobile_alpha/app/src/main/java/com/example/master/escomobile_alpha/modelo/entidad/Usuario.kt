package com.example.master.escomobile_alpha.modelo.entidad

import org.json.JSONObject
import java.io.Serializable

class Usuario(): Serializable {
    var boleta: String? = null
    var nombre: String? = null
    var primerAp: String? = null
    var segundoAp: String? = null
    var correo: String? = null
    var pass: String? = null
    var pass_confirm: String? = null
    var esProfesor: Boolean? = null

    constructor( boleta: String, nombre: String, primerAp: String, segundoAp: String, correo: String, pass: String, pass_confirm : String, esProfesor: Boolean ): this() {
        this.boleta = boleta
        this.nombre = nombre
        this.primerAp = primerAp
        this.segundoAp = segundoAp
        this.correo = correo
        this.pass = pass
        this.pass_confirm = pass_confirm
        this.esProfesor = esProfesor
    }

    constructor( json: JSONObject ): this() {
        this.boleta = json.optString("id")
        this.nombre = json.optString("nombre")
        this.primerAp = json.optString("primerAp")
        this.segundoAp = json.optString("segundoAp")
        this.correo = json.optString("correo")
        this.pass = json.optString("contrasenia")
        this.esProfesor = json.optBoolean("esProfesor")
    }

    override fun toString(): String {
        return "$primerAp $segundoAp $nombre"
    }
}