package com.example.master.escomobile_alpha.vista.fragment

import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentProfesorBinding
import com.example.master.escomobile_alpha.util.CustomProgressBar
import com.example.master.escomobile_alpha.viewmodel.ProfesorViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ProfesorFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProfesorFragment : BaseFragment() {
	private lateinit var profesorViewModel: ProfesorViewModel
	private lateinit var profesorBinding: FragmentProfesorBinding
	private lateinit var searchView: SearchView
	
	companion object {
		@JvmStatic
		fun newInstance() = ProfesorFragment()
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu( true )
	}
	
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
	    CustomProgressBar.show( inflater.context )
	    profesorBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_profesor, container, false )
	    profesorViewModel = ViewModelProviders.of( this as Fragment ).get( ProfesorViewModel::class.java )
	    profesorViewModel.init( inflater.context )
	    profesorBinding.recyclerViewProfesores.layoutManager = LinearLayoutManager( inflater.context )
	    profesorBinding.recyclerViewProfesores.adapter = profesorViewModel.profesorAdapter
	    
	    profesorViewModel.getProfesores()
	    profesorViewModel.setupRecyclerViewFastScroller( profesorBinding )
	    showMenu()
	    CustomProgressBar.getDialog()?.dismiss()
	    
        return profesorBinding.root
    }
	
	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		inflater?.inflate( R.menu.menu_search_profesor, menu )
		val searchItem = menu?.findItem( R.id.action_search_profesor )
		val searchManager = activity?.getSystemService( Context.SEARCH_SERVICE ) as? SearchManager
		searchView = searchItem?.actionView as SearchView
		
		searchView.setSearchableInfo( searchManager?.getSearchableInfo( activity?.componentName ) )
		searchView.setOnQueryTextListener( object: SearchView.OnQueryTextListener {
			override fun onQueryTextChange(newText: String?): Boolean {
				print("TEXT_CHANGE ${ newText }")
				
				return true
			}
			
			override fun onQueryTextSubmit(query: String?): Boolean {
				print("TEXT_QUERY ${ query }")
				
				return true
			}
		})
		
		super.onCreateOptionsMenu( menu, inflater )
	}
	
	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when( item?.itemId ) {
			R.id.action_search_profesor -> return false
		}
		
		return super.onOptionsItemSelected(item)
	}
}


















