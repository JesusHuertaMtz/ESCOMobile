package com.example.master.escomobile_alpha.vista.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.modelo.entidad.Cita
import com.example.master.escomobile_alpha.viewholder.expandable.CitaExpandableListAdapter
import kotlinx.android.synthetic.main.fragment_tab_cita_acp.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [TabCitaACP.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TabCitaACP : Fragment() {
	// TODO: Rename and change types of parameters
	private val FECHA : String = "param1"
	private val CITAS : String = "param2"
	private val TCITA : String = "param3"
	private lateinit var fechas: List<String>
	private lateinit var citas: HashMap<String, MutableList<Cita>>
	private var tipoCita : Int = -1
	private lateinit var citaExpandableListView: CitaExpandableListAdapter

	companion object {
		val AGENDADA = 0
		val CONFIRMAR = 1
		val PASADA = 2
		val CANCELADA = 3

		@JvmStatic
		fun newInstance( fechasDeCitas: List<String>, citas: HashMap<String, MutableList<Cita>>, tipoCita: Int ) =
				TabCitaACP().apply {
					arguments = Bundle().apply {
						putStringArray( FECHA, fechasDeCitas.toTypedArray() )
						putSerializable( CITAS, citas )
						putInt( TCITA, tipoCita )
					}
				}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			fechas = it.getStringArray( FECHA )?.asList() ?: listOf()
			citas = it.getSerializable( CITAS ) as HashMap<String, MutableList<Cita>>? ?: hashMapOf()
			tipoCita = it.getInt( TCITA )
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		val view = inflater.inflate(R.layout.fragment_tab_cita_acp, container, false)

		activity?.applicationContext?.let { context ->
			citaExpandableListView = CitaExpandableListAdapter( activity!!, fechas, citas, tipoCita )
			view.expandable_list_view.setAdapter( citaExpandableListView )
		}

		return view
	}

	fun addCita( fecha: String, cita: Cita ) {
		val containFecha = fechas.contains( fecha )

		if( containFecha ) {
			citas.get( fecha )?.add( cita )

		} else {
			val fechasAux = fechas.toMutableList()
			fechasAux.add( fecha )

			fechas = fechasAux.toList()
			val mCitas = mutableListOf( cita )
			citas.set( fecha, mCitas )
		}

		this.arguments = Bundle().apply {
			putStringArray( FECHA, fechas.toTypedArray() )
			putSerializable( CITAS, citas )
		}
		citaExpandableListView.updateData( fechas, citas )
	}

	interface OnCitaChangeState {
		fun onCitaAgendadaOCancelada( fecha: String, cita: Cita, esAgendada: Boolean )
	}
}
