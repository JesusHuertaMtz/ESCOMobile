package com.example.master.escomobile_alpha.controlador

import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.example.master.escomobile_alpha.controlador.mapa.MapsSetting
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

/**
 * Created by isaac on 14/03/18.
 */
class MapsLocationUpdate {
    private lateinit var locationCallback: LocationCallback
    // Declare una propiedad LocationRequest
    private lateinit var locationRequest: LocationRequest
    // Bandera para actualizar la ubicación del usuario
    private var locationUpdateState = false

    companion object {
        //REQUEST_CHECK_SETTINGS se utiliza como el código de solicitud pasado a onActivityResult .
        const val REQUEST_CHECK_SETTINGS = 2
    }

    /**
     * Solicita actualizaciones de la ubicación del usuario.
     * - parameters:
     *  - fusedLocationClient:
     *  - activity: Actividad donde se mostrarán las solicitudes del permiso.
     * */
    private fun startLocationUpdates( fusedLocationClient : FusedLocationProviderClient, activity: Activity ) {
        //Solicita permiso al usuario para acceder a su ubicación.
        if( ActivityCompat.checkSelfPermission( activity.applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( activity, arrayOf( android.Manifest.permission.ACCESS_FINE_LOCATION ), MapsSetting.LOCATION_PERMISSION_REQUEST_CODE )

            return
        }

        //Si hay permiso, solicite actualizaciones de ubicación.
        fusedLocationClient.requestLocationUpdates( locationRequest, locationCallback, null /* Looper */ )
    }

    /**
     * Este método es el encargado de solicitar el permiso al usuario para poder acceder
     * al GPS y Wifi para obtener de manera precisa la ubicación del usuario.
     * */
    private fun createLocationRequest( fusedLocationClient : FusedLocationProviderClient, activity: Activity ) {
        //Crea una instancia de LocationRequest, se agrega a una instancia de
        //LocationSettingsRequest.Builder y recupera y maneja los cambios que
        //se realizarán en función del estado actual de la configuración de ubicación del usuario.
        locationRequest = LocationRequest()
        //Tiempo en el que la aplicación recibirá actualizaciones de la ubicación.
        locationRequest.interval = 20000
        //fasterInterval especifica la velocidad más rápida a la que la aplicación puede manejar
        //las actualizaciones. Establecer la tasa fastestInterval rápida de fastestInterval limita
        //la rapidez con que se enviarán las actualizaciones a su aplicación. Antes de comenzar a
        //solicitar actualizaciones de ubicación, debe verificar el estado de la configuración
        //de ubicación del usuario.
        locationRequest.fastestInterval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        //Crea un cliente de configuración y una tarea para verificar la configuración de ubicación.
        val client = LocationServices.getSettingsClient(activity)
        val task = client.checkLocationSettings(builder.build())

        //El éxito de una tarea significa que todo está bien y puede continuar e
        //iniciar una solicitud de ubicación.
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates(fusedLocationClient, activity)
        }

        task.addOnFailureListener { e ->
            //Una falla en la tarea significa que la configuración de la ubicación
            //tiene algunos problemas que pueden corregirse. Esto podría deberse a que la
            //configuración de ubicación del usuario está desactivada. Para solucionar esto,
            //muestra al usuario un cuadro de diálogo como se muestra a continuación:
            if (e is ResolvableApiException) {
                //Las opciones de localización no se cumplieron, pero esto lo podemos
                //arreglar mostrando un dialogo al usuario.
                try {
                    //Muestra el dialogo llamando a startResolutionForResult(),
                    //y verifica el resultado en onActivityResult().
                    e.startResolutionForResult(/*this@MapsActivity*/null, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
}





