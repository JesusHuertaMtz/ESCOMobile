package com.example.master.escomobile_alpha.modelo.entidad

import org.json.JSONArray
import org.json.JSONObject

class ContactoEmpresa() {
    var nombre: String? = null
    var mediosContacto = arrayListOf<MedioContacto>()

    constructor(json: JSONObject) : this() {
        this.nombre = json.optString("n_contacto")
        setMediosContacto( json.optJSONArray("m_contacto" ) )
    }

    private fun setMediosContacto( jsonArray: JSONArray ) {
        for( i in 0..jsonArray.length() -1 ) {
            val medioContacto = MedioContacto( jsonArray.getJSONObject( i ) )

            this.mediosContacto.add( medioContacto )
        }
    }

    fun makeStringContacto(): String {
        var contacto = "nombre contacto: ${nombre}\n"

        for( medioContacto in mediosContacto ) {
            contacto += "${medioContacto.tipo} : ${medioContacto.valor}\n"
        }

        return contacto
    }
}