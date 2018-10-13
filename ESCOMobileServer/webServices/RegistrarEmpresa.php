<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/conf/configWebService.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/crud/CRUDEmpresa.php' );

$json_response;

if( $_SERVER["REQUEST_METHOD"] === "POST" ) {
    header('Content-Type: application/json');
    
    $json_decoded = Util::getJSONRequest();
    $empresa = new Empresa();

    $empresa->setID( NULL );
	$empresa->setNombre( $json_decoded["nombre_empresa"] );
	$empresa->setZonaTrabajo( $json_decoded["zona_trabajo"] );
	$empresa->setGiro( $json_decoded["giro"] );
	$empresa->setSector( $json_decoded["sector"] );
	$empresa->setRFC( $json_decoded["rfc"] );
	$empresa->setLogo( $json_decoded["logo"] );
	$contactos = [];

	foreach( $json_decoded["contacto"] as $json ) {
		$contacto = new Contacto();
		$medioContacto = [];

		if( $json["medio_contacto"] != NULL ) {
			foreach( $json["medio_contacto"] as $m_contacto ) {
				array_push( $medioContacto, $m_contacto );
			}
			$contacto->setMedioContacto( $medioContacto );
		}

		$contacto->setIdEmpresa( NULL ); //Obtener el id de la empresa.
		$contacto->setNombreContacto( $json["nombre"] );
		$contacto->setCargo( $json["cargo"] );
		
		array_push( $contactos, $contacto );
	}

	$empresa->setContactos( $contactos ); //Es un arreglo.

	$crud = new CRUDEmpresa();
	$success = $crud->insertEmpresa( $empresa );

	if( $success["status"] ) {
		$data = array( "mensaje" => "NO PROBLEM" );
		$json = Util::getJSONResponse( Util::$ERROR, 200, "Empresa registrada con éxito.", $data );

	} else {
		$data = array( "mensaje" => $success["error"] );
		$json = Util::getJSONResponse( Util::$ERROR, 500, "Error al registrar la empresa, contacto o medio de contacto.", $data );
	}

	echo $json;
    
} else {
	//Necesariamente debe ser una petición POST y no de otro tipo.
	$data = array( "mensaje" => "SE DEBE HACER LA PETICIÓN POR POST" );
	$json = Util::getJSONResponse( Util::$ERROR, 500, "Método no válido.", $data );
}

/*
 *{
 *  "status":"", // OK, ERROR, BAD_REQUEST
 *  "code":"" //200, 500, 400
 *  "description":"" //Success, ERROR: los datos ya existen, Falta un campo
 *  "data": {
 *      "mensaje":"El correo ya esta registrado"
 *  }
 *} 
 * 
 *  */
