package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Genero
import retrofit2.Call
import retrofit2.http.*

interface GeneroService {

    @GET("genero")
    fun MostrarGenero(): Call<List<Genero>?>?

    @GET("genero/custom")
    fun MostrarGeneroPersonalizado(): Call<List<Genero>?>?

    @POST("genero")
    fun RegistrarGenero(@Body c: Genero?): Call<Genero?>?

    @PUT("genero/{id}")
    fun ActualizarGenero(@Path("id") id:Long, @Body g: Genero?): Call<Genero?>?

    @DELETE("genero/{id}")
    fun EliminarGenero(@Path("id") id:Long): Call<Genero?>?
}