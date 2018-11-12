package com.example.master.escomobile_alpha.viewholder.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.master.escomobile_alpha.databinding.EventoRecyclerViewItemBinding
import com.example.master.escomobile_alpha.modelo.entidad.Evento
import com.example.master.escomobile_alpha.viewholder.EventoViewHolder
import kotlinx.android.synthetic.main.evento_recycler_view_item.view.*

class EventoAdapter( val context: Context ) : RecyclerView.Adapter<EventoViewHolder>() {
    private var layoutInflater: LayoutInflater
    var eventos: MutableList<Evento> = arrayListOf()
    private lateinit var listener : OnEventoSelected

    init {
        layoutInflater = LayoutInflater.from( context )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val recyclerViewItemBinding = EventoRecyclerViewItemBinding.inflate( layoutInflater, parent, false )
        return EventoViewHolder(recyclerViewItemBinding.root, recyclerViewItemBinding)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        holder.setData( eventos.get( position ) )

        holder.itemView.setOnClickListener {
            Toast.makeText( layoutInflater.context, it.title_evento.text, Toast.LENGTH_SHORT ).show()
            //listener.onEventoSelected( eventos.get( position ) )
        }
    }

    fun updateEventos( eventos: MutableList<Evento>) {
        this.eventos = eventos
    }

    interface OnEventoSelected {
        fun onEventoSelected( evento: Evento )
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

}