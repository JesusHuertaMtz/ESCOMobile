package com.example.master.escomobile_alpha.vista.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentEstadisticasProfesorBinding
import com.example.master.escomobile_alpha.viewholder.tabadapter.TabAdapter
import kotlinx.android.synthetic.main.fragment_estadisticas_profesor.*

/**
 * A simple [Fragment] subclass.
 * Use the [EstadisticasProfesorFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EstadisticasProfesorFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var nombreProfesor: String? = null
	private val ARG_PARAM1 = "param1"
	private lateinit var estadisticasProfesorBinding: FragmentEstadisticasProfesorBinding

	companion object {
		@JvmStatic
		fun newInstance( param1: String ) =
				EstadisticasProfesorFragment().apply {
					arguments = Bundle().apply {
						putString(ARG_PARAM1, param1)
					}
				}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			nombreProfesor = it.getString(ARG_PARAM1)
		}
	}
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		//bolsaTrabajoRVB = DataBindingUtil.inflate( inflater, R.layout.fragment_bolsa_trabajo, container, false )
		estadisticasProfesorBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_estadisticas_profesor, container, false )
		setBackArrowInToolbar()
		fillTabLayout()

		return estadisticasProfesorBinding.root
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

	private fun setBackArrowInToolbar() {
		estadisticasProfesorBinding.toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white )
		estadisticasProfesorBinding.toolbar.setNavigationOnClickListener {
			activity!!.onBackPressed()
		}
	}

	private fun fillTabLayout() {
		activity?.supportFragmentManager?.let { fragmentManager ->
			val tabAdapter = TabAdapter( fragmentManager )
			tabAdapter.addFragment( TabEstadisticasFragment.newInstance(), "Estadísticas")
			tabAdapter.addFragment( TabComentarioFragment.newInstance( nombreProfesor ?: "" ), "Comentarios")

			estadisticasProfesorBinding.viewPager.adapter = tabAdapter
			estadisticasProfesorBinding.tablayout.setupWithViewPager( view_pager )
		}
	}
}








