package com.example.laboratorio4

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    lateinit var retrofit:Retrofit
    lateinit var paisesAPI:PaisesAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonText = findViewById<TextView>(R.id.jsonText)
        retrofit = Retrofit.Builder()
            .baseUrl("https://www.uajms.edu.bo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        paisesAPI = retrofit.create(PaisesAPI::class.java)
        val listarCall = paisesAPI.listar()
        listarCall.enqueue(object: Callback<List<Paises>>{
            override fun onResponse(call: Call<List<Paises>>, response: Response<List<Paises>>) {
                if (response.isSuccessful) {
                    val paisesList: List<Paises>? = response.body()
                    val paisesIterator: Iterator<Paises>? = paisesList?.iterator()
                    var paises = ""
                    while (paisesIterator?.hasNext() == true) {
                        val pais: Paises? = paisesIterator.next()
                        paises += "${pais?.nombre}\n"
                    }
                    jsonText.text = paises
                }
                Log.i("TEOTEO","conexion exitosa")
            }

            override fun onFailure(call: Call<List<Paises>>, t: Throwable) {
                Log.i("TEOTEO","Fallo en la conexion"+t.message+"\n"+t.stackTrace)
                Log.d("TEOTEO", Log.getStackTraceString(t));
                jsonText.text = Log.getStackTraceString(t)

            }

        }

           )

        agregar()
    }
    fun agregar(){
        //agregar
        val paisesAPI: PaisesAPI = retrofit.create(PaisesAPI::class.java)
        val paises = Paises(2,"ITALIA 2000","A")
        val callAdd:Call<Paises> = paisesAPI.agregar(paises)
        callAdd.enqueue(object : Callback<Paises>{
            override fun onResponse(p0: Call<Paises>, p1: Response<Paises>) {
                Log.i("TEOTEO","Agregado correctamente")
            }
            override fun onFailure(p0: Call<Paises>, p1: Throwable) {
                Log.i("TEOTEO","Se produjo un error al agregar")
            }
        })
    }

    fun modificar(p:Paises){
        //modificar
        val callAdd:Call<Paises> = paisesAPI.modificar(p)
        callAdd.enqueue(object : Callback<Paises>{
            override fun onResponse(p0: Call<Paises>, p1: Response<Paises>) {
                if(p1.isSuccessful){
                    Log.i("TEOTEO","Actualizado correctamente")
                }else{
                    Log.i("TEOTEO","no actualizado "+p1.body())
                }
            }
            override fun onFailure(p0: Call<Paises>, p1: Throwable) {
                Log.i("TEOTEO","Se produjo un error en la conexión al API")
            }
        })
}}