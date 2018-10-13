<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/conf/configWebService.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/crud/CRUDUsuario.php' );

$id = $nombre = $primerApellido = $segundoApellido = $correo = $contrasenia = $esProfesor = "";
$json_response;

if( $_SERVER["REQUEST_METHOD"] === "POST" ) {
    header('Content-Type: application/json');
    
    $decoded = Util::getJSONRequest();

	$id = $decoded["id"];
	$contrasenia = $decoded["contrasenia"];
	if( isset( $decoded["fecha"] ) ) {
		$fecha = $decoded["fecha"];
	}
    
    $usuario = new Usuario( $id, $nombre, $primerApellido, $segundoApellido, $correo, $contrasenia, $esProfesor );
    
    $crud = new CRUDUsuario();
    $result = $crud->getUsuario( $usuario );
    
    if( $result["status"] ) {
        //CREAR JSON
        $user = $result["object"];
        $data = array( "id" => $user->getId(), "nombre" => $user->getNombre(), "primerAp" => $user->getPrimerApellido(),
        				"segundoAp" => $user->getSegundoApellido(), "correo" => $user->getCorreo(), 
                        "foto" => "", "esProfesor" => $user->getEsProfesor() );
        $json = Util::getJSONResponse( Util::$SUCCESS, 200, "¡Bienvenido!", $data );
        
        echo $json;
        
    } else {
        $data = array( "mensaje" => $result["error"] );
        $json = getJSONResponse( Util::$ERROR, 500, "Boleta/# de empleado y/o contraseña incorrectos.", $data );

        echo $json;
    }

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
 **/
