package com.example.master.escomobile_alpha.viewholder.expandable

import android.app.Activity
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.modelo.entidad.Cita
import com.example.master.escomobile_alpha.modelo.logica_negocio.BSCita
import com.example.master.escomobile_alpha.util.SPLogin
import com.example.master.escomobile_alpha.vista.ManagerActivity
import com.example.master.escomobile_alpha.vista.fragment.TabCitaACP
import kotlinx.android.synthetic.main.cita_list_item.view.*
import kotlinx.android.synthetic.main.horario_list_group.view.*
import java.util.*

class CitaExpandableListAdapter( val activity: Activity,
                                 private var fechasDeCitasAgendadas : List<String>,
                                 private var citasAgendadasEnUnDia : HashMap<String, MutableList<Cita>>,
                                 private val tipoCita: Int ): BaseExpandableListAdapter() {
	private val bsCita = BSCita()
	private val esProfesor: Boolean
	private val colors = listOf( R.color.redlight, R.color.purplelight, R.color.bluelight, R.color.blueoceanlight, R.color.greenlight, R.color.yellowlight, R.color.orangelight )

	init {
		val user = SPLogin.loadUserFromSharedPreferences( activity )
		esProfesor = user.esProfesor ?: false
	}

	override fun getChild(groupListPosition: Int, expandedListPosition: Int): Any {
		val fecha = fechasDeCitasAgendadas[groupListPosition]
		val cita = citasAgendadasEnUnDia[fecha]?.get( expandedListPosition )

		return cita ?: ""
	}

	override fun getChildId(groupListPosition: Int, expandedListPosition: Int): Long {
		return expandedListPosition.toLong()
	}

	override fun getChildView(groupListPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
		val cita = getChild( groupListPosition, expandedListPosition ) as Cita
		val layoutInflater = LayoutInflater.from(activity.applicationContext)
		val view = layoutInflater.inflate( R.layout.cita_list_item, null )

		view.txt_tipo_cita.text = cita.tipo
		view.txt_hora.text = cita.hora
		view.txt_nombre.text = cita.idProfesor
		view.txt_motivo.text = cita.motivo
		updateViewByTipoCita( view, cita )

		return view
	}

	private fun updateViewByTipoCita( view: View, cita: Cita ) {
		if( tipoCita == TabCitaACP.AGENDADA ) {
			view.btn_check.visibility = View.GONE
			view.btn_uncheck.setOnClickListener {
				cancelarCita( cita )
			}

		} else if( tipoCita == TabCitaACP.CONFIRMAR ) {
			if( esProfesor ) {
				view.btn_check.setOnClickListener {
					agendarCita( cita )
				}

			} else {
				view.btn_check.visibility = View.GONE
			}

			view.btn_uncheck.setOnClickListener {
				cancelarCita( cita )
			}

		} else {
			view.btn_check.visibility = View.GONE
			view.btn_uncheck.setImageResource( R.drawable.ic_cita_delete )
			view.btn_uncheck.setOnClickListener {
				//eliminarCita( cita )
			}
		}
	}

	private fun cancelarCita( cita: Cita ) {
		bsCita.cancelarCita( activity, cita, esProfesor ) { cita ->
			cita?.let { cita ->
				val ma = activity as ManagerActivity
				ma.onCitaAgendadaOCancelada( cita.fecha, cita, esAgendada = false)
				removeCita( cita )
			}
		}
	}

	private fun agendarCita( cita: Cita ) {
		bsCita.agendarCita( activity, cita ) { resCita ->
			resCita?.let { cita ->
				val ma = activity as ManagerActivity
				ma.onCitaAgendadaOCancelada( cita.fecha, cita, esAgendada = true)
				removeCita( cita )
			}
		}
	}

	private fun removeCita( cita: Cita ) {
		val isRemoveCita = citasAgendadasEnUnDia.get( cita.fecha )?.remove( cita )

		if( isRemoveCita ?: false ) {
			val size = citasAgendadasEnUnDia.get( cita.fecha )?.size ?: 0

			if( size == 0 ) {
				val fechasAux = fechasDeCitasAgendadas.toMutableList()

				citasAgendadasEnUnDia.remove( cita.fecha )
				fechasAux.remove( cita.fecha )
				fechasDeCitasAgendadas = fechasAux.toList()
			}

			notifyDataSetChanged()

		} else {
			println("ERROR NO SE PUDO ELIMINAR LA CITA!!!!!")
		}
	}

	override fun getChildrenCount( groupListPosition: Int ): Int {
		val day = fechasDeCitasAgendadas[groupListPosition]
		return citasAgendadasEnUnDia[day]?.size ?: 0
	}

	//METODOS DE GRUPO
	override fun getGroup(group: Int): Any {
		val fechaConHora = fechasDeCitasAgendadas[group].split(" ")
		val fecha = fechaConHora[0]

		return fecha
	}

	override fun getGroupCount(): Int {
		return fechasDeCitasAgendadas.size
	}

	override fun getGroupId(groupListPosition: Int): Long {
		return groupListPosition.toLong()
	}

	override fun getGroupView(groupListPosition: Int, isExpanded: Boolean, converView: View?, parent: ViewGroup?): View {
		val day = getGroup( groupListPosition ) as String
		val layoutInflater = LayoutInflater.from(activity.applicationContext)
		val view = layoutInflater.inflate( R.layout.horario_list_group, null )

		view.txt_dia_semana.text = day
		val position = colors.size - 1
		view.setBackgroundColor( ContextCompat.getColor( activity, colors[groupListPosition % position] ) )

		return view
	}

	override fun hasStableIds(): Boolean {
		return false
	}

	override fun isChildSelectable( groupListPosition: Int, expandedListPosition: Int ): Boolean {
		return true
	}

	fun updateData( fechas: List<String>, citas: HashMap<String, MutableList<Cita>> ) {
		fechasDeCitasAgendadas = fechas
		citasAgendadasEnUnDia = citas

		notifyDataSetChanged()
	}
}
/*
+--------+---------------------+----------------------+---------------+------------+----------+-------------------+-------------+---------------+-----------------+----------------+-------------+
| idCita | TipoCita_idTipoCita | Profesor_numEmpleado | Alumno_boleta | fecha      | hora     | motivo            | Area_idArea | Area_idPlanta | Area_idEdificio | Area_idEscuela | Area_idTipo |
+--------+---------------------+----------------------+---------------+------------+----------+-------------------+-------------+---------------+-----------------+----------------+-------------+
|     34 |                   1 | 1234567890           |    2014631010 | 2018-10-22 | 12:50:00 | Alguno medio raro |        2103 |             5 |               2 |              1 |        2103 |
+--------+---------------------+----------------------+---------------+------------+----------+-------------------+-------------+---------------+-----------------+----------------+-------------+



*/