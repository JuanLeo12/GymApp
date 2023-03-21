package pe.com.gymapp.servicios

import pe.com.gymapp.clases.CompraProducto
import retrofit2.Call
import retrofit2.http.*

interface CompraProductoService {
    @GET("compraProducto")
    fun MostrarCompraProducto(): Call<List<CompraProducto>?>?

    @POST("compraProducto")
    fun RegistrarCompraProducto(@Body cp: CompraProducto?): Call<CompraProducto?>?

    @PUT("compraProducto/{id}")
    fun ActualizarProducto(@Path("id") id:Long, @Body cp: CompraProducto?): Call<CompraProducto?>?
}