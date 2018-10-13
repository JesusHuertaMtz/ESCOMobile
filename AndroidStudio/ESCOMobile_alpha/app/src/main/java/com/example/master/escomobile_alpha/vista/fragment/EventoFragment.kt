package com.example.master.escomobile_alpha.vista.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.modelo.entidad.Evento
import com.example.master.escomobile_alpha.viewholder.EventoAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventoFragment : Fragment(), EventoAdapter.OnEventoSelected {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var  eventoAdapter: EventoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_evento, container, false)

        val reclycerView = view.findViewById<RecyclerView>( R.id.recycler_view )
        reclycerView.layoutManager = GridLayoutManager( context, 2 )
        //GET EVENTOS
        eventoAdapter = EventoAdapter( activity?.applicationContext!! )
        val evento = Evento()
        evento.descripcion = "Prueba mortal XD XD"
        evento.nombre = "Evento 1"
        evento.ponente = "Prueba mortal XD XD"
        evento.imagen = R.drawable.google
        val evento2 = Evento()
        evento2.descripcion = "Prueba mortal XD XD"
        evento2.nombre = "Evento 2"
        evento2.ponente = "Prueba mortal XD XD"
        evento2.imagen = R.drawable.oracle
        val evento3 = Evento()
        evento3.descripcion = "Prueba mortal XD XD"
        evento3.nombre = "Evento 3"
        evento3.ponente = "Prueba mortal XD XD"
        evento3.imagen = R.drawable.escom_front

        val evs = mutableListOf<Evento>()
        evs.add( evento )
        evs.add( evento2 )
        evs.add( evento3 )

        eventoAdapter.updateEventos( evs )
        reclycerView.adapter = eventoAdapter

        registerForContextMenu( reclycerView )

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EventoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onEventoSelected(evento: Evento) {
        val detailsevento = EventoDetailsFragment.newInstance( evento )
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.add( detailsevento, "evento ")?.addToBackStack(null)?.commit()

    }
}
