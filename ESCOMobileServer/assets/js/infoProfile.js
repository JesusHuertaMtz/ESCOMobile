$(document).ready(function() {
	var jsonObj = new Object();
	jsonObj.request = "getPageInfo"
	var json = JSON.stringify( jsonObj );

	$.ajax({
		method: "POST",
		contentType: "application/json",
		url: "webServices/GestionarFacebook.php",
		data: json

	}).done( function( json ) {
		var jsonS = JSON.stringify( json );
		$("#namePage").attr("href", "https://www.facebook.com/" + json.data.idPage );
		$("#namePage").text( json.data.namePage );
		$("#pictureProfile").attr("src", json.data.picture );

	}).fail( function( msg ) {
		var myString = JSON.stringify( msg );
		alert("ERROR: " + myString );
	});
})