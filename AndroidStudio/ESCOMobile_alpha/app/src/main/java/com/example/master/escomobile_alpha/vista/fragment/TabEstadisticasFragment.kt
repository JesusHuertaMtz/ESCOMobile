package com.example.master.escomobile_alpha.vista.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.master.escomobile_alpha.R

/**
 * A simple [Fragment] subclass.
 * Use the [TabEstadisticasFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TabEstadisticasFragment : Fragment() {

	companion object {
		@JvmStatic
		fun newInstance() = TabEstadisticasFragment()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_tab_estadisticas, container, false)
	}
}
