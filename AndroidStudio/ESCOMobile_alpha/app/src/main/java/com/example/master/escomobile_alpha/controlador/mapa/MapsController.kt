package com.example.master.escomobile_alpha.controlador.mapa

import android.app.Activity
import android.graphics.Color
import android.os.Environment
import android.widget.Button
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.util.Edificio
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.kml.KmlLayer

import java.io.*
import java.util.*


/**
 * Created by Master Chief on 09/03/2018.
 */
/**
 * Clase para manejar los eventos del mapa.
 * */
class MapsController( val activity: Activity ) : OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnPolygonClickListener {
    lateinit var map : GoogleMap private set
    private var mapSettings : MapsSetting
    private lateinit var mapGroundOverlay: MapsGroundOverlay
    private lateinit var mapEvents : MapsEvent

    init {
        /* Inicia el mapa */
        mapSettings = MapsSetting.newInstance( activity )
    }

    /**
     * Puedes manipiular el mapa una vez que este disponible.
     */
    override fun onMapReady( googleMap: GoogleMap? ) {
        this.map = googleMap!!

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener( this )

        //Añade un estilo al mapa
        addStyleToMap()

        //setMapSettings()
        mapSettings.createLocationRequest( map, activity )

        //Añade la imagen de la cafeteria y las plantas de ESCOM
        mapGroundOverlay = MapsGroundOverlay()
        mapGroundOverlay.addImage( this )

        //Añadimos los eventos al mapa
        mapEvents = MapsEvent.newInstance( activity, map )

	    //añade el evento on infowondow al mapa
	    if( activity is MapsEvent.OnInfoWindowClick ) {
		    mapEvents.setInfoWindowClickListener( activity )
	    }

        onBtnClickListener()
    }

    /**
     * Este método se debe invocar únicamente en la clase donde se
     * sobreescribe el método onRequestPermissionsResult().
     * */
    fun setMapSettings() {
        mapSettings.setUserLocationInMap( this.map, activity )
        mapSettings.setViewESCOM( map )
    }

    override fun onMarkerClick( marker: Marker? ): Boolean {
        return false
    }

    private fun addStyleToMap() {
        val style = MapStyleOptions.loadRawResourceStyle( activity.applicationContext, R.raw.mapstyle_nolabels )
        map.setMapStyle( style )
    }

    /////////////METODOS POLIGONO
    var marker : Marker? = null
    override fun onPolygonClick( polygon: Polygon? ) {
        val bound = 256
        val color = Color.argb( 100, Random().nextInt( bound ), Random().nextInt( bound ), Random().nextInt( bound ) )
        polygon?.fillColor = color

        if( marker != null ) {
            marker?.remove()
        }

        marker = map.addMarker( MarkerOptions().apply {
            title("ID ${ polygon?.id }")
            val latLng : LatLng = centroid( polygon?.points!! )
            position( latLng )
            println(latLng)
        } )

        marker?.showInfoWindow()
    }

    fun centroid( points: List<LatLng> ): LatLng {
        val centroid = doubleArrayOf( 0.0, 0.0 )

        for( i in points.indices ) {
            centroid[0] += points[i].latitude
            centroid[1] += points[i].longitude
        }

        val totalPoints = points.size
        centroid[0] = centroid[0] / totalPoints
        centroid[1] = centroid[1] / totalPoints

        return LatLng( centroid[0], centroid[1] )
    }

    ////////////////////////
    //TODO: AÑADE LAS COORDENADAS EN UN ARCHIVO KML DESCOMENTAR CUANDO SE NECESITE
    //CREAR SALONES
    lateinit var btnPB : Button
    lateinit var btnP1 : Button
    lateinit var btnP2 : Button

    fun onBtnClickListener() {
        btnPB = activity.findViewById( R.id.btnPB )
        btnP1 = activity.findViewById( R.id.btnP1 )
        btnP2 = activity.findViewById( R.id.btnP2 )

        mapGroundOverlay.initializeLayer( this )
        btnPB.setBackgroundResource( R.color.colorPrimaryLight )

        btnPB.setOnClickListener {
            btnPB.setBackgroundResource( R.color.colorPrimaryLight )
            btnP1.setBackgroundResource( R.color.white_transparent )
            btnP2.setBackgroundResource( R.color.white_transparent )

            println("PLANTA BAJA")
            mapGroundOverlay.showPlanta( Edificio.P_BAJA, R.raw.mapa_pb_escom, this )
        }

        btnP1.setOnClickListener {
            btnPB.setBackgroundResource( R.color.white_transparent )
            btnP1.setBackgroundResource( R.color.colorPrimaryLight )
            btnP2.setBackgroundResource( R.color.white_transparent )

            mapGroundOverlay.showPlanta( Edificio.PRIMER_P, R.raw.mapa_p1_escom, this )

            /*mutablePolygon.fillColor = Color.GREEN

            mapEvents.clearMarkers()

            val filename = "mapa_kml.txt"
            val file = File( Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOCUMENTS ), filename )
            val inputStream: InputStream = file.inputStream()
            //val lineList = mutableListOf<String>()

            inputStream.bufferedReader().useLines {
                lines -> lines.forEach {
                    println( it )
                }
            }*/
        }

        btnP2.setOnClickListener {
            btnPB.setBackgroundResource( R.color.white_transparent )
            btnP1.setBackgroundResource( R.color.white_transparent )
            btnP2.setBackgroundResource( R.color.colorPrimaryLight )

            mapGroundOverlay.showPlanta( Edificio.SEGUNDO_P, R.raw.mapa_p2_escom, this )

            /*mutablePolygon.fillColor = Color.WHITE
            var coordenadas = ""

            println("COORDENADAS************")
            for( marker in mapEvents.markers ) {
                //println("${marker.position.longitude},${marker.position.latitude},0")
                println("${marker.position.latitude} ${marker.position.longitude}")
                coordenadas += "            ${marker.position.longitude},${marker.position.latitude},0\n"
            }

            saveFile( coordenadas )*/
        }
    }

    private fun saveFile( coordenadas : String ) {
        /*val kmlFormat_1 = "<Placemark>\n" +
                "        <name>2109</name>\n" +
                "        <styleUrl>#line-000000-1200-nodesc</styleUrl>\n" +
                "        <LineString>\n" +
                "          <tessellate>1</tessellate>\n" +
                "          <coordinates>\n" +
                "            \n"
        val kmlFormat_2 =
                "          </coordinates>\n" +
                "        </LineString>\n" +
                "      </Placemark>"*/
        val kmlFormat_1 = "<name>2109</name>\n" +
                "        <styleUrl>#poly-000000-1200-77-nodesc</styleUrl>\n" +
                "        <Polygon>\n" +
                "          <outerBoundaryIs>\n" +
                "            <LinearRing>\n" +
                "          <tessellate>1</tessellate>\n" +
                "          <coordinates>\n"

        val kmlFormat_2 =
                "          </coordinates>\n" +
                "        </LinearRing>\n" +
                "          </outerBoundaryIs>\n" +
                "        </Polygon>\n" +
                "      </Placemark>"

        val filename = "mapa_kml.txt"
        val file = File( Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOCUMENTS ), filename )
        println("PATH ${ file.absolutePath }")

        // have the object build the directory structure, if needed.
        if( !file.exists() ) {
            file.createNewFile()
        }

        file.setWritable( true )
        file.appendText( kmlFormat_1, Charsets.UTF_8 )
        file.appendText( coordenadas, Charsets.UTF_8 )
        file.appendText( kmlFormat_2, Charsets.UTF_8 )

        println("COORDENADAS ALMACENADAS!")
    }


    //TODO: PROVISIONAL
    fun changeFloor( floor: Edificio ) {
        when( floor ) {
            Edificio.P_BAJA -> {
                btnPB.callOnClick()
            }
            Edificio.PRIMER_P -> {
                btnP1.callOnClick()
            }
            Edificio.SEGUNDO_P -> {
                btnP2.callOnClick()
            }
        }
    }
}



