package com.example.master.escomobile_alpha.vista.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master.escomobile_alpha.databinding.FragmentEventoDetailsBinding
import com.example.master.escomobile_alpha.modelo.entidad.Evento

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "evento"

/**
 * A simple [Fragment] subclass.
 * Use the [EventoDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventoDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Evento? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable( ARG_PARAM1 ) as Evento
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val eventoDetailsBindig = FragmentEventoDetailsBinding.inflate( inflater, container, false )
        eventoDetailsBindig.evento = param1

        return eventoDetailsBindig.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventoDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance( evento: Evento) =
                EventoDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable( ARG_PARAM1, param1 )
                    }
                }
    }
}
