package com.example.master.maps.modelo.pojo

/**
 * Created by Master Chief on 19/02/2018.
 * Almacena el nombre de la escuela, el nombre corto de la escuela, el identificador
 * y los edificios que forman parte de la escuela.
 */
class Escuela {
    var id : Int = -1
    var nombre : String = ""
    var nombre_corto : String = ""
    var edificios : ArrayList<AreaGeografica>? = null
}