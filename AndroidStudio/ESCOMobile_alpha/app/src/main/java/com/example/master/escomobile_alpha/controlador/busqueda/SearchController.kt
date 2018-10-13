package com.example.master.escomobile_alpha.controlador.busqueda

import android.support.constraint.Placeholder
import android.support.v4.widget.DrawerLayout
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.example.master.escomobile_alpha.util.request.RequestManager

class SearchController( val searchView: FloatingSearchView ): FloatingSearchView.OnSearchListener {
    private var onSearchListener: SearchController.OnQueryChangeListener? = null

    init {
        searchView.setOnQueryChangeListener { oldQuery, newQuery ->
            println("OLDQUERY ${ oldQuery }")
            println("NEWQUERY ${ newQuery }")

            if( !newQuery.isEmpty() ) {
                this.searchView.showProgress()
                this.onSearchListener?.onQueryChangeListener( newQuery )
                this.searchView.hideProgress()
            }
        }

        //Muestra los tipos de busqueda
        searchView.setOnSearchListener( this )
    }

    override fun onSearchAction(currentQuery: String?) {
        println("QUERY ${ currentQuery }")
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
        this.searchView.clearSearchFocus()
        val query = searchSuggestion?.body
        println("Suggestion ${ query }")
    }

    //Añade el menú al botón hamburger
    fun attachNavigationDrawerToMenuButton( drawer_layout: DrawerLayout ) {
        this.searchView.attachNavigationDrawerToMenuButton( drawer_layout )
    }

    //añade el evento onFocusListener
    fun setOnFocusChangeListener( focusChangeListener: FloatingSearchView.OnFocusChangeListener ) {
        this.searchView.setOnFocusChangeListener( focusChangeListener )
    }

    fun setOnQueryChangeListener( listener: OnQueryChangeListener ) {
        this.onSearchListener = listener
    }

    fun clearSearch() {
        this.searchView.hideProgress()
        this.searchView.clearQuery()
    }

    fun setSearchHint( searchHint: String ) {
        searchView.setSearchHint( searchHint )
    }

    interface OnQueryChangeListener {
        fun onQueryChangeListener( query: String )
    }
}