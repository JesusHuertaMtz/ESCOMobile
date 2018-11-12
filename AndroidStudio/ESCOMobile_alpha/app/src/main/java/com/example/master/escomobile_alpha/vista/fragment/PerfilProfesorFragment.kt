package com.example.master.escomobile_alpha.vista.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentPerfilProfesorBinding
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import com.example.master.escomobile_alpha.util.SPLogin
import com.example.master.escomobile_alpha.vista.ManagerActivity
import com.example.master.escomobile_alpha.vista.ProfesorActivity

/**
 * A simple [Fragment] subclass.
 * Use the [PerfilProfesorFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PerfilProfesorFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var profesor : Profesor? = null
	
	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @return A new instance of fragment PerfilProfesorFragment.
		 */
		// TODO: Rename and change types and number of parameters
		private const val ARG_PARAM1 = "param1"
		
		@JvmStatic
		fun newInstance( profesor: Profesor ) =
				PerfilProfesorFragment().apply {
					arguments = Bundle().apply {
						putParcelable( ARG_PARAM1, profesor )
					}
				}
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let { bundle ->
			profesor = bundle.getParcelable( ARG_PARAM1 )
		}
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		//Obtener los datos del profesor
		val perfilProfesorBinding = FragmentPerfilProfesorBinding.inflate( inflater, container, false )
		profesor?.cubiculo?.academia = "Academia ${profesor?.cubiculo?.academia ?: "-"}"
		profesor?.cubiculo?.id = "Cubículo: ${profesor?.cubiculo?.id ?: "-"}"
		profesor?.calificacion = if( profesor?.calificacion == "0" ) "Calificación: -" else "Calificación ${profesor?.calificacion ?: "-"}"
		perfilProfesorBinding.profesor = profesor

		setBackArrowInToolbar( perfilProfesorBinding )
		addEventButtons( perfilProfesorBinding )

		return perfilProfesorBinding.root
	}

	fun addEventButtons( perfilProfesorBinding: FragmentPerfilProfesorBinding ) {
		perfilProfesorBinding.btnHorario.setOnClickListener {
			showActivityProfesor( ProfesorActivity.HORARIO )
		}

		perfilProfesorBinding.btnEstadisticas.setOnClickListener {
			showActivityProfesor( ProfesorActivity.ESTADISTCAS )
		}

		perfilProfesorBinding.btnMapa.setOnClickListener {
			//showActivityProfesor( ProfesorActivity.UBICAR_MAPA )
			val ma = activity as? ManagerActivity
			ma?.setFragmentMap( profesor!! )
		}

		val esProfesor = SPLogin.loadUserFromSharedPreferences( activity!! ).esProfesor ?: false
		if( esProfesor ) {
			perfilProfesorBinding.btnCalificar.visibility = View.GONE
			perfilProfesorBinding.btnCita.visibility = View.GONE
			//Toast.makeText( layoutInflater.context, "Agendar cita y calificar", Toast.LENGTH_SHORT ).show()

		} else {
			perfilProfesorBinding.btnCalificar.setOnClickListener {
				showActivityProfesor( ProfesorActivity.CALIFICAR )
			}

			perfilProfesorBinding.btnCita.setOnClickListener {
				showActivityProfesor( ProfesorActivity.CITA )
			}
		}
	}

	private fun showActivityProfesor( tipoConsulta: String ) {
		val intent = Intent( activity, ProfesorActivity::class.java )
		intent.putExtra( ProfesorActivity.TIPO_CONSULTA, tipoConsulta )
		intent.putExtra( ProfesorActivity.INFO_PROFESOR, profesor )
		startActivity( intent )
	}

	private fun setBackArrowInToolbar( fppb: FragmentPerfilProfesorBinding ) {
		val managerActivity = activity as? ManagerActivity
		
		managerActivity?.hideMenus()
		fppb.toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white )
		fppb.toolbar.setNavigationOnClickListener {
			activity!!.onBackPressed()
		}
	}
}
