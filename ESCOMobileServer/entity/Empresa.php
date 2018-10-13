<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Empresa.
 *
 * @author isaac
 */
class Empresa {
	private $idEmpresa;         //Valor entero representando el identificador de una empresa.
	private $nombre;  	        //Valor string que contiene el nombre de la empresa.
	private $zonaTrabajo;   	//Lugar en el que se ubiaca el trabajo.
	private $giro;             	//Giro al que pertenece la empresa.
	private $sector; 		    //Sector al que pertenece la empresa.
	private $rfc;				//RFC de la empresa.
	private $contactos;			//Instancia de la clase Contacto.
	private $logo;

	function __construct() {}

	function __construct1( $idEmpresa, $nombre, $zonaTrabajo, $giro, $sector, $rfc, $contactos, $logo ) {
		$this->idEmpresa = $idEmpresa;
		$this->nombre = $nombre;
		$this->zonaTrabajo = $zonaTrabajo;
		$this->giro = $giro;
		$this->sector = $sector;
		$this->rfc = $rfc;
		$this->contactos = $contactos;
		$this->logo = $logo;
	}

	function getID() {
		return $this->idEmpresa;
	}

	function getNombre() {
		return $this->nombre;
	}
	
	function getZonaTrabajo() {
		return $this->zonaTrabajo;
	}
	
	function getGiro() {
		return $this->giro;
	}
	
	function getSector() {
		return $this->sector;
	}

	function getRFC() {
		return $this->rfc;
	}

	function getContactos() {
		return $this->contactos;
	}

	function getLogo() {
		return $this->logo;
	}

	function setID( $idEmpresa ) {
		$this->idEmpresa = $idEmpresa;
	}

	function setNombre( $nombre ) {
		$this->nombre = $nombre;
	}
	
	function setZonaTrabajo( $zonaTrabajo ) {
		$this->zonaTrabajo = $zonaTrabajo;
	}
	
	function setGiro( $giro ) {
		$this->giro = $giro;
	}
	
	function setSector( $sector ) {
		$this->sector = $sector;
	}

	function setRFC( $rfc ) {
		$this->rfc = $rfc;
	}

	function setContactos( $contactos ) {
		$this->contactos = $contactos;
	}

	function setLogo( $logo ) {
		$this->logo = $logo;
	}
}









