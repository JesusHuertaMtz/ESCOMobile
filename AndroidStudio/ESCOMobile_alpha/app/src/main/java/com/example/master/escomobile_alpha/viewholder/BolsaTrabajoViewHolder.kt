package com.example.master.escomobile_alpha.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.master.escomobile_alpha.databinding.BolsaTrabajoRecyclerViewItemBinding
import com.example.master.escomobile_alpha.modelo.entidad.Empresa

class BolsaTrabajoViewHolder( itemView : View, val recyclerViewItemBinding: BolsaTrabajoRecyclerViewItemBinding ) : RecyclerView.ViewHolder( itemView ) {
    fun setData( empresa: Empresa ) {
        recyclerViewItemBinding.empresaConOfertasVigentes = empresa
    }
}