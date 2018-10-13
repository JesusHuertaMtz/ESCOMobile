function getData() {
	var empresa = document.getElementById("name_empresa").value;
	var vacante = document.getElementById("vacante").value;

	console.log( empresa + "" + vacante);
}

function hora(dato){
		if(dato=="Definido"){
				document.getElementById("Dom").style.display = "block";
				document.getElementById("Lun").style.display = "block";
				document.getElementById("Mar").style.display = "block";
				document.getElementById("Mie").style.display = "block";
				document.getElementById("Jue").style.display = "block";
				document.getElementById("Vie").style.display = "block";
				document.getElementById("Sab").style.display = "block";
		}
		if(dato=="Medio Tiempo"){
			document.getElementById("Dom").style.display = "block";
			document.getElementById("Lun").style.display = "block";
			document.getElementById("Mar").style.display = "block";
			document.getElementById("Mie").style.display = "block";
			document.getElementById("Jue").style.display = "block";
			document.getElementById("Vie").style.display = "block";
			document.getElementById("Sab").style.display = "block";
		}
		if(dato=="Tiempo Completo"){
			document.getElementById("Dom").style.display = "block";
			document.getElementById("Lun").style.display = "block";
			document.getElementById("Mar").style.display = "block";
			document.getElementById("Mie").style.display = "block";
			document.getElementById("Jue").style.display = "block";
			document.getElementById("Vie").style.display = "block";
			document.getElementById("Sab").style.display = "block";
		}
		if(dato=="Sin Definir"){
			document.getElementById("Dom").style.display = "none";
			document.getElementById("Lun").style.display = "none";
			document.getElementById("Mar").style.display = "none";
			document.getElementById("Mie").style.display = "none";
			document.getElementById("Jue").style.display = "none";
			document.getElementById("Vie").style.display = "none";
			document.getElementById("Sab").style.display = "none";
		}
}


function mostrar(dato){
		if(dato=="Definido"){
				document.getElementById("rango").style.display = "block";
				document.getElementById("rango2").style.display = "block";
				document.getElementById("fijo").style.display = "none";
		}
		if(dato=="2"){
				document.getElementById("rango").style.display = "none";
				document.getElementById("fijo").style.display = "none";
				document.getElementById("rango2").style.display = "none";
		}
		if(dato=="3"){
				document.getElementById("rango").style.display = "none";
				document.getElementById("fijo").style.display = "block";
				document.getElementById("rango2").style.display = "none";
		}
}


function visualizar(dato){
		if(dato=="option3"){
				document.getElementById("horario1").style.display = "block";
				document.getElementById("horario2").style.display = "block";
				document.getElementById("inlineCheckbox1").style.display = "block";
				document.getElementById("inlineCheckbox2").style.display = "block";
				document.getElementById("inlineCheckbox3").style.display = "block";
				document.getElementById("inlineCheckbox4").style.display = "block";
				document.getElementById("inlineCheckbox5").style.display = "block";
		}
		if(dato=="option4"){
				document.getElementById("horario1").style.display = "none";
				document.getElementById("horario2").style.display = "none";
				document.getElementById("inlineCheckbox1").style.display = "none";
				document.getElementById("inlineCheckbox2").style.display = "none";
				document.getElementById("inlineCheckbox3").style.display = "none";
				document.getElementById("inlineCheckbox4").style.display = "none";
				document.getElementById("inlineCheckbox5").style.display = "none";
		}

}


/*function habilitar(value)

{

	if(value=="1")

	{

		// habilitamos

		document.getElementById("rango2").disabled=false;
		document.getElementById("rango").disabled=false;

	}else {

		// deshabilitamos

		document.getElementById("rango2").disabled=true;
		document.getElementById("rango").disabled=true;

	}

}*/
