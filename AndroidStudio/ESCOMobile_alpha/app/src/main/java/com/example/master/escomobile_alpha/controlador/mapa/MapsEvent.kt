package com.example.master.escomobile_alpha.controlador.mapa

import android.app.Activity
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import com.example.master.escomobile_alpha.modelo.logica_negocio.BSBusquedaEscom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.model.*

/**
 * Created by isaac on 3/04/18.
 */
class MapsEvent private constructor() : GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,
                                        GoogleMap.OnCameraIdleListener, OnMarkerDragListener,
                                        GoogleMap.OnInfoWindowClickListener {

    private lateinit var map: GoogleMap
	private var infoWindowClickListener: OnInfoWindowClick? = null
    var markersOptions = arrayListOf<MarkerOptions>()
    var markers = arrayListOf<Marker>()

    companion object {
        fun newInstance( activity: Activity, map: GoogleMap ): MapsEvent {
            val mapEvent = MapsEvent()
            mapEvent.init( activity, map )

            return mapEvent
        }
    }

    /*
    * Inicializa los eventos que tendrá el mapa */
    private fun init( activity: Activity, map: GoogleMap ) {
        this.map = map

        map.setOnMapClickListener( this )
        map.setOnMapLongClickListener( this )
        map.setOnCameraIdleListener( this )
        map.setOnMarkerDragListener( this )
	    map.setOnInfoWindowClickListener( this )
    }

    /*
    * */
    override fun onMapClick( point: LatLng? ) {

    }

    override fun onMapLongClick( point: LatLng? ) {
        point?.let { clickPoint ->
            val marker = MarkerOptions().apply {
                position( clickPoint )
                title("Posicion presionada:")
                draggable( true )

                icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN ) )
                snippet("lat: ${clickPoint.latitude}\n" +
                        "lng: ${clickPoint.longitude}")
                infoWindowAnchor( 1f, 1f )
                println("lat :${clickPoint.latitude} lon:  ${clickPoint.longitude}")
            }

            val m = map.addMarker( marker )
            m.isDraggable = true
            markers.add( m )
        }
    }

    override fun onCameraIdle() {

    }

    fun showMarkers() {
        for( m in markers ) {
            println("id ${ m.id }")
        }
    }

    fun clearMarkers() {
        for( m in markers ) {
            m.remove()
        }

        markers.clear()
    }

	fun setInfoWindowClickListener( infoWindowClickListener: OnInfoWindowClick ) {
		this.infoWindowClickListener = infoWindowClickListener
	}

    //TODO: Eventos del marker draggable
    override fun onMarkerDragEnd( marker: Marker? ) {
        //println("POSITION ${marker?.position?.latitude }  ${marker?.position?.longitude}")
    }

    override fun onMarkerDragStart(marker: Marker?) {

    }

    override fun onMarkerDrag(marker: Marker?) {

    }

    //TODO: Evento de infowindowClickListener
    override fun onInfoWindowClick( marker: Marker? ) {
	    println("onInfoWindowClickListener ${ marker?.id } ${marker?.tag} ${marker?.title}")
	    BSBusquedaEscom().searchProfesorByName( marker?.title ?: "" ) { profesor ->
		    profesor?.let {
			    infoWindowClickListener?.onInfoWindowClick( it )
		    }
	    }
    }

	interface OnInfoWindowClick {
		fun onInfoWindowClick( profesor: Profesor )
	}
}