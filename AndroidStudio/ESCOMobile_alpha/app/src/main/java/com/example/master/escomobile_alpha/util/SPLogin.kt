package com.example.master.escomobile_alpha.util

import android.app.Activity
import android.content.Context
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.util.request.RequestManager

/*
* SharedPreferences Login */
class SPLogin {

	companion object {
		private val ID : String = "ID"
		private val NOMBRE : String = "NOMBRE"
		private val PRIMERAP : String = "PRIMERAP"
		private val SEGUNDOAP : String = "SEGUNDOAP"
		private val PASS : String = "PASS"
		private val CORREO : String = "CORREO"
		private val ESPROFESOR : String = "ESPROFESOR"

		fun saveUserInSharedPreference( activity: Activity, user: Usuario ) {
			val idPref = activity.getString( R.string.preferences_login_key )
			val sharedPref = activity.getSharedPreferences( idPref, Context.MODE_PRIVATE )

			sharedPref.edit().apply {
				putString( ID, user.boleta )
				putString( NOMBRE, user.nombre )
				putString( PRIMERAP , user.primerAp )
				putString( SEGUNDOAP, user.segundoAp )
				putString( PASS, user.pass )
				putString( CORREO, user.correo )
				putBoolean( ESPROFESOR, user.esProfesor ?: false )
				apply()
			}
		}

		fun loadUserFromSharedPreferences( context: Context ): Usuario {
			val idPref = context.getString( R.string.preferences_login_key )
			val sharedPref = context.getSharedPreferences( idPref, Context.MODE_PRIVATE )
			val user = Usuario()

			//Llave a buscar y valor de retorno en caso de que no se encuentre
			user.boleta = sharedPref.getString( ID, null )
			user.nombre = sharedPref.getString( NOMBRE, null )
			user.primerAp = sharedPref.getString( PRIMERAP, null )
			user.segundoAp = sharedPref.getString( SEGUNDOAP, null )
			user.correo = sharedPref.getString( CORREO, null )
			user.pass = sharedPref.getString( PASS, null )
			user.esProfesor = sharedPref.getBoolean( ESPROFESOR, false )

			return user
		}

		fun loadUserFromSharedPreferences( activity: Activity ): Usuario {
			val idPref = activity.getString( R.string.preferences_login_key )
			val sharedPref = activity.getSharedPreferences( idPref, Context.MODE_PRIVATE )
			val user = Usuario()

			//Llave a buscar y valor de retorno en caso de que no se encuentre
			user.boleta = sharedPref.getString( ID, null )
			user.nombre = sharedPref.getString( NOMBRE, null )
			user.primerAp = sharedPref.getString( PRIMERAP, null )
			user.segundoAp = sharedPref.getString( SEGUNDOAP, null )
			user.correo = sharedPref.getString( CORREO, null )
			user.pass = sharedPref.getString( PASS, null )
			user.esProfesor = sharedPref.getBoolean( ESPROFESOR, false )

			return user
		}

		fun deleteUserSharedPreference( activity: Activity ) {
			val idPref = activity.getString( R.string.preferences_login_key )
			val sharedPref = activity.getSharedPreferences( idPref, Context.MODE_PRIVATE )

			sharedPref.edit().apply {
				clear()
				apply()
			}
		}

		fun updateUserTokenFCM( activity: Activity, token: String ) {
			val user = loadUserFromSharedPreferences( activity )
			val url = RequestManager.URL_REGISTRAR_USUARIO

			if( token.isEmpty() ) {
				println("ERROR TOKEN IS EMPTY***********************")
			}

			user.correo?.let {mail ->
				val params = hashMapOf<String, Any>( "updateToken" to "updateToken",
												"correo" to mail, "token" to token )

				RequestManager().postRequest( url, params ) { jsonResponse ->
					//println("TOKEN UPDATE ${ jsonResponse }")
				}
			}
		}
	}
}