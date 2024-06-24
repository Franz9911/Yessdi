package com.example.labretrofitktejemplo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

//aqui pueden entrar todas las llamadas a la api rest
//debemos crear clas data para todas las instancias
//pnatallas y menus

interface ClientAPI {
    //@GET("/blogdis413/wp-json/paises/v1/listar")
    @GET("http://192.168.1.7:3000/users")
    fun getPaises2(): Call<List<UsersLibreria>>

    @GET("http://192.168.1.7:3000/users/contrasenha/{contrasenha}")
    fun getUsuarioContrasenha(@Path("contrasenha")contrasenha: String): Call<UsersLibreria>

    @POST("http://192.168.1.7:3000/users/agregar")
    fun agregar(@Body usersLibreria: UsersLibreria?): Call<UsersLibreria>

    @PUT("/blogdis413/wp-json/paises/v1/modificar")
    fun modificar(@Body paises: Paises): Call<Paises>

    @PUT("/blogdis413/wp-json/paises/v1/eliminar")
    fun eliminar(@Body paises: Paises): Call<Paises>

    @DELETE("http://192.168.1.7:3000/users/eliminar/{id}")
    fun eliminarUser(@Path("id") id: Int): Call<Void>

    //libros
    @GET("http://192.168.1.7:3000/libros")
    fun getLibro(): Call<List<Libros>>

    @GET("http://192.168.1.7:3000/reservas/usuario/{idUsuario}")
    fun getReservasUsuario(@Path("idUsuario")idUsuario:String): Call<List<Reservas>>

    @DELETE("http://192.168.1.7:3000/reserva/eliminar/{idReserva}")
    fun eliminarReserva(@Path("idReserva") idReserva: String): Call<Void>

    @POST("http://192.168.1.7:3000/users/loguin")
    fun loguin(@Body usersLibreria: UsersLibreria?): Call<UsersLibreria>

    @POST("http://192.168.1.7:3000/reserva/agregar")
    fun nuevaReserva(@Body reservas: Reservas?): Call<Reservas>
}