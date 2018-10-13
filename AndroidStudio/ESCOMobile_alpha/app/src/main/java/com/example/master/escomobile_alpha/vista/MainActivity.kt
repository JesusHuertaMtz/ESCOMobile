package com.example.master.escomobile_alpha.vista

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.util.SPLogin
import com.example.master.escomobile_alpha.vista.fragment.IniciarSesionFragment
import com.example.master.escomobile_alpha.vista.fragment.RegistroFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showMapa()
        goToRegistro()
        btnIniciarsesion()

        supportFragmentManager.addOnBackStackChangedListener {
            //El fragment se ha removido, volvemos a mostrar los botones
            findViewById<ImageView>( R.id.img_escom ).visibility = View.VISIBLE
            findViewById<Button>( R.id.btn_registarse ).visibility = View.VISIBLE
            findViewById<Button>( R.id.btn_iniciar_sesion ).visibility = View.VISIBLE
            findViewById<Button>( R.id.btn_coninuar_sin_registro ).visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()

	    val user = SPLogin.loadUserFromSharedPreferences( this )
	    println("USER ${ user.boleta } PASS ${ user.pass }")
	    if( user.boleta != null && user.pass != null ) {
		    //Usuario loggeado ir a pantalla de inicio
		    val intent = Intent( this, ManagerActivity::class.java )
		    println("ON RESUME GO TO LOGIN")
		    startActivity( intent )
		    finish()

	    } else {
		    //Mostrar login
	    }
    }

    private fun goToRegistro() {
        val btnRegistrarse = findViewById<Button>( R.id.btn_registarse )
        val framgentRegistro = RegistroFragment.newInstance()

        btnRegistrarse.setOnClickListener {
            println("GO TO REGISTRO")
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace( R.id.fragment_container, framgentRegistro ).addToBackStack( null ).commit()
            hideElements()
        }
    }

    private fun btnIniciarsesion() {
        val btnIniciarSesion = findViewById<Button>( R.id.btn_iniciar_sesion )
        // Create a new Fragment to be placed in the activity layout
        val fragmenIniciarSesion = IniciarSesionFragment.newInstance()

        btnIniciarSesion.setOnClickListener {
            println("GO TO LOGIN")
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace( R.id.fragment_container, fragmenIniciarSesion ).addToBackStack(null ) .commit()
            hideElements()
        }
    }

    private fun hideElements() {
        //Escondemos los botones para que no se muestren sobre el fragment
        findViewById<ImageView>( R.id.img_escom ).visibility = View.GONE
        findViewById<Button>( R.id.btn_registarse ).visibility = View.GONE
        findViewById<Button>( R.id.btn_iniciar_sesion ).visibility = View.GONE
        findViewById<Button>( R.id.btn_coninuar_sin_registro ).visibility = View.GONE
    }

    private fun showMapa() {
        val btnContinuarSinRegistro = findViewById<Button>( R.id.btn_coninuar_sin_registro )

        btnContinuarSinRegistro.setOnClickListener {
            val intent = Intent( this, MapsActivity::class.java )

            startActivity( intent )
        }
    }
}
