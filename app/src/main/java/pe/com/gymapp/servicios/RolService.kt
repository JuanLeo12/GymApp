package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Rol
import retrofit2.Call
import retrofit2.http.*

interface RolService {
    @GET("rol")
    fun MostrarRol(): Call<List<Rol>?>?

    @GET("rol/custom")
    fun MostrarRolPersonalizado(): Call<List<Rol>?>?

    @POST("rol")
    fun RegistrarRol(@Body r: Rol?): Call<Rol?>?

    @PUT("rol/{id}")
    fun ActualizarRol(@Path("id") id:Long, @Body r: Rol?): Call<Rol?>?

    @DELETE("rol/{id}")
    fun EliminarRol(@Path("id") id:Long): Call<Rol?>?
}