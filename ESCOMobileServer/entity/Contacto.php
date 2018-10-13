<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Contacto.
 *
 * @author isaac
 */
class Contacto {
	private $idEmpresa;         //Valor entero representando el identificador de una empresa.
	private $nombre_contacto;   //Nombre de la persona con quién se deben poner en contacto.
	private $cargo;             //Cargo del mismo.
	private $medioContacto;     //Arreglo clave-valor con los medios de contacto.

	function __construct() {}

	function __construct1( $idEmpresa, $nombre_contacto, $cargo, $medioContacto ) {
		$this->idEmpresa = $idEmpresa;
		$this->nombre_contacto = $nombre_contacto;
		$this->cargo = $cargo;
		$this->medioContacto = $medioContacto;
	}

	function getIdEmpresa() {
		return $this->idEmpresa;
	}

	function getNombreContacto() {
		return $this->nombre_contacto;
	}

	function getCargo() {
		return $this->cargo;
	}

	function getMedioContacto() {
		return $this->medioContacto;
	}

	function setIdEmpresa( $idEmpresa ) {
		$this->idEmpresa = $idEmpresa;
	}

	function setNombreContacto( $nombreContacto ) {
		$this->nombre_contacto = $nombreContacto;
	}

	function setCargo( $cargo ) {
		$this->cargo = $cargo;
	}

	function setMedioContacto( $medioContacto ) {
		$this->medioContacto = $medioContacto;
	}
}
