package pe.com.gymapp

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import pe.com.gymapp.adaptadores.*
import pe.com.gymapp.clases.*
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.*
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentoVenta : Fragment() {
    private lateinit var spProdVen: Spinner
    private lateinit var spTPVen: Spinner
    private lateinit var spCliVen: Spinner
    private lateinit var spEmpVen: Spinner
    private lateinit var txtCantVen: EditText
    private lateinit var lblCodVenta: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var lstVen: ListView

    private val objventa= Venta()

    private val objproducto= Producto()
    private val objtipopago= TipoPago()
    private val objcliente= Cliente()
    private val objempleado= Empleado()

    private var cod=0L
    private var cant=0
    private var prod=""
    private var cli=""
    private var emp=""
    private var tpago=""
    private var codprod=0L
    private var codcli=0L
    private var codemp=0L
    private var codtpago=0L
    private var fila=-1
    private var indiceprod=-1
    private var indicecli=-1
    private var indiceemp=-1
    private var indicetpago=-1
    private var posprod=-1
    private var poscli=-1
    private var posemp=-1
    private var postpago=-1

    private var ventaService: VentaService?=null
    private var registroVenta:List<Venta>?=null

    private var productoService: ProductoService?=null
    private var registroProducto:List<Producto>?=null

    private var clienteService: ClienteService?=null
    private var registroCliente:List<Cliente>?=null

    private var empleadoService: EmpleadoService?=null
    private var registroEmpleado:List<Empleado>?=null

    private var tipoPagoService: TipoPagoService?=null
    private var registroTipoPago:List<TipoPago>?=null

    private val objutilidad= Util()



    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoProducto?=null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento_venta,container,false)
        //creamos los controles
        spCliVen=raiz.findViewById(R.id.spCliVen)
        spProdVen=raiz.findViewById(R.id.spProdVen)
        spEmpVen=raiz.findViewById(R.id.spEmpVen)
        spTPVen=raiz.findViewById(R.id.spTPVen)
        txtCantVen=raiz.findViewById(R.id.txtCantVen)
        lblCodVenta=raiz.findViewById(R.id.lblCodVenta)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        lstVen=raiz.findViewById(R.id.lstVen)

        registroVenta=ArrayList()
        registroCliente=ArrayList()
        registroProducto=ArrayList()
        registroEmpleado=ArrayList()
        registroTipoPago=ArrayList()

        //implementamos el servicio
        ventaService= ApiUtil.ventaService
        clienteService= ApiUtil.clienteService
        productoService= ApiUtil.productoService
        empleadoService= ApiUtil.empleadoService
        tipoPagoService= ApiUtil.tipoPagoService

        //mostramos las categorias
        MostrarVenta(raiz.context)
        MostrarComboCliente(raiz.context)
        MostrarComboProducto(raiz.context)
        MostrarComboEmpleado(raiz.context)
        MostrarComboTipoPago(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(spCliVen.adapter.toString() =="" || spProdVen.adapter.toString() =="" || spEmpVen.adapter.toString() =="" || spTPVen.adapter.toString() =="" || txtCantVen.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                spCliVen.requestFocus()
            }else{
                //capturando valores
                cant= txtCantVen.text.toString().toInt()

                poscli=spCliVen.selectedItemPosition
                cli= (registroCliente as ArrayList<Cliente>).get(poscli).nombre.toString()
                codcli= (registroCliente as ArrayList<Cliente>).get(poscli).idcliente

                posprod=spProdVen.selectedItemPosition
                prod= (registroProducto as ArrayList<Producto>).get(posprod).nombre.toString()
                codprod= (registroProducto as ArrayList<Producto>).get(posprod).idproducto

                posemp=spEmpVen.selectedItemPosition
                emp= (registroEmpleado as ArrayList<Empleado>).get(posemp).nombre.toString()
                codemp= (registroEmpleado as ArrayList<Empleado>).get(posemp).idempleado

                postpago=spProdVen.selectedItemPosition
                tpago= (registroTipoPago as ArrayList<TipoPago>).get(postpago).tipopago.toString()
                codtpago= (registroTipoPago as ArrayList<TipoPago>).get(postpago).idtipopago


                //enviamos los valores a la clase
                objventa.cantidad=cant.toDouble()
                objventa.idventa=cod

                objcliente.idcliente=codcli
                objventa.cliente=objcliente

                objproducto.idproducto=codprod
                objventa.producto=objproducto

                objempleado.idempleado=codemp
                objventa.empleado=objempleado

                objtipopago.idtipopago=codtpago
                objventa.tipopago=objtipopago

                //llamamos al metodo para registrar
                RegistrarVenta(raiz.context,objventa)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmVenta) as ViewGroup)
                //actualizamos el fragmento
                val fventa=FragmentoVenta()
                DialogoRegistrar("Registro de Venta","¿Está seguro de registrar la Venta?, no se podrá modificar los datos",fventa)
            }
        }

        lstVen.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodVenta.setText(""+ (registroVenta as ArrayList<Venta>).get(fila).idventa)
            txtCantVen.setText(""+ (registroVenta as ArrayList<Venta>).get(fila).cantidad.toInt())

            for(x in (registroCliente as ArrayList<Cliente>).indices){
                if((registroCliente as ArrayList<Cliente>).get(x).nombre== (registroVenta as ArrayList<Venta>).get(fila).cliente?.nombre){
                    indicecli=x
                }
            }
            spCliVen.setSelection(indicecli)

            for(x in (registroProducto as ArrayList<Producto>).indices){
                if((registroProducto as ArrayList<Producto>).get(x).nombre== (registroVenta as ArrayList<Venta>).get(fila).producto?.nombre){
                    indiceprod=x
                }
            }
            spProdVen.setSelection(indiceprod)

            for(x in (registroEmpleado as ArrayList<Empleado>).indices){
                if((registroEmpleado as ArrayList<Empleado>).get(x).nombre== (registroVenta as ArrayList<Venta>).get(fila).empleado?.nombre){
                    indiceemp=x
                }
            }
            spEmpVen.setSelection(indiceemp)

            for(x in (registroTipoPago as ArrayList<TipoPago>).indices){
                if((registroTipoPago as ArrayList<TipoPago>).get(x).tipopago== (registroVenta as ArrayList<Venta>).get(fila).tipopago?.tipopago){
                    indicetpago=x
                }
            }
            spTPVen.setSelection(indicetpago)
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodVenta.text.toString().toLong()
                cant= txtCantVen.text.toString().toInt()

                poscli=spCliVen.selectedItemPosition
                cli= (registroCliente as ArrayList<Cliente>).get(poscli).nombre.toString()
                codcli= (registroCliente as ArrayList<Cliente>).get(poscli).idcliente

                posprod=spProdVen.selectedItemPosition
                prod= (registroProducto as ArrayList<Producto>).get(posprod).nombre.toString()
                codprod= (registroProducto as ArrayList<Producto>).get(posprod).idproducto

                posemp=spEmpVen.selectedItemPosition
                emp= (registroEmpleado as ArrayList<Empleado>).get(posemp).nombre.toString()
                codemp= (registroEmpleado as ArrayList<Empleado>).get(posemp).idempleado

                postpago=spProdVen.selectedItemPosition
                tpago= (registroTipoPago as ArrayList<TipoPago>).get(postpago).tipopago.toString()
                codtpago= (registroTipoPago as ArrayList<TipoPago>).get(postpago).idtipopago


                //enviamos los valores a la clase
                objventa.cantidad=cant.toDouble()
                objventa.idventa=cod

                objcliente.idcliente=codcli
                objventa.cliente=objcliente

                objproducto.idproducto=codprod
                objventa.producto=objproducto

                objempleado.idempleado=codemp
                objventa.empleado=objempleado

                objtipopago.idtipopago=codtpago
                objventa.tipopago=objtipopago

                //llamamos a la funcion para registrar
                ActualizarVenta(raiz.context,objventa,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmVenta) as ViewGroup)
                val fventa=FragmentoVenta()
                DialogoCRUD("Actualización de la Venta","Se actualizó la venta correctamente",fventa)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstVen.requestFocus()
            }

        }


        return raiz
    }

    fun MostrarComboProducto(context: Context?){
        val call= productoService!!.MostrarProductoPersonalizado()
        call!!.enqueue(object : Callback<List<Producto>?> {
            override fun onResponse(
                call: Call<List<Producto>?>,
                response: Response<List<Producto>?>
            ) {
                if(response.isSuccessful){
                    registroProducto=response.body()
                    spProdVen.adapter= AdaptadorComboProducto(context,registroProducto)


                }
            }

            override fun onFailure(call: Call<List<Producto>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarComboCliente(context: Context?){
        val call= clienteService!!.MostrarClientePersonalizado()
        call!!.enqueue(object : Callback<List<Cliente>?> {
            override fun onResponse(
                call: Call<List<Cliente>?>,
                response: Response<List<Cliente>?>
            ) {
                if(response.isSuccessful){
                    registroCliente=response.body()
                    spCliVen.adapter= AdaptadorComboCliente(context,registroCliente)


                }
            }

            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }



    fun MostrarComboEmpleado(context: Context?){
        val call= empleadoService!!.MostrarEmpleadoPersonalizado()
        call!!.enqueue(object : Callback<List<Empleado>?> {
            override fun onResponse(
                call: Call<List<Empleado>?>,
                response: Response<List<Empleado>?>
            ) {
                if(response.isSuccessful){
                    registroEmpleado=response.body()
                    spEmpVen.adapter= AdaptadorComboEmpleado(context,registroEmpleado)


                }
            }

            override fun onFailure(call: Call<List<Empleado>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarComboTipoPago(context: Context?){
        val call= tipoPagoService!!.MostrarTipoPagoPersonalizado()
        call!!.enqueue(object : Callback<List<TipoPago>?> {
            override fun onResponse(
                call: Call<List<TipoPago>?>,
                response: Response<List<TipoPago>?>
            ) {
                if(response.isSuccessful){
                    registroTipoPago=response.body()
                    spTPVen.adapter= AdaptadorComboTipoPago(context,registroTipoPago)


                }
            }

            override fun onFailure(call: Call<List<TipoPago>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }



    fun MostrarVenta(context: Context?){
        val call= ventaService!!.MostrarVenta()
        call!!.enqueue(object : Callback<List<Venta>?> {
            override fun onResponse(
                call: Call<List<Venta>?>,
                response: Response<List<Venta>?>
            ) {
                if(response.isSuccessful){
                    registroVenta=response.body()
                    lstVen.adapter= AdaptadorVenta(context,registroVenta)
                }
            }

            override fun onFailure(call: Call<List<Venta>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun RegistrarVenta(context: Context?, v: Venta?){
        val call= ventaService!!.RegistrarVenta(v)
        call!!.enqueue(object : Callback<Venta?> {
            override fun onResponse(call: Call<Venta?>, response: Response<Venta?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<Venta?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    fun ActualizarVenta(context: Context?, v: Venta?, id:Long){
        val call= ventaService!!.ActualizarVenta(id,v)
        call!!.enqueue(object : Callback<Venta?> {
            override fun onResponse(call: Call<Venta?>, response: Response<Venta?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<Venta?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    //creamos una función para los cuadros de dialogo del CRUD
    fun DialogoCRUD(titulo:String,mensaje:String,fragmento:Fragment){
        dialogo= AlertDialog.Builder(context)
        dialogo!!.setTitle(titulo)
        dialogo!!.setMessage(mensaje)
        dialogo!!.setCancelable(false)
        dialogo!!.setPositiveButton("Ok"){
                dialogo,which->
            ft=fragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor,fragmento,null)
            ft?.addToBackStack(null)
            ft?.commit()
        }
        dialogo!!.show()
    }

    fun DialogoRegistrar(titulo:String,mensaje:String,fragmento:Fragment){
        dialogo=AlertDialog.Builder(context)
        dialogo!!.setTitle(titulo)
        dialogo!!.setMessage(mensaje)
        dialogo!!.setCancelable(false)
        dialogo!!.setPositiveButton("Si"){
                dialog,which->
            ft=fragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor,fragmento,null)
            ft?.addToBackStack(null)
            ft?.commit()
        }
        dialogo!!.setNegativeButton("No"){
                dialog,which->
        }
        dialogo!!.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}