package com.example.master.escomobile_alpha.util

class Message {
    companion object {
        fun getMessageIncorrectFormat( dato: String ) : String {
            return "¡Error! El campo ${ dato } no cumple con el formato válido definido."
        }

        fun getMessageRequiredField( dato: String ) : String {
            return "¡Error! El campo ${dato} es obligatorio, favor de ingresarlo."
        }
    }
}