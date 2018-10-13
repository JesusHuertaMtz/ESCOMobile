package com.example.master.escomobile_alpha.util

interface ErrorMessage {

    /**
     * Muestra el mensaje de error dependiendo si el campo tiene el formato incorrecto
     * o si está vacío. null significa que el campo está vacío y "<>" significa que el campo
     * no cumple con el formato correcto.
     * @param campo El campo a validar.
     * @param dato El nombre del campo que puede estar vacío o no, o con el fomrtao incorrecto.
     * @return null si no existe mensaje de error, en otro caso regresa el mensaje de error.
     * */
     fun getErrorMessage( campo: String?, dato: String ): String? {
        var text : String? = null

        if( campo == FormValidator.CAMPO_VACIO ) {
            text = Message.getMessageRequiredField( dato )

        } else if( campo == FormValidator.FORMATO_INCORRECTO ) {
            text = Message.getMessageIncorrectFormat( dato )

        } else if( campo == FormValidator.CONTRASENIA_NO_COINCIDE ) {
            text = "Las contraseñas no coinciden"//Message.getMessageIncorrectFormat( dato )
        }

        return text
    }
}