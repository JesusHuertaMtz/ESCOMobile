package com.example.master.escomobile_alpha.modelo.entidad

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

class Empresa : Serializable {
    var id: Int? = null
    var nombre: String? = null
    var logo: String? = null
    var contactos: String? = null //Parsear el contacto.
    var oferta: OfertaLaboral? = null

    constructor( id: Int, nombre: String, logo: String, contactos: String, oferta: OfertaLaboral ) {
        this.id = id
        this.nombre = nombre
        this.logo = logo
        this.contactos = contactos
        this.oferta = oferta
    }

    constructor( json: JSONObject ) {
        this.id = json.optInt("id" )
        this.nombre = json.optString("nombre" )
        this.logo = json.optString("logo" )
    }

    fun getImageBitmap(): Bitmap? {
        logo?.let { logo ->
            var imageBase64 = logo.replace("data:image/png;base64,", "" )
            imageBase64 = imageBase64.replace("data:image/jpg;base64,", "" )
            val decodedString = Base64.decode( imageBase64, Base64.DEFAULT )
            val decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.size )

            return decodedByte
        }

        return null
    }
}

/*
{
    "id": "8",
    "nombre": "Pineapple",
    "contactos": [
        {
            "n_contacto": "Rocío Palacios Dolores",
            "m_contacto": [
                {
                    "tipo": "Teléfono fijo",
                    "valor": "5587404120"
                },
                {
                    "tipo": "Correo electrónico",
                    "valor": "palacios_dcio@gmail.com"
                }
            ]
        }
    ],
    "ofertas": {
        "id": "25",
        "puesto": "desarrollador",
        "requisitos": " Manejo de SQL Server, JavaScript, .NET, Visual Basic",
        "sueldo": "$ 30000 a 35000",
        "horario": "Medio tiempo",
        "fecha_pub": "2018-08-01"
    }
}
 */