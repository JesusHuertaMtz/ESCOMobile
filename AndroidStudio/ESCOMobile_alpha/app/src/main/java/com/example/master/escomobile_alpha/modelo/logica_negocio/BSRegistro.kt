package com.example.master.escomobile_alpha.modelo.logica_negocio

import android.arch.lifecycle.MutableLiveData
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.util.FormValidator

//THIS IS GAME
class BSRegistro() {
    val user : MutableLiveData<Usuario> = MutableLiveData()
    var usuario : Usuario? = null
    val validator = FormValidator()

    constructor( usuario: Usuario ) : this() {
        this.usuario = usuario
        this.user.value = usuario
    }

    /*
    * TODO: REALIZA LA IMPLEMENTACION DE LAS VALIDACIONES
    * */
    fun isAValidUserInfo(): Boolean {
        usuario?.boleta     = validator.validateBoleta( usuario?.boleta, usuario?.esProfesor ?: false )
        usuario?.nombre     = validator.validateName( usuario?.nombre )
        usuario?.primerAp   = validator.validateName( usuario?.primerAp )
        usuario?.segundoAp  = validator.validateName( usuario?.segundoAp )
        usuario?.correo     = validator.validateEmail( usuario?.correo )
        usuario?.pass       = validator.validateContrasenia( usuario?.pass )
        usuario?.pass_confirm = validator.comparePass( usuario?.pass, usuario?.pass_confirm )

        user.value = usuario

        return isValid()
    }

    private fun isValid() : Boolean {
        var registroSuccess = isValidField( usuario?.nombre ) && isValidField( usuario?.primerAp )
        registroSuccess = registroSuccess && isValidField( usuario?.segundoAp ) && isValidField( usuario?.correo )
        registroSuccess = registroSuccess && isValidField( usuario?.pass )
        registroSuccess = registroSuccess && usuario?.pass_confirm != FormValidator.CONTRASENIA_NO_COINCIDE
        registroSuccess = registroSuccess && isValidField( usuario?.boleta )

        return registroSuccess
    }

    /**
     * Válida que el campo nombre sea correcto.
     * */
    fun isValidField( field : String? ) : Boolean {
        if( field != FormValidator.CAMPO_VACIO && field != FormValidator.FORMATO_INCORRECTO ) {
            return true
        }

        return false
    }
}









