package com.example.master.escomobile_alpha.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.master.escomobile_alpha.databinding.EventoRecyclerViewItemBinding
import com.example.master.escomobile_alpha.modelo.entidad.Evento


class EventoViewHolder( itemView: View, val recyclerViewItemBinding: EventoRecyclerViewItemBinding ):
        RecyclerView.ViewHolder( itemView ) {

    fun setData( evento: Evento) {
        recyclerViewItemBinding.evento = evento
    }

}