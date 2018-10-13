
$(document).ready( function() {	
	getNumeroOfertasRegistradas();
	getOfertasNoPublicadas();
	getOfertasPublicadasPorAnio();

	$( "#ofertasPorAño" ).change(function() {
		getOfertasPublicadasPorAnio();
	});
});

function getNumeroOfertasRegistradas() {
	var json = makeJSON( "getNumeroOfertasRegistradasEsteAño" );

	$.ajax({
		method: "POST",
		contentType: "application/json",
		url: "webServices/ConsultarBolsaTrabajo.php",
		data: json

	}).done( function( jsonResponse ) {
		$("#totalOfertas").text( jsonResponse.data.total_ofertas );
		
	}).fail( function( jsonResponse ) {
		var myString = JSON.stringify( jsonResponse );
		alert("ERROR: " + myString );
	});
}

/*=================================* OFERTAS NO PUBLICADAS *=================================*/
function getOfertasNoPublicadas() {
	$.ajax({
		method: "POST",
		contentType: "application/json",
		url: "webServices/ConsultarBolsaTrabajo.php",
		data: makeJSON( "getOfertasSinPublicar" )

	}).done( function( jsonResponse ) {
		var myString = JSON.stringify( jsonResponse );
		var ofertasSinPublicar = jsonResponse.data.ofertas_sin_publicar;

		parserOfertasSinPublicar( ofertasSinPublicar );
		//alert( "Data Saved: " + myString );


	}).fail( function( jsonResponse ) {
		var myString = JSON.stringify( jsonResponse );
		alert("ERROR: " + myString );
	});
}

function parserOfertasSinPublicar( jsonOfertasSinPublicar ) {
	clearTable("ofertasNoPublicadas");

	if( typeof jsonOfertasSinPublicar === 'undefined' ) {
		return;
	}

	for( var i = 0; i < jsonOfertasSinPublicar.length; i++ ) {
		var contactos = jsonOfertasSinPublicar[i].contactos;
		var contacto = makeDivContactosFromJSON( contactos, i );
		
		addRowInTableOfertasSinPublicar( jsonOfertasSinPublicar, contacto, i );
	}

	loadPopoverAndDropMenu();
}

function addRowInTableOfertasSinPublicar( jsonOfertasSinPublicar, contacto, index ) {
	var table = $('#ofertasNoPublicadas').DataTable(); //Obtiene un objeto DataTable
	var options = "<div class='d-flex flex-row justify-content-around'>" +
		    		"<i class='fa fa-pencil md-18' onclick='editarOferta( this )'></i>" +
		    		"<i class='fa fa-trash md-18' onclick='editarOferta( this )'></i>" +
		    	"</div>";
	var select = '<input class="form-check-input" type="checkbox" id="'+ jsonOfertasSinPublicar[index].ofertas.id + '" value="0">';

	var node = table.row.add( [
		select,
		index + 1,
		jsonOfertasSinPublicar[index].nombre,
		jsonOfertasSinPublicar[index].ofertas.horario,
		jsonOfertasSinPublicar[index].ofertas.requisitos,
		jsonOfertasSinPublicar[index].ofertas.sueldo,
		contacto,
		options
	] ).draw( false ).node();

	$( node ).attr("id", jsonOfertasSinPublicar[index].id );
}

/*=================================* OFERTAS PUBLICADAS *=================================*/
function getOfertasPublicadasPorAnio() {
	var jsonObject = new Object();
	jsonObject.request = "getOfertasPorAnio";
	jsonObject.anio = $( "#ofertasPorAño" ).val();
	var json = JSON.stringify( jsonObject );

	$.ajax({
		method: "POST",
		contentType: "application/json",
		url: "webServices/ConsultarBolsaTrabajo.php",
		data: json

	}).done( function( jsonResponse ) {
		var myString = JSON.stringify( jsonResponse );
		var ofertasPublicadas = jsonResponse.data.ofertas_publicadas;

		parserOfertasPublicadas( ofertasPublicadas );
		//alert( "Data Saved: " + myString );

	}).fail( function( jsonResponse ) {
		var myString = JSON.stringify( jsonResponse );
		alert("ERROR: " + myString );
	});
}

function parserOfertasPublicadas( jsonOfertasPublicadas ) {
	clearTable("ofertasPublicadas");

	if( typeof jsonOfertasPublicadas === 'undefined' ) {
		return;
	}

	for( var i = 0; i < jsonOfertasPublicadas.length; i++ ) {
		var contactos = jsonOfertasPublicadas[i].contactos;
		var contacto = makeDivContactosFromJSON( contactos, i );
		
		addRowInTableOfertasPublicadas( jsonOfertasPublicadas, contacto, i );
	}

	loadPopoverAndDropMenu();
}

function addRowInTableOfertasPublicadas( jsonOfertasPublicadas, contacto, index ) {
	var table = $('#ofertasPublicadas').DataTable(); //Obtiene un objeto DataTable
	var options = "<div class='d-flex flex-row justify-content-around'>" +
		    		"<i class='fa fa-pencil md-18' onclick='editarOferta( this )'></i>" +
		    		"<i class='fa fa-trash md-18' onclick='editarOferta( this )'></i>" +
		    	"</div>";

	var node = table.row.add( [
		index + 1,
		jsonOfertasPublicadas[index].nombre,
		jsonOfertasPublicadas[index].ofertas.horario,
		jsonOfertasPublicadas[index].ofertas.requisitos,
		jsonOfertasPublicadas[index].ofertas.sueldo,
		contacto,
		jsonOfertasPublicadas[index].ofertas.fecha_pub,
		//options
	] ).draw( false ).node();

	$( node ).attr("id", jsonOfertasPublicadas[index].id );
}

/*=================================* OFERTAS METODOS COMUNES *=================================*/

function clearTable( idTable ) {
	var table = $('#' + idTable ).DataTable();
	table.clear().draw();
}

function makeDivContactosFromJSON( contactos, index ) {
	var contacto = '<div class="btn-group dropright">';

	for( var j = 0; j < contactos.length; j++ ) { // title='Popover Header' 
		if( j == 0 ) {
			contacto += '<button id="dropdownMenuButton' + index +'" class="btn btn-light dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
			contactos[j].n_contacto + '</button><div class="dropdown-menu" aria-labelledby="dropdownMenuButton">';
		}

		contacto += '<button onclick="changeContacto(this)" class="btn btn-light" data-id="' + index +'" data-content="';

		for( var k = 0; k < contactos[j].m_contacto.length; k++ ) {
			contacto += contactos[j].m_contacto[k].tipo + ' : ' + contactos[j].m_contacto[k].valor + '<br>';
		}

		contacto += '">' + contactos[j].n_contacto + '</button>';
	}

	contacto += '</div></div>';

	return contacto;
}

function loadPopoverAndDropMenu() {
	$('#ofertasNoPublicadas').on( 'draw.dt', function () {
	    pop();
	});
	pop();
}

function pop() {
	//Muestra los popover de los button con clase btn-light
	$('.btn-light').popover( { placement: "right", html: true, container: 'body', trigger: "hover" } );
	$('.dropdown-toggle').dropdown();
}

function changeContacto( sender ) {
	//$("#dropdownMenuButton").text( sender.innerHTML );
	$( sender ).text( sender.innerHTML );
}

/*=================================* EDITAR OFERTAS *=================================*/
/* Se presione el lápiz para editar */
function editarOferta( ev ) {
	var sender = $( ev ); //Convierte a elemento de JQuery
	var table = $('#ofertasNoPublicadas').DataTable(); //Obtiene un objeto DataTable
	var td = sender.parent().parent(); //Obtiene la columna del icono en el que se dio click
	var cell = table.cell( td ).index().row; //Obtiene el id de la fila.

	/* Se obtienen los datos de la empresa seleccionada para mostrar y editar los campos */

	alert( td + " " + cell );
}

function makeJSON( value ) {
	var jsonObject = new Object();
	jsonObject.request = value;
	
	var myString = JSON.stringify( jsonObject );
	
	return myString;
}











