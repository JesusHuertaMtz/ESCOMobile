package com.example.master.escomobile_alpha.util

class FormValidator {
    companion object {
        const val FORMATO_INCORRECTO = "<>"
        val CAMPO_VACIO : String? = null
        const val CONTRASENIA_NO_COINCIDE = "<pass>"
        const val SIN_RESULTADO_BUSQUEDA = "No hay resultado de búsqueda"
    }
    val domains = listOf( "hotmail.com", "gmail.com", "ipn.mx", "outlook.com", "yahoo.com", "live.com", "live.com.mx" )
    val SPECIAL_CHARACTER = "|!\"#$\\%&/()=?¡¿_:;,.-[]{}+*-<>"

    /**
     * Válida la boleta o número de empleado del profesor. No implementado debido a que
     * no sabemos el formato del número de empleado del profesor.
     * */
    fun validateBoleta( boleta: String?, esProfesor: Boolean ): String? {
        //Busca algún caracter que no sea un dígito
        val regex = Regex("[^0-9]")

        if( boleta.equals( "" ) ) {
            return CAMPO_VACIO

        } else if( boleta?.length ?: 0 < 10 ) {
            return FORMATO_INCORRECTO

        } else if( esProfesor ) {
            //Se está registrando como profesor
            if( regex.matches( boleta ?: "" ) ) {
                //No es un número de empleado válido ya que encontró un caracter que no es un dígito
                return FORMATO_INCORRECTO
            }

        } else {
            val esBoletaValida = boleta?.substring( 0, 2 )?.equals("20") ?: false

            if( !esBoletaValida ) {
                return FORMATO_INCORRECTO
            }
        }

        return boleta
    }

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