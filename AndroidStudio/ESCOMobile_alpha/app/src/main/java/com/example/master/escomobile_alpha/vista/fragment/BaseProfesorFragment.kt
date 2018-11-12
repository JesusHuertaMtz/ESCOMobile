package com.example.master.escomobile_alpha.vista.fragment

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.vista.ManagerActivity
import kotlinx.android.synthetic.main.fragment_opinion.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
open class BaseProfesorFragment : Fragment() {

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

	fun setBackArrowInToolbar( view: View? ) {
		view?.toolbar?.setNavigationIcon( R.drawable.ic_arrow_back_white )
		view?.toolbar?.setNavigationOnClickListener {
			activity!!.onBackPressed()
		}
	}
}
