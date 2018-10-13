<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Usuario
 *
 * @author isaac
 */
class Usuario {
    private $id;
    private $nombre;
    private $primerApellido;
    private $segundoApellido;
    private $correo;
    private $contrasenia;
    private $esProfesor;
    
    function __construct($id, $nombre, $primerApellido, $segundoApellido, $correo, $contrasenia, $esProfesor) {
        $this->id = $id;
        $this->nombre = $nombre;
        $this->primerApellido = $primerApellido;
        $this->segundoApellido = $segundoApellido;
        $this->correo = $correo;
        $this->contrasenia = $contrasenia;
        $this->esProfesor = $esProfesor;
    }
    
    function __constructor1() {
        
    }
            
    function getId() {
        return $this->id;
    }

    function getNombre() {
        return $this->nombre;
    }

    function getPrimerApellido() {
        return $this->primerApellido;
    }

    function getSegundoApellido() {
        return $this->segundoApellido;
    }

    function getCorreo() {
        return $this->correo;
    }

    function getContrasenia() {
        return $this->contrasenia;
    }

    function getEsProfesor() {
        return $this->esProfesor;
    }

    function setId($id) {
        $this->id = $id;
    }

    function setNombre($nombre) {
        $this->nombre = $nombre;
    }

    function setPrimerApellido($primerApellido) {
        $this->primerApellido = $primerApellido;
    }

    function setSegundoApellido($segundoApellido) {
        $this->segundoApellido = $segundoApellido;
    }

    function setCorreo($correo) {
        $this->correo = $correo;
    }

    function setContrasenia($contrasenia) {
        $this->contrasenia = $contrasenia;
    }

    function setEsProfesor($esProfesor) {
        $this->esProfesor = $esProfesor;
    }

}
