package com.example.master.escomobile_alpha.modelo.entidad

import org.json.JSONObject

class Busqueda() {
    //TODO MODELO TEMPORAL. AQUI DEBE IR EL MODELO A TRABAJAR PARA BUSQUEDAS
    var nombre: String = ""
    var salon: Salon? = null

    constructor( jsonObject: JSONObject ):this() {
        this.nombre = jsonObject.optString( "nombre" )
        this.salon = Salon( jsonObject.optJSONObject("salon" ) )
    }
}