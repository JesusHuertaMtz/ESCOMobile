package com.example.master.escomobile_alpha.util

import java.util.*

class DateTimeValidator {

	companion object {
		val DATE_SELECTED_IS_WEEKEND : Int = 0
		val DATE_SELECTED_IS_HOLIDAY : Int = 1
		val DATE_SELECTED_IS_VALID : Int = 2
		val HOUR_SELECTED_IS_VALID : Int = 3
		val HOUR_SELECTED_ISNT_VALID : Int = 4
		private val MIN_TIME : Int = 7
		private val MAX_TIME : Int = 20

		fun validateDateIsNotWeekend( year: Int, month: Int, dayOfMonth: Int ): Int {
			val dateSelected = Calendar.getInstance()
			dateSelected.set( year, month, dayOfMonth )
			val dateOfWeek = dateSelected.get( Calendar.DAY_OF_WEEK )

			if( dateOfWeek == Calendar.SATURDAY || dateOfWeek == Calendar.SUNDAY ) {
				//No se puede agendar en fin de semana
				return DATE_SELECTED_IS_WEEKEND

			} else if( dateOfWeek == Calendar.SUNDAY ) {
				//Validar que no sea día festivo oficial
				return DATE_SELECTED_IS_HOLIDAY
			}

			return DATE_SELECTED_IS_VALID
		}

		fun validateTime( hourOfDay: Int, minute: Int ): Int {
			if( hourOfDay >= MIN_TIME && hourOfDay <= MAX_TIME ) {
				if( hourOfDay == MAX_TIME && minute > 0 ) {
					return HOUR_SELECTED_ISNT_VALID

				} else if( minute == 0 || minute == 30 ) {
					return HOUR_SELECTED_IS_VALID

				} else {
					return HOUR_SELECTED_ISNT_VALID
				}

			} else {
				return HOUR_SELECTED_ISNT_VALID
			}
		}
	}
}


















