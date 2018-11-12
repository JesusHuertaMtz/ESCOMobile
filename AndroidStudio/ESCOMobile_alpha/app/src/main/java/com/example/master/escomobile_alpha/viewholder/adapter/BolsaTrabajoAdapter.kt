package com.example.master.escomobile_alpha.viewholder.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.master.escomobile_alpha.databinding.BolsaTrabajoRecyclerViewItemBinding
import com.example.master.escomobile_alpha.modelo.entidad.Empresa
import com.example.master.escomobile_alpha.viewholder.BolsaTrabajoViewHolder


class BolsaTrabajoAdapter( val context: Context) : RecyclerView.Adapter<BolsaTrabajoViewHolder>() {
    var empresasConOfertasVigentes : MutableList<Empresa> = arrayListOf()
    private var layoutInflater: LayoutInflater
    private var listener: OnOfertaLaboralSelected

    init {
        layoutInflater = LayoutInflater.from( context )

        if( context is OnOfertaLaboralSelected) {
            listener = context

        } else {
            throw ClassCastException( context.toString() + "must implement OnOfertaLaboralSelected" )
        }
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): BolsaTrabajoViewHolder {
        val recyclerViewItemBinding = BolsaTrabajoRecyclerViewItemBinding.inflate( layoutInflater, parent, false )

        return BolsaTrabajoViewHolder(recyclerViewItemBinding.root, recyclerViewItemBinding)
    }

    override fun onBindViewHolder(holder: BolsaTrabajoViewHolder, position: Int ) {
        holder.setData( empresasConOfertasVigentes[position] )

        holder.recyclerViewItemBinding.lblNombreEmpresa.text = empresasConOfertasVigentes[position].nombre
        holder.recyclerViewItemBinding.lblPuesto.text = empresasConOfertasVigentes[position].oferta?.puesto
        holder.recyclerViewItemBinding.lblSueldo.text = "Sueldo: ${empresasConOfertasVigentes[position].oferta?.sueldo}"
        val logoBitmap = empresasConOfertasVigentes[position].getImageBitmap()
        logoBitmap?.let { logo ->
            holder.recyclerViewItemBinding.logoEmpresa.setImageBitmap( logo )
        }

        holder.itemView.setOnClickListener {
            //Toast.makeText( layoutInflater.context, "OFERTA: ${ empresasConOfertasVigentes[position].id }", Toast.LENGTH_SHORT ).show()
            listener.onOfertaLaboralSelected( empresasConOfertasVigentes[position] )
        }
    }

    override fun getItemCount(): Int {
        return empresasConOfertasVigentes.size
    }

    /*================ MÉTODOS PÚBLICOS ================*/
    fun updateEmpresas( empresas: MutableList<Empresa> ) {
        this.empresasConOfertasVigentes = empresas
    }

    /*================ INTERFAZ ================*/
    interface OnOfertaLaboralSelected {
        fun onOfertaLaboralSelected( empresa: Empresa )
    }
}