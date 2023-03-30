package pe.com.gymapp.servicios

import pe.com.gymapp.clases.TipoPago
import retrofit2.Call
import retrofit2.http.*

interface TipoPagoService {

    @GET("tipopago")
    fun MostrarTipoPago(): Call<List<TipoPago>?>?

    @GET("tipopago/custom")
    fun MostrarTipoPagoPersonalizado(): Call<List<TipoPago>?>?

    @POST("tipopago")
    fun RegistrarTipoPago(@Body tp: TipoPago?): Call<TipoPago?>?

    @PUT("tipopago/{id}")
    fun ActualizarTipoPago(@Path("id") id:Long, @Body r: TipoPago?): Call<TipoPago?>?

    @DELETE("tipopago/{id}")
    fun EliminarTipoPago(@Path("id") id:Long): Call<TipoPago?>?

}