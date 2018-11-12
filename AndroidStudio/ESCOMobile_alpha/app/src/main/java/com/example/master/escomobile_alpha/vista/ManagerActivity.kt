package com.example.master.escomobile_alpha.vista

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.arlib.floatingsearchview.FloatingSearchView
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.controlador.busqueda.SearchController
import com.example.master.escomobile_alpha.controlador.mapa.MapsEvent
import com.example.master.escomobile_alpha.modelo.entidad.Empresa
import com.example.master.escomobile_alpha.viewholder.adapter.BolsaTrabajoAdapter
import com.example.master.escomobile_alpha.controlador.menu.MenuController
import com.example.master.escomobile_alpha.firebase.MyFirebaseMessagingService
import com.example.master.escomobile_alpha.modelo.entidad.Busqueda
import com.example.master.escomobile_alpha.modelo.entidad.Cita
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import com.example.master.escomobile_alpha.util.SPLogin
import com.example.master.escomobile_alpha.util.versio_app.ValidateVersionApp
import com.example.master.escomobile_alpha.viewholder.adapter.ProfesorAdapter
import com.example.master.escomobile_alpha.viewholder.adapter.SearchAdapter
import com.example.master.escomobile_alpha.vista.fragment.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_manager.*
import kotlinx.android.synthetic.main.nav_header_menu_controller.view.*

class ManagerActivity : MenuController(), BolsaTrabajoAdapter.OnOfertaLaboralSelected,
						FloatingSearchView.OnFocusChangeListener,
						SearchAdapter.OnSuggestionSearchSelectedListener,
						ProfesorAdapter.OnProfesorSelected,
						MapsEvent.OnInfoWindowClick,
						TabCitaACP.OnCitaChangeState {

	private val ID_FRAGMENT = "myFragment"
    private lateinit var searchController : SearchController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_manager )

        setMenu( MenuOptions.OPTIONS )
	    showInfoUser()

        if( savedInstanceState != null ) {
            //Muestra e fragment almacenado
            fragment = supportFragmentManager.getFragment( savedInstanceState, ID_FRAGMENT )!!
	        println("SET A MAP FRAGMENT")
			replaceFragment( fragment as Fragment )

        } else {
            //No hay ningun fragment almacenado. Mostramos el mapa
            println("CRATE A NEW MAP")
            setFragmentMap()
            searchController = SearchController( floating_search_view )
            searchController.attachNavigationDrawerToMenuButton( drawer_layout )
            searchController.setOnFocusChangeListener( this )
        }

	    MyFirebaseMessagingService.getTokenfirebase {token ->
		    SPLogin.updateUserTokenFCM( this, token )
	    }
    }

	override fun onResume() {
		super.onResume()
		ValidateVersionApp.getVersion( this )
	}

    override fun onSaveInstanceState( outState: Bundle ) {
        super.onSaveInstanceState( outState )

	    println("SAVED FRAGMENT ************")
        supportFragmentManager.putFragment( outState, ID_FRAGMENT, fragment )
    }

    override fun onFocusCleared() {
        if( supportFragmentManager.backStackEntryCount > 0 ) {
            supportFragmentManager.popBackStack()
        }

        //searchController.clearSearch()
        println("FOCUS CLEARED")
    }

    override fun onFocus() {
        println("FOCUS")
	    val searchFragment = SearchFragment.newInstance()
	    searchController.setOnQueryChangeListener( searchFragment )

	    val transaction = supportFragmentManager.beginTransaction()
	    transaction.replace( R.id.fragment_search, searchFragment ).addToBackStack(null ).commit()
    }

    override fun onSuggestionSearchSelectedListener( query: Busqueda ) {
        searchController.searchView.clearSearchFocus()

        val mapFragment = fragment as MapFragment
        val latLng = LatLng( query.salon?.coordenadas?.get(0) ?: 19.504523875, query.salon?.coordenadas?.get(1) ?:-99.146849225 )

        mapFragment.removeMarkerSearch()
        mapFragment.addMarker( latLng, query.nombre )
        mapFragment.changeFloor( query.salon?.piso ?: 0 )
        Toast.makeText( layoutInflater.context, "Search: ${ query }", Toast.LENGTH_SHORT ).show()
    }

	override fun onRemoveMarkerListener() {
		val mapFragment = fragment as MapFragment
		mapFragment.removeMarkerSearch()
	}

	override fun onOfertaLaboralSelected( empresa: Empresa ) {
		Toast.makeText( layoutInflater.context, "on_OFERTA_Selected: ${ empresa.id }", Toast.LENGTH_SHORT ).show()

		val detailsBolsaTrabajo = BolsaTrabajoDetailsFragment.newInstance( empresa )
		replaceFragment( detailsBolsaTrabajo )
	}

    override fun onProfesorSelected( profesor: Profesor ) {
        val perfilProfesor = PerfilProfesorFragment.newInstance( profesor )
        
	    replaceFragment( perfilProfesor )
    }

	override fun onInfoWindowClick( profesor: Profesor ) {
		println("SHOW PROFESOR ${ profesor }")
		onProfesorSelected( profesor )
	}

	override fun onCitaAgendadaOCancelada( fecha: String, cita: Cita, esAgendada: Boolean ) {
		val citaFragment = supportFragmentManager.findFragmentById( R.id.content_fragment ) as? CitaFragment
		println("FURULO -->> ${ citaFragment == null } -- $fecha")

		if( citaFragment == null ) {
			fragment = CitaFragment.newInstance()
			val transaction = supportFragmentManager.beginTransaction()
			transaction.replace( R.id.content_fragment, fragment ).commit()

		} else {
			citaFragment.setCita( fecha, cita, esAgendada )
		}
	}

    fun showMenuFloating() {
        hideMenu()
        floating_search_view.attachNavigationDrawerToMenuButton( drawer_layout )
        floating_search_view.visibility = View.VISIBLE
    }

    fun showSolidMenu() {
        showMenu()
        floating_search_view.visibility = View.GONE
    }

    fun hideMenus() {
        hideMenu()
        floating_search_view.visibility = View.GONE
    }

    fun setSearchHint( searchHint: String ) {
        searchController.setSearchHint( searchHint )
    }

    /**
     * Este método añade un fragment a la vista dependiendo del elemento seleccionado
     * por defecto muestra el mapa. Sirve además para guardar el estado del menú y que no
     * muestre el mapa cada vez que se cambie la orientación del dispositivo.
     * */
    private fun setFragmentMap() {
        println("MAP NEW INSTANCE*************************")
        fragment = MapFragment.newInstance()
	    val transaction = supportFragmentManager.beginTransaction()
	    transaction.replace( R.id.content_fragment, fragment ).commit()
    }

	fun setFragmentMap( profesor: Profesor ) {
		println("MAP NEW INSTANCE*************************")
		fragment = MapFragment.newInstance( profesor )
		val transaction = supportFragmentManager.beginTransaction()
		transaction.replace( R.id.content_fragment, fragment ).commit()
	}
	
	private fun replaceFragment( fragment: Fragment ) {
		val transaction = supportFragmentManager.beginTransaction()
		
		transaction.replace( R.id.content_fragment, fragment ).addToBackStack( null ).commit()
	}

	private fun showInfoUser() {
		val user = SPLogin.loadUserFromSharedPreferences( this )

		val view = nav_view.getHeaderView( 0 )
		view.txt_menu_correo.text = user.correo
		view.txt_menu_nombre.text = user.primerAp + " " + user.segundoAp + " " + user.nombre
	}
}




