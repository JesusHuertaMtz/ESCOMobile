package com.example.master.escomobile_alpha.modelo.logica_negocio

import com.example.master.escomobile_alpha.modelo.entidad.Contacto
import com.example.master.escomobile_alpha.modelo.entidad.Empresa
import com.example.master.escomobile_alpha.modelo.entidad.OfertaLaboral
import org.json.JSONArray

class BSBolsaTrabajo {

    /* Recibe un objeto JSON con las ofertas de las empresas*/
    fun parserJSONOfertasVigentes( jsonEmpresasConOfertasVigentes: JSONArray ): MutableList<Empresa> {
        val empresas  : MutableList<Empresa> = arrayListOf()

        for( i in 0..jsonEmpresasConOfertasVigentes.length() - 1 ) {
            val jsonEmpresa = jsonEmpresasConOfertasVigentes.getJSONObject( i )
            val jsonOfertas = jsonEmpresa.getJSONArray("ofertas" )
            val contactos = parserContactos( jsonEmpresa.optJSONArray("contactos") )[0].makeStringContacto()
            println("CONTACTOS ${contactos}")
            for( j in 0..jsonOfertas.length() - 1 ) {
                val oferta = OfertaLaboral( jsonOfertas.getJSONObject( j ) )
                val id = jsonEmpresa.optInt("id" )
                val nombreEmpresa = jsonEmpresa.optString("nombre" )
                val logo = jsonEmpresa.optString("logo" )

                empresas.add( Empresa( id, nombreEmpresa, logo, contactos, oferta ) )
            }
        }

        return empresas
    }

    private fun parserContactos( json: JSONArray ): MutableList<Contacto> {
        val contactos = arrayListOf<Contacto>()

        for( i in 0..json.length() - 1 ) {
            val contacto = Contacto( json.getJSONObject( i ) )
            contactos.add( contacto )
        }

        return contactos
    }
}