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

        val cafeteria = mapsContoller.map.addGroundOverlay( GroundOverlayOptions().apply {
            image( BitmapDescriptorFactory.fromResource( R.drawable.cafeteria ) ) //Imagen que s eva a añadir
            anchor( 0.5f, 0.5f ) //0.31f, 0.7f //Sujetadores o anclas de la imagen
            position( cafet, 30f ) //140 //La posicion donde se centrará la imagen
            bearing( 30f ) //Rota la imagen en grados siguiendo las manecillas del reloj comenzando desde el norte
        })
        cafeteria.setDimensions((cafeteria.width * 0.7).toFloat(), (cafeteria.height * 0.7).toFloat())
    }

    private fun getGroundOverlay( map: GoogleMap, idOverlay: Int, isVisible: Boolean ): GroundOverlay {
        val escom = LatLng( 19.5045672, -99.1469098 )
        val groundOverlay = map.addGroundOverlay( GroundOverlayOptions().apply {
            image( BitmapDescriptorFactory.fromResource( idOverlay ) ) //Imagen que se va a añadir
            //achor( 0.461f, 0.545f )
            anchor( 0.461f, 0.540f ) //0.31f, 0.7f //Sujetadores o anclas de la imagen
            position( escom, 158f ) //156//170//140 //La posicion donde se centrará la imagen
            visible( isVisible )
            //transparency( 0.7f ) //Valor entre 0 y 1. 0 para opaco y 1 para transparencia
        })

        //Para el nuevo mapa hay que hacer un ajuste de tamaño
        val h : Float = (groundOverlay.height * 0.96).toFloat()
        groundOverlay.setDimensions( groundOverlay.width, h )

        return groundOverlay
    }

    fun showPlanta( planta: Edificio, idKMLPlanta: Int, mapsContoller: MapsController ) {

        groundOverlayP2.isVisible = planta.equals( Edificio.SEGUNDO_P )
        groundOverlayP1.isVisible = planta.equals( Edificio.PRIMER_P )
        groundOverlayPB.isVisible = planta.equals( Edificio.P_BAJA )

        /*
        //NO SE MUEsTRA PROVICIONALMENTE EL KML
        layer.removeLayerFromMap()
        layer = KmlLayer( mapsContoller.map, idKMLPlanta, mapsContoller.activity )
        layer.addLayerToMap()
        mapsContoller.map.setOnPolygonClickListener( mapsContoller )
        */
    }

    //Se quito provisionalmente porque ya no se usa KML
    fun initializeLayer( mapsContoller: MapsController ) {
        layer = KmlLayer( mapsContoller.map, R.raw.mapa_pb_escom, mapsContoller.activity )
        layer.addLayerToMap()
        mapsContoller.map.setOnPolygonClickListener( mapsContoller )

    }
}