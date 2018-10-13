package com.example.master.escomobile_alpha.util

import android.content.Context
import java.io.*

class CacheManager {
    companion object {
        val CACHE_EMPRESA = "empresas"
    }

    fun writeObjectInCacheDir( context: Context, myObject: Any, cacheName : String ) {
        val cacheDir = File( context.cacheDir, cacheName )
        println("PATH CACHE ${ cacheDir.path }")

        try {
            val fos = FileOutputStream( cacheDir )
            val oos = ObjectOutputStream( fos )

            oos.writeObject( myObject )

            fos.close()
            oos.close()

        } catch( ioe: IOException) {
            println("ERROR WRITE ${ioe.localizedMessage}")
        }
    }

    fun readObjectFromCacheDir( context: Context, cacheName: String ) : Any? {
        val cacheDir = context.cacheDir.path + File.separatorChar + cacheName

        try {
            val fis = FileInputStream( cacheDir )
            val ois = ObjectInputStream( fis )

            val myObject = ois.readObject()

            return myObject

        } catch( ioe: IOException) {
            println("ERROR READ ${ioe.localizedMessage}")

            return null
        }
    }

}