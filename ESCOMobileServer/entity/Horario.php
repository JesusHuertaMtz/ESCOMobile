<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Horario.
 *
 * @author isaac
 */
abstract class DaysOfWeek {
	const Sunday 	= 0;
	const Monday 	= 1;
	const Tuesday 	= 2;
	const Wednesday = 3;
	const Thursday 	= 4;
	const Friday 	= 5;
	const Saturday 	= 6;

	public static function geStringValueOf( $day, $isShort ) {
		$stringValue = "";

		switch( $day ) {
			case DaysOfWeek::Sunday :
				$stringValue = ( !$isShort ) ? "Domingo": "Do";
				break;

			case DaysOfWeek::Monday :
				$stringValue = ( !$isShort ) ? "Lunes": "Lu";
				break;
			
			case DaysOfWeek::Tuesday :
				$stringValue = ( !$isShort ) ? "Martes": "Ma";
				break;
			
			case DaysOfWeek::Wednesday :
				$stringValue = ( !$isShort ) ? "Miércoles": "Mi";
				break;
			
			case DaysOfWeek::Thursday :
				$stringValue = ( !$isShort ) ? "Jueves": "Ju";
				break;break;
			
			case DaysOfWeek::Friday :
				$stringValue = ( !$isShort ) ? "Viernes": "Vi";
				break;
			
			case DaysOfWeek::Saturday :
				$stringValue = ( !$isShort ) ? "Sábado": "Sa";
				break;

			default:
				$stringValue = "No es un día de la semana";
				break;
		}

		return $stringValue;
	}
}

class Dia {
	private $dia;
	private $hora_ini;
	private $hora_fin;

	function __construct() {
		$a = func_get_args();
		$i = func_num_args();

		if( method_exists( $this, $f = '__construct' . $i ) ) {
			call_user_func_array( array( $this, $f ), $a );
		}
	}

	function __construct3( $dia, $hora_ini, $hora_fin ) {
		$this->dia = $dia;
		$this->hora_ini = $hora_ini;
		$this->hora_fin = $hora_fin;
	}

	function setDia( $dia ) {
		$this->dia = $dia;
	}
	
	function getDia() {
		return DaysOfWeek::geStringValueOf( $this->dia, FALSE );
	}

	function getDiaRawValue() {
		return $this->dia;
	}

	function setHoraIni( $hora_ini ) {
		$this->hora_ini = $hora_ini;
	}

	function getHoraIni() {
		return $this->hora_ini;
	}

	function setHoraFin( $hora_fin ) {
		$this->hora_fin = $hora_fin;
	}

	function getHoraFin() {
		return $this->hora_fin;
	}

}

class Horario {
	private $idOfertaTrabajo;
	private $dias = [];
	private $tipo;
	
	function __construct() {
		$a = func_get_args();
		$i = func_num_args();

		if( method_exists( $this, $f = '__construct' . $i ) ) {
			call_user_func_array( array( $this, $f ), $a );
		}
	}

	function __construct3( $idOfertaTrabajo, $dias, $tipo ) {
		$this->idOfertaTrabajo = $idOfertaTrabajo;
		$this->dias = $dias;
		$this->tipo = $tipo;
	}

	function addDia( $dia ) {
		//Veririca que el día no se haya ya añadido
		$isExist = array_search( $dia, $dias );

		if( !$isExist ) {
			array_push( $dias, $dia );
		}
	}

	function getIDOfertaTrabajo() {
		return $this->idOfertaTrabajo;
	}

	function setIDOfertaTrabajo( $idOfertaTrabajo ) {
		$this->idOfertaTrabajo = $idOfertaTrabajo;
	}

	function getDias() {
		return $this->dias;
	}

	function setDias( $dias ) {
		$this->dias = $dias;
	}

	function getTipo() {
		return $this->tipo;
	}

	function setTipo( $tipo ) {
		$this->tipo = $tipo;
	}
}




