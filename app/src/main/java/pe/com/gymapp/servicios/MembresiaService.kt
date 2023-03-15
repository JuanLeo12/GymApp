package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Membresia
import retrofit2.Call
import retrofit2.http.*

interface MembresiaService {
    @GET("membresia")
    fun MostrarMembresia(): Call<List<Membresia>?>?

    @GET("membresia/custom")
    fun MostrarMembresiaPersonalizado(): Call<List<Membresia>?>?

    @POST("membresia")
    fun RegistrarMembresia(@Body m: Membresia?): Call<Membresia?>?

    @PUT("membresia/{id}")
    fun ActualizarMembresia(@Path("id") id:Long, @Body m: Membresia?): Call<Membresia?>?

    @DELETE("membresia/{id}")
    fun ActualizarMembresia(@Path("id") id:Long): Call<Membresia?>?
}