package com.example.master.escomobile_alpha.util

import android.app.Activity
import android.content.Context
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.modelo.entidad.Usuario

/*
* SharedPreferences Login */
class SPLogin {

	companion object {
		val ID = "ID"
		val NOMBRE = "NOMBRE"
		val PRIMERAP = "PRIMERAP"
		val SEGUNDOAP = "SEGUNDOAP"
		val PASS = "PASS"
		val CORREO = "CORREO"

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
				apply()
			}
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
	}
}