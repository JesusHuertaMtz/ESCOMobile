package com.example.master.escomobile_alpha.vista.fragment

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.master.escomobile_alpha.databinding.FragmentOpinionBinding
import com.example.master.escomobile_alpha.modelo.entidad.Comentario
import com.example.master.escomobile_alpha.util.SPLogin
import com.example.master.escomobile_alpha.viewmodel.OpinionViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [OpinionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OpinionFragment : BaseProfesorFragment() {
	private var nombreProfesor: String? = null
	private val ARG_PROFESOR = "profesor"
	private lateinit var opinionViewModel: OpinionViewModel
	private lateinit var fragmentOpinionBinding:FragmentOpinionBinding
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

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		fragmentOpinionBinding = FragmentOpinionBinding.inflate( inflater, container, false )

		opinionViewModel = ViewModelProviders.of( this as Fragment ).get( OpinionViewModel::class.java )
		fragmentOpinionBinding.txtProfesor.text = nombreProfesor
		setBackArrowInToolbar( fragmentOpinionBinding.root )
		addEventsButtons()

		return fragmentOpinionBinding.root
	}

	fun addEventsButtons() {
		fragmentOpinionBinding.btnEnviar.setOnClickListener {
			val user = SPLogin.loadUserFromSharedPreferences( activity!! )

			if( user.boleta != null && user.correo != null ) {
				val comentario = Comentario()
				comentario.boleta = user.boleta ?: ""
				comentario.correo = user.correo ?: ""
				comentario.nombreProfesor = nombreProfesor ?: ""
				comentario.puntuacion = fragmentOpinionBinding.ratingBar.numStars.toString()
				comentario.comentario = fragmentOpinionBinding.txtComentario.text.toString()

				opinionViewModel.sendComments( comentario ) { msg ->
					Toast.makeText( layoutInflater.context, msg, Toast.LENGTH_SHORT ).show()
					if( msg.equals( OpinionViewModel.OPINION_PUBLICADA ) ) {

					}
					removeFragment()
				}

			} else {
				Toast.makeText( layoutInflater.context, "Hubo un error", Toast.LENGTH_SHORT ).show()
			}
		}
	}

	private fun removeFragment() {
		/*val intent = Intent()
		intent.putExtra( "calificacion", fragmentOpinionBinding.ratingBar.numStars )
		activity?.setResult( RESULT_OK, intent )*/
		activity?.finish()
	}
}
