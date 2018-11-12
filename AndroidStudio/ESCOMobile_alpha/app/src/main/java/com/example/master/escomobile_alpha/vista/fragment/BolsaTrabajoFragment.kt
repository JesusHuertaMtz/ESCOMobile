package com.example.master.escomobile_alpha.vista.fragment


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentBolsaTrabajoBinding
import com.example.master.escomobile_alpha.util.CustomProgressBar
import com.example.master.escomobile_alpha.viewmodel.BolsaTrabajoViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BolsaTrabajoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BolsaTrabajoFragment : BaseFragment() {
    private lateinit var bolsaTrabajoViewModel: BolsaTrabajoViewModel
    private lateinit var bolsaTrabajoRVB: FragmentBolsaTrabajoBinding

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BolsaTrabajoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = BolsaTrabajoFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        bolsaTrabajoRVB = DataBindingUtil.inflate( inflater, R.layout.fragment_bolsa_trabajo, container, false )
        bolsaTrabajoViewModel = ViewModelProviders.of( this as Fragment ).get( BolsaTrabajoViewModel::class.java )
        //bolsaTrabajoViewMode.init() //Si se requiere añadir algo al cerear la instancia escribir este metodo
        val view = bolsaTrabajoRVB.root

        bolsaTrabajoRVB.bolsaRecyclerView.layoutManager = LinearLayoutManager( inflater.context )
        bolsaTrabajoViewModel.init( inflater.context )
        bolsaTrabajoViewModel.getEmpresasConOfertasVigentes()
        bolsaTrabajoRVB.bolsaRecyclerView.adapter = bolsaTrabajoViewModel.bolsaTrabajoAdapter

        showMenu()
        setTitle("Bolsa de trabajo")
        //AHORA SOLO SE CONSULTAN LAS OFERTA LABORALES

        return view
    }
}








