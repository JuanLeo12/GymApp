package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Mantenimiento
import retrofit2.Call
import retrofit2.http.*

interface MantenimientoService {

    @GET("mantenimiento")
    fun MostrarMantenimiento(): Call<List<Mantenimiento>?>?

    @GET("mantenimiento/custom")
    fun MostrarMantenimientoPersonalizado(): Call<List<Mantenimiento>?>?

    @POST("mantenimiento")
    fun RegistrarMantenimiento(@Body m: Mantenimiento?): Call<Mantenimiento?>?

    @PUT("mantenimiento/{id}")
    fun ActualizarMantenimiento(@Path("id") id:Long, @Body m: Mantenimiento?): Call<Mantenimiento?>?

    @DELETE("mantenimiento/{id}")
    fun EliminarMantenimiento(@Path("id") id:Long): Call<Mantenimiento?>?
}