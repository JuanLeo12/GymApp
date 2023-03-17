package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Cliente
import retrofit2.Call
import retrofit2.http.*

interface ClienteService {
    @GET("cliente")
    fun MostrarCliente(): Call<List<Cliente>?>?

    @GET("cliente/custom")
    fun MostrarClientePersonalizado(): Call<List<Cliente>?>?

    @POST("cliente")
    fun RegistrarCliente(@Body r: Cliente?): Call<Cliente?>?

    @PUT("cliente/{id}")
    fun ActualizarCliente(@Path("id") id:Long, @Body r: Cliente?): Call<Cliente?>?

    @DELETE("cliente/{id}")
    fun EliminarCliente(@Path("id") id:Long): Call<Cliente?>?
}