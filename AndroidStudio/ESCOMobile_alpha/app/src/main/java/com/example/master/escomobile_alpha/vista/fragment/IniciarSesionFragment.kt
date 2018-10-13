package com.example.master.escomobile_alpha.vista.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentIniciarSesionBinding
import com.example.master.escomobile_alpha.modelo.entidad.Usuario
import com.example.master.escomobile_alpha.util.CustomProgressBar
import com.example.master.escomobile_alpha.util.ErrorMessage
import com.example.master.escomobile_alpha.util.SPLogin
import com.example.master.escomobile_alpha.viewmodel.IniciarSesionViewModel
import com.example.master.escomobile_alpha.vista.ManagerActivity

/**
 * A simple [Fragment] subclass.
 * Use the [IniciarSesionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class IniciarSesionFragment : BaseFragment() {
    //TODO: Variables de instancia
    private lateinit var sesionViewModel: IniciarSesionViewModel
    private lateinit var fisb : FragmentIniciarSesionBinding

    companion object {
        @JvmStatic
        val USER = "com.USUARIO"
        fun newInstance() = IniciarSesionFragment()
    }

    //TODO: Métodos del ciclo de vida del fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //DataBinding
        fisb = DataBindingUtil.inflate( inflater, R.layout.fragment_iniciar_sesion, container, false )
        sesionViewModel = ViewModelProviders.of( this as Fragment ).get( IniciarSesionViewModel::class.java )
        sesionViewModel.init()
        val view = fisb.root

        //Añade el icono y el evento del navigation toolbar
        fisb.toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white )
        fisb.toolbar.setNavigationOnClickListener {
            val viewFocus = activity!!.currentFocus
            if( viewFocus != null ) {
                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow( viewFocus.getWindowToken(), 0 )
            }

            activity!!.onBackPressed()
        }

        //Añade el evento al botón iniciar sesión
        btnSetListener()
        setObserver()

        return view
    }

    override fun onStop() {
        super.onStop()

        fisb.txtBoleta.text.clear()
        fisb.txtPass.text.clear()
        hideProgressBar()
    }

    //TODO: MÉTODOS PRIVADOS
    private fun setObserver() {
        sesionViewModel.getUsuario().observe( this, Observer { user ->
            if( !showErrorMessageIfNeeded( user?.pass, getString( R.string.txt_hint_pass ) ) ) {
                sesionViewModel.iniciarSesion( activity?.applicationContext!!) { json, user ->
                    if( user != null ) {
                        //Se realiza la autenticación correctamente.
                        val intent = Intent( context, ManagerActivity::class.java )
                        startActivity( intent )
	                    SPLogin.saveUserInSharedPreference( activity!!, user )
						activity?.finish()

                    } else {
                        //Hubo problema con los datos.
                        Toast.makeText( context, json.optString("description" ), Toast.LENGTH_SHORT ).show()
                        hideProgressBar()
                    }
                }
            }
            //showErrorMessageIfNeeded( user?.pass, getString( R.string.txt_hint_pass ) )
        })
    }

    private fun btnSetListener() {
        fisb.btnIngresar.setOnClickListener {
            val usuario = Usuario()

            showProgressBar()
            usuario.boleta = fisb.txtBoleta.text.toString()
            usuario.pass = fisb.txtPass.text.toString()

            sesionViewModel.updateUserInfo( usuario )
        }

        fisb.btnRegistrate.setOnClickListener {
            val fragmentRegistro = RegistroFragment.newInstance()

            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace( activity!!.findViewById<FrameLayout>( R.id.fragment_container ).id, fragmentRegistro ).addToBackStack(null).commit()
        }
    }
}
