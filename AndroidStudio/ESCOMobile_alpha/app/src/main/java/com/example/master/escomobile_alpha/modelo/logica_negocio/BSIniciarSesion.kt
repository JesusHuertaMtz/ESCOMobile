package com.example.master.escomobile_alpha.modelo.logica_negocio

import android.arch.lifecycle.MutableLiveData
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.util.FormValidator

class BSIniciarSesion() {
    val user : MutableLiveData<Usuario> = MutableLiveData()
    var usuario : Usuario? = null
    val validator = FormValidator()

    constructor( usuario: Usuario ) : this() {
        this.usuario = usuario
        this.user.value = usuario
    }

    /*
    * TODO: REALIZA LA IMPLEMENTACION DE LAS VALIDACIONES
    * TODO: FALTA VÁLIDAR LA BOLETA
    * */
    fun isAValidUserInfo() {
        usuario?.pass = validator.validateContrasenia( usuario?.pass )
        user.value = usuario
    }
}