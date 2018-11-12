package com.example.master.escomobile_alpha.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.master.escomobile_alpha.modelo.entidad.Busqueda
import com.example.master.escomobile_alpha.modelo.logica_negocio.BSBusquedaEscom
import com.example.master.escomobile_alpha.util.FormValidator
import com.example.master.escomobile_alpha.util.request.RequestManager
import com.example.master.escomobile_alpha.viewholder.adapter.SearchAdapter

class SearchViewModel: ViewModel() {
    lateinit var searchAdapter: SearchAdapter
    var searchType = "NombreProf"

    fun init( context: Context ) {
        searchAdapter = SearchAdapter(context)
    }

    //Realizar busquedas y cosas asi XD
    fun realizarBusqueda( query: String, callback: ( String ) -> Unit ) {
        val rm = RequestManager()
        val params = hashMapOf<String, Any>( "request" to "search", "query" to query,
                "search_type" to searchType )

        rm.postRequest( RequestManager.URL_SQL_SERVER, params ) { json ->
            if( json.optInt("code") == 200 ) {
                val data = json.optJSONObject("data")
                val result = data.optJSONArray("search_result")

                if( result != null ) {
	                val bsEscom = BSBusquedaEscom()
	                val searchResult = bsEscom.parseCompactJSONsearch( result )

	                searchAdapter.update( searchResult )
	                callback( searchResult.joinToString( "\n" ) )

                } else {
                    val search = Busqueda()
	                search.nombre = FormValidator.SIN_RESULTADO_BUSQUEDA
                    searchAdapter.update( mutableListOf( search ) )
                    callback( FormValidator.SIN_RESULTADO_BUSQUEDA )
                }

            } else {
                callback( json.optString("description") )
            }
        }
    }
}