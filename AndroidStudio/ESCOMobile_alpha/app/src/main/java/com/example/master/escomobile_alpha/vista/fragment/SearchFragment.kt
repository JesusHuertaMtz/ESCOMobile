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
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.controlador.busqueda.SearchController
import com.example.master.escomobile_alpha.databinding.FragmentSearchBinding
import com.example.master.escomobile_alpha.viewmodel.SearchViewModel
import com.example.master.escomobile_alpha.vista.ManagerActivity

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment(), SearchController.OnQueryChangeListener {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchRVB: FragmentSearchBinding

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
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

        return view
    }

    private fun setOnButtonFilterClickListener() {
        val managerActivity = activity as? ManagerActivity

        searchRVB.btnProfesores.setOnClickListener {
            searchViewModel.searchType = "NombreProf"

            clearButton()
            val icon_profesor = ResourcesCompat.getDrawable( resources, R.drawable.ic_activo_profesores, null )
            searchRVB.btnProfesores.setImageDrawable( icon_profesor )
            managerActivity?.setSearchHint( "Buscar profesor" )
            Toast.makeText( activity, "Buscar profesores", Toast.LENGTH_SHORT ).show()
        }
        searchRVB.btnGrupos.setOnClickListener {
            searchViewModel.searchType = "Grupo"

            clearButton()
            val icon_grupo = ResourcesCompat.getDrawable( resources, R.drawable.ic_activo_grupos, null )
            searchRVB.btnGrupos.setImageDrawable( icon_grupo )
            managerActivity?.setSearchHint( "Buscar grupo" )
            Toast.makeText( activity, "Buscar grupos", Toast.LENGTH_SHORT ).show()
        }
        searchRVB.btnSalones.setOnClickListener {
            searchViewModel.searchType = "Salon"

            clearButton()
            val icon_salon = ResourcesCompat.getDrawable( resources, R.drawable.ic_activo_classroom, null )
            searchRVB.btnSalones.setImageDrawable( icon_salon )
            managerActivity?.setSearchHint( "Buscar salón" )
            Toast.makeText( activity, "Buscar salones", Toast.LENGTH_SHORT ).show()
        }
        searchRVB.btnAcademias.setOnClickListener {
            searchViewModel.searchType = "NombAcademia"

            clearButton()
            val icon_academia = ResourcesCompat.getDrawable( resources, R.drawable.ic_activo_academias, null )
            searchRVB.btnAcademias.setImageDrawable( icon_academia )
            managerActivity?.setSearchHint( "Buscar academia" )
            Toast.makeText( activity, "Buscar academias", Toast.LENGTH_SHORT ).show()
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
