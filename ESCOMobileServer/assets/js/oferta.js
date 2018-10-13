
$(document).ready( function() {
	//Muestra el modal
	$("#myOferta").click( function() {
		 $("#modalOferta").modal();
	});

	//Ejecuta el evento al cerrar el modal
	$("#modalOferta").on('hidden.bs.modal', function() {
		//Limpia el formulario
		$(this).find('form')[0].reset();
		$("#select_horario").show();
		$("#horarioOferta").show();
	});

	$("#oferta").click( registerOferta );
});

/* =============== Campo: HORARIO. =============== */
var days = { "lun": "Lunes", "mar": "Martes", "mie": "Miércoles", "jue": "Jueves", "vie": "Viernes", "sab": "Sábado" };
var numDay = { "lun": "1", "mar": "2", "mie": "3", "jue": "4", "vie": "5", "sab": "6" };
var daysID = ["lun", "mar", "mie", "jue", "vie", "sab"];

function updateSchedule( select ) {
	var typeSchedule =  $( select ).val();

	if( typeSchedule == "Tiempo completo" || typeSchedule == "Medio tiempo" ) {
		$("#select_horario").show();
		$("#horarioOferta").show();

	} else {
		$("#select_horario").hide();
		$("#horarioOferta").hide();
	}
}

function showScheduleForm( checkbox ) {
	var isSelectedDefinido = $( checkbox ).attr("id");

	if( isSelectedDefinido === "inlineRadio1" ) {
		$("#horarioOferta").show();

	} else {
		$("#horarioOferta").hide();
	}
}

function showScheduleForDay( checkboxDay ) {
	var idDay = $( checkboxDay ).attr("id");
	var day = days[idDay];

	if( day === "Sábado" ) {
		addOrRemoveDivSchedule( $( checkboxDay ).is(":checked"), day );
	} else {
		addOrRemoveDivSchedule( !$( checkboxDay ).is(":checked"), day );
	}
}

function addOrRemoveDivSchedule( isChecked, day ) {
	if( isChecked ) {
		$("#horarioOferta").append( createFieldSchedule( day ) );

	} else {
		$("#div_g" + day ).remove();
		$("#div_r" + day ).remove();
	}
}

function createFieldSchedule( day ) {
	var fieldSchedule = '<div class="form-group" id="div_g' + day + '"><p>' + day + '</p></div>' +
							'<div class="form-row" id="div_r' + day + '"><div class="form-group col-sm-6">' +
									'<label for="hora_ini_' + day + '">De: </label>' +
									'<input type="time" class="form-control" name="hora" value="09:00:00" max="23:59:00"' +
										'min="07:00:00" step="1" id="hora_ini_' + day + '"></div> ' +
								'<div class="form-group col-sm-6">' +
									'<label for="hora_fin_' + day + '"> A: </label>' +
									'<input type="time" class="form-control" name="hora" value="18:00:00" max="23:59:00"' +
										'min="07:00:00" step="1" id="hora_fin_' + day + '"></div></div>';
	return fieldSchedule;
}
/* =============== FIN Campo: HORARIO. =============== */

/* =============== Campo: Sueldo. =============== */
function onChangeTypeSueldo( select ) {
	var typeSueldo = $( select ).val();
	console.log( select );

	$("#rango_1").remove();
	$("#rango_2").remove();
	$("#cantidad_fijo").remove();

	if( typeSueldo === "Fijo" ) {
		$("#sueldo_container").append( createDivSueldoFijo() );
		$("#sueldo").prop('disabled', false );

	} else if( typeSueldo === "Rango" ) {
		$("#sueldo_container").append( createDivRangoSueldo );

	} else {
		$("#sueldo_container").append( createDivSueldoFijo() );
		$("#sueldo").prop('disabled', true );
	}
}

function createDivSueldoFijo() {
	var sueldoFijo = '<div class="form-group col-sm-8" id="cantidad_fijo"><label for="sueldo">* Cantidad</label>' +
						'<input type="number" id="sueldo" class="form-control"  min="1" max="100000" ' +
							'step="any" placeholder="ej. 15,000.00" />' +
						'<small class="form-text text-muted">El sueldo es de un rango de $1.00 a $100,000</small> </div>';

	return sueldoFijo;
}

function createDivRangoSueldo() {
	var rangoSueldo = '<div class="form-group col-sm-4" id="rango_1"> <label for="sueldo_1">* Cantidad</label>' +
						'<input type="number" id="sueldo_1" class="form-control"  min="1" max="100000" ' +
							'step="any" placeholder="ej. 15,000.00" onchange="sueldoValidator( this )"/>' +
						'<small class="form-text text-muted">El sueldo es de un rango de $1.00 a $100,000</small> </div>' +
					'<div class="form-group col-sm-4" id="rango_2"> <label for="sueldo_2">* Cantidad</label> '+
						'<input type="number" id="sueldo_2" class="form-control"  min="1" max="100000" '+
							'step="any" placeholder="ej. 15,000.00" onchange="sueldoValidator2( this )" disabled/>' +
						'<small class="form-text text-muted" id="err_sueldo_2">El sueldo es de un rango de $1.00 a $100,000</small></div>';
	return rangoSueldo;
}

function sueldoValidator( sueldo_1 ) {
	var sueldo_ini = parseFloat( $( sueldo_1 ).val() );

	if( $( sueldo_1 ).val().trim() !== "" && !isNaN( sueldo_ini ) ) {
		$("#sueldo_2").attr("min", sueldo_ini + 1 );
		$("#sueldo_2").prop('disabled', false );

	} else {
		$("#sueldo_2").text("");
		$("#sueldo_2").prop('disabled', false );
	}

	sueldoValidator2( $("#sueldo_2") );
}

function sueldoValidator2( sueldo_2 ) {
	var sueldo_ini = parseFloat( $("#sueldo_1").val() );
	var sueldo_fin = parseFloat( $( sueldo_2 ).val() );

	if( !isNaN( sueldo_fin ) && sueldo_fin > sueldo_ini ) {
		$("#err_sueldo_2").text("El sueldo es de un rango de $1.00 a $100,000");

	} else {
		$("#err_sueldo_2").text("El sueldo final debe ser mayor a " + $("#sueldo_1").val() );
	}
}
/* =============== FIN Campo: Sueldo. =============== */

/* =============== Campo: Botón registrar oferta. =============== */
function registerOferta() {
	var jsonOferta = makeJSONRO();
	$("#modalOferta").modal('hide');
	
	$.ajax({
		method: "POST",
		contentType: "application/json",
		url: "webServices/RegistrarBolsaTrabajo.php",
		data: jsonOferta

	}).done( function( msg ) {
		var myString = JSON.stringify( msg );
		Lobibox.notify( "success", {
			msg: msg.description
		});

	}).fail( function( msg ) {
		var myString = JSON.stringify( msg );
		Lobibox.notify( "error", {
			msg: msg.description
		});
	});
}

function makeJSONRO() {
	var object = new Object();

 	object.escuela = 1;
	object.id_empresa = $("#empresa").attr("data-id");
	object.oferta = makeJSONOferta();  	

	return JSON.stringify( object );
}

function makeJSONOferta() {
	var oferta = new Object();

	oferta.vacante 		= $("#Vacante").val();
	oferta.requisitos 	= $("#Requisitos").val();
	oferta.idiomas 	 	= $("#idiomas").val();
	oferta.perfil 		= $("#Perfil").val();
	oferta.prestaciones = $("#Prestaciones").val();
	oferta.puesto		= $("#puesto").val();
	oferta.sueldo 		= makeJSONSalario();
	oferta.horario 		= makeJSONHorario();

	return oferta;
}

function makeJSONSalario() {
	var salario = new Object();
	var monto = new Object();
	var rango = new Object();
	var tipoSueldo = $("#tipo_sueldo").val();

	if( tipoSueldo === "Fijo" ) {
		salario.tipo = "Definido";
		monto.fijo = true;
		rango.de = null;
		monto.cantidad = $("#sueldo").val();
		monto.rango = rango;
		salario.monto = monto;
			
	} else if( tipoSueldo === "Rango" ) {
		salario.tipo = "Definido";
		monto.fijo = false;
		rango.de = $("#sueldo_1").val();
		rango.a = $("#sueldo_2").val();
		monto.rango = rango;
		salario.monto = monto;

	} else {
		salario.tipo = tipoSueldo;
		monto.fijo = true;
		rango.de = null;
		monto.cantidad = null
		monto.rango = rango;
		salario.monto = monto;
	}

	return salario;
}

function makeJSONHorario() {
	var horario = new Object();
	var tipoHorario = $("#horario").val();

	if( ( tipoHorario === "Tiempo completo" || tipoHorario === "Medio tiempo" ) && $("#inlineRadio1").is(":checked") ) {
		horario.tipo = "Definido";
		var jornadas = [];

		for( var i = 0; i < daysID.length; i++ ) {
			var dayID = daysID[i];
			var day = days[dayID];

			if( day === "Sábado" ) {
				if( $("#" + day ).is(":checked") ) {
					var jornada = makeJSONJornada( dayID, !$("#" + dayID ).is(":checked") );
					jornadas.push( jornada );
				}

			} else {
				var jornada = makeJSONJornada( dayID, $("#" + dayID ).is(":checked") );
				jornadas.push( jornada );
			}
		}
		horario.jornada = jornadas;

	} else {
		horario.tipo = tipoHorario;
		horario.jornada = null;
	}

	return horario;
}

function makeJSONJornada( day, isChecked ) {
	var jornada = new Object();
	var dayName = days[day];

	jornada.dia = numDay[day];

	if( isChecked ) {
		//Obtiene la hora en común
		jornada.hora_ini = $("#hora_ini").val();
		jornada.hora_fin = $("#hora_fin").val();
	} else {
		//Se definio una hora distinta pra este día
		jornada.hora_ini = $("#hora_ini_" + dayName ).val();
		jornada.hora_fin = $("#hora_fin_" + dayName ).val();
	}

	return jornada;
}
/* =============== FIN Campo: Sueldo. =============== */
