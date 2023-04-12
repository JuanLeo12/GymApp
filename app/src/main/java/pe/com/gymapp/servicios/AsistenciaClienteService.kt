package pe.com.gymapp.servicios

import pe.com.gymapp.clases.AsistenciaCliente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AsistenciaClienteService {

    @GET("asistenciaCliente")
    fun MostrarAsistenciaCliente(): Call<List<AsistenciaCliente>?>?

    @POST("asistenciaCliente")
    fun RegistrarAsistenciaCliente(@Body ac: AsistenciaCliente?): Call<AsistenciaCliente?>?

}