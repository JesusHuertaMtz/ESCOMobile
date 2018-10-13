package com.example.master.escomobile_alpha.vista.fragment

import android.support.v4.app.Fragment
import android.widget.Toast
import com.example.master.escomobile_alpha.util.CustomProgressBar
import com.example.master.escomobile_alpha.util.ErrorMessage
import com.example.master.escomobile_alpha.vista.ManagerActivity

/**
 * A simple [Fragment] subclass.
 *
 */
open class BaseFragment : Fragment(), ErrorMessage {

    /**
     * Muestra el mensaje de error.
     * */
    fun showErrorMessageIfNeeded( campo: String?, dato: String ) : Boolean {
        val text = getErrorMessage( campo, dato )
        var isNeedShowMessage = false

        if( text != null ) {
            Toast.makeText( activity, text, Toast.LENGTH_SHORT ).show()
            isNeedShowMessage = true
        }

        return isNeedShowMessage
    }

    /**
     * Remueve de la vista el progress bar
     * */
    fun hideProgressBar() {
        context?.let { context ->
            CustomProgressBar.getDialog()?.dismiss()
        }
    }

    /**
     * Muestra en pantalla el progress bar
     * */
    fun showProgressBar() {
        context?.let { context ->
            CustomProgressBar.show( context )
        }
    }

    /**
     * Muesta el menú
     * */
    fun showMenu() {
        val managerActivity = activity as? ManagerActivity
        managerActivity?.showSolidMenu()
    }

    /**
     * Muesta el menú flotante
     * */
    fun showMenuFloating() {
        val managerActivity = activity as? ManagerActivity
        managerActivity?.showMenuFloating()
        managerActivity
    }
}
