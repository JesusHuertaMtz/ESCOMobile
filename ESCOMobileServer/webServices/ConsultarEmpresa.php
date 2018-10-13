<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Description of ConsultarEmpresa.
 *
 * @author isaac
 */
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/conf/configWebService.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/crud/CRUDEmpresa.php' );

if( $_SERVER["REQUEST_METHOD"] === "POST" ) {
    header('Content-Type: application/json');
    
    $json_decoded = Util::getJSONRequest();
    
    if( $json_decoded["request"] == "getEmpresas") {
    	$crud = new CRUDEmpresa();
		$jsonEmpresas = $crud->getEmpresas();

		$data = array( "empresas" => $jsonEmpresas	 );
		$json = Util::getJSONResponse( Util::$ERROR, 200, "SUCCESS", $data );
		echo $json;

    } else if( $json_decoded["request"] == "getNumeroEmpresasRegistradas" ) {
    	$crud = new CRUDEmpresa();
		$numEmpresasReg = $crud->getNumEmpresaRegistradas();

		$data = array( "num_empresas_reg" => $numEmpresasReg );
		$json = Util::getJSONResponse( Util::$ERROR, 200, "SUCCESS", $data );
		echo $json;

    } else if( $json_decoded["request"] == "autocompletarNombreEmpresas" ) {
    	$crud = new CRUDEmpresa();
		$nombreEmpresas = $crud->getAutoCompleteEmpresa( $json_decoded["chars"] );

		$data = array( "id_nombre_empresas" => $nombreEmpresas );
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
