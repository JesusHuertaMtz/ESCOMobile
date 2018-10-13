package com.example.master.escomobile_alpha.vista

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import com.example.master.escomobile_alpha.vista.fragment.EstadisticasProfesorFragment
import com.example.master.escomobile_alpha.vista.fragment.HorarioProfesorFragment
import com.example.master.escomobile_alpha.vista.fragment.OpinionFragment
import kotlinx.android.synthetic.main.activity_profesor.*

class ProfesorActivity : AppCompatActivity() {

	companion object {
		val HORARIO = "com.example.master.escomobile_alpha.HORARIO"
		val ESTADISTCAS = "com.example.master.escomobile_alpha.ESTADISTICAS"
		val UBICAR_MAPA = "com.example.master.escomobile_alpha.UBICAR_MAPA"
		val CITA = "com.example.master.escomobile_alpha.CITA"
		val CALIFICAR = "com.example.master.escomobile_alpha.CALIFICAR"
		val TIPO_CONSULTA = "com.example.master.escomobile_alpha.TIPO_CONSULTA"
		val INFO_PROFESOR = "com.example.master.escomobile_alpha.PROFESOR_DATA"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_profesor)

		showFragmentByTipoConsulta()
	}

	private fun showFragmentByTipoConsulta() {
		val transaction = supportFragmentManager.beginTransaction()
		val profesor: Profesor = intent.getParcelableExtra( ProfesorActivity.INFO_PROFESOR )

		when( intent.getStringExtra( TIPO_CONSULTA ) ) {
			HORARIO -> {
				val horarioProfesorFragment = HorarioProfesorFragment.newInstance( profesor )
				transaction.replace( content_fragment.id, horarioProfesorFragment ).commit()
			}

			ESTADISTCAS -> {
				val estadisticasProfesorFragment = EstadisticasProfesorFragment.newInstance( profesor.nombre )
				transaction.replace( content_fragment.id, estadisticasProfesorFragment ).commit()
			}

			UBICAR_MAPA -> {

			}

			CITA -> {

			}

			CALIFICAR -> {
				val opinionFragment = OpinionFragment.newInstance( profesor.nombre )
				transaction.replace( content_fragment.id, opinionFragment ).commit()
			}
		}
	}
}




















