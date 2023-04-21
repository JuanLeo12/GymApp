package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Cliente
import retrofit2.Call
import retrofit2.http.*

interface ClienteService {
    @GET("cliente")
    fun MostrarCliente(): Call<List<Cliente>?>?

    @GET("cliente/custom")
    fun MostrarClientePersonalizado(): Call<List<Cliente>?>?

    @GET("cliente/telf")
    fun BuscarXTelefonoCliente(@Body cl: Cliente?): Call<Cliente?>?

    @GET("cliente/nombre")
    fun BuscarXNombrePaternoCliente(@Body cl: Cliente?): Call<Cliente?>?

    @GET("cliente/apellidop")
    fun BuscarXApellidoPCliente(@Body cl: Cliente?): Call<Cliente?>?

    @GET("cliente/apellidom")
    fun BuscarXApellidoMCliente(@Body cl: Cliente?): Call<Cliente?>?

    @POST("cliente")
    fun RegistrarCliente(@Body c: Cliente?): Call<Cliente?>?

    @PUT("cliente/{id}")
    fun ActualizarCliente(@Path("id") id:Long, @Body r: Cliente?): Call<Cliente?>?

    @PATCH("cliente/{id}")
    fun EnableCliente(@Path("id") id:Long): Call<Cliente?>?

    @DELETE("cliente/{id}")
    fun EliminarCliente(@Path("id") id:Long): Call<Cliente?>?
}