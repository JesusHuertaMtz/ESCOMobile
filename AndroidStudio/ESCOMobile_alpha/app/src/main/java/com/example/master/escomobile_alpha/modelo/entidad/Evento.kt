package com.example.master.escomobile_alpha.modelo.entidad

import android.media.Image
import java.io.Serializable
import java.util.*

class Evento(): Serializable {
    var id : Int? = null
    var nombre: String? = null
    var fecha: Date? = null
    var imagen: Int? = null
    var ponente: String? = null
    var descripcion: String? = null

    constructor( id: Int, nombre: String, fecha: Date, imagen: Int, ponente: String, descripcion: String ): this() {
        this.id = id
        this.nombre = nombre
        this.fecha = fecha
        this.imagen = imagen
        this.ponente = ponente
        this.descripcion = descripcion
    }
}