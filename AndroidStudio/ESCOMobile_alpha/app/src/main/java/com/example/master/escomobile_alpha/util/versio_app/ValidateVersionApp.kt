package com.example.master.escomobile_alpha.util.versio_app

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.util.request.RequestManager

class ValidateVersionApp {

	companion object {
		fun getVersion( activity: Activity ) {
			val packageInfo = activity.packageManager.getPackageInfo( activity.packageName, 0 )
			val version = packageInfo.versionName
			val url = RequestManager.URL_VERSION_APP
			val params = hashMapOf<String, Any>( "version" to version )
			println("VERSION $version")

			//Realizamos una petición al servidor para saber cual es la versoón atual de la app

			RequestManager().postRequest( url, params ) { jsonResponse ->
				if( jsonResponse.optInt("code" ) == 200 ) {
					//La app esta actualizada!
				} else {
					val dialog = Dialog( activity )
					dialog.requestWindowFeature( Window.FEATURE_NO_TITLE )
					dialog.setContentView( R.layout.update_app )
					dialog.findViewById<TextView>( R.id.txt_update ).text = jsonResponse.optString("description")
					dialog.findViewById<Button>( R.id.btn_aceptar ).setOnClickListener {
						dialog.dismiss()
						activity.finish()
					}
					dialog.show()
				}
			}
		}
	}

}