/* Crea la tabla y añade los eventos para desplazarse con el teclado */
$(document).ready(function() {
	var table = $('#empresas').DataTable( {
		"language": {
			"sProcessing":     "Procesando...",
			"sLengthMenu":     "Mostrar _MENU_ registros",
			"sZeroRecords":    "No se encontraron resultados",
			"sEmptyTable":     "Ningún dato disponible en esta tabla",
			"sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
			"sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
			"sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
			"sInfoPostFix":    "",
			"sSearch":         "Buscar:",
			"sUrl":            "",
			"sInfoThousands":  ",",
			"sLoadingRecords": "Cargando...",
			"oPaginate": {
				"sFirst":    "Primero",
				"sLast":     "Último",
				"sNext":     "Siguiente",
				"sPrevious": "Anterior"
			},
			"oAria": {
				"sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
				"sSortDescending": ": Activar para ordenar la columna de manera descendente"
			}
		},
		"ordering": true,
		"columnDefs": [
			{ "width": "22%",  "targets": 0 },	//Nombre
			{ "width": "13%", "targets": 1 },		//giro
			{ "width": "27%", "targets": 2 },		//contacto
			{ "width": "13%", "targets": 3 },		//rfc
			{ "width": "6%", "targets": 4 },		//no ofertas
			{ "width": "6%", "targets": 5 },		//visualizaciones
			{ "width": "13%", "targets": 6 },		//opciones
			{"className": "dt-center", "targets": [0, 1, 2, 3, 4, 5, 6]}
		],
		"searching": true
	});
});

/* Se presione el lápiz para editar */
function editarEmpresa( ev ) {
	var sender = $( ev ); //Convierte a elemento de JQuery
	var table = $('#empresas').DataTable(); //Obtiene un objeto DataTable
	var td = sender.parent().parent(); //Obtiene la columna del icono en el que se dio click
	var cell = table.cell( td ).index().row; //Obtiene el id de la fila.

	/* Se obtienen los datos de la empresa seleccionada para mostrar y editar los campos */
	alert( td + " " + cell );
}

/* Muestra el modal */
$(document).ready( function() {
    getEmpresas();
    getNumeroEmpresasRegistradas();
});

function getEmpresas() {
	var json = makeJSON( "getEmpresas" );

	$.ajax({
		method: "POST",
		contentType: "application/json",
		url: "webServices/ConsultarEmpresa.php",
		data: json

	}).done( function( jsonResponse ) {
		setDataTable( jsonResponse.data.empresas );
		
	}).fail( function( jsonResponse ) {
		var myString = JSON.stringify( jsonResponse );
		alert("ERROR: " + myString );
	});
}

function makeJSON( type ) {
	//Crea un objeto JSON
	var jsonObject = new Object();

	jsonObject.request 	= type;

	var myString = JSON.stringify( jsonObject );
	
	return myString;
}

function setDataTable( jsonEmpresas ) {
	clearTableWith("empresas");

	if( typeof jsonEmpresas === 'undefined' ) {
		return;
	}

	for( var i = 0; i < jsonEmpresas.length; i++ ) { 
		var contacto = makeDivContactosFromJSON( jsonEmpresas[i].contactos, i );
		addRowInTableWith( "empresas", jsonEmpresas, contacto, i );
	}

	loadPopoverAndDropMenu();
}

function loadPopoverAndDropMenu() {
	$('#empresas').on( 'draw.dt', function () {
	    showPop();
	});
	showPop();
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

function clearTableWith( idTable ) {
	var table = $('#' + idTable ).DataTable(); //Obtiene un objeto DataTable
	table.clear().draw();
}

function addRowInTableWith( idTable, jsonEmpresas, contacto, index ) {
	var table = $('#' + idTable ).DataTable(); //Obtiene un objeto DataTable
	var options = "<div class='d-flex flex-row justify-content-around'>" +
		    		"<i class='fa fa-pencil md-18' onclick='editarEmpresa( this )'></i>" +
		    		"<i class='fa fa-trash md-18' onclick='editarEmpresa( this )'></i>" +
		    	"</div>";

	var node = table.row.add( [
		jsonEmpresas[index].nombre,
		jsonEmpresas[index].giro,
		contacto,
		jsonEmpresas[index].rfc,
		jsonEmpresas[index].ofertas_pub,
		"N/D",
		options
	] ).draw( false ).node();

	$( node ).attr("id", jsonEmpresas[index].id );
}

function showPop() {
	//Muestra los popover de los button con clase btn-light
	$('.dropdown-toggle').dropdown();
	$('.btn-light').popover( { placement: "right", html: true, container: 'body', trigger: "hover" } );//
}

function changeContacto( sender ) {
	$("#dropdownMenuButton" + $(sender).attr("data-id") ).text( sender.innerHTML );
}

function getNumeroEmpresasRegistradas() {
	var json = makeJSON( "getNumeroEmpresasRegistradas" );

	$.ajax({
		method: "POST",
		contentType: "application/json",
		url: "webServices/ConsultarEmpresa.php",
		data: json

	}).done( function( jsonResponse ) {
		$("#numEmpresasReg").text( jsonResponse.data.num_empresas_reg );
		
	}).fail( function( jsonResponse ) {
		var myString = JSON.stringify( msg );
		alert("ERROR: " + myString );
	});
}










