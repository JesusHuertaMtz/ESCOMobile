$(document).ready( function() {
	
	publicarBoletin();

	//Muestra las empresas
	showAutocompleteEmpresas();

	//Pone el id de la empresa al input
	$("input[name=datalist_empresas]").focusout( function(){
		var val = $( this ).val();
		var options = $("#datalist_empresas").children();
		var id = "";

		for( var i = 0; i < options.length; i++ ) {
			console.log( $( options[i] ).attr("value") );

			if( $( options[i] ).attr("value") == $( this ).val() ) {
				id = $( options[i] ).attr("data-id");
				break;
			}
		}

		$("#empresa").attr("data-id", id );
	});

});

function showAutocompleteEmpresas() {
	var isSuccess = true;
	$("#empresa").keypress( function( event ) {
        var letras = $("#empresa").val();
        var jsonObject = new Object();

        jsonObject.request = "autocompletarNombreEmpresas";
        jsonObject.chars = letras;
        console.log( String.fromCharCode( event.which ) );

        if( isSuccess && ( event.which >= 97 && event.which <= 122 || event.which >= 65 && event.which <= 90 ) ) {
        	isSuccess = false;

        	$.ajax({
				method: "POST",
				contentType: "application/json",
				url: "webServices/ConsultarEmpresa.php",
				data: JSON.stringify( jsonObject )

			}).done( function( jsonResponse ) {
				var jsonEmpresas = jsonResponse.data.id_nombre_empresas;
				//console.log( JSON.stringify( jsonEmpresas ) );

				$("#datalist_empresas").empty();
				for( var i = 0; i < jsonEmpresas.length; i++ ) {
					$("#datalist_empresas").append("<option value='" +  jsonEmpresas[i].nombre_empresa + "' data-id='" + jsonEmpresas[i].id + "'>");
				}

				isSuccess = true;

			}).fail( function( msg ) {
				var myString = JSON.stringify( msg );
				alert("ERROR: " + myString );
				isSuccess = true;
			});
        }
    });
}

/*
Al presionar sobre el botón publicar, publica las ofertas laborales seleccionadas.
*/
function publicarBoletin() {
	$('#publicar').on('click', function () {
		var jsonArray = makeJSONBoletin();
		console.log( jsonArray );

		$.ajax({
			method: "POST",
			contentType: "application/json",
			url: "webServices/GestionarFacebook.php",
			data: jsonArray

		}).done( function( msg ) {
			var myString = JSON.stringify( msg );
			alert( "Data Saved: " + myString );

		}).fail( function( msg ) {
			var myString = JSON.stringify( msg );
			alert("ERROR: " + myString );
		});
	});
}

function makeJSONBoletin() {
	//Obtiene las filas visibles de la tabla.
	var tbody = $("#ofertasNoPublicadas tbody tr");
	var jsonObjects = [];

	//Itera a través del arreglo para crear el objeto JSON.
	for( var i = 0; i < tbody.length; i++ ) {
		var tds = $( tbody[i] );
		var td = tds.children();

		var isRowChecked = $( td[0] ).find("input").is(':checked');

		//Solo obtiene los datos de las filas seleccionadas
		if( isRowChecked ) {
			//Crea un objeto JSON
			var jsonObject = new Object();

			var nameContacto = $( $( td[6] ).find("button")[0] ).text();
			var medioContacto = $( $( td[6] ).find("button")[1] ).attr("data-content").replace( /<br>/g, "\n" );
			console.log( nameContacto + "\n" + medioContacto );

			jsonObject.id 		= $( td[0] ).find("input").attr("id");
			jsonObject.no 		= td[1].innerHTML;
			jsonObject.nombre 	= td[2].innerHTML;
			jsonObject.horario 	= td[3].innerHTML;
			jsonObject.vacante 	= td[4].innerHTML;
			jsonObject.sueldo 	= td[5].innerHTML;
			jsonObject.contacto = nameContacto + "\n" + medioContacto;

			jsonObjects.push( jsonObject );
		}
	}

	var jsonArray = new Object();
	jsonArray.request = "publicarBoletin";
	jsonArray.data = jsonObjects;

	var myString = JSON.stringify( jsonArray );
	
	return myString;
}

