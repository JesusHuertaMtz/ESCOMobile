<?php
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/conf/configWebService.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/crud/CRUDBolsaTrabajo.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/util/MakePDF.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/FacebookAPI.php' );

if( !session_id() ) {
    session_start();
}

if( $_SERVER["REQUEST_METHOD"] === "POST" ) {
	header('Content-Type: application/json');
	$json_decoded = Util::getJSONRequest();

	//Verificamos que exista un token
	if( isset( $_SESSION['fb_access_token'] ) ) {
		if( $json_decoded["request"] == "getPageInfo" ) {
			$accessToken = $_SESSION['fb_access_token'];
		
			$fbApi = new FacebookAPI();
			$fb = $fbApi->getFacebook();

			//Se obtienen los datos del usuario.
			$graphNode = $fbApi->getUserInfo( $accessToken );

			///Obtenemos los datos de la página
			$res_pages = $fbApi->getPageInfo( $accessToken );
			$idPage = $res_pages["id"];
			$tokenPage = $res_pages["token"];
			$namePage = $res_pages["name"];

			$grapPicture = $fbApi->getPagePicture( $idPage, $tokenPage );

			$data = array( "idPage" => $idPage, "namePage" => $namePage, "picture" => $grapPicture["picture"]["url"] );
			$json = Util::getJSONResponse( Util::$ERROR, 200, "No problem.", $data );

			echo $json;

		} else if( $json_decoded["request"] == "publicarBoletin" ) {
    		//TODO: PUBLICAR EN FACEBOOK LA IMAGEN DE LA OFERTA LABORAL.
    		$pdf = new MakePDF();
    		$pdf->createPDF( $json_decoded["data"], 4 );

    		$crud = new CRUDBolsaTrabajo();	
    		//Actualiza las ofertas para que se muestren vigentes.
    		if( empty( $json_decoded["data"] ) ) {
    			$data = array( "mensaje" => "No hay ofertas seleccionadas" );
				$jsonRes = Util::getJSONResponse( Util::$ERROR, 400, "Error no hay ofertas seleccionadas.", $data );
				echo $jsonRes;

				return;
    		}

    		foreach( $json_decoded["data"] as $jsonOferta ) {
    			$success = $crud->updateOfertaById( $jsonOferta["id"] );

    			if( $success["status"] ) {
		    		$data = array( "mensaje" => "NO PROBLEM" );
					$jsonRes = Util::getJSONResponse( Util::$ERROR, 200, "Boletín publicado correctamente.", $data );

    			} else {
    				$data = array( "mensaje" => $success["error"] );
					$jsonRes = Util::getJSONResponse( Util::$ERROR, 500, "Error al registrar la oferta.", $data );
					echo $jsonRes;

					return;
    			}
    		}

    		echo $jsonRes;
    	}

	} else {
		$data = array( "mensaje" => "Hubo un error al obtener el token de Facebook" );
		$json = Util::getJSONResponse( Util::$ERROR, 404, "Error al obtener el token.", $data );

		echo $json;
	}

} else {
	//Necesariamente debe ser una petición POST y no de otro tipo.
	$data = array( "mensaje" => "SE DEBE HACER LA PETICIÓN POR POST" );
	$json = Util::getJSONResponse( Util::$ERROR, 500, "Método no válido.", $data );
}


?>