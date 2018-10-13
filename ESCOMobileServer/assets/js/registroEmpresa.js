var cont_contacto = 0;
var nombreEmpresa;
var rfcEmpresa;
var giroEmpresa;
var nombreContactoEmp;
var tipoContactoEmpresa;
var contactoEmpresa;

$(document).ready(function() {	
	$("#addEmpresa").click(function(){
        $("#modalEmpresa").modal();
    });
    
	$("#button_regEmpresa").on("click", function(){

		nombreEmpresa = $("#nombreEmpresa").val();
		rfcEmpresa = $("#rfcEmpresa").val();
		giroEmpresa = $("#giroEmpresa").val();
		nombreContactoEmp = $("#nombreContactoEmp").val();
		tipoContactoEmpresa = $("#tipoContactoEmpresa").val();
		contactoEmpresa = $("#contactoEmpresa").val();

		// Validación de campos obligatorios. 
		if(nombreEmpresa == "" || nombreContactoEmp == "" || contactoEmpresa == ""){
			alert("Debes introducir todos los campos marcados como obligatorios");
			return false;
		}
		// Valida que se seleccione una opción de cada combo box. 
		else if(giroEmpresa == null || giroEmpresa == 0 || tipoContactoEmpresa == null || tipoContactoEmpresa == 0) {
			alert("Debes seleccionar una opción de cada menú desplegable.");
  			return false;
		}

		//Valida formato del primer campo: Nombre de la empresa.
		if(nombreEmpresa.length > 200){
			alert("El nombre de la empresa debe tener máximo 200 caracteres.");
			return false;
		}
		//Valida RFC.
		if(rfcEmpresa != ""){
			if(!(/^([A-ZÑ\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))((-)?([A-Z\d]{3}))?$/.test(rfcEmpresa))) {
				alert("Formato de RFC incorrecto. \nIntroduce un RFC válido. Omite espacios en blanco y guiones entre los dígitos.");
			  	return false;
			}
		}
		else if(rfcEmpresa == ""){
			rfcEmpresa = null;
		}
		//Valida nombre del contacto.
		if(nombreEmpresa.length > 100){
			alert("El nombre del encargado en la empresa debe tener máximo 100 caracteres.");
			return false;
		}
		// ****** Validaciones para el combo box de contacto. ******
		//Teléfono fijo.
		if(tipoContactoEmpresa == "Teléfono fijo"){
			//Valida que tenga entre 7 y 10 dígitos nuéricos. 
			if(isNaN(contactoEmpresa)){
				alert("Debes introducir un número de telefónico fijo válido.");
				return false;
			}
			else{
				if(contactoEmpresa.length < 7 || contactoEmpresa.length > 10){
					alert("Debes introducir un número de teléfono fijo válido, con entre 7 y 10 dígitos.");
					return false;	
				}
			}
		}
		//Teléfono Celular.
		else if(tipoContactoEmpresa == "Teléfono celular"){
			//Valida que tenga entre 7 y 10 dígitos nuéricos. 
			if(isNaN(contactoEmpresa)){
				alert("Debes introducir un número de telefónico celular válido.");
				return false;
			}
			else{
				if(contactoEmpresa.length < 10 || contactoEmpresa.length > 13){
					alert("Debes introducir un número de teléfono celular válido, con entre 10 y 13 dígitos.");
					return false;	
				}
			}
		}
		//Correo electrónico.
		else if(tipoContactoEmpresa == "Correo electrónico" && contactoEmpresa.length <= 100){
			if(!(/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/.test(contactoEmpresa))) {
				alert("Formato de correo electrónico incorrecto. \nIntroduce un correo electrónico válido.");
			  	return false;
			}
		}
		//Linkein.
		else if(contactoEmpresa.length <= 100){
			if(tipoContactoEmpresa == "Linked in"){
				alert("Elegiste Linked in.");
			}
			//Facebook.
			else if(tipoContactoEmpresa == "Facebook"){
				alert("Elegiste Facebook.");
			}
			//Twitter.
			else if(tipoContactoEmpresa == "Twitter"){
				alert("Elegiste Twitter.");
			}
		}
		else{
			alert("Debes introducir un contacto correcto, con máximo 100 dígitos.");
			return false;
		}
		
		var objEmpresa = new Object();
		objEmpresa.nombre_empresa = nombreEmpresa;
		objEmpresa.zona_trabajo = null;
		objEmpresa.giro = giroEmpresa;
		objEmpresa.sector = null;
		objEmpresa.rfc = rfcEmpresa;
		objEmpresa.logo = getLogoEmpresaBase64();

			var jsonArray_medioContacto = [];
				var objMedioContacto = new Object();
			 		objMedioContacto.tipo = tipoContactoEmpresa;
					objMedioContacto.valor = contactoEmpresa;
			jsonArray_medioContacto.push(objMedioContacto);
			for(var i = 0; i < cont_contacto; i++){
				var tipoContactoEmpresa_aux = $('#a' + i).val();
				var contactoEmpresa_aux = $('#b' + i).val();
					// ****** Validaciones para el combo box de contacto. ******
					//Teléfono fijo.
					if(tipoContactoEmpresa_aux == "Teléfono fijo"){
						//Valida que tenga entre 7 y 10 dígitos nuéricos. 
						if(isNaN(contactoEmpresa_aux)){
							alert("Debes introducir un número de telefónico fijo válido.");
							return false;
						}
						else{
							if(contactoEmpresa_aux.length < 7 || tipoContactoEmpresa_auxaux.length > 10){
								alert("Debes introducir un número de teléfono fijo válido, con entre 7 y 10 dígitos.");
								return false;	
							}
						}
					}
					//Teléfono Celular.
					else if(tipoContactoEmpresa_aux == "Teléfono celular"){
						//Valida que tenga entre 7 y 10 dígitos nuéricos. 
						if(isNaN(contactoEmpresa_aux)){
							alert("Debes introducir un número de telefónico celular válido.");
							return false;
						}
						else{
							if(contactoEmpresa_aux.length < 10 || contactoEmpresa_aux.length > 13){
								alert("Debes introducir un número de teléfono celular válido, con entre 10 y 13 dígitos.");
								return false;	
							}
						}
					}
					//Correo electrónico.
					else if(tipoContactoEmpresa_aux == "Correo electrónico" && contactoEmpresa_aux.length <= 100){
						if(!(/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/.test(contactoEmpresa_aux))) {
							alert("Formato de correo electrónico incorrecto. \nIntroduce un correo electrónico válido.");
						  	return false;
						}
					}
					//Linkein.
					else if(contactoEmpresa_aux.length <= 100){
						if(tipoContactoEmpresa_aux == "Linked in"){
							alert("Elegiste Linked in.");
						}
						//Facebook.
						else if(tipoContactoEmpresa_aux == "Facebook"){
							alert("Elegiste Facebook.");
						}
						//Twitter.
						else if(tipoContactoEmpresa_aux == "Twitter"){
							alert("Elegiste Twitter.");
						}
					}
					else{
						alert("Debes introducir un contacto correcto, con máximo 100 dígitos.");
						return false;
					}
				var objMedioContacto_aux = new Object();
					objMedioContacto_aux.tipo = tipoContactoEmpresa_aux;
					objMedioContacto_aux.valor = contactoEmpresa_aux;
				jsonArray_medioContacto.push(objMedioContacto_aux)
			}

			var jsonArray_conctacto = [];
				var objEncargado = new Object();
					objEncargado.nombre = nombreContactoEmp;
					objEncargado.cargo = null;
					objEncargado.medio_contacto = jsonArray_medioContacto;
			jsonArray_conctacto.push(objEncargado);
			
		objEmpresa.contacto = jsonArray_conctacto;

		var jsonObject = JSON.stringify(objEmpresa);
		alert(jsonObject);
		guardarEmpresa( jsonObject ); 
		

		//var inputElement = document.getElementById("empresaLogo");
		//alert(inputElement + "\nNombre: " + inputElement.name + "\nPeso: " + inputElement.size + "\nTipo: " + inputElement.type);

	});


	$("#button_agregarContacto").on("click", function(){
		/*$("#formContacto").append(
							'<div class="form-group">' + 
								'<select class="form-control-inline" id="a' + (cont_contacto) + '" >' +
									'<option>Teléfono fijo</option>' +
									'<option>Teléfono celular</option>' +
									'<option>Correo electrónico</option>' +
									'<option>Linked in</option>' +
									'<option>Facebook</option>' +
									'<option>Twitter</option>' +
									'<option>Otro</option>' +
								'</select>' +
							'</div>' +
							'<div  class="form-group">' +
								'<input type="text" class="form-control" id="b' + (cont_contacto) + '" placeholder="conctacto">' +
							'</div>');*/

		$("#formContacto").append( addContacto() );
		cont_contacto++;
	});


	$("#button_agregarImagen").on("click", function(){
		$("#formEmpresa").append('<input type="file" id="empresaLogo" name="logoEmpresa	"' + 
			'accept="image/png, image/jpeg" /><br><br>' +
			'<img id="imgLogo" src="#" alt="logoEmpresa" width="300" height = "200" />');

		$("#empresaLogo").change(function() {
			getLogoEmpresaFromPC(this);
		});
	});

	$("#modalEmpresa").on('hidden.bs.modal', function(){
		cont_contacto=0;
	});	
})

function getLogoEmpresaFromPC( input ) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			$('#imgLogo').attr('src', e.target.result);
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function getLogoEmpresaBase64() {
	return $("#imgLogo").attr("src");
}

function addContacto() {
	var contacto = '<div class="form-group col-sm-4" id="div_1_' + (cont_contacto) + '" >' +
									'<select class="form-group" id="a' + (cont_contacto) + '">' +
										'<option>Teléfono fijo</option>' +
										'<option>Teléfono celular</option>' +
										'<option>Correo electrónico</option>' +
										'<option>Linked in</option>' +
										'<option>Facebook</option>' +
										'<option>Twitter</option>' +
										'<option>Otro</option>' +
									'</select></div><div class="form-group col-sm-1" id="div_2_' + (cont_contacto) + '"></div>' +
								'<div class="form-group col-sm-7" id="div_3_' + (cont_contacto) + '">' +
									'<div  class="form-inline">' +
										'<input type="text" class="form-control" id="b' + (cont_contacto) + '" placeholder="Contacto">'+
										'<button class="btn btn-secondary" form="" data-con="'+cont_contacto +'" onclick="deleteContacto( this );">-</button></div></div>';
	return contacto;										
}

function deleteContacto( btn ) {
	$("#div_1_" + $(btn).attr("data-con") ).remove();
	$("#div_2_" + $(btn).attr("data-con") ).remove();
	$("#div_3_" + $(btn).attr("data-con") ).remove();
}

function guardarEmpresa(jsonObject) {
	//alert(jsonObject);
	$("#modalEmpresa").modal('hide');

	$.ajax({
		method: "POST",
		contentType: "application/json",
		url: "webServices/RegistrarEmpresa.php",
		data: jsonObject

	}).done( function( msg ) {
		var myString = JSON.stringify( msg );
		//Muestra mensaje de écito
		Lobibox.notify( "success", {
			msg: msg.description
		});

		//CARGA TODAS LAS EMPRESAS DE NUEVO!. INTENTAR MEJORAR ESTO
		getEmpresas();
		getNumeroEmpresasRegistradas();

	}).fail( function( msg ) {
		var myString = JSON.stringify( msg );
		alert("ERROR: " + myString );
	});
}

