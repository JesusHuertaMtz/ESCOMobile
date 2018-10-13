package com.example.master.escomobile_alpha.modelo.entidad

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class Profesor() : Parcelable {
    var nombre = ""
    var asignaturas = mutableListOf<Asignatura>()
    var cubiculo : Salon? = null
    var foto : Bitmap? = null

	constructor( jsonObject: JSONObject ): this() {
		this.nombre = jsonObject.optString("nombre_prof")
		getAsignaturas( jsonObject )
		this.cubiculo = getCubiculoId( jsonObject )
		//this.foto = jsonObject.opt("foto")
	}

	private fun getAsignaturas( jsonObject: JSONObject ) {
		val jsonAsignaturas = jsonObject.optJSONArray( "asignaturas" )

		jsonAsignaturas?.let { asignaturas ->
			for( index in 0..asignaturas.length() - 1 ) {
				this.asignaturas.add( Asignatura( asignaturas.optJSONObject( index ) ) )
			}
		}
	}

	private fun getCubiculoId( jsonObject: JSONObject ): Salon? {
		var salon : Salon? = null
		val cubiculo = jsonObject.optJSONObject("cubiculo" )

		if( cubiculo != null ) {
			salon = Salon( cubiculo )
		}

		return salon
	}

	///Métodos Parcelable
	constructor(parcel: Parcel) : this() {
		nombre = parcel.readString()
		cubiculo = parcel.readParcelable( Salon::class.java.classLoader )
		asignaturas = mutableListOf<Asignatura>().apply {
			parcel.readList( this, Asignatura::class.java.classLoader )
		}
		foto = parcel.readParcelable(Bitmap::class.java.classLoader)
	}
	

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString( nombre )
		parcel.writeParcelable(cubiculo, flags)
		parcel.writeList( asignaturas )
		parcel.writeParcelable( foto, flags )
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Profesor> {
		override fun createFromParcel(parcel: Parcel): Profesor {
			return Profesor(parcel)
		}

		override fun newArray(size: Int): Array<Profesor?> {
			return arrayOfNulls(size)
		}
	}
}
/*
{
	"nombre_prof": "Jiménez Galán Yasmín Ivette",
	"foto": "Base64"
}
O

{
    "nombre_prof": "Jiménez Galán Yasmín Ivette",
    "grupo": "5CV2",
    "asignatura": "Trabajo Terminal II",
    "salon": {
        "id": 1205",
        "coordenadas": {
            "lat": "-19.08292",
            "lng": "9.0182"
        }
    }
    "cubiculo": {
        "id": "",
        "academia": "ISW",
        "coordenadas": {
            "lat": "-19.08292",
            "lng": "9.0182"
        }
    }
}
*/