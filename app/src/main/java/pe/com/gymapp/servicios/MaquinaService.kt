package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Maquina
import retrofit2.Call
import retrofit2.http.*

interface MaquinaService {
    @GET("maquina")
    fun MostrarMaquina(): Call<List<Maquina>?>?

    @GET("maquina/custom")
    fun MostrarMaquinaPersonalizado(): Call<List<Maquina>?>?

    @POST("maquina")
    fun RegistrarMaquina(@Body m: Maquina?): Call<Maquina?>?

    @PUT("maquina/{id}")
    fun ActualizarMaquina(@Path("id") id:Long, @Body m: Maquina?): Call<Maquina?>?

    @DELETE("maquina/{id}")
    fun EliminarMaquina(@Path("id") id:Long): Call<Maquina?>?
}