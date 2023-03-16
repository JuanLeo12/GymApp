package pe.com.gymapp.remoto

import pe.com.gymapp.servicios.*

object ApiUtil {
    //reemplazar localhost por tu direccion IP
    val API_URl="http://192.168.18.99:8095/gimnasio/"

    val productoService: ProductoService?
        get() =RetrofitClient.getClient(API_URl)?.create(ProductoService::class.java)

    val rolService: RolService?
        get() =RetrofitClient.getClient(API_URl)?.create(RolService::class.java)

    val membresiaService: MembresiaService?
        get() =RetrofitClient.getClient(API_URl)?.create(MembresiaService::class.java)

    val maquinaService: MaquinaService?
        get() =RetrofitClient.getClient(API_URl)?.create(MaquinaService::class.java)

    val proveedorService: ProveedorService?
        get() =RetrofitClient.getClient(API_URl)?.create(ProveedorService::class.java)

    val compraProductoService: CompraProductoService?
        get() =RetrofitClient.getClient(API_URl)?.create(CompraProductoService::class.java)

}