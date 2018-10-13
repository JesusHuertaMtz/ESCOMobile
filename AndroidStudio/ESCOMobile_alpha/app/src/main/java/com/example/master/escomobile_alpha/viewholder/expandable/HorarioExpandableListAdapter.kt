package com.example.master.escomobile_alpha.viewholder.expandable

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.modelo.entidad.Asignatura
import com.example.master.escomobile_alpha.modelo.entidad.Profesor
import kotlinx.android.synthetic.main.horario_list_group.view.*
import kotlinx.android.synthetic.main.horario_list_item.view.*

class HorarioExpandableListAdapter( val context: Context, val profesor: Profesor ): BaseExpandableListAdapter() {
	private var daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")
	private var colors = listOf( R.color.orangelight, R.color.yellowlight, R.color.greenlight, R.color.blueoceanlight, R.color.purplelight )
	private var asignaturasImpartidasAlDia = hashMapOf( "Lunes" to mutableListOf<Asignatura>(), "Martes" to mutableListOf(), "Miércoles" to mutableListOf(), "Jueves" to mutableListOf(), "Viernes" to mutableListOf() )

	init {
		makeHashMapOfAsignaturas()
	}

	private fun makeHashMapOfAsignaturas() {
		for( day in daysOfWeek ) {
			for( asignatura in profesor.asignaturas ) {
				val shceduleSubject = asignatura.horario[day] ?: ""

				if( !shceduleSubject.trim().isEmpty() ) {
					asignaturasImpartidasAlDia[day]?.add( asignatura )
				}
			}
		}
	}

	private fun getScheduleWithLabIfHave( asignatura: Asignatura?, day: String ): String? {
		val horario = asignatura?.horario?.get( day )

		return if( horario?.contains("L") == true ) horario.replace("L","" ) + "\nLab: ${asignatura.laboratorio}" else horario
	}

	override fun getChild(groupListPosition: Int, expandedListPosition: Int): Any {
		val day = daysOfWeek[groupListPosition]
		return asignaturasImpartidasAlDia[day]?.get( expandedListPosition )!!
	}

	override fun getChildId(groupListPosition: Int, expandedListPosition: Int): Long {
		return expandedListPosition.toLong()
	}

	override fun getChildView(groupListPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
		val asignatura = getChild( groupListPosition, expandedListPosition ) as Asignatura
		val layoutInflater = LayoutInflater.from(context)
		val view = layoutInflater.inflate( R.layout.horario_list_item, null )
		val day = daysOfWeek.get( groupListPosition )
		val dayOfSubject = asignatura.horario.keys.indexOf( day )
		val scheduleSubject = asignatura.horario[day] ?: ""

		println("DIA $day ---> $dayOfSubject  ===== LIST $groupListPosition GROUP $expandedListPosition")

		if( dayOfSubject == groupListPosition && !scheduleSubject.isEmpty() ) { //Si el día que la materia se imparte es el mismo que
			//el grupo entonces mostrar el horario
			view.txt_asignatura.text = asignatura.nombre
			view.txt_horario.text = getScheduleWithLabIfHave( asignatura, day )
			view.txt_grupo.text = asignatura.grupo
			view.txt_salon.text = asignatura.salon?.id

		} else {
			view.visibility = View.GONE
		}

		return view
	}

	override fun getChildrenCount( groupListPosition: Int ): Int {
		val day = daysOfWeek[groupListPosition]
		return asignaturasImpartidasAlDia[day]?.size ?: 0
	}

	//METODOS DE GRUPO
	override fun getGroup(group: Int): Any {
		return daysOfWeek[group]
	}

	override fun getGroupCount(): Int {
		return daysOfWeek.size
	}

	override fun getGroupId(groupListPosition: Int): Long {
		return groupListPosition.toLong()
	}

	override fun getGroupView(groupListPosition: Int, isExpanded: Boolean, converView: View?, parent: ViewGroup?): View {
		val dayOfWeek = getGroup( groupListPosition ) as String
		val layoutInflater = LayoutInflater.from(context)
		val view = layoutInflater.inflate( R.layout.horario_list_group, null )

		view.txt_dia_semana.text = dayOfWeek
		view.setBackgroundColor( ContextCompat.getColor( context, colors[groupListPosition] ) )

		return view
	}

	override fun hasStableIds(): Boolean {
		return false
	}

	override fun isChildSelectable( groupListPosition: Int, expandedListPosition: Int ): Boolean {
		return true
	}
}
