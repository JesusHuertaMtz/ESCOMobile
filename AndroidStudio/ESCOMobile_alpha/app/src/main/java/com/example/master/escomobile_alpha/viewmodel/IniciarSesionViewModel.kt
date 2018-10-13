package com.example.master.escomobile_alpha.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.modelo.logica_negocio.BSIniciarSesion
import com.example.master.escomobile_alpha.util.request.RequestManager
import org.json.JSONObject

class IniciarSesionViewModel: ViewModel()  {
    private lateinit var bsIniciarSesion: BSIniciarSesion

    fun init() {
        bsIniciarSesion =  BSIniciarSesion()
    }

    fun updateUserInfo( usuario: Usuario ) {
        bsIniciarSesion.usuario = usuario
        bsIniciarSesion.isAValidUserInfo()
    }

    fun iniciarSesion( context: Context, completionHandler: (response: JSONObject, usuario: Usuario? ) -> Unit ) {
        val url= RequestManager.URL_INICIAR_SESION
        val boleta= bsIniciarSesion.usuario?.boleta ?: ""
        val contrasenia= bsIniciarSesion.usuario?.pass ?: ""
        val params = hashMapOf<String,Any>( "id" to boleta, "contrasenia" to contrasenia )

        RequestManager().postRequest( url, params ) { json ->
            if( json.optInt("code") == 200 ) {
                val usuario = Usuario( json.getJSONObject("data") )
                usuario.pass = bsIniciarSesion.usuario?.pass
                println("USER ${usuario.nombre} ${usuario.boleta}")

                completionHandler( json, usuario )
            } else {
                println("ERROR ${ json.opt("description") }")
                completionHandler( json, null )
            }
        }
    }

    fun getUsuario() : LiveData<Usuario> {
        return bsIniciarSesion.user
    }
}