package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Producto
import retrofit2.Call
import retrofit2.http.*

interface ProductoService {
    @GET("producto")
    fun MostrarProducto(): Call<List<Producto>?>?

    @GET("producto/custom")
    fun MostrarProductoPersonalizado(): Call<List<Producto>?>?

    @POST("producto")
    fun RegistrarProducto(@Body p: Producto?): Call<Producto?>?

    @PUT("producto/{id}")
    fun ActualizarProducto(@Path("id") id:Long, @Body p: Producto?): Call<Producto?>?

    @PATCH("producto/{id}")
    fun EnableProducto(@Path("id") id:Long): Call<Producto?>?

    @DELETE("producto/{id}")
    fun EliminarProducto(@Path("id") id:Long): Call<Producto?>?
}