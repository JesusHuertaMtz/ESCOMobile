package com.example.master.escomobile_alpha.modelo.entidad

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class Asignatura(): Parcelable {
    var nombre : String = ""
    var salon : Salon? = null
    var grupo: String = ""
	var academia : String = ""
	var laboratorio : String = ""
	var horario = mutableMapOf( "Lunes" to "", "Martes" to "", "Miércoles" to "", "Jueves" to "", "Viernes" to "" )

	constructor(parcel: Parcel) : this() {
		nombre = parcel.readString()
		salon = parcel.readParcelable(Salon::class.java.classLoader)
		grupo = parcel.readString()
		academia = parcel.readString()
		laboratorio = parcel.readString()
		horario = mutableMapOf<String, String>().apply {
			parcel.readMap( this, MutableMap::class.java.classLoader )
		}

				/*
				HashMap<String, String>().apply {
			parcel.readHashMap( HashMap::class.java.classLoader )
		}*/
	}

	constructor( jsonObject: JSONObject ): this() {
        this.grupo = jsonObject.optString("grupo")
        this.nombre = jsonObject.optString("asignatura")
        this.salon = getSalonID( jsonObject )
		this.laboratorio = jsonObject.optString("laboratorio" )
	    this.academia = jsonObject.optString( "academia" )
		getHorario( jsonObject )
    }
    
    private fun getSalonID( jsonObject: JSONObject ): Salon? {
	    val jsonSalon = jsonObject.optJSONObject("salon" )
	    var salon : Salon? = null

	    jsonSalon?.let { json ->
		    salon = Salon( jsonSalon )
	    }

	    return salon
    }

	private fun getHorario( jsonObject: JSONObject ) {
		val jsonHorario = jsonObject.optJSONObject("horario" )

		if( jsonHorario != null ) {
			println("HORARIO ${jsonHorario}")

			for( key in horario.keys ) {
				horario[key] = jsonHorario.optString( key.substring( 0, 2 ) )
				println("DIA ABREV ${ key.substring( 0, 2 ) } ---> ${horario[key]}")
			}
		}
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString( nombre )
		parcel.writeParcelable( salon, flags )
		parcel.writeString( grupo )
		parcel.writeString( academia )
		parcel.writeString( laboratorio )
		parcel.writeMap( horario )
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Asignatura> {
		override fun createFromParcel(parcel: Parcel): Asignatura {
			return Asignatura(parcel)
		}

		override fun newArray(size: Int): Array<Asignatura?> {
			return arrayOfNulls(size)
		}
	}
}

/*
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