package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Incidencia
import retrofit2.Call
import retrofit2.http.*

interface IncidenciaService {
    @GET("incidencia")
    fun MostrarIncidencia(): Call<List<Incidencia>?>?

    @POST("incidencia")
    fun RegistrarIncidencia(@Body e: Incidencia?): Call<Incidencia?>?

    @PUT("incidencia/{id}")
    fun ActualizarIncidencia(@Path("id") id:Long, @Body r: Incidencia?): Call<Incidencia?>?

}