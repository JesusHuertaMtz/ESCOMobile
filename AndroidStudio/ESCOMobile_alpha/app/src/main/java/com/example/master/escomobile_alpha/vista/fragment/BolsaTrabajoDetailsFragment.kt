package com.example.master.escomobile_alpha.vista.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentBolsaTrabajoDetailsBinding
import com.example.master.escomobile_alpha.modelo.entidad.Empresa
import com.example.master.escomobile_alpha.vista.ManagerActivity
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 * Use the [BolsaTrabajoDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BolsaTrabajoDetailsFragment : Fragment() {

    companion object {
        private val EMPRESA = "empresa"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param empresa Empresa seleccionada para ver detalle de oferta laboral.
         * @return A new instance of fragment BolsaTrabajoDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance( empresa: Empresa) : BolsaTrabajoDetailsFragment {
            val args = Bundle()
            val fragment = BolsaTrabajoDetailsFragment()

            args.putSerializable( EMPRESA, empresa as Serializable )
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragmentBolsaTrabajoDetailsFragmentBinding = FragmentBolsaTrabajoDetailsBinding.inflate( inflater, container, false )
        val empresa = arguments?.getSerializable( EMPRESA ) as Empresa
        val logoBitmap = empresa.getImageBitmap()

        setBackArrowInToolbar( fragmentBolsaTrabajoDetailsFragmentBinding )
        empresa.oferta?.sueldo = "Sueldo: ${empresa.oferta?.sueldo}"
        fragmentBolsaTrabajoDetailsFragmentBinding.empresa = empresa
        logoBitmap?.let { logo ->
            fragmentBolsaTrabajoDetailsFragmentBinding.imageView2.setImageBitmap( logo )
        }

        return fragmentBolsaTrabajoDetailsFragmentBinding.root
    }

    private fun setBackArrowInToolbar( fbtdfb: FragmentBolsaTrabajoDetailsBinding ) {
        val managerActivity = activity as? ManagerActivity

        managerActivity?.hideMenus()
        fbtdfb.toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white )
        fbtdfb.toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
