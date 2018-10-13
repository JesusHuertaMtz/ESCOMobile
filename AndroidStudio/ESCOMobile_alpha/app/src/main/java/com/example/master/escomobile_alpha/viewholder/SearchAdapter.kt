package com.example.master.escomobile_alpha.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.master.escomobile_alpha.databinding.SearchRecyclerViewItemBinding
import com.example.master.escomobile_alpha.modelo.entidad.Busqueda

class SearchAdapter( val context: Context ): RecyclerView.Adapter<SearchViewHolder>() {
    var searchResult = mutableListOf<Busqueda>()
    private var layoutInflater: LayoutInflater
    private var listener : OnSuggestionSearchSelectedListener

    init {
        layoutInflater = LayoutInflater.from( context )

        if( context is OnSuggestionSearchSelectedListener ) {
            listener = context

        } else {
            throw ClassCastException( context.toString() + "must implement OnOfertaLaboralSelected" )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val searchRecyclerViewItemBinding = SearchRecyclerViewItemBinding.inflate( layoutInflater, parent, false )

        return SearchViewHolder( searchRecyclerViewItemBinding.root, searchRecyclerViewItemBinding )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.searchRecyclerViewItemBinding.lblName.text = searchResult[position].nombre

        holder.itemView.setOnClickListener {
            listener.onSuggestionSearchSelectedListener( searchResult[position] )
        }
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }

    fun update( searchResult: MutableList<Busqueda> ) {
        this.searchResult = searchResult
        this.notifyDataSetChanged()
    }

    /*======= INTERFAZ =======*/
    interface OnSuggestionSearchSelectedListener {
        fun onSuggestionSearchSelectedListener( query: Busqueda )
    }
}