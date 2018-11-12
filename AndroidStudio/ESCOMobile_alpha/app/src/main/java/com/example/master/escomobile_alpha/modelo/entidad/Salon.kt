package com.example.master.escomobile_alpha.modelo.entidad

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class Salon(): Parcelable {
    var id :String = ""
    var piso :Int = 0
    var coordenadas = mutableListOf<Double>()
	var academia : String = ""

    constructor( jsonObject: JSONObject ): this() {
        this.id = jsonObject.optString("id" )
        this.piso = jsonObject.optInt("piso" )
	    this.academia = jsonObject.optString("academia" )
        val jsonCooredanas = jsonObject.optJSONObject("coordenadas" )
	    if( jsonCooredanas != null ) {
		    this.coordenadas = mutableListOf( jsonCooredanas.optDouble("lat"), jsonCooredanas.optDouble("lng") )
	    }
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        piso = parcel.readInt()
	    academia = parcel.readString()
	    coordenadas = mutableListOf<Double>().apply {
		    parcel.readList( this, Double::class.java.classLoader )
	    }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(piso)
	    parcel.writeString(academia)
	    parcel.writeList(coordenadas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Salon> {
        override fun createFromParcel(parcel: Parcel): Salon {
            return Salon(parcel)
        }

        override fun newArray(size: Int): Array<Salon?> {
            return arrayOfNulls(size)
        }
    }
}