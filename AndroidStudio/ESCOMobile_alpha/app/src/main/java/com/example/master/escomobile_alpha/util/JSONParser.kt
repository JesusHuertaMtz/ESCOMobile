package com.example.master.escomobile_alpha.util

import com.example.master.maps.modelo.pojo.AreaGeografica
import com.example.master.maps.modelo.pojo.Escuela
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject
import org.json.JSONException
import org.json.JSONArray


/**
 * Created by Master Chief on 02/02/2018.
 */
class JSONParser {

    fun parseJSON( json : String ) : Escuela? {

        if( !isJSONValid( json ) )
            return null

        val jsonObject = JSONObject( json ) //Creamos un objeto JSON a partir de la cadena

        val jsonEscuela = jsonObject.optJSONObject("escuela" )
        val array_latlng = arrayListOf<LatLng>()
        val escuela = Escuela()

        if( jsonEscuela != null ) {
            escuela.id = jsonEscuela.opt("id" ).toString().toInt()
            escuela.nombre = jsonEscuela.opt( "nombre" ).toString() //Regresa null si la etiqueta no existe
            escuela.nombre_corto = jsonEscuela.opt( "nombre_corto" ).toString() //Regresa null si la etiqueta no existe

            val jsonAreaGeografica = jsonEscuela.optJSONObject("area_geografica")

            val areaGeografica = AreaGeografica()
            areaGeografica.tipoAreaGeografica = getTipo( jsonAreaGeografica.opt("tipo").toString() )

            val coordenadas = jsonAreaGeografica.optJSONArray("coordenada_edificio_1")

            if( coordenadas != null ) {
                for( a in 0  until coordenadas.length() ) {
                    val coordenada = coordenadas.getJSONObject( a ).optJSONObject( ( a + 1 ).toString() )

                    println("Coordenada: ${ coordenada }")

                    val lat : Double = coordenada.opt( "lat" ).toString().toDouble()
                    val lng : Double = coordenada.opt( "lng" ).toString().toDouble()

                    println( lat )
                    println( lng )

                    if( lat != null && lng != null ) {
                        array_latlng.add( LatLng( lat, lng ) )

                    } else {
                        println("LATLNG ES NULL")
                    }
                }

                areaGeografica.coordenadasPoligono = array_latlng
                escuela.edificios = ArrayList<AreaGeografica>()
                escuela.edificios?.add( areaGeografica )
            } else {
                println(" **** ¡NO HAY COORDENADAS!. **** ")
            }

        } else {
            println(" **** JSON error: No es un JSON Válido. **** ")

            return null
        }

        return escuela
    }













    fun parseJSONSalones( json : String ) : Escuela? {

        if( !isJSONValid( json ) )
            return null

        val jsonObject = JSONObject( json ) //Creamos un objeto JSON a partir de la cadena

        val jsonEscuela = jsonObject.optJSONObject("escuela" )
        var array_latlng = arrayListOf<LatLng>()
        val escuela = Escuela()

        if( jsonEscuela != null ) {
            escuela.id = jsonEscuela.opt("id" ).toString().toInt()
            escuela.nombre = jsonEscuela.opt( "nombre" ).toString() //Regresa null si la etiqueta no existe
            escuela.nombre_corto = jsonEscuela.opt( "nombre_corto" ).toString() //Regresa null si la etiqueta no existe
            escuela.edificios = ArrayList<AreaGeografica>()

            val jsonAreaGeografica = jsonEscuela.optJSONObject("area_geografica")
            val salones = jsonAreaGeografica.optJSONArray("salones")

            if( salones != null ) {
                for( a in 0  until salones.length() ) {
                    val salon = salones.getJSONObject( a )//.optJSONObject( ( a + 1 ).toString() )
                    val coordenadas = salon.optJSONArray("coordenadas")

                    for( index in 0  until coordenadas.length() ) {
                        val coordenada = coordenadas.getJSONObject( index ).optJSONObject( ( index + 1 ).toString() )

                        println("Coordenada: ${ coordenada }")

                        val lat : Double = coordenada.opt( "lat" ).toString().toDouble()
                        val lng : Double = coordenada.opt( "lng" ).toString().toDouble()

                        if( lat != null && lng != null ) {
                            array_latlng.add( LatLng( lat, lng ) )

                        } else {
                            println("LATLNG ES NULL")
                        }
                    }

                    val areaGeografica = AreaGeografica()
                    areaGeografica.tipoAreaGeografica = getTipo( jsonAreaGeografica.opt("tipo").toString() )
                    areaGeografica.coordenadasPoligono = array_latlng
                    escuela.edificios?.add( areaGeografica )
                    array_latlng = arrayListOf<LatLng>()
                }

            } else {
                println(" **** ¡NO HAY COORDENADAS!. **** ")
            }

        } else {
            println(" **** JSON error: No es un JSON Válido. **** ")

            return null
        }

        return escuela
    }
























    private fun getTipo( tipo : String ) : TipoAreaGeografica {
        when( tipo ) {
            "terreno" -> return TipoAreaGeografica.TERRENO
            "edificio" -> return TipoAreaGeografica.EDIFICIO
            "salon" -> return TipoAreaGeografica.SALON
        }

        return TipoAreaGeografica.TERRENO
    }

    fun isJSONValid( test: String ): Boolean {
        try {
            JSONObject( test )

        } catch( ex: JSONException ) {
            try {
                JSONArray( test )
            } catch( ex1: JSONException ) {
                return false
            }
        }

        return true
    }
}