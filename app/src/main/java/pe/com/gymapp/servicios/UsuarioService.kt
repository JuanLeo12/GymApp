package pe.com.gymapp.servicios

import pe.com.gymapp.clases.Usuario
import retrofit2.Call
import retrofit2.http.*

interface UsuarioService {
    @GET("usuario")
    fun MostrarUsuario(): Call<List<Usuario>?>?

    @GET("usuario/custom")
    fun MostrarUsuarioPersonalizado(): Call<List<Usuario>?>?

    @POST("usuario")
    fun RegistrarUsuario(@Body u: Usuario?): Call<Usuario?>?

    @PUT("usuario/{id}")
    fun ActualizarUsuario(@Path("id") id:Long, @Body r: Usuario?): Call<Usuario?>?

    @DELETE("usuario/{id}")
    fun EliminarUsuario(@Path("id") id:Long): Call<Usuario?>?
}