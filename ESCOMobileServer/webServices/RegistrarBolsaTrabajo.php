<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Description of RegistrarBolsaTrabajo.
 *
 * @author isaac
 */
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/conf/configWebService.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/crud/CRUDBolsaTrabajo.php' );

$escuela = $idEmpresa = $oferta = $vacante = $perfil = $prestaciones	 = "";

if( $_SERVER["REQUEST_METHOD"] === "POST" ) {
    header('Content-Type: application/json');
    $json = Util::getJSONRequest();

    if( isset( $json["request"] ) ) {
    	//Aqui estaba lo de publicar boletin, se paso a gestionar facebook
    	

    } else {
		$idEscuela		= $json["escuela"];
		$idEmpresa  	= $json["id_empresa"];
		$vacante    	= $json["oferta"]["vacante"];
		$requisitos 	= $json["oferta"]["requisitos"];
		$idiomas	 	= $json["oferta"]["idiomas"];
		$perfil     	= $json["oferta"]["perfil"];
		$puesto     	= $json["oferta"]["puesto"];
		$prestaciones   = $json["oferta"]["prestaciones"];
		$sueldo_oferta 	= getSueldo( $json["oferta"]["sueldo"] );
		$horario 		= getHorario( $json["oferta"]["horario"] );

		$oferta = new OfertaLaboral( NULL, $idEscuela, $idEmpresa, $perfil, $puesto, $vacante, 
						$requisitos, $idiomas, $sueldo_oferta, $horario );

		$crud = new CRUDBolsaTrabajo();	
		$success = $crud->insertOfertaLaboral( $oferta );

		if( $success["status"] ) {
			$data = array( "mensaje" => "NO PROBLEM" );
			$json = Util::getJSONResponse( Util::$ERROR, 200, "Oferta registrada con éxito.", $data );

		} else {
			$data = array( "mensaje" => $success["error"] );
			$json = Util::getJSONResponse( Util::$ERROR, 500, "Error al registrar la oferta.", $data );
		}

		echo $json;
    }
    
} else {
	//Necesariamente debe ser una petición POST y no de otro tipo.
	$data = array( "mensaje" => "SE DEBE HACER LA PETICIÓN POR POST" );
	$json = Util::getJSONResponse( Util::$ERROR, 500, "Método no válido.", $data );
}

function getSueldo( $jsonSueldo ) {
	$tipo = $jsonSueldo["tipo"];

	if( $jsonSueldo["monto"]["fijo"] ) {
		//El sueldo es fijo
		if( $jsonSueldo["monto"]["rango"]["de"] != NULL ) {
			$sueldo_min = $jsonSueldo["monto"]["rango"]["de"];

		} else {
			$sueldo_min = $jsonSueldo["monto"]["cantidad"];
		}

		$sueldo_max = NULL;

	} else {
		//El sueldo es un rango
		$sueldo_min = $jsonSueldo["monto"]["rango"]["de"];
		$sueldo_max = $jsonSueldo["monto"]["rango"]["a"];
	}

	$sueldo_oferta = new Sueldo( $sueldo_min, $sueldo_max, $tipo, NULL );

	return $sueldo_oferta;
}

function getHorario( $jsonHorario ) {
	$tipo = $jsonHorario["tipo"];
	$dias = [];

	if( $jsonHorario["jornada"] != NULL ) {

		foreach( $jsonHorario["jornada"] as $json ) {
			$dia = new Dia( $json["dia"], $json["hora_ini"], $json["hora_fin"] );

			array_push( $dias, $dia );
		}
	}

	$horario = new Horario( NULL, $dias, $tipo ); //El id del horario y el de la oferta son null.

	return $horario;
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
