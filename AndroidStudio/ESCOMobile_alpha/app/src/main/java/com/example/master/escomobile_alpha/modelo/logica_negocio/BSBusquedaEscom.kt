package com.example.master.escomobile_alpha.modelo.logica_negocio

import com.example.master.escomobile_alpha.modelo.entidad.Busqueda
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import com.example.master.escomobile_alpha.util.request.RequestManager
import org.json.JSONArray

class BSBusquedaEscom {

    fun parseCompactJSONsearch( jsonArray: JSONArray ): MutableList<Busqueda> {
        val searchResults = mutableListOf<Busqueda>()

        for( index in 0..jsonArray.length() - 1 ) {
            val json = jsonArray.optJSONObject( index )
            val result = Busqueda( json )

            searchResults.add( result )
        }

        return searchResults
    }

	fun searchProfesorByName( nombre: String, completionHandler: (profesor: Profesor?) -> Unit ) {
		val rm = RequestManager()
		val params = hashMapOf<String, Any>( "request" to "getProfesorByName", "nombre" to nombre )

		rm.postRequest( RequestManager.URL_SQL_SERVER, params ) { jsonResponse ->
			if( jsonResponse.optInt("code" ) == 200 ) {
                val jsonProfesor = jsonResponse.optJSONObject( "data" ).optJSONObject( "profesor" )

				if( jsonProfesor != null ) {
					val profesor = Profesor( jsonProfesor )
					completionHandler( profesor )

				} else {
					completionHandler( Profesor() )
				}

			} else {
				//Hubo error
				println("PROFESOR NO ENCONTRADO")
				completionHandler( null )
			}
		}
	}

    ///////////////////////////////////////////////////
    /***************** LAS FUNCIONES SIGUIENTES SE CAMBIARON VERIFICAR SI SE VAN A SEGUIR USANSDO ********************/
    fun parseJSONProfesores( jsonArray: JSONArray): MutableList<Profesor> {
        val profesores = mutableListOf<Profesor>()

        for( index in 0..jsonArray.length() - 1 ) {
            val json = jsonArray.optJSONObject( index )
            val profesor = Profesor( json )

            profesores.add( profesor )
        }

        return profesores
    }

    fun getResultBySearchType( profesores: MutableList<Profesor>, searchType: String ): MutableList<String> {
        when( searchType ) {
            "NombreProf" -> {
                return getNombreProfesores( profesores )
            }
            "Grupo" -> {
                return getGrupos( profesores )
            }
            "Salon" -> {
                return getSalones( profesores )
            }
            "NombAcademia" -> {
                return getAcademias( profesores )
            }
            else -> return mutableListOf()
        }
    }

    private fun getNombreProfesores( profesores: MutableList<Profesor> ): MutableList<String> {
        val nombreProfesores = mutableListOf<String>()

        for( profesor in profesores ) {
            nombreProfesores.add( profesor.nombre )
        }

        return nombreProfesores
    }

    private fun getSalones( profesores: MutableList<Profesor> ): MutableList<String> {
        val salones = mutableListOf<String>()

        for( profesor in profesores ) {
            salones.add( profesor.asignaturas[0].salon?.id ?: "" )
        }

        return salones
    }

    private fun getGrupos( profesores: MutableList<Profesor> ): MutableList<String> {
        val grupos = mutableListOf<String>()

        for( profesor in profesores ) {
            grupos.add( profesor.asignaturas[0].grupo )
        }

        return grupos
    }

    private  fun getAcademias( profesores: MutableList<Profesor> ): MutableList<String> {
        val academias = mutableListOf<String>()

        for( profesor in profesores ) {
            academias.add( profesor.cubiculo?.academia ?: "" )
        }

        return academias
    }
}