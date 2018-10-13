<?php
require_once( "FacebookAPI.php" );

if( !session_id() ) {
    session_start();
}

$urlRedirect = "https://localhost/ESCOMobileServer/fb-callback.php";

$fbApi = new FacebookAPI();
$fb = $fbApi->getFacebook();

$helper = $fb->getRedirectLoginHelper();

$permissions = ["email", "manage_pages", "publish_pages", "pages_show_list", "user_photos", "user_posts"];

$loginUrl = $helper->getLoginUrl( $urlRedirect, $permissions );

//echo '<a href="' . htmlspecialchars( $loginUrl ) . '">Log in with Facebook!</a>';
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ESCOMobile</title>

		<link rel="stylesheet" type="text/css" href="assets/DataTables/Bootstrap-4-4.0.0/css/bootstrap.min.css">
		<!-- Para los iconos se necesita conexión a internet-->
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<!------>
		<style type="text/css">
			.myDivMsg {
				width: 50%;
			}
			.blue {
				color: #007bff;
			}

			.orange {
				color: #ffb300;
			}

			img {
				width: 40%;
				height: 20%;
				border-radius: 50%;
			}

		</style>
	</head>
	<body>
		<!-- fixed-top -->
		<!--<nav class="navbar navbar-light flex-md-nowrap p-0 shadow"> 
			<div class="col-sm-4 col-md-4"></div>
			<h1><span class="blue">ESCOMobile</span> <span class="orange">bolsa</span></h1>
			<div class="col-sm-2 col-md-2"></div>
			<img class="imagenPerfil" src="assets/image/escom.jpg"/>
			<a class="nav-link-2" href="#">Bolsa de trabajo</a>
		</nav> -->

		<div class="d-flex justify-content-center m-4">
			<h1><span class="blue">ESCOMobile</span> <span class="orange">bolsa</span></h1>
		</div>

		<div class="d-flex justify-content-center m-5">
			<h1>¡Bienvenido!</h1>
		</div>

		<div class="d-flex justify-content-center align-items-center m-5 p-5">
			<div class="d-flex text-wrap text-justify myDivMsg">
				<h4>
					Para continuar es necesario que inicies sesión con Facebook. De esta manera podrás 
					publicar las ofertas laborales en la página de Facebook que administres, además se 
					publicarán en la app móvil ESCOMobile.
				</h4>
			</div>
		</div>

		<div class="d-flex justify-content-center align-items-center m-3 p-2">
			<a type="button" href=<?php echo "'".htmlspecialchars( $loginUrl )."'" ?> 
			class="btn btn-primary btn-lg btn-icon text-light">
				Inicia sesión con Facebook
				<i class="fa fa-facebook-square align-middle" style="font-size:48px;"></i>
			</a	>
		</div>



		<script type="text/javascript" src="assets/DataTables/jQuery-3.2.1/jquery-3.2.1.js"></script>
		<script src="assets/DataTables/Bootstrap-4-4.0.0/js/bootstrap.min.js"></script>
	</body>
</html>












