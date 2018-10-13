package com.example.master.escomobile_alpha.vista.fragment


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentTabComentarioBinding
import com.example.master.escomobile_alpha.util.CustomLinearLayoutManager
import com.example.master.escomobile_alpha.util.CustomProgressBar
import com.example.master.escomobile_alpha.viewmodel.EstadisticasProfesorViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [TabComentarioFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TabComentarioFragment : Fragment() {
	private lateinit var tabComentarioBinding: FragmentTabComentarioBinding
	private lateinit var estadisticasProfesorViewModel: EstadisticasProfesorViewModel
	private var nombreProfesor: String? = null
	private val NOMBRE = "nombre_profesor"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		arguments?.let {
			nombreProfesor = it.getString( NOMBRE )
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		CustomProgressBar.show( inflater.context )
		tabComentarioBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_tab_comentario, container, false )
		estadisticasProfesorViewModel = ViewModelProviders.of( this as Fragment ).get( EstadisticasProfesorViewModel::class.java )
		tabComentarioBinding.recyclerView.layoutManager = CustomLinearLayoutManager( inflater.context ).apply {
			isScrollEnabled = false
		}
		tabComentarioBinding.txtNombreProfesor.text = nombreProfesor
		estadisticasProfesorViewModel.init( inflater.context )
		//estadisticasProfesorViewModel.getComentariosBy( nombreProfesor )
		tabComentarioBinding.recyclerView.adapter = estadisticasProfesorViewModel.estadisticasProfesorAdapter

		CustomProgressBar.getDialog()?.dismiss()

		return tabComentarioBinding.root
	}

	companion object {
		@JvmStatic
		fun newInstance( nombreProfesor: String ) = TabComentarioFragment().apply {
			arguments = Bundle().apply {
				putString( NOMBRE, nombreProfesor )
			}
		}
	}
}
