package com.example.master.escomobile_alpha.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.master.escomobile_alpha.modelo.entidad.Empresa
import com.example.master.escomobile_alpha.modelo.logica_negocio.BSBolsaTrabajo
import com.example.master.escomobile_alpha.util.CacheManager
import com.example.master.escomobile_alpha.util.request.RequestManager
import com.example.master.escomobile_alpha.viewholder.BolsaTrabajoAdapter

class BolsaTrabajoViewModel: ViewModel() {
    lateinit var bolsaTrabajoAdapter : BolsaTrabajoAdapter

    fun init(  context: Context  ) {
        bolsaTrabajoAdapter = BolsaTrabajoAdapter( context )
    }

    fun getEmpresasConOfertasVigentes() {
        //Realizar petición

        //TODO: IMPLEMENTAR LOS SIGUIENTES PASOS PARA ALMACENAR EN CACHE
        //Consultar los ids de las ofertas almacenadas en la BD
        if( isCacheStorageEmpty( bolsaTrabajoAdapter.context ) ) {
            //El almacenamiento está vacío. Preguntar si hay nuevas ofertas.
            val url = RequestManager.URL_OFERTAS_VIGENTES
            val params = hashMapOf<String, Any>( "request" to "getOfertasVigentes" )

            RequestManager().postRequest( url, params ) { jsonResponse ->
                if( jsonResponse.optInt("code") == 200 ) {
                    val empresasConOfertasVigentes = jsonResponse.optJSONObject("data" ).optJSONArray("ofertas_vigentes" )
                    val empresas = BSBolsaTrabajo().parserJSONOfertasVigentes( empresasConOfertasVigentes )

                    writeEmpresasInCache( bolsaTrabajoAdapter.context, empresas )

                    bolsaTrabajoAdapter.updateEmpresas( empresas )
                    bolsaTrabajoAdapter.notifyDataSetChanged()

                } else {
                    //Hubo error
                    println("ERROR ${ jsonResponse.opt("description") }")
                }
            }
        } else {
            //TODO
            //Existen ofertas en cache. Varificamos que no existan nuevas ofertas
            //Borrar ofertas no vigentes de la cache
            //Añadir ofertas vigentes a la cache

            //Se muestran las ofertas almacenadas en cache
            val empresas = readEmpresasFromCache( bolsaTrabajoAdapter.context )
            empresas?.let { n_empresas ->
                bolsaTrabajoAdapter.updateEmpresas( n_empresas )
                bolsaTrabajoAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun isCacheStorageEmpty( context: Context ) : Boolean {
        val cm = CacheManager()
        var isCacheStorageEmpty = false
        val objectCache = cm.readObjectFromCacheDir( context, CacheManager.CACHE_EMPRESA )

        if( objectCache == null ) {
            println("NO HAY CACHE")
            isCacheStorageEmpty = true
        }

        return isCacheStorageEmpty
    }

    private fun writeEmpresasInCache( context: Context, empresas: MutableList<Empresa> ) {
        val cm = CacheManager()

        cm.writeObjectInCacheDir( context, empresas, CacheManager.CACHE_EMPRESA )
    }

    private fun readEmpresasFromCache( context: Context ): MutableList<Empresa>? {
        val cm = CacheManager()

        val myObjects = cm.readObjectFromCacheDir( context, CacheManager.CACHE_EMPRESA )

        if( myObjects != null ) {
            val empresas = myObjects as MutableList<Empresa>
            println("HAY ${empresas.size} EMPRESAS")

            return empresas

        } else {
            return null
        }
    }
}





