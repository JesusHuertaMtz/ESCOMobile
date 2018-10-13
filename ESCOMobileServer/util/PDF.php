<?php
require_once( $_SERVER['DOCUMENT_ROOT']."ESCOMobileServer/library/FPDF/fpdf.php" );

class PDF extends FPDF {
	//Cabecera de página

	function Header() {
		//Logos
		/**$this->Image("escom.jpg", 170, 8, 30, 25, "JPG" );
		$this->Image("ipn.png", 10, 8, 25, 30, "PNG");* A5*/
		
		$this->Image( "../assets/image/escom.jpg", 240, 8, 35, 30, "JPG" );
		$this->Image( "../assets/image/ipn.png", 25, 8, 30, 35, "PNG");
		
		//Arial bold 15
		$this->SetFont( 'Arial', 'B', 12 );
		
		//Añade el título
		$titles = array("INSTITUTO POLITÉCNICO NACIONAL", "ESCUELA SUPERIOR DE CÓMPUTO", 
		"SUBDIRECCIÓN DE SERVICIOS EDUCATIVOS E INTEGRACIÓN SOCIAL", "DEPARTAMENTO DE EXTENSIÓN Y APOYOS EDUCATIVOS");
		$this->Ln(5);
		$this->SetTextColor( 0, 32, 96 );
		for( $i = 0; $i < count( $titles ); $i++ ) {
			$title = iconv('UTF-8', 'windows-1252', $titles[$i] ); //Acepta acentos
			$this->Cell( 0, 0, $title, 0, 0, "C" );
			$this->Ln( 5 ); //Salto de línea
		}
		
		$this->Ln( 5 ); //Salto de línea
		$title = iconv('UTF-8', 'windows-1252', "BOLETIN 18 AÑO 2018" ); //Acepta acentos
		$this->Cell( 65 ); // A5 == 30
		$this->Cell( 102, 0, $title, 0, 0, "L" );
		
		$this->Cell( 0, 0, "BOLSA DE TRABAJO", 0, 0, "L" );
		$this->Ln( 5 ); //Salto de línea
		
		$this->SetFont( 'Arial', '', 9 );
		$this->SetTextColor( 0, 0, 0 );
	}
   
	//Pie de página
	/*function Footer() {
		//Posición: a 1,5 cm del final
		$this->SetY( -15 );
		//Arial italic 8
		$this->SetFont('Arial','I',8);
		//Número de página
		$this->Cell( 0, 10, 'Page '.$this->PageNo().'/{nb}', 0, 0, 'C' );
	}*/
	
	//Tabla simple
	function TablaSimple( $header, $rows ) {
		//Ancho de columna
		//$width_cols = array( 10, 40, 30, 40, 30, 40 ); //A5
		$width_cols = array( 15, 50, 30, 80, 40, 60 );
		$i = 0;
		
		//Formato de cabecera
		//Colores, ancho de línea y fuente en negrita
		$this->SetFillColor( 0, 32, 96 );	
		$this->SetTextColor( 255 );
		$this->SetDrawColor( 0, 0, 0 );
		$this->SetLineWidth( .3 );
		$this->SetFont('','B');
		
		//Cabecera
		foreach( $header as $col )
			$this->Cell( $width_cols[$i++], 7, $col, 1, 0, "C", 1 );
		$this->Ln();
		
		//Restauración de colores y fuentes
		$this->SetFillColor( 224, 235, 255 );
		$this->SetTextColor(0);
		$this->SetFont('');
		
		//Obtiene las posiciones actuales de x e y
		$x = $this->GetX();
		$y = $this->GetY();
		$w = 0;
		$max= 5;
		
		for( $j = 0; $j < count( $rows ); $j++ ) {
			for( $i = 0; $i < count( $rows[$j] ); $i++ ) {
				$title = iconv('UTF-8', 'windows-1252', $rows[$j][$i] ); //Acepta acentos
				//$m = $this->wordWrap( $title, $width_cols[$i] );
				$this->MultiCell( $width_cols[$i], 5, $title, 0, "C" );
			
				//Para concatenar las multicell
				$w += $width_cols[$i];
				$this->SetXY( $x + $w, $y );
				
				/*
				if( $m > $max )
					$max = $m;
				*/
			}
			
			$w = 0;
			$this->SetXY( $w, $y + $max * 5 );
			$this->Ln();
			
			$x = $this->GetX();
			$y = $this->GetY();
			
			$this->Line( $x, $y, 285, $y ); // 20mm from each edge
		}
	}
}
 
?> 












