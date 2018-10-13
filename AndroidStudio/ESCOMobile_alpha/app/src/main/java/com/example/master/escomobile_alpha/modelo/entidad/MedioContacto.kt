package com.example.master.escomobile_alpha.modelo.entidad

import org.json.JSONObject

class MedioContacto() {
    var tipo = ""
    var valor = ""

    constructor( json: JSONObject ): this() {
        this.tipo = json.optString("tipo" )
        this.valor = json.optString("valor" )
    }
}