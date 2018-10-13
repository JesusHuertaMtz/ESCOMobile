<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Description of ConsultarBolsaTrabajo.
 *
 * @author isaac
 */

require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/conf/configWebService.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/crud/CRUDEscombd.php' );

if( $_SERVER["REQUEST_METHOD"] === "POST" ) {
    header('Content-Type: application/json');
    
    $json_decoded = Util::getJSONRequest();
    $crud = new CRUDEscombd();

    if( $json_decoded["request"] == "getAll") {
		$jsonEmpresas = $crud->getAllRows();

		$data = array( "ofertas_sin_publicar" => $jsonEmpresas	 );
		$json = Util::getJSONResponse( Util::$ERROR, 200, "SUCCESS", $data );

		echo $json;

    } else if( $json_decoded["request"] == "search" ) {
		$jsonEmpresas = $crud->searchQuery( $json_decoded["query"], $json_decoded["search_type"] );

		$data = array( "search_result" => $jsonEmpresas	 );
		$json = Util::getJSONResponse( Util::$ERROR, 200, "SUCCESS", $data );

		echo $json;

    } else if( $json_decoded["request"] == "getProfesores" ) {
		$jsonProfesores = $crud->getProfesores();

		$data = array( "profesores" => $jsonProfesores );
		$json = Util::getJSONResponse( Util::$ERROR, 200, "SUCCESS", $data );

		echo $json;

    } else if( $json_decoded["request"] == "getProfesorByName" ) {
		$jsonProfesor = $crud->getProfesorByName( $json_decoded["nombre"] );

		$data = array( "profesor" => $jsonProfesor );
		$json = Util::getJSONResponse( Util::$ERROR, 200, "SUCCESS", $data );

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
 *  */
