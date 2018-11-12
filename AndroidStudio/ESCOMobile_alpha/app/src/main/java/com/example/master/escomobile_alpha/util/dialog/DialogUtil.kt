package com.example.master.escomobile_alpha.util.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import com.example.master.escomobile_alpha.R
import com.example.master.escomobile_alpha.databinding.FragmentAgendarCitaBinding
import com.example.master.escomobile_alpha.util.DateTimeValidator
import java.util.*

class DialogUtil {

	companion object {
		private var chekedItem : Int = 0

		fun createDatePickerDialog( activity: Activity, facb: FragmentAgendarCitaBinding ) : DatePickerDialog {
			val calendar = Calendar.getInstance()
			val year = calendar.get( Calendar.YEAR )
			val month = calendar.get( Calendar.MONTH )
			val dayOfMonth = calendar.get( Calendar.DAY_OF_MONTH )
			val calendarPlusMonth = Calendar.getInstance( TimeZone.getTimeZone("UTC") )
			calendarPlusMonth.add( Calendar.MONTH, 1 )

			val datePickerDialog = DatePickerDialog( activity, R.style.datePicker,
					DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
						val dateSelected = DateTimeValidator.validateDateIsNotWeekend( year, month, dayOfMonth )

						if( dateSelected == DateTimeValidator.DATE_SELECTED_IS_VALID ) {
							facb.btnFecha.text = "$year-${month + 1}-$dayOfMonth"

						} else {
							val text = "No es posible agendar en el día: $year-${month + 1}-$dayOfMonth"
							Toast.makeText( activity, text, Toast.LENGTH_SHORT ).show()
						}

			}, year, month, dayOfMonth ).apply {
				datePicker.minDate = System.currentTimeMillis()
				datePicker.maxDate = calendarPlusMonth.timeInMillis
				setTitle("Fecha de la cita")
			}


			return datePickerDialog
		}

		fun createTipoCitaDialog( activity: Activity, facb: FragmentAgendarCitaBinding ): AlertDialog {
			val tiposDeCita = activity.resources.getStringArray( R.array.tipoCita )

			val alert = AlertDialog.Builder( activity, R.style.datePicker ).apply {
				setTitle("Tipo de cita")
				setSingleChoiceItems( tiposDeCita, chekedItem ) { dialog, which ->
					chekedItem = which
				}
				setPositiveButton("Aceptar" ) { dialog, id ->
					facb.btnTipo.text = tiposDeCita[chekedItem]
					facb.btnTipo.tag = chekedItem + 1
					dialog.dismiss()
				}
				setNegativeButton( "Cancelar" ) { dialog, id ->
					facb.btnTipo.text = "Tipo de cita"
					dialog.dismiss()
				}
			}.create()

			return alert
		}

		fun createTimePickerDialog( activity: Activity, facb: FragmentAgendarCitaBinding ): TimePickerDialog {
			val calendar = Calendar.getInstance()
			val hourOfDay = calendar.get( Calendar.HOUR_OF_DAY )
			val minute = calendar.get( Calendar.MINUTE )
			val is24HourView = true

			val time = TimePickerDialog( activity, R.style.datePicker, TimePickerDialog.OnTimeSetListener {
				view, hourOfDay, minute ->

				val hourSelected = DateTimeValidator.validateTime( hourOfDay, minute )
				if( hourSelected == DateTimeValidator.HOUR_SELECTED_IS_VALID ) {
					facb.btnHora.text = "$hourOfDay:$minute"
				} else {
					val text = "No es posible agendar en la hora: $hourOfDay:$minute. Solo es posible cada media hora apartir de las 7 hasta la 20 horas"
					Toast.makeText( activity, text, Toast.LENGTH_LONG ).show()
				}

			}, hourOfDay, minute, is24HourView ).apply {
				setTitle("Hora de la cita")
			}

			TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

			}

			return time
		}

		fun createAlert( activity: Activity ): AlertDialog {
			val alert = AlertDialog.Builder( activity ).apply {
				setTitle("Información que cura")
				setMessage(R.string.txt_hint_solicitar_cita)

				setPositiveButton("Perfecto. Correcto. Lo entiendo" ) { dialog, id ->
					dialog.dismiss()
				}
			}.create()

			return alert
		}

		fun createAlertPositive( context: Context, title: String, msg: String, listener: DialogInterface.OnClickListener ): AlertDialog {
			val alert = AlertDialog.Builder( context, R.style.datePicker ).apply {
				setTitle( title )
				setMessage( msg )

				setPositiveButton( "Aceptar", listener )
				setNegativeButton("Cancelar") {
					dialog, id ->
				}
			}.create()

			return alert
		}
	}
}