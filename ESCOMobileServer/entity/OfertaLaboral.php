<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Oferta laboral.
 *
 * @author isaac
 */
class OfertaLaboral {
	private $id;
	private $idEscuela;
	private $idEmpresa;
	private $perfil;
	private $puesto;
	private $numVacantes;
	private $requisitos;
	private $idiomas;
	private $sueldo;
	private $horario;

	function __construct() {
		$a = func_get_args();
		$i = func_num_args();

		if( method_exists( $this, $f = '__construct' . $i ) ) {
			call_user_func_array( array( $this, $f ), $a );
		}
	}

	//Poner el numero de parametros al lado del construct
	function __construct10( $id, $idEscuela, $idEmpresa, $perfil, $puesto, $numVacantes, $requisitos, $idiomas, $sueldo, $horario ) {
		$this->id = $id;
		$this->idEscuela = $idEscuela;
		$this->idEmpresa = $idEmpresa;
		$this->perfil = $perfil;
		$this->puesto = $puesto;
		$this->numVacantes = $numVacantes;
		$this->requisitos = $requisitos;
		$this->idiomas = $idiomas;
		$this->sueldo = $sueldo;
		$this->horario = $horario;
	}

	function getID() {
		return $this->id;
	}

	function getIDEscuela() {
		return $this->idEscuela;
	}

	function getIDEmpresa() {
		return $this->idEmpresa;
	}

	function getPerfil() {
		return $this->perfil;
	}

	function getPuesto() {
		return $this->puesto;
	}

	function getNumVacantes() {
		return $this->numVacantes;
	}

	function getRequisitos() {
		return $this->requisitos;
	}

	function getIdiomas() {
		return $this->idiomas;
	}

	function getSueldo() {
		return $this->sueldo;
	}

	function getHorario() {
		return $this->horario;
	}

	function setID( $id ) {
		$this->id = $id;
	}

	function setIDEscuela( $idEscuela ) {
		$this->idEscuela = $idEscuela;
	}

	function setIDEmpresa( $idEmpresa ) {
		$this->idEmpresa = $idEmpresa;
	}

	function setPerfil( $perfil ) {
		$this->perfil = $perfil;
	}

	function setPuesto( $puesto ) {
		$this->puesto = $puesto;
	}

	function setNumVacantes( $numVacantes ) {
		$this->numVacantes = $numVacantes;
	}

	function setRequisitos( $requisitos ) {
		$this->requisitos = $requisitos;
	}

	function setIdiomas( $idiomas ) {
		$this->idiomas = $idiomas;
	}

	function setSueldo( $sueldo ) {
		$this->sueldo = $sueldo;
	}

	function setHorario( $horario ) {
		$this->horario = $horario;
	}
}
