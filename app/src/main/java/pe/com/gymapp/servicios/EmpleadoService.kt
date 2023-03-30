package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Empleado
import retrofit2.Call
import retrofit2.http.*

interface EmpleadoService {
    @GET("empleado")
    fun MostrarEmpleado(): Call<List<Empleado>?>?

    @GET("empleado/custom")
    fun MostrarEmpleadoPersonalizado(): Call<List<Empleado>?>?

    @POST("empleado")
    fun RegistrarEmpleado(@Body e: Empleado?): Call<Empleado?>?

    @PUT("empleado/{id}")
    fun ActualizarEmpleado(@Path("id") id:Long, @Body r: Empleado?): Call<Empleado?>?

    @DELETE("empleado/{id}")
    fun EliminarEmpleado(@Path("id") id:Long): Call<Empleado?>?
}