package com.example.master.maps.modelo.pojo

import com.example.master.escomobile_alpha.util.TipoAreaGeografica
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Master Chief on 19/02/2018.
 */
class AreaGeografica() {
    var id_area : String? = null
    var tipoAreaGeografica : TipoAreaGeografica = TipoAreaGeografica.TERRENO
    var descripcion : String? = null
    var coordenadasPoligono : ArrayList<LatLng>? = null

    constructor( id_area: String, tipoAreaGeografica: TipoAreaGeografica, descripcion : String ) : this() {
        this.id_area = id_area
        this.tipoAreaGeografica = tipoAreaGeografica
        this.descripcion = descripcion
    }

    constructor( id_area: String, tipoAreaGeografica: TipoAreaGeografica, descripcion : String, coordenadasPoligono: ArrayList<LatLng> ) : this() {
        this.id_area = id_area
        this.tipoAreaGeografica = tipoAreaGeografica
        this.descripcion = descripcion
        this.coordenadasPoligono = coordenadasPoligono
    }
}


