package com.example.master.escomobile_alpha.vista.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentAgendarCitaBinding
import com.example.master.escomobile_alpha.modelo.entidad.Cita
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.util.SPLogin
import com.example.master.escomobile_alpha.util.dialog.DialogUtil
import com.example.master.escomobile_alpha.viewmodel.AgendarCitaViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [AgendarCitaFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AgendarCitaFragment : BaseProfesorFragment() {
	// TODO: Rename and change types of parameters
	private val ARG_PARAM1 = "param1"
	private lateinit var nombreProfesor: String
	private lateinit var agendarCitaBinding : FragmentAgendarCitaBinding
	private lateinit var agendarCitaViewModel : AgendarCitaViewModel
	private lateinit var listener: TabCitaACP.OnCitaChangeState

	companion object {
		@JvmStatic
		fun newInstance( nombreProfesor: String ) = AgendarCitaFragment().apply {
					arguments = Bundle().apply {
						putString(ARG_PARAM1, nombreProfesor)
					}
				}
	}

	override fun onAttach(context: Context?) {
		super.onAttach(context)

		if( context is TabCitaACP.OnCitaChangeState) {
			listener = context
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			nombreProfesor = it.getString( ARG_PARAM1 ) ?: ""
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		agendarCitaBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_agendar_cita, container, false )
		agendarCitaViewModel = ViewModelProviders.of( this as Fragment ).get( AgendarCitaViewModel::class.java )
		agendarCitaViewModel.setupSpinner( activity!!, agendarCitaBinding.spinner, nombreProfesor ) {
			if( !it ) {
				println("NOMBRE $nombreProfesor")
				activity?.finish()
			}
		}
		agendarCitaBinding.cita = Cita()

		setBackArrowInToolbar( agendarCitaBinding.root )
		addEventsButtons()
		addObserver()

		return agendarCitaBinding.root
	}

	//TODO: Añadir menu_ayuda_agendar_cita

	private fun addEventsButtons() {
		val timePickerDialog = DialogUtil.createTimePickerDialog( activity!!, agendarCitaBinding )
		val alert = DialogUtil.createTipoCitaDialog( activity!!, agendarCitaBinding )
		val datePickerDialog = DialogUtil.createDatePickerDialog( activity!!, agendarCitaBinding )
		val instructionDialog = DialogUtil.createAlert( activity!! )

		agendarCitaBinding.btnFecha.setOnClickListener {
			datePickerDialog.show()
		}

		agendarCitaBinding.btnHora.setOnClickListener {
			timePickerDialog.show()
		}

		agendarCitaBinding.btnTipo.setOnClickListener {
			alert.show()
		}

		agendarCitaBinding.btnHelp.setOnClickListener {
			instructionDialog.show()
		}

		agendarCitaBinding.btnAgendar.setOnClickListener {
			val cita = agendarCitaBinding.cita ?: Cita()
			val profesor = agendarCitaBinding.spinner.selectedItem as? Usuario

			if( profesor == null ) {
				Toast.makeText( activity, "La cita solo se puede agendar con profesores registrados en la app.", Toast.LENGTH_SHORT ).show()
			}

			profesor?.boleta?.let { idProfesor ->
				cita.tipo = agendarCitaBinding.btnTipo.tag?.toString() ?: ""//agendarCitaBinding.btnTipo.text.toString()
				cita.idProfesor = idProfesor
				cita.idAlumno = SPLogin.loadUserFromSharedPreferences( activity!! ).boleta ?: ""
				cita.fecha = agendarCitaBinding.btnFecha.text.toString()
				cita.hora = agendarCitaBinding.btnHora.text.toString()
				cita.motivo = agendarCitaBinding.txtMotivo.text.toString()

				agendarCitaViewModel.updateCita( cita )
			}
		}
	}

	private fun addObserver() {
		agendarCitaViewModel.getCita().observe( this, Observer { cita ->
			//TODO VALIDAR CITA!!!!
			println("CAMBIO CITA ${ cita?.tipo }")
			cita?.let {
				if( cita.tipo.equals("CAMPO_VACIO") ) {
					Toast.makeText( activity, "Todos los campos son obligatorios", Toast.LENGTH_SHORT ).show()

				} else {
					agendarCitaViewModel.agendarCita( it ) { statusCita ->
						if( statusCita ) {
							Toast.makeText( activity, "La cita ha sido solicitada al profesor. Espera la confirmacióndel mismo", Toast.LENGTH_LONG ).show()
							listener.onCitaAgendadaOCancelada( cita.fecha, cita, true )

						} else {
							val msg = "Hubo un error al tratar de solicitar la cita. Puede ser que la hora que solicitas ya este agendada."
							Toast.makeText( activity, msg, Toast.LENGTH_LONG ).show()
						}

						activity?.onBackPressed()
					}
				}
			}
		})
	}
}
