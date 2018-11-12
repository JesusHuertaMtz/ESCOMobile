package com.example.master.escomobile_alpha.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.os.Build
import com.example.master.escomobile_alpha.firebase.MyFirebaseMessagingService
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.modelo.logica_negocio.BSRegistro
import com.example.master.escomobile_alpha.util.model_name.DeviceModel
import com.example.master.escomobile_alpha.util.request.RequestManager
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class RegistroViewModel : ViewModel() {
    //lateinit var fields: ObservableArrayMap<String, String>
    private lateinit var bsRegistro: BSRegistro

    fun init() {
        bsRegistro =  BSRegistro()
        //fields = ObservableArrayMap()
    }

    fun updateUser( usuario: Usuario ) : Boolean {
        bsRegistro.usuario = usuario //= BSRegistro( usuario )
        return bsRegistro.isAValidUserInfo()
    }

    fun registrarUsuario( completionHandler: (response: JSONObject?, user: Usuario?) -> Unit ) {
        val url = RequestManager.URL_REGISTRAR_USUARIO
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault() )
        val date = dateFormat.format( Calendar.getInstance().time )
        val data = hashMapOf( "dispositivo" to "${Build.MANUFACTURER} ${Build.MODEL}",
                "SO" to DeviceModel.getDeviceModelName() )
        var token = ""
        MyFirebaseMessagingService.getTokenfirebase { tokenFCM ->
            token = tokenFCM
        }
        val params = hashMapOf( "id" to (bsRegistro.usuario?.boleta ?: ""),
                "nombre" to (bsRegistro.usuario?.nombre ?: "" ),
                "primerAp" to (bsRegistro.usuario?.primerAp ?: ""),
                "segundoAp" to (bsRegistro.usuario?.segundoAp ?: ""),
                "correo" to (bsRegistro.usuario?.correo ?: ""),
                "contrasenia" to (bsRegistro.usuario?.pass ?:""),
                "esProfesor" to (bsRegistro.usuario?.esProfesor ?: false),
                "fecha" to date,
                "token" to token,
                "data" to data as Any )

        RequestManager().postRequest( url, params ) { json ->
            if( json.optInt("code") == 200 ) {
                val usuario = Usuario( json.getJSONObject("data") )
                println("USER ${usuario.nombre} ${usuario.boleta}")

                completionHandler( json, usuario )
            } else {
                println("ERROR ${ json.opt("description") }")
                completionHandler( json, null )
            }
        }

    }

    /**Sirve cuando pones la etiqueta android en el xml
     * android:onClick="@{ () -> registroViewModel.onClickRegistrarse() }"*/
    /*fun onClickRegistrarse() {
        fields.put( "Name", bsRegistro.user.value?.nombre )
    }*/

    fun getUsuario() : LiveData<Usuario> {
        return bsRegistro.user
    }
}