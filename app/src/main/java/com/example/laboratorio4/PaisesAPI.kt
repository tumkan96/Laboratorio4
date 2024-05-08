package com.example.laboratorio4
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
interface PaisesAPI {
    @GET("/blogdis413/wp-json/paises/v1/listar")
    fun listar(): Call<List<Paises>>

    @POST("/blogdis413/wp-json/paises/v1/agregar")
    fun agregar(@Body paises: Paises): Call<Paises>

    @PUT("/blogdis413/wp-json/paises/v1/modificar")
    fun modificar(@Body paises: Paises): Call<Paises>

    @PUT("/blogdis413/wp-json/paises/v1/eliminar")
    fun eliminar(@Body paises: Paises): Call<Paises>
}