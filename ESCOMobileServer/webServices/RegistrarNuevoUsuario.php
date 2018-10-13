<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/conf/configWebService.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/crud/CRUDUsuario.php' );

$id = $nombre = $primerApellido = $segundoApellido = $correo = $contrasenia = $esProfesor = "";

//$std = new stdClass();
$json_response;

if( $_SERVER["REQUEST_METHOD"] === "POST" ) {
	header('Content-Type: application/json');

	//Attempt to decode the incoming RAW post data from JSON.
	$decoded = Util::getJSONRequest();

	$id = $decoded["id"];
	$nombre = $decoded["nombre"];
	$primerApellido = $decoded["primerAp"];
	$segundoApellido = $decoded["segundoAp"];
	$correo = $decoded["correo"];
	$contrasenia = $decoded["contrasenia"];
	$esProfesor = $decoded["esProfesor"];// === "true";
	$fecha = $decoded["fecha"];

	$usuario = new Usuario( $id, $nombre, $primerApellido, $segundoApellido, $correo, $contrasenia, $esProfesor );

	$crud = new CRUDUsuario();
	$result = $crud->insertUsuario( $usuario );

	if( $result["status"] ) {
		$data = array( "mensaje" => $result["error"] );
		$json = Util::getJSONResponse( Util::$SUCCESS, 200, "Registro completo", $data );

	} else {
		$data = array( "mensaje" => $result["error"] );
		$json = Util::getJSONResponse( Util::$ERROR, 500, "No se pudo registrar usuario.", $data );
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
