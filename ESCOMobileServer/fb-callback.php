<?php
require_once( "FacebookAPI.php" );

if( !session_id() ) {
    session_start();
}

$fbApi = new FacebookAPI();
$fb = $fbApi->getFacebook();

//Obtenemos el token de acceso. Solo se puede obtener después del redireccionamiento.
$accessToken = $fbApi->getAccessTokenIfExist();

// Logged in
echo '<h3>Access Token</h3>';
var_dump( $accessToken->getValue() );



// The OAuth 2.0 client handler helps us manage access tokens
$oAuth2Client = $fb->getOAuth2Client();

// Get the access token metadata from /debug_token
$tokenMetadata = $oAuth2Client->debugToken( $accessToken );
//echo '<h3>Metadata</h3>';
//var_dump( $tokenMetadata );

// Validation (these will throw FacebookSDKException's when they fail)
$tokenMetadata->validateAppId( APP_ID ); // Replace {app-id} with your app id
// If you know the user ID this access token belongs to, you can validate it here
//$tokenMetadata->validateUserId('123');
$tokenMetadata->validateExpiration();

if( !$accessToken->isLongLived() ) {
	// Exchanges a short-lived access token for a long-lived one
	try {
		$accessToken = $oAuth2Client->getLongLivedAccessToken( $accessToken );

	} catch( Facebook\Exceptions\FacebookSDKException $e ) {
		echo "<p>Error getting long-lived access token: " . $e->getMessage() . "</p>\n\n";
		exit;
	}

	//echo '<h3>Long-lived</h3>';
	//var_dump( $accessToken->getValue() );
}

$_SESSION['fb_access_token'] = (string) $accessToken;

header('Location: https://localhost/ESCOMobileServer/escomobilebolsa.html');


















/*
//Abre los archivos hasta que se encuentre null.
$name = "boletin-";
$ruta = "/tmp/";
$pathImage = '/var/www/html/ESCOMobileServer/assets/image/escom.jpg';

$idPhoto = $fbApi->uploadImage( $pathImage, $tokenPage );
$imageIDs = [$idPhoto];

for( $index = 1; $index < 10; $index++ ) {
	$file = $ruta.$name.$index.".png";
	$fileImg = fopen( $file, "r" );

	if( $fileImg === FALSE ) {
		break;
	}

	$idPhoto = $fbApi->uploadImage( $file, $tokenPage );
	//echo '<br>Photo ID: ' . $idPhoto;
	array_push( $imageIDs, $idPhoto );

	fclose( $fileImg );
	//echo $file."<br>";
}

//echo "Imagenes subidas a servidor sin publicarse.<br>";
//var_dump( $imageIDs );

$msg = 'My awesome photo upload example.';
$idPublishPhoto = $fbApi->publishImages( $imageIDs, $msg, $idPage, $tokenPage );
//echo '<br>Photo PIBLISHED ID: ' . $idPublishPhoto;
*/
?>
