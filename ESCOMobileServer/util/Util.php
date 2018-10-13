<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Util
 *
 * @author isaac
 */
class Util {
    static $SUCCESS = 0;
    static $ERROR = 1;
    static $BAD_REQ = 2;
    
    static function sanitized_string( $data ) {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);

        return $data;
    }

    /**
	Obtiene un JSON sitodo funciona bien, en caso de error envia un JSON con el error al cliente y regiresa false.
    */
    static function getJSONRequest() {
    	//Make sure that the content type of the POST request has been set to application/json
	    $contentType = isset($_SERVER["CONTENT_TYPE"]) ? trim($_SERVER["CONTENT_TYPE"]) : 'Content-Type';
	    
	    if( strcasecmp( $contentType, 'application/json' ) != 0 ) {
	        $data = array( "mensaje" => "Se debe añadir a la cabecera el tipo de contenido." );
	        $json = Util::getJSONResponse( Util::$BAD_REQ, 400, "Se espera recibir un JSON", $data );
	        
	        echo $json;
	        return FALSE;
	    }
	    
	    //Receive the RAW post data.
	    $content = trim( file_get_contents("php://input") );

	    //Attempt to decode the incoming RAW post data from JSON.
	    $decoded = json_decode( $content, true );

	    //If json_decode failed, the JSON is invalid.
	    if( !is_array( $decoded ) ) {
	        $data = array( "mensaje" => "Error en el formato del JSON" );
	        $json = Util::getJSONResponse( Util::$ERROR, 500, "JSON no válido", $data );
	        
	        echo $json;
	        return FALSE;
	    }

	    return $decoded;
    }
    
    static function getJSONResponse( $status, $code, $description, $data ) {
	    $json = array( "status" => $status,
	            "code" => $code, "description" => $description,
	            "data" => $data );
	        
	    $json_response = json_encode( $json, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE );
	    
	    return $json_response;
	}

	static function createMessageError( $msg ) {
		return $success = array( "status" => FALSE, "error" => $msg );
	}

	static function createMessageSuccess() {
		return $success = array( "status" => TRUE, "error" => "ninguno" );
	}
}
