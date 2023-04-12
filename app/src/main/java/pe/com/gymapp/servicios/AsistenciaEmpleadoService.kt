package pe.com.gymapp.servicios

import pe.com.gymapp.clases.AsistenciaEmpleado
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AsistenciaEmpleadoService {

    @GET("asistenciaEmpleado")
    fun MostrarAsistenciaEmpleado(): Call<List<AsistenciaEmpleado>?>?

    @POST("asistenciaEmpleado")
    fun RegistrarAsistenciaEmpleado(@Body ae: AsistenciaEmpleado?): Call<AsistenciaEmpleado?>?

}