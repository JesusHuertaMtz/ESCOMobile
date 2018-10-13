package com.example.master.escomobile_alpha.util

class FormValidator {
    companion object {
        const val FORMATO_INCORRECTO = "<>"
        val CAMPO_VACIO : String? = null
        const val CONTRASENIA_NO_COINCIDE = "<pass>"
    }
    val domains = listOf( "hotmail.com", "gmail.com", "ipn.mx", "outlook.com", "yahoo.com" )
    val SPECIAL_CHARACTER = "|!\"#$\\%&/()=?¡¿_:;,.-[]{}+*-<>"

    /**
     * Válida los campos del registro siguiendo las reglas de negocio del sistema.
     * @param nombre Campo que puede ser el nombre, primer apellido o segundo apellido a válidar.
     * @return El tipo de formato puede ser FORMATO_INCORRECTO, CAMPO_VACIO o CONTRASENIA_NO_COINCIDE
     * */
    fun validateName( nombre : String? ) : String? {
        nombre?.let { nombre ->
            if( nombre.length >= 2 && nombre.length <= 20 ) {
                //Separamos la cadena por los espacios contenidos en ella.
                val nombres = nombre.split( " ")

                if( nombres.size > 3 ) {
                    return FORMATO_INCORRECTO

                } else {
                    //Válidamos que cada palabra no contenga caracteres especiales.
                    val regex = Regex("[^A-Za-z]")
                    var count = 0 //Contador de nombres. Es necesario para nvalidar los primeros dos
                    //nombres, por ej., si se introduce un apellido como "de la O". las primeras
                    //palabras cumplen con el formato excepto la O entonces esa no se válida
                    //que sea mayor que dos.

                    for( name in nombres ) {
                        if( regex.matches( name ) && name.length < 2 && count < 2 ) {
                            //La cadena contiene un caracter especial.
                            return FORMATO_INCORRECTO

                        } else if( regex.matches( name ) ) {
                            return FORMATO_INCORRECTO
                        }

                        count++
                    }
                }

            } else if( nombre == "" ) {
                //El campo está vacío
                return CAMPO_VACIO

            } else if( nombre.length < 2 ) {
                //No cumple con el formato correcto.
                return FORMATO_INCORRECTO
            }
        }

        return nombre
    }

    fun validateEmail( email: String? ) : String? {
        email?.let { mail ->
            if( mail == "" ) {
                return CAMPO_VACIO

            } else {
                val strings = mail.split("@" )

                //Válida la primer parte antes del arroba
                if( strings[0].length in 1..30 ) {
                    //Cumple con la longitud adecuada de texto antes del @
                    //Válidamos que contenga caracteres aceptados por el sistema
                    val regex = Regex("\\s")

                    if( regex.matches( mail ) ) {
                        return FORMATO_INCORRECTO

                    }
                }

                //Válida el texto después del arroba
                if( strings.size == 2 ) {
                    if( domains.indexOf( strings[1] ) < 0 ) {
                        //El dominio ingresado por el usuario no es uno aceptado por el sistema.
                        println("FORMATO INCORRECTO email")
                        return FORMATO_INCORRECTO
                    }

                } else {
                    return FORMATO_INCORRECTO
                }

            }
        }

        return email
    }

    //TODO: FALTA VALIDAR CORRECTAMANETE LAS CONTRASENI EN LA EXPREG
    fun validateContrasenia( contrasenia: String? ) : String? {
        contrasenia?.let { pass ->
            if( pass.length in 8..30 ) {
                val regexp = Regex("[\\w\\W]{8,30}") // the \\w = A-Za-z0-9 //|!'"#$%&/()=?¡¿_:;,.-\[\]{}+*-<>\\
                println("PASS ${pass} ${regexp.matches( pass )}")
                if( regexp.matches( pass ) ) {
                    //TODO validar que tenga caracter especial, un dígito, una letra mayúscula y una míniscula

                } else {
                    return FORMATO_INCORRECTO
                }

            } else {
                return FORMATO_INCORRECTO
            }
        }

        return contrasenia
    }

    fun comparePass( pass: String?, pass2: String? ) : String? {
        if( pass != null && pass2 != null ) {
            if( pass.equals( pass2 ) && pass.length in 8..30 ) {
                return pass
            }
        }

        return CONTRASENIA_NO_COINCIDE
    }
}