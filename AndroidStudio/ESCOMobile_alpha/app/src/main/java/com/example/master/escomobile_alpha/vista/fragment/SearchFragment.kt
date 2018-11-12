package com.example.master.escomobile_alpha.vista.fragment

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.controlador.busqueda.SearchController
import com.example.master.escomobile_alpha.databinding.FragmentSearchBinding
import com.example.master.escomobile_alpha.viewmodel.SearchViewModel
import com.example.master.escomobile_alpha.vista.ManagerActivity
import android.view.WindowManager

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment(), SearchController.OnQueryChangeListener {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchRVB: FragmentSearchBinding
	private val searchHints = hashMapOf( "NombreProf" to "Buscar profesor", "Grupo" to "Buscar grupo", "Salon" to "Buscar salón", "NombAcademia" to "Buscar academia" )
	private val searchIconId = hashMapOf( "NombreProf" to R.drawable.ic_activo_profesores, "Grupo" to R.drawable.ic_activo_grupos, "Salon" to R.drawable.ic_activo_classroom, "NombAcademia" to R.drawable.ic_activo_academias )

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
        private var itemSelected = ""
    }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
	}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchRVB = DataBindingUtil.inflate( inflater, R.layout.fragment_search, container, false )
        searchViewModel = ViewModelProviders.of( this as Fragment ).get( SearchViewModel::class.java )

        val view = searchRVB.root
        setOnButtonFilterClickListener()

        searchRVB.rcSearch.layoutManager = LinearLayoutManager( inflater.context )

        searchViewModel.init( inflater.context )
        //searchViewModel //Llamar a un metodo como bolsaTrabajoViewModel.getEmpresasConOfertasVigentes()
        searchRVB.rcSearch.adapter = searchViewModel.searchAdapter
	    if( !itemSelected.isEmpty() ) {
		    changeSearchType( itemSelected )
	    }

        return view
    }

    private fun setOnButtonFilterClickListener() {
        searchRVB.btnProfesores.setOnClickListener {
            changeSearchType( "NombreProf" )
	        itemSelected = "NombreProf"
        }
        searchRVB.btnGrupos.setOnClickListener {
            changeSearchType( "Grupo" )
	        itemSelected = "Grupo"
        }
        searchRVB.btnSalones.setOnClickListener {
            changeSearchType( "Salon" )
	        itemSelected = "Salon"
        }
        searchRVB.btnAcademias.setOnClickListener {
	        changeSearchType( "NombAcademia" )
	        itemSelected = "NombAcademia"
        }
    }

	private fun changeSearchType( searchType: String ) {
		val managerActivity = activity as? ManagerActivity
		searchViewModel.searchType = searchType

		clearButton()
		val icon = ResourcesCompat.getDrawable( resources, searchIconId[searchType] ?: 0, null )
		getImageButton( searchType )?.setImageDrawable( icon )
		managerActivity?.setSearchHint( searchHints[searchType] ?: "" )
		Toast.makeText( activity, searchHints[searchType] ?: "", Toast.LENGTH_SHORT ).show()
	}

	private fun getImageButton( searchType: String ): ImageButton? {
		when( searchType ) {
			"NombreProf" -> return searchRVB.btnProfesores
			"Grupo" -> return searchRVB.btnGrupos
			"Salon" -> return searchRVB.btnSalones
			"NombAcademia" -> return searchRVB.btnAcademias
			else -> return null
		}
	}

    private fun clearButton() {
        val icon_academia_inactivo = ResourcesCompat.getDrawable( resources, R.drawable.ic_inactivo_academias, null )
        val icon_grupos_inactivo = ResourcesCompat.getDrawable( resources, R.drawable.ic_inactivo_grupos, null )
        val icon_profesores_inactivo = ResourcesCompat.getDrawable( resources, R.drawable.ic_inactivo_profesores, null )
        val icon_salones_inactivo = ResourcesCompat.getDrawable( resources, R.drawable.ic_inactivo_classroom, null )

        searchRVB.btnAcademias.setImageDrawable( icon_academia_inactivo )
        searchRVB.btnGrupos.setImageDrawable( icon_grupos_inactivo )
        searchRVB.btnProfesores.setImageDrawable( icon_profesores_inactivo )
        searchRVB.btnSalones.setImageDrawable( icon_salones_inactivo )
    }

    override fun onQueryChangeListener( query: String ) {
	    searchViewModel.realizarBusqueda( query ) { response ->
		    println("SEARCH ${ response }")
	    }
    }
}
