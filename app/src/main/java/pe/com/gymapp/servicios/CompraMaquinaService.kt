package pe.com.gymapp.servicios

import pe.com.gymapp.clases.CompraMaquina
import retrofit2.Call
import retrofit2.http.*

interface CompraMaquinaService {
    @GET("compraMaquina")
    fun MostrarComprarMaquina(): Call<List<CompraMaquina>?>?

    @POST("compraMaquina")
    fun RegistrarCompraMaquina(@Body r: CompraMaquina?): Call<CompraMaquina?>?

    @PUT("compraMaquina/{id}")
    fun ActualizarCompraMaquina(@Path("id") id:Long, @Body r: CompraMaquina?): Call<CompraMaquina?>?
}