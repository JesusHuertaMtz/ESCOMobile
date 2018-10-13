package com.example.master.escomobile_alpha.controlador.menu

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.util.SPLogin
import com.example.master.escomobile_alpha.vista.MainActivity
import com.example.master.escomobile_alpha.vista.fragment.BolsaTrabajoFragment
import com.example.master.escomobile_alpha.vista.fragment.MapFragment
import com.example.master.escomobile_alpha.vista.fragment.ProfesorFragment
import kotlinx.android.synthetic.main.activity_manager.*
import kotlinx.android.synthetic.main.app_bar_menu_controller.*

open class MenuController : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //Variable para poder acceder a los elementos del menú
    lateinit var menu : Menu
    //Almacena el identificador del elemento del menú seleccionado
    //Se actualiza cada vez que se selecciona un elemento.
    var itemID = 0
    var fragmentMap : Fragment? = null
    var fragment: Fragment? = null

    enum class MenuOptions {
        NONE, ALL, FAB, OPTIONS
    }

    override fun onBackPressed() {
        if( drawer_layout.isDrawerOpen( GravityCompat.START ) ) {
            drawer_layout.closeDrawer( GravityCompat.START )
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.manager, menu)
        this.menu = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        itemID = item.itemId
        val transaction = supportFragmentManager.beginTransaction()

        when( item.itemId ) {
            R.id.nav_map -> {
                fragment = MapFragment.newInstance()
            }
            /*R.id.nav_evento -> {
                fragment = EventoFragment.newInstance( "", "" )
            }*/
            R.id.nav_profesor -> {
                fragment = ProfesorFragment.newInstance()
            }
            R.id.nav_citas -> {

            }
            R.id.nav_bolsa -> {
                fragment = BolsaTrabajoFragment.newInstance()
            }
	        R.id.nav_cerrar_sesion -> {
		        Toast.makeText( this, "Cerrar sesión", Toast.LENGTH_SHORT ).show()
		        SPLogin.deleteUserSharedPreference( this )
		        val intent = Intent( this, MainActivity::class.java )
		        startActivity( intent )
		        finish()
	        }
        }

        transaction.replace( R.id.content_fragment, fragment ).commit()
        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    /**
     * Esta función añade elementos a la vista dependiendo del valor que se le pase
     * como parámetro. Los elementos que se pueden mostrar son mostrar el botón
     * flotante y las opciones de tres puntos en la parte superior derecha.
     * @param flag Opción del enum MenuOptions.
     * */
    fun setMenu( flag: MenuOptions ) {
        addDrawerMenu()
        //android:background="?attr/colorPrimary"

        when( flag ) {
            MenuOptions.NONE -> {
                //No añade nada. Solo el menu drawer.
            }

            MenuOptions.ALL -> {
                enabledMenuOptions()
                enabledFloatActionButton()
            }

            MenuOptions.FAB -> {
                enabledFloatActionButton()
            }

            MenuOptions.OPTIONS -> {
                enabledMenuOptions()
            }
        }
    }

    /**
     * Oculta el menú
     * */
    fun hideMenu() {
        toolbar.visibility = View.GONE
        println("MENU HIDE")
    }

    /**
     * Muestra el menú
     * */
    fun showMenu() {
        toolbar.visibility = View.VISIBLE
    }

    /**
     * Muestra el menú desplegable.
     * */
    private fun addDrawerMenu() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close )
        drawer_layout.addDrawerListener( toggle )
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener( this )
    }

    /**
     * Muestra los tres puntos de opciones en la parte superior derecha.
     * */
    private fun enabledMenuOptions() {
        setSupportActionBar(toolbar)
    }

    /**
     * Habilita la funcion del boton flotante
     * Añadir el evento en cada fragment o actovity
     * */
    private fun enabledFloatActionButton() {
        fab.visibility = View.VISIBLE

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}
