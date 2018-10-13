package com.example.master.escomobile_alpha.vista.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import com.example.master.escomobile_alpha.viewholder.expandable.HorarioExpandableListAdapter
import kotlinx.android.synthetic.main.fragment_horario_profesor.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HorarioProfesorFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HorarioProfesorFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HorarioProfesorFragment : Fragment() {
	private val ARG_PARAM1 = "profesor"
	private var profesor: Profesor? = null
	private var listener: OnFragmentInteractionListener? = null

	companion object {
		@JvmStatic
		fun newInstance( profesor: Profesor ) =
				HorarioProfesorFragment().apply {
					arguments = Bundle().apply {
						putParcelable( ARG_PARAM1, profesor )
					}
				}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			profesor = it.getParcelable( ARG_PARAM1 )
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		val view = inflater.inflate( R.layout.fragment_horario_profesor, container, false )
		setBackArrowInToolbar( view )

		activity?.applicationContext?.let { context ->
			val horarioExpandableListAdapter = HorarioExpandableListAdapter( context, profesor!! )
			view.expandable_list_view.setAdapter( horarioExpandableListAdapter )
			view.txt_nombre_prof.text = profesor?.nombre
		}

		return view
	}

	// TODO: Rename method, update argument and hook method into UI event
	fun onButtonPressed(uri: Uri) {
		listener?.onFragmentInteraction(uri)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		/* DESCOMENTAR SI SE NECESITA COMUNICAR CON PROFESORACTIVITY
		if(context is OnFragmentInteractionListener) {
			listener = context
		} else {
			throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
		} */
	}

	override fun onDetach() {
		super.onDetach()
		listener = null
	}

	override fun onResume() {
		super.onResume()

		val appActivity = activity as AppCompatActivity
		appActivity.supportActionBar?.hide()
	}

	override fun onStop() {
		super.onStop()

		val appActivity = activity as AppCompatActivity
		appActivity.supportActionBar?.show()
	}

	private fun setBackArrowInToolbar( view: View? ) {
		view?.toolbar?.setNavigationIcon( R.drawable.ic_arrow_back_white )
		view?.toolbar?.setNavigationOnClickListener {
			activity!!.onBackPressed()
		}
	}

	interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		fun onFragmentInteraction(uri: Uri)
	}
}
