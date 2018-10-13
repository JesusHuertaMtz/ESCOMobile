package com.example.master.escomobile_alpha.controlador.mapa

import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.util.Edificio
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.kml.KmlLayer

class MapsGroundOverlay() {
    lateinit var groundOverlayPB: GroundOverlay
    lateinit var groundOverlayP1: GroundOverlay
    lateinit var groundOverlayP2: GroundOverlay
    lateinit var layer: KmlLayer

    fun addImage( mapsContoller: MapsController ) {
        val cafet = LatLng( 19.5041925,-99.1477129 )

        //Imagen en mapa
        // A ground overlay. añade una "superposición de suelo".
        groundOverlayPB = getGroundOverlay( mapsContoller.map, R.drawable.escom_pb, true )
        groundOverlayP1 = getGroundOverlay( mapsContoller.map, R.drawable.escom_p1, false )
        groundOverlayP2 = getGroundOverlay( mapsContoller.map, R.drawable.escom_p2, false )

        mapsContoller.map.addGroundOverlay( GroundOverlayOptions().apply {
            image( BitmapDescriptorFactory.fromResource( R.drawable.cafeteria ) ) //Imagen que s eva a añadir
            anchor( 0.5f, 0.5f ) //0.31f, 0.7f //Sujetadores o anclas de la imagen
            position( cafet, 30f ) //140 //La posicion donde se centrará la imagen
            bearing( 30f ) //Rota la imagen en grados siguiendo las manecillas del reloj comenzando desde el norte
        })
    }

    private fun getGroundOverlay( map: GoogleMap, idOverlay: Int, isVisible: Boolean ): GroundOverlay {
        val escom = LatLng( 19.5045672, -99.1469098 )

        return map.addGroundOverlay( GroundOverlayOptions().apply {
            image( BitmapDescriptorFactory.fromResource( idOverlay ) ) //Imagen que se va a añadir
            anchor( 0.461f, 0.545f )//anchor( 0.483f, 0.525f ) //0.31f, 0.7f //Sujetadores o anclas de la imagen
            position( escom, 156f ) //170//140 //La posicion donde se centrará la imagen
            visible( isVisible )
            //transparency( 0.7f ) //Valor entre 0 y 1. 0 para opaco y 1 para transparencia
        })
    }

    fun showPlanta( planta: Edificio, idKMLPlanta: Int, mapsContoller: MapsController ) {

        groundOverlayP2.isVisible = planta.equals( Edificio.SEGUNDO_P )
        groundOverlayP1.isVisible = planta.equals( Edificio.PRIMER_P )
        groundOverlayPB.isVisible = planta.equals( Edificio.P_BAJA )

        layer.removeLayerFromMap()
        layer = KmlLayer( mapsContoller.map, idKMLPlanta, mapsContoller.activity )
        layer.addLayerToMap()
        mapsContoller.map.setOnPolygonClickListener( mapsContoller )
    }

    fun initializeLayer( mapsContoller: MapsController ) {
        layer = KmlLayer( mapsContoller.map, R.raw.mapa_pb_escom, mapsContoller.activity )
        layer.addLayerToMap()
        mapsContoller.map.setOnPolygonClickListener( mapsContoller )

    }
}