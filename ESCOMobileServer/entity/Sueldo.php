<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Sueldo.
 *
 * @author isaac
 */
class Sueldo {
	private $sueldo_min;
	private $sueldo_max;
	private $otro;
	private $id;

	function __construct() {
		$a = func_get_args();
		$i = func_num_args();

		if( method_exists( $this, $f = '__construct' . $i ) ) {
			call_user_func_array( array( $this, $f ), $a );
		}
	}

	//Poner el numero de parametros al lado del construct
	function __construct4( $sueldo_min, $sueldo_max, $otro, $id ) {
		$this->sueldo_min = $sueldo_min; 
		$this->sueldo_max = $sueldo_max;
		$this->otro = $otro;
		$this->id = $id;
	}

	function getID() {
		return $this->id;
	}

	function getSueldoMin() {
		return $this->sueldo_min;
	}
	
	function getSueldoMax() {
		return $this->sueldo_max;
	}

	function getOtro() {
		return $this->otro;
	}	

	function setID( $id ) {
		$this->id = $id;
	}

	function setSueldoMin( $sueldo_min ) {
		$this->sueldo_min = $sueldo_min;
	}

	function setSueldoMax( $sueldo_max ) {
		$this->sueldo_max = $sueldo_max;
	}

	function setOtro( $otro ) {
		//$this->otro = $otro;
		switch ( $otro ) {
			case 'Definido':
				$this->otro = 0;
				break;
			
			default:
				$this->otro = 1;
				break;
		}
	}

}
