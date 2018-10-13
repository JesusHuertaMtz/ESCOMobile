package com.example.master.escomobile_alpha.vista.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentRegistroBinding
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.util.ErrorMessage
import com.example.master.escomobile_alpha.viewmodel.RegistroViewModel
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import com.example.master.escomobile_alpha.util.CustomProgressBar
import com.example.master.escomobile_alpha.vista.ManagerActivity

/**
 * A simple [Fragment] subclass.
 * Use the [RegistroFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RegistroFragment : BaseFragment() {
    // TODO: Variables de instancia
    private lateinit var frb : FragmentRegistroBinding
    private lateinit var registroViewModel: RegistroViewModel

    companion object {
        @JvmStatic
        fun newInstance() = RegistroFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        frb = DataBindingUtil.inflate(inflater, R.layout.fragment_registro, container, false)
        registroViewModel = ViewModelProviders.of(this).get(RegistroViewModel::class.java)
        registroViewModel.init()

        //Añade el icono y el evento del navigation toolbar
        frb.toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white )
        frb.toolbar.setNavigationOnClickListener {
            val view = activity!!.currentFocus
            if( view != null ) {
                val imm = activity!!.getSystemService( INPUT_METHOD_SERVICE ) as InputMethodManager
                imm.hideSoftInputFromWindow( view.getWindowToken(), 0 )
            }

            activity!!.onBackPressed()
        }

        btnSetListener()
        observer()

        return frb.root
    }

    override fun onStop() {
        super.onStop()

        frb.txtBoletaNumEmpleado.text.clear()
        frb.txtNombre.text.clear()
        frb.txtPrimerAp.text.clear()
        frb.txtSegundoAp.text.clear()
        frb.txtEmail.text.clear()
        frb.txtPass.text.clear()
        frb.txtConfirmPass.text.clear()
        frb.checkProfesor.isChecked = false
        hideProgressBar()
    }

    private fun btnSetListener() {
        frb.btnRegistrarse.setOnClickListener {
            showProgressBar()

            val usuario = Usuario( frb.txtBoletaNumEmpleado.text.toString(),
                    frb.txtNombre.text.toString(), frb.txtPrimerAp.text.toString(),
                    frb.txtSegundoAp.text.toString(), frb.txtEmail.text.toString(),
                    frb.txtPass.text.toString(), frb.txtConfirmPass.text.toString(),
                    frb.checkProfesor.isChecked )

            if( registroViewModel.updateUser( usuario ) ) {
                //Los datos ingresados del usuario tienen el formato correcto
                registroViewModel.registrarUsuario { jsonResponse ->
                    if( jsonResponse?.optInt("code") == 200 ) {
                        val intent = Intent( context, ManagerActivity::class.java )
                        startActivity( intent )
                        activity?.finish()

                    } else {
                        Toast.makeText( context, jsonResponse?.optString("description"), Toast.LENGTH_SHORT ).show()
                        hideProgressBar()
                    }
                }
            }
        }

        frb.btnIniciarSesion.setOnClickListener {
            val fragmentSesion = IniciarSesionFragment.newInstance()

            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace( activity!!.findViewById<FrameLayout>( R.id.fragment_container ).id, fragmentSesion ).addToBackStack(null).commit()
        }
    }

    /**
     * Observa algun cambio del usuario
     * */
    private fun observer() {
        registroViewModel.getUsuario().observe( this, Observer { user ->
            /**
             * En la clase registroModel esta definida una instancia de BSRegistro
             * la que sirve para válidar los datos del registro.
             * */
            showErrorMessageIfNeeded( user?.nombre, getString( R.string.txt_hint_nombre ) )
            showErrorMessageIfNeeded( user?.primerAp, getString( R.string.txt_hint_primer_ap ) )
            showErrorMessageIfNeeded( user?.segundoAp, getString( R.string.txt_hint_segundo_ap ) )
            showErrorMessageIfNeeded( user?.correo, getString( R.string.txt_hint_mail ) )
            showErrorMessageIfNeeded( user?.pass, getString( R.string.txt_hint_pass ) )
            showErrorMessageIfNeeded( user?.pass_confirm, getString( R.string.txt_hint_pass2 ) )
        })
    }
}
