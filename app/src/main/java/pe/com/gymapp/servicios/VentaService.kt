package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Venta
import retrofit2.Call
import retrofit2.http.*

interface VentaService {

    @GET("venta")
    fun MostrarVenta(): Call<List<Venta>?>?

    @POST("venta")
    fun RegistrarVenta(@Body v: Venta?): Call<Venta?>?

    @PUT("venta/{id}")
    fun ActualizarVenta(@Path("id") id:Long, @Body v: Venta?): Call<Venta?>?

}