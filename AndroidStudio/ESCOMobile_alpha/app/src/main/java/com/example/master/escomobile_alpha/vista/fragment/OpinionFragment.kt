package com.example.master.escomobile_alpha.vista.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master.escomobile_alpha.R
import kotlinx.android.synthetic.main.fragment_opinion.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [OpinionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OpinionFragment : Fragment() {
	private var nombreProfesor: String? = null
	private val ARG_PROFESOR = "profesor"

	companion object {
		@JvmStatic
		fun newInstance( nombreProfesor: String ) =
				OpinionFragment().apply {
					arguments = Bundle().apply {
						putString( ARG_PROFESOR, nombreProfesor )
					}
				}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			nombreProfesor = it.getString( ARG_PROFESOR )
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		val view = inflater.inflate(R.layout.fragment_opinion, container, false)
		setBackArrowInToolbar( view )

		return view
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
}
