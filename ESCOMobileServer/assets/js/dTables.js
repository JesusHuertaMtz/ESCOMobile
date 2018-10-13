/* Crea la tabla y añade los eventos para desplazarse con el teclado */
$(document).ready(function() {
	var language = {
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
		};

	var jsonOptionsTableOfePub = {
		"language": language,
		"ordering": true,
		"order": [[ 0, "desc" ]],
		"columnDefs": [
			{"className": "dt-center", "targets": 0 }
		],
		"searching": true
	};

	var jsonOptionsTableOfeNoPub = {
		"language": language,
		"ordering": true,
		"autoWidth": false,
		"columnDefs": [
			{"className": "dt-center", "targets": [0, 1] },
			{ "width": "1%",  "targets": 0 },	//Sel.
			{ "width": "1%",  "targets": 1 },	//No.
			{ "width": "20%", "targets": 2 },	//Nombre de la empresa
			{ "width": "14%", "targets": 3 },	//Horario
			{ "width": "40%", "targets": 4 },	//Vacante
			{ "width": "14%", "targets": 5 },	//Sueldo
			{ "width": "8%",  "targets": 6 },	//Contacto
			{ "width": "5%", "targets": 7 }	//Opciones
		],
		"searching": true
	};

	var table = $('#ofertasNoPublicadas').DataTable( jsonOptionsTableOfeNoPub );
	$('#ofertasPublicadas').DataTable( jsonOptionsTableOfePub );

	//EVENTO al presionar sobre una celda de la tabla
	//var table = $('#ofertasNoPublicadas').DataTable();
	/*
	var col;
	var row;
	var dataCell;


	$('#ofertasNoPublicadas tbody').on('click', 'td', function () {
		col = table.cell( this ).index().column;
		row = table.cell( this ).index().row;

		if( !$(this).children().is("input") ) {
			//No es un input
			dataCell = table.cell( this ).data();
			$(this).empty();
			$(this).append("<input id='focusable' class='fontSize form-control' type='text'>");

			$("#focusable").val( dataCell );
			$("#focusable").focus();
			$("#focusable").focusout(function() {
				dataCell = $(this).val();
				$(this).replaceWith( dataCell );
				table.cell( row, col ).data( dataCell );
			});

			$("#focusable").keypress(function( event ) {
				//alert( event.keyCode );
				if( event.which == 13 ) {
					event.preventDefault();

					if( col + 1 <= 5 ) {
						var td = $(this).closest("td");

						$(this).focusout();
						$(td).parent().children()[col + 1].click();

					} else {
						var rows = $("#ofertasNoPublicadas tbody tr");
						var numberOfRows = rows.length;

						if( row + 1 < numberOfRows ) {
							$(this).focusout();
							rows[row + 1].children[0].click();
						}
					}

				} else if( event.keyCode == 40 ) { //DOWN
					var rows = $("#ofertasNoPublicadas tbody tr");
					var numberOfRows = rows.length;

					if( row + 1 < numberOfRows ) {
						$(this).focusout();
						rows[row + 1].children[col].click();
					}

				} else if( event.keyCode == 39 ) { //RIGHT
					if( col + 1 <= 5 ) {
						var td = $(this).closest("td");

						$(this).focusout();
						$(td).parent().children()[col + 1].click();
					}

				} else if( event.keyCode == 38 ) { //UP
					var rows = $("#ofertasNoPublicadas tbody tr");
					var numberOfRows = rows.length;

					if( row - 1 >= 0 ) {
						$(this).focusout();
						rows[row - 1].children[col].click();
					}

				} else if( event.keyCode == 37 ) { //LEFT
					if( col - 1 >= 0 ) {
						var td = $(this).closest("td");

						$(this).focusout();
						$(td).parent().children()[col - 1].click();
					}

				}
			});
		} else {
			//Es un input
			//$(this).children().attr("id", "focusable");
		}
	});
	*/
});

/* Añade una oferta laboral */
$(document).ready(function() {
	var t = $('#ofertasNoPublicadas').DataTable();
	var last_row = t.row(':last').data();
	var lastId = parseInt( last_row ) + 1;

	$('#addOfertaLaboral').on( 'click', function () {
		t.row.add( [
			lastId.toString(),
			"",
			"",
			"",
			"",
			""
		] ).draw( false );
	});

	// Automatically add a first row of data
	//$('#addOfertaLaboral').click();
});

/* Muestra el modal */
/*
$(document).ready(function(){
    $("#myBtn").click(function(){
        $("#myModal").modal();
    });
});
*/