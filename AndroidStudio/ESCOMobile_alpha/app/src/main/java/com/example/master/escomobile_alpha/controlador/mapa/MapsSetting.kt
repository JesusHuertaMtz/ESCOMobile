package com.example.master.escomobile_alpha.controlador.mapa

import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v4.app.ActivityCompat
import com.example.master.escomobile_alpha.util.Constant
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task

/**
 * http://androidkt.com/android-geofence/
 **/


/**
 * Created by Master Chief on 09/03/2018.
 */
/**
 * Clase para añadir las opciones básicas al mapa.
 * No solicita permiso para acceder a la ubicación.
 * */
class MapsSetting private constructor() {
    ///Muestra la última localización del usuario
    private lateinit var lastLocation: Location
    ///Necesario para mostrar la ubicación del usuario.
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    ///Crea un objeto Geocoder para convertir una coordenada de latitud y longitud en una dirección y viceversa
    private lateinit var geocoder: Geocoder

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
        //REQUEST_CHECK_SETTINGS se utiliza como el código de solicitud pasado a onActivityResult .
        const val REQUEST_CHECK_SETTINGS = 2

        fun newInstance( activity: Activity ): MapsSetting {
            //Crea una nueva instancia de la clase e inicializa la variable fusedLocationClient
            val mapSetting = MapsSetting()
            mapSetting.initVariables( activity )

            return mapSetting
        }
    }

    //TODO: Métodos privados.
    /**
     * Inicializa fusedLocationClient y geocoder
     * - parameters:
     *  - activity: Actividad asociada a esta variable.
     * */
    private fun initVariables( activity: Activity ) {
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient( activity.applicationContext )
        this.geocoder = Geocoder( activity.applicationContext )
    }

    /**
     * Coloca una marker personalizado en el mapa.
     * - parameters:
     *  - map: Mapa donde se mostrará el marker
     *  - location: Ubicación que tendrá el marker.
     * */
    private fun placeMarkerOnMap( map: GoogleMap, location: LatLng ) {
        // Crea un objeto MarkerOptions y establece la ubicación actual del usuario como la posición para el marcador
        val markerOptions = MarkerOptions().position(location)
        val titleStr = getAddress( location )

        //añade el titulo al marker
        markerOptions.title( titleStr )
        ///Cambia el color del amrker
        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN ) )

        //Muestra el marker en el mapa
        map.addMarker( markerOptions )
    }

    /**
     * Toma las coordenadas de una ubicación y devuelve una dirección legible y viceversa.
     * - parameters:
     *  - latLng: Ubiacación de la cual queremos obtener la dirección o coordenada.
     **/
    private fun getAddress( latLng: LatLng ) : String {
        val addresses : List<Address>?
        val address : Address?
        var addressText = ""

        //Pide al geocodificador que obtenga la dirección de la ubicación pasada al método.
        addresses = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 )
        println("ADDRESS ${ addresses.size } ${ addresses[0].maxAddressLineIndex } ${ addresses.isNotEmpty() }")

        //Si la respuesta contiene alguna dirección, añádela a una cadena y regresa.
        if( addresses != null && addresses.isNotEmpty() ) {
            address = addresses[0]

            for( i in 0 until address.maxAddressLineIndex + 1 ) {
                addressText += if ( i == 0 ) address.getAddressLine( i ) else "\n" + address.getAddressLine( i )
            }

        } else {
            addressText = "Dirección no disponible"
        }

        return addressText
    }

    //TODO: Métodos públicos.
    /**
     * Comprueba si a la aplicación se le ha concedido el permiso ACCESS_FINE_LOCATION.
     * Si no lo ha hecho, solicítelo al usuario.
     * Muestra la ubiación del usuario en el mapa si se ha concedido el permiso.
     * - parameters:
     *  - activity: Actividad donde se mostrará el dialogo para solicitar el permiso.
     * */
    fun setUserLocationInMap( map: GoogleMap, activity: Activity ) {
        //Solcitar permisos antes de mostrar la ubicación es obligatorio.
        if( ActivityCompat.checkSelfPermission( activity.applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_DENIED ) {
            //No tiene perimosos. Solicitamos al usuario el permiso mostrando el dialogo correspondiente.
            val permissions = arrayOf( android.Manifest.permission.ACCESS_FINE_LOCATION )
            ActivityCompat.requestPermissions( activity, permissions, LOCATION_PERMISSION_REQUEST_CODE)

            return
        }

        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        //Brinda la ubicación más reciente disponible actualmente.
        fusedLocationClient.lastLocation.addOnSuccessListener( activity ) { location ->
            //Si obtuvo la ubicación más reciente mueve la cámara a la ubicación actual del usuario.
            if( location != null ) {
                lastLocation = location
                val currentLatLng = LatLng( location.latitude, location.longitude )

                placeMarkerOnMap( map, currentLatLng )
                //map.animateCamera( CameraUpdateFactory.newLatLngZoom( currentLatLng, Constant.MAP_ZOOM ) )
            }
        }
    }
    
    /**
     * Muestra en el mapa a la ESCOM. Tiene un zoom mínimo y máximo
     * - parameters:
     *  - map: Mapa donde se va a mostrar ESCOM.
     * */
    fun setViewESCOM( map: GoogleMap ) {
        // añade una marca en ESCOM.
        val escom = LatLng( 19.5045672, -99.1469098 )

        map.uiSettings.isZoomControlsEnabled = true
        //Añade el mínimo y máximo zoom que se puede hacer al mapa
        map.setMinZoomPreference( Constant.MIN_MAP_ZOOM )
        map.setMaxZoomPreference( Constant.MAX_MAP_ZOOM )
        //placeMarkerOnMap( map, escom ) //Muestra un

        map.moveCamera( CameraUpdateFactory.newLatLngZoom( escom, Constant.MAP_ZOOM ) )
    }

    fun createLocationRequest( map: GoogleMap, activity: Activity ) {
        val locationRequest = LocationRequest().apply {
            //Tiempo en milisegundos en el cual la app recibirá actualizaciones
            interval = 10000
            //Velocidad a la que la app manejará las actializaciones de la ubicación.
            //fasterInterval especifica la velocidad más rápida a la que la aplicación puede manejar
            //las actualizaciones. Establecer la tasa fastestInterval rápida de fastestInterval limita
            //la rapidez con que se enviarán las actualizaciones a su aplicación. Antes de comenzar a
            //solicitar actualizaciones de ubicación, debe verificar el estado de la configuración
            //de ubicación del usuario.
            fastestInterval = 5000
            //Setea la prioridad de la solicitud
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        //Obtenemos la ubicación del usuario con las opciones que configuramos en locationRequest.
        val builder = LocationSettingsRequest.Builder().addLocationRequest( locationRequest )

        //Verificamos que la actual configuración de ubicación sea satisfecha.
        //Crea un cliente de configuración y una tarea para verificar la configuración de ubicación.
        val client: SettingsClient = LocationServices.getSettingsClient( activity.applicationContext )
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings( builder.build() )

        println("SOLICITANDO PERMISO XD!!!!!!!!!!!!!!!!!")
        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            println("PERMISO CONCEDIDO WE XD!!!!!!!!!!!!!!!!!")
            setUserLocationInMap( map, activity )
            setViewESCOM( map )
        }

        task.addOnFailureListener { exception ->
            println("PERMISO NOO CONCEDIDO WE XD!!!!!!!!!!!!!!!!! ${ exception.localizedMessage }")
            setUserLocationInMap( map, activity )
            setViewESCOM( map )

            if( exception is ResolvableApiException ) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult( activity, REQUEST_CHECK_SETTINGS)
                } catch( sendEx: IntentSender.SendIntentException ) {
                    // Ignore the error.
                    println("ERROR: ${ sendEx.localizedMessage }")
                }
            }
        }
    }
}