package com.example.master.escomobile_alpha.vista.fragment


import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.controlador.mapa.MapsController
import com.example.master.escomobile_alpha.controlador.mapa.MapsEvent
import com.example.master.escomobile_alpha.controlador.mapa.MapsSetting
import com.example.master.escomobile_alpha.util.Edificio
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MapFragment : BaseFragment() {
	private lateinit var mapController : MapsController

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate( R.layout.fragment_map, container, false )

        var map = childFragmentManager.findFragmentById( R.id.map )
        if( map == null ) {
            Toast.makeText( activity, "MAPA NULL!!!!", Toast.LENGTH_SHORT ).show()

        } else {
            map = map as SupportMapFragment
            mapController = MapsController( activity!! )
	        map.getMapAsync( mapController )
        }

        showMenuFloating()

        return view
    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Toast.makeText( activity, "onRequest", Toast.LENGTH_SHORT ).show()
        when( requestCode ) {
            MapsSetting.REQUEST_CHECK_SETTINGS -> {
                if( grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    //Permiso concedido. Yay! Mostramos la configuración en el mapa.
                    mapController.setMapSettings()
                    Toast.makeText( activity, "¡Bien!", Toast.LENGTH_SHORT ).show()

                } else {
                    //Permiso negado, boo!, deshabilitamos la funcionalidad que depende de este permiso
                    Toast.makeText( activity, R.string.map_request_denied, Toast.LENGTH_SHORT ).show()
                }
            }

            MapsSetting.LOCATION_PERMISSION_REQUEST_CODE -> {
                if( grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    //Permiso concedido. Yay! Mostramos la configuración en el mapa.
                    mapController.setMapSettings()
                    Toast.makeText( activity, "¡Bien X2!", Toast.LENGTH_SHORT ).show()
                }
            }

            else -> {
                Toast.makeText( activity, "ERROR", Toast.LENGTH_SHORT ).show()
            }
        }
    }

    //TODO PROVISIONAL
    var markerSearch: Marker? = null
    fun addMarker( latLng: LatLng, title: String ) {
        val marker = MarkerOptions().apply {
            position( latLng )
            title( title )
        }
        markerSearch = mapController.map.addMarker( marker )
        mapController.map.moveCamera( CameraUpdateFactory.newLatLng( latLng ) )
    }

    fun removeMarkerSearch() {
        markerSearch?.remove()
    }

    fun changeFloor( floor: Int ) {
        when( floor ) {
            0 -> mapController.changeFloor( Edificio.P_BAJA )
            1 -> mapController.changeFloor( Edificio.PRIMER_P )
            2 -> mapController.changeFloor( Edificio.SEGUNDO_P )
        }

    }
}
