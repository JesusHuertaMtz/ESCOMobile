package com.example.master.escomobile_alpha.util.request

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.master.escomobile_alpha.util.JSONParser
import com.example.master.maps.modelo.pojo.Escuela
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

/**
 * Created by Master Chief on 20/02/2018.
 */
class RequestManager /*: AsyncTask<String, String, String>()*/ {
    private val CONTENT_TYPE_APP_JSON = "application/json"
    companion object {
        var BASE_URL = "http://192.168.100.31/ESCOMobileServer/webServices"
        val URL_INICIAR_SESION = "${BASE_URL}/IniciarSesion.php"
        val URL_REGISTRAR_USUARIO = "${BASE_URL}/RegistrarNuevoUsuario.php"
        val URL_OFERTAS_VIGENTES = "${BASE_URL}/ConsultarBolsaTrabajo.php"
        val URL_SQL_SERVER = "${BASE_URL}/ConsultarEscom.php"
    }
    /**
     * Usa la API Volley
     * */
    fun sendSimpleRequest( context: Context, url: String, completionHandler: (response: Escuela?) -> Unit ) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue( context )
        var latlngsEscuela : Escuela? = null

        // Request a string response from the provided URL.
        val stringRequest = StringRequest( Request.Method.GET, url,
                Response.Listener<String> { response ->
                    //Toast.makeText( context, "Response is: " + response.toString(), Toast.LENGTH_LONG ).show()
                    latlngsEscuela = JSONParser().parseJSON( response.toString() )
                    //drawPolygon( latlngs )
                    completionHandler( latlngsEscuela )
                },

                Response.ErrorListener {
                    //Toast.makeText( context, "That didn't work!", Toast.LENGTH_SHORT ).show()
                    completionHandler( latlngsEscuela )
                }
        )
        // Add the request to the RequestQueue.
        queue.add( stringRequest )
    }

    fun sendSimpleRequest2( context: Context, url: String, completionHandler: (response: Escuela?) -> Unit ) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue( context )
        var latlngsEscuela : Escuela? = null

        // Request a string response from the provided URL.
        val stringRequest = StringRequest( Request.Method.GET, url,
                Response.Listener<String> { response ->
                    //Toast.makeText( context, "Response is: " + response.toString(), Toast.LENGTH_LONG ).show()
                    latlngsEscuela = JSONParser().parseJSONSalones( response.toString() )
                    //drawPolygon( latlngs )
                    completionHandler( latlngsEscuela )
                },

                Response.ErrorListener {
                    //Toast.makeText( context, "That didn't work!", Toast.LENGTH_SHORT ).show()
                    completionHandler( latlngsEscuela )
                }
        )
        // Add the request to the RequestQueue.
        queue.add( stringRequest )
    }

    /*================================= APIs AsyncHttp =================================*/
    fun postRequest( url: String, params: HashMap<String, Any>, completionHandler: ( response: JSONObject ) -> Unit ) {
        val clientRequest = AsyncHttpClient()
        val jsonParams = makeRequestParametersFrom( params )
        println("JSON ${jsonParams}")

        clientRequest.post( url, jsonParams, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val response = responseBody?.toString(Charsets.UTF_8)
                val json = JSONObject( response )

                println("RESPONSE ${json.opt("code")} ${json.getJSONObject("data")} ${json.opt("description")}")
                completionHandler( json )
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val response = responseBody?.toString( Charsets.UTF_8 )

                var json = JSONObject().optJSONObject( response )

                if( json == null ) {
                    json = JSONObject()
                    json.accumulate("code", "500" )
                    json.accumulate("description", "Error en el servidor")
                }

                println("ERROR ${ response }")
                completionHandler( json )
            }
        })
    }

    private fun makeJSONObjectFrom( params: HashMap<String, Any> ) : JSONObject {
        val jsonObject = JSONObject()

        for( param in params ) {
            jsonObject.put( param.key, param.value )
        }

        return jsonObject
    }

    private fun makeRequestParametersFrom( params: HashMap<String, Any> ) : RequestParams {
        val requestParams = RequestParams()
        requestParams.setUseJsonStreamer( true )

        for( param in params ) {
            requestParams.put( param.key, param.value )
        }

        return requestParams
    }

}



