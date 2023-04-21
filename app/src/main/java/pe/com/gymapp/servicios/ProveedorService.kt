package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Proveedor
import retrofit2.Call
import retrofit2.http.*

interface ProveedorService {
    @GET("proveedor")
    fun MostrarProveedor(): Call<List<Proveedor>?>?

    @GET("proveedor/custom")
    fun MostrarProveedorPersonalizado(): Call<List<Proveedor>?>?

    @POST("proveedor")
    fun RegistrarProveedor(@Body p: Proveedor?): Call<Proveedor?>?

    @PUT("proveedor/{id}")
    fun ActualizarProveedor(@Path("id") id:Long, @Body p: Proveedor?): Call<Proveedor?>?

    @PATCH("proveedor/{id}")
    fun EnableProveedor(@Path("id") id:Long): Call<Proveedor?>?

    @DELETE("proveedor/{id}")
    fun EliminarProveedor(@Path("id") id:Long): Call<Proveedor?>?
}