package com.example.master.escomobile_alpha.util.request

import android.content.Context
import com.example.master.escomobile_alpha.util.JSONParser
import com.example.master.maps.modelo.pojo.AreaGeografica
import com.example.master.maps.modelo.pojo.Escuela
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

/**
 * Created by Master Chief on 20/02/2018.
 */
class BSEscuela {

    companion object {
        val URL = "http://192.168.100.3/maps/JSON.php"
        val URL_SAL = "http://192.168.100.3/maps/JSON_salon.php"
    }

    /**
     * Obtiene las coordenadas del servidor.
     * Returns:
     *  - PolygonOptions con las coordenadas de la escuela.
     * */
     //fun getCoordenadaEscuela() : PolygonOptions? {
     /*fun getCoordenadaEscuela( context: Context ) : PolygonOptions? {
        //val response = RequestManager().execute( URL ).get()
        val escuela : Escuela?
        var coordenadas : PolygonOptions? = null

        RequestManager().sendSimpleRequest( context, URL ) { response ->
            response?.let { escuela ->
                println("ESCUELA: ${ escuela.nombre }")

                escuela.edificios?.let { area ->
                    coordenadas = drawInMap( area[0] )

                    println("COORDENADAS: ${ coordenadas }")
                }
            }
        }
        var coordenadas : PolygonOptions? = null

        //escuela = JSONParser().parseJSON( response )

        escuela?.let { escuela ->
            println("ESCUELA: ${ escuela.nombre }")

            escuela.edificios?.let { area ->
                coordenadas = drawInMap( area[0] )

                println("COORDENADAS: ${ coordenadas }")
            }
        } //Comentar

        return coordenadas
    }*/

    private fun drawAreaGeografica( latlngs: ArrayList<LatLng> ) : PolygonOptions {
        val polygonOption = PolygonOptions()

        for( i in 0 until latlngs.count() ) {
            polygonOption.add( latlngs.get( i ) )
        }

        //val polygon = map.addPolygon( polygonOption )
        return polygonOption
    }

    fun drawInMap( areaGeografica: AreaGeografica ) : PolygonOptions? {
        areaGeografica.coordenadasPoligono?.let { coordenadas ->
            return drawAreaGeografica( coordenadas )
        }

        return null
    }

    ////////////////SALONES
    private fun drawAreaGeografica_ver2( latlngs: ArrayList<LatLng> ) : PolylineOptions {
        val polygonOption = PolylineOptions()

        for( i in 0 until latlngs.count() ) {
            polygonOption.add( latlngs.get( i ) )
        }

        //val polygon = map.addPolygon( polygonOption )
        return polygonOption
    }

    fun drawInMapLine( areaGeografica: AreaGeografica ) : PolylineOptions? {
        areaGeografica.coordenadasPoligono?.let { coordenadas ->
            return drawAreaGeografica_ver2( coordenadas )
        }

        return null
    }
}