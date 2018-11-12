package com.example.master.escomobile_alpha.vista

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.controlador.mapa.MapsController
import com.example.master.escomobile_alpha.controlador.mapa.MapsSetting
import com.google.android.gms.maps.SupportMapFragment

class MapsActivity : AppCompatActivity() {
    private lateinit var mapController : MapsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_maps )

        // Obtiene el SupportMapFragment y notifica cuando el mapa este listo para ser usuado.
        val mapFragment = supportFragmentManager.findFragmentById( R.id.map ) as SupportMapFragment

        mapController = MapsController(this )
        //Muestra las opciones en el mapa.
        mapFragment.getMapAsync( mapController )
    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Toast.makeText( this, "onRequest", Toast.LENGTH_SHORT ).show()
        when( requestCode ) {
            MapsSetting.REQUEST_CHECK_SETTINGS -> {
                if( grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    //Permiso concedido. Yay! Mostramos la configuración en el mapa.
                    mapController.setMapSettings()
                    Toast.makeText( this, "¡Bien!", Toast.LENGTH_SHORT ).show()

                } else {
                    //Permiso negado, boo!, deshabilitamos la funcionalidad que depende
                    //de este permiso
                    Toast.makeText( this, R.string.map_request_denied, Toast.LENGTH_SHORT ).show()
                }

            }

            MapsSetting.LOCATION_PERMISSION_REQUEST_CODE -> {
                if( grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    //Permiso concedido. Yay! Mostramos la configuración en el mapa.
                    mapController.setMapSettings()
                    Toast.makeText( this, "¡Bien X2!", Toast.LENGTH_SHORT ).show()

                }
            }

            else -> {
                Toast.makeText( this, "ERROR", Toast.LENGTH_SHORT ).show()
            }
        }
    }
}
