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

    val empleadoService: EmpleadoService?
        get() =RetrofitClient.getClient(API_URl)?.create(EmpleadoService::class.java)

    val usuarioService: UsuarioService?
        get() =RetrofitClient.getClient(API_URl)?.create(UsuarioService::class.java)

    val generoService: GeneroService?
        get() =RetrofitClient.getClient(API_URl)?.create(GeneroService::class.java)

    val clienteService: ClienteService?
        get() =RetrofitClient.getClient(API_URl)?.create(ClienteService::class.java)

    val compraMaquinaService: CompraMaquinaService?
        get() =RetrofitClient.getClient(API_URl)?.create(CompraMaquinaService::class.java)

    val tipoPagoService: TipoPagoService?
        get() =RetrofitClient.getClient(API_URl)?.create(TipoPagoService::class.java)

    val ventaService: VentaService?
        get() =RetrofitClient.getClient(API_URl)?.create(VentaService::class.java)

    val incidenciaService: IncidenciaService?
        get() =RetrofitClient.getClient(API_URl)?.create(IncidenciaService::class.java)

    val mantenimientoService: MantenimientoService?
        get() =RetrofitClient.getClient(API_URl)?.create(MantenimientoService::class.java)

    val seguimientoFisicoService: SeguimientoFisicoService?
        get() =RetrofitClient.getClient(API_URl)?.create(SeguimientoFisicoService::class.java)

    val asistenciaClienteService: AsistenciaClienteService?
        get() =RetrofitClient.getClient(API_URl)?.create(AsistenciaClienteService::class.java)

    val asistenciaEmpleadoService: AsistenciaEmpleadoService?
        get() =RetrofitClient.getClient(API_URl)?.create(AsistenciaEmpleadoService::class.java)
}