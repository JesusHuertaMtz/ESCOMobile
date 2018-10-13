package com.example.master.escomobile_alpha.util.model_name

import android.os.Build

class DeviceModel {

    companion object {
        fun getDeviceModelName() : String {
            var model = ""

            if( Build.VERSION.RELEASE < "6" ) {
                model = "Lollipop"

            } else if( Build.VERSION.RELEASE < "7" ) {
                model = "Marshmallow"

            } else if( Build.VERSION.RELEASE < "8" ) {
                model = "Nougat"

            } else if( Build.VERSION.RELEASE < "9" ) {
                model = "Oreo"
            }

            return model
        }
    }

}