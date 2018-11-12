package com.example.master.escomobile_alpha.vista

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.modelo.entidad.Cita
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import com.example.master.escomobile_alpha.vista.fragment.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_profesor.*

class ProfesorActivity : AppCompatActivity(), TabCitaACP.OnCitaChangeState {
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
				val mapFragment = MapFragment.newInstance()
				//val latLng = LatLng( profesor.cubiculo?.coordenadas?.get(0) ?: 0.0, profesor.cubiculo?.coordenadas?.get(1) ?: 0.0 )
				//mapFragment.addMarker( latLng, profesor.nombre )
				//mapFragment.changeFloor( profesor.cubiculo?.piso ?: 0 )
				transaction.replace( content_fragment.id, mapFragment ).commit()
			}

			CITA -> {
				val agendarCitaFragment = AgendarCitaFragment.newInstance( profesor.nombre )
				transaction.replace( content_fragment.id, agendarCitaFragment ).commit()
			}

			CALIFICAR -> {
				val opinionFragment = OpinionFragment.newInstance( profesor.nombre )
				transaction.replace( content_fragment.id, opinionFragment ).commit()
			}
		}
	}

	override fun onCitaAgendadaOCancelada( fecha: String, cita: Cita, esAgendada: Boolean ) {
		val citaFragment = supportFragmentManager.findFragmentById( R.id.content_fragment ) as? CitaFragment
		println("FURULO -->> ${ citaFragment == null } -- $fecha")

		if( citaFragment == null ) {
			val fragment = CitaFragment.newInstance()
			val transaction = supportFragmentManager.beginTransaction()
			transaction.replace( R.id.content_fragment, fragment ).commit()

		} else {
			citaFragment.setCita( fecha, cita, esAgendada )
		}
	}
}




















