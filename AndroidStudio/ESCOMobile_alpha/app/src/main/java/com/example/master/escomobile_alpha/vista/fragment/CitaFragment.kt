package com.example.master.escomobile_alpha.vista.fragment


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone

import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentCitaBinding
import com.example.master.escomobile_alpha.modelo.entidad.Cita
import com.example.master.escomobile_alpha.util.SPLogin
import com.example.master.escomobile_alpha.viewholder.tabadapter.TabAdapter
import com.example.master.escomobile_alpha.viewmodel.CitaViewModel
import com.example.master.escomobile_alpha.vista.ManagerActivity

/**
 * A simple [Fragment] subclass.
 * Use the [CitaFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CitaFragment : BaseFragment() {
	private lateinit var citaBinding : FragmentCitaBinding
	private lateinit var citaViewModel: CitaViewModel
	private lateinit var tabAdapter: TabAdapter

	companion object {
		@JvmStatic
		fun newInstance() = CitaFragment()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		citaBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_cita, container, false )
		citaViewModel = ViewModelProviders.of( this as Fragment ).get( CitaViewModel::class.java )

		showMenu()
		setOnclickAddNewCita()
		fillTabLayout()

		return citaBinding.root
	}

	private fun setOnclickAddNewCita() {
		citaBinding.fab.setOnClickListener {
			val managerActivity = activity as ManagerActivity
			val transaction = managerActivity.supportFragmentManager?.beginTransaction()
			transaction?.add( R.id.content_fragment, AgendarCitaFragment.newInstance("" ) )?.addToBackStack( null )?.commit()
		}
	}

	private fun fillTabLayout() {
		activity?.supportFragmentManager?.let { fragmentManager ->
			tabAdapter = TabAdapter( fragmentManager )
			val user = SPLogin.loadUserFromSharedPreferences( activity!! )
			var requestSuccess = 0
			showProgressBar()

			citaViewModel.getCitasAgendadas( user ) { fechas, citas ->
				tabAdapter.addFragment( TabCitaACP.newInstance( fechas, citas, TabCitaACP.AGENDADA ), "Agendadas" )
				requestSuccess += 1
				notifyDataSetChange( tabAdapter, requestSuccess )

				citaViewModel.getCitasPendientes( user ) { fechas, citas ->
					tabAdapter.addFragment( TabCitaACP.newInstance( fechas, citas, TabCitaACP.CONFIRMAR ), "Por confirmar" )
					requestSuccess += 1
					notifyDataSetChange( tabAdapter, requestSuccess )

					citaViewModel.getCitasPasadas( user ) { fechas, citas ->
						tabAdapter.addFragment( TabCitaACP.newInstance( fechas, citas, TabCitaACP.PASADA ), "Pasadas" )
						requestSuccess += 1
						notifyDataSetChange( tabAdapter, requestSuccess )

						citaViewModel.getCitasCanceladas( user ) { fechas, citas ->
							tabAdapter.addFragment( TabCitaACP.newInstance( fechas, citas, TabCitaACP.CANCELADA ), "Canceladas" )
							requestSuccess += 1
							notifyDataSetChange( tabAdapter, requestSuccess )
						}
					}
				}
			}

			citaBinding.viewPager.adapter = tabAdapter
			citaBinding.tablayout.setupWithViewPager( citaBinding.viewPager )

			if( user.esProfesor ?: false ) {
				citaBinding.fab.isGone = true
			}
		}
	}

	fun setCita( fecha:String, cita: Cita, esAgendada: Boolean ) {
		val position = if( esAgendada ) 0 else 3
		val tabCitaACP = tabAdapter.getItem( position ) as TabCitaACP
		tabCitaACP.addCita( fecha, cita )
	}

	private fun notifyDataSetChange( tabAdapter: TabAdapter, requestSuccess: Int ) {
		hideProgressBarIfAllCitasIsLoad( requestSuccess )
		tabAdapter.notifyDataSetChanged()
	}

	private fun hideProgressBarIfAllCitasIsLoad( numRequestSuccess : Int ) {
		if( numRequestSuccess == 4 ) {
			hideProgressBar()
		}

		println("cont $numRequestSuccess")
	}
}
