<?php
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/conf/configWebService.php' );
require_once( $_SERVER['DOCUMENT_ROOT'].'ESCOMobileServer/FacebookAPI.php' );
require_once( "PDF.php" );

class MakePDF {
	
	/**
	* Crea los PDF
	* @param $ofertas Arreglo de ofertas laborales que formarán el PDF.
	*/
	public function createPDF( $ofertas, $numOfertasPorPagina ) {
		$pdf = new PDF(); //Crea el PDF
		$pdf->AliasNbPages();
		$countPage = 0;
		$ofertasPorPagina = array();

		//Comeiza a obtener las ofertas
		foreach( $ofertas as $oferta ) {
			if( $countPage < $numOfertasPorPagina ) {
				//Añadimos la oferta laboral a la actual páginay
				$ofertaFormatter = array( $oferta["no"], $oferta["nombre"], $oferta["horario"], 
									$oferta["vacante"], $oferta["sueldo"], $oferta["contacto"] );
				array_push( $ofertasPorPagina, $ofertaFormatter );
				$countPage++;

			} else {
				//Creamos una nueva página del PDF
				$this->addPageToPDF( $pdf, $ofertasPorPagina );
				//Limpiamos el arreglo
				unset( $ofertasPorPagina );
				$ofertasPorPagina = array();
				$countPage = 0;
			}
		}

		//Añadimos la ofertas restantes en una página nueva
		$this->addPageToPDF( $pdf, $ofertasPorPagina );

		//Generamos el PDF en la carpeta temporal.
		//$pdf->Output();
		$pdf->Output("F", "/tmp/boletin.pdf");

		$this->makeImageFromPDF( "/tmp/boletin.pdf", "/tmp/boletin" );
	}

	private function addPageToPDF( $pdf, $ofertas ) {
		//Títulos de las columnas
		$header = array( "No.", "NOMBRE DE LA EMPRESA", "HORARIO", "VACANTE", "SUELDO $", "CONTACTO" );

		var_dump( $ofertas );
		
		if( count( $ofertas ) > 0 ) {
			$pdf->AddPage("L", "A4");
			$pdf->SetY(50);
			//Añadimos las ofertas a la página
			$pdf->TablaSimple( $header, $ofertas );
		}
	}

	private function makeImageFromPDF( $pathPDF, $savePath ) {
		//Convertimos el pdf a imágenes
		exec("pdftoppm ".$pathPDF." ".$savePath." -png");
	}

	/**
	Método de prueba para utilizar la clase PDF encargada de crear el PDF
	*/
	public function createPDF_2() {
		$pdf = new PDF();
		//Títulos de las columnas
		$header = array( "No.", "NOMBRE DE LA EMPRESA", "HORARIO", "VACANTE", "SUELDO $", "CONTACTO" );
		$pdf->AliasNbPages();

		//Primera página
		$pdf->AddPage("L", "A4");
		$pdf->SetY(50);




		$row = array("224","Secretaría de Hacienda y Crédito Público – Banco Interamericano de Desarrollo (BID)",
				"10:00 a 20:00 hrs.","Analista de Sistemas (una vacante) Conocimiento en casos de uso, diagramas de flujo, levantamiento de requerimientos, elaboración de prototipos y en sistemas operacionales o transaccionales.",
				" $27,000 Bruto (Según aptitudes)","Marco Antonio Chávez Leal Tel:36881212 Correo: marco_chavezl@hacienda.gob.mx");
		$row2 = array("230", "WORKSLOG", "9:00 a 18:00 hrs.",
				"Analista Programador .Net y SQL Server (tres vacantes) Conocimientos necesarios: Programación en .NET y SQLSERVER, conocimientos deseables: Sector Financiero (Casa de Bolsa, Operadoras de Fondos, o Asesores Patrimoniales o áreas de Tesorería o mercados de un Banco.", "$15,000 a $25 000", "Maya Herrera Tel: 5652 - 9901 Correo: maya.h@workslog.mx" );
		$row3 = array("225", "Secretaría de Hacienda y Crédito Público – Banco Interamericano de Desarrollo (BID)",
				"10:00 a 20:00 hrs.", "Programador Java Jr. (tres vacantes) Conocimientos en Java, Javascript, JSP, Spring, SQL – Oracle, Sistemas operacionales o transaccionales, control de versiones (Subversion) y desarrollo en 3 capas.", " $27,000 Bruto (Según aptitudes)",
				"Marco Antonio Chávez Leal Tel:36881212 Correo: marco_chavezl@hacienda.gob.mx" );
				
		$rows = array( $row, $row2, $row3, $row2 );




		$pdf->TablaSimple( $header, $rows );

		//Segunda página.
		$pdf->AddPage("L", "A4");
		$pdf->SetY(50);

		$rows = array( $row, $row2 );
		$pdf->TablaSimple( $header, $rows );

		//Muestra el PDF
		$pdf->Output();
		$pdf->Output("F", "/tmp/boletin.pdf");

		$pdf = "/tmp/boletin.pdf";
		$save = "/tmp/boletin";

		exec("pdftoppm ".$pdf." ".$save." -png");
	}

}

?>