package pe.com.gymapp.servicios

import pe.com.gymapp.clases.SeguimientoFisico
import retrofit2.Call
import retrofit2.http.*

interface SeguimientoFisicoService {

    @GET("seguimientoFisico")
    fun MostrarSeguimientoFisico(): Call<List<SeguimientoFisico>?>?

    @GET("seguimientoFisico/custom")
    fun MostrarSeguimientoFisicoPersonalizado(): Call<List<SeguimientoFisico>?>?

    @POST("seguimientoFisico")
    fun RegistrarSeguimientoFisico(@Body sf: SeguimientoFisico?): Call<SeguimientoFisico?>?

    @PUT("seguimientoFisico/{id}")
    fun ActualizarSeguimientoFisico(@Path("id") id:Long, @Body sf: SeguimientoFisico?): Call<SeguimientoFisico?>?

    @DELETE("seguimientoFisico/{id}")
    fun EliminarSeguimientoFisico(@Path("id") id:Long): Call<SeguimientoFisico?>?
}