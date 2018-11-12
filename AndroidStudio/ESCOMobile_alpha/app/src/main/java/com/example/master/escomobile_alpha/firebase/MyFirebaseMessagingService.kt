package com.example.master.escomobile_alpha.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat.getSystemService
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.util.SPLogin
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

	companion object {
		/**
		 * Método necesario para recibir notificaciones en segundo plano
		 * Se debe invocar desde MainActivity, es decir, lo antes posible para registrar
		 * el servicio
		 * */
		fun createNotificationChannel( context: Context ) {
			// Create the NotificationChannel, but only on API 26+ because
			// the NotificationChannel class is new and not in the support library
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				val name = "ESCOMobile"
				val descriptionText = "Prueba Firebase"
				val importance = NotificationManager.IMPORTANCE_DEFAULT
				val channel = NotificationChannel("ESCOMobile_APP", name, importance).apply {
					description = descriptionText
				}
				// Register the channel with the system
				val notificationManager: NotificationManager = context.
						getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
				notificationManager.createNotificationChannel(channel)
			}
		}

		/**
		 * Devuelve el Token obtenido. Sedebe comprobar cierto periodo de tiempo debido a
		 * que puede cambiar ya sea porque se desisntalo la app, el usuario borro los datos
		 * de la app o porque ha caducado.
		 * */
		fun getTokenfirebase( completionHandler: ( token: String ) -> Unit ) {
			FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
				val token  = instanceIdResult.token
				println("TOKEN $token")
				completionHandler( token )
			}
		}

		fun subscribeToTopic() {
			FirebaseMessaging.getInstance().subscribeToTopic("Bolsa_Trabajo")
		}
	}

	override fun onNewToken(token: String?) {
		super.onNewToken(token)

		println("NEW TOKEN $token")
	}

	override fun onMessageReceived(remoteMessage: RemoteMessage?) {
		super.onMessageReceived(remoteMessage)

		if( remoteMessage?.data?.size ?: 0 > 0 ) {
			println("DATA ${ remoteMessage?.data }")
		}

		println("MENSAJE $remoteMessage")
		makeNotification( remoteMessage )
	}

	private fun makeNotification( remoteMessage: RemoteMessage? ) {
		val notification = NotificationCompat.Builder( this, "" ).apply {
			setSmallIcon( R.mipmap.ic_icon_logo )
			setContentTitle( remoteMessage?.notification?.title )
			setContentText( remoteMessage?.notification?.body )
			setStyle( NotificationCompat.BigTextStyle().bigText( remoteMessage?.notification?.body ) )
			setPriority( NotificationCompat.PRIORITY_DEFAULT )
			setAutoCancel( true )
		}

		/*
		with( NotificationManagerCompat.from( this ) ) {
			// notificationId is a unique int for each notification that you must define
			notify( 2, notification.build() )
		}*/
	}
}