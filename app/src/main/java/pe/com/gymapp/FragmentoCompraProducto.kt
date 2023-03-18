package pe.com.gymapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import pe.com.gymapp.adaptadores.AdaptadorComboProducto
import pe.com.gymapp.adaptadores.AdaptadorComboProveedor
import pe.com.gymapp.adaptadores.AdaptadorCompraProducto
import pe.com.gymapp.clases.CompraProducto
import pe.com.gymapp.clases.Producto
import pe.com.gymapp.clases.Proveedor
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.CompraProductoService
import pe.com.gymapp.servicios.ProductoService
import pe.com.gymapp.servicios.ProveedorService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoCompraProducto : Fragment() {
    private lateinit var spProComPro: Spinner
    private lateinit var spProvComPro: Spinner
    private lateinit var txtCantComPro: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var lstComPro: ListView

    val objcompraproducto= CompraProducto()

    private val objproducto=Producto()
    private val objproveedor=Proveedor()

    private var cod=0L
    private var pro=""
    private var prov=null
    private var cant=0.0
    private var fila=-1

    private var compraProductoService: CompraProductoService?=null
    private var registrocompraProducto:List<CompraProducto>?=null

    private var productoService: ProductoService?=null
    private var registroproducto:List<Producto>?=null

    private var proveedorService: ProveedorService?=null
    private var registroproveedor:List<Proveedor>?=null

    var objutilidad= Util()

    var ft: FragmentTransaction?=null

    private var _binding: FragmentoProducto?=null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento_compra_producto,container,false)
        //creamos los controles
        spProComPro=raiz.findViewById(R.id.spProComPro)
        spProvComPro=raiz.findViewById(R.id.spProvComPro)
        txtCantComPro=raiz.findViewById(R.id.txtCantComPro)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        lstComPro=raiz.findViewById(R.id.lstComPro)

        registrocompraProducto=ArrayList()
        registroproducto=ArrayList()
        registroproveedor=ArrayList()

        //implementamos el servicio
        compraProductoService= ApiUtil.compraProductoService
        productoService=ApiUtil.productoService
        proveedorService=ApiUtil.proveedorService

        //mostramos las categorias
        MostrarCompraProducto(raiz.context)
        MostrarComboProducto(raiz.context)
        MostrarComboProveedor(raiz.context)

        //agregamos los eventos
        //
        //
//        btnRegistrar.setOnClickListener {
//            if(spProComPro.adapter.toString() =="" || spProvComPro.adapter.toString() =="" || txtCantComPro.getText().toString()==""){
//                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
//                spProComPro.requestFocus()
//            }else{
//                //capturando valores
//                pro=spProComPro.adapter.toString()
//                //prov=spProvComPro.adapter.toString()
//                cant= txtCantComPro.getText().toString().toDouble()
//
//                //enviamos los valores a la clase
//                //objcompraproducto.producto=pro
//                objcompraproducto.proveedor=prov
//                objcompraproducto.cantidad=cant
//                //llamamos al metodo para registrar
//                RegistrarCompraProducto(raiz.context,objcompraproducto)
//                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmCompProd) as ViewGroup)
//                //actualizamos el fragmento
//                val fcompproducto=FragmentoCompraProducto()
//                ft=fragmentManager?.beginTransaction()
//                ft?.replace(R.id.contenedor,fcompproducto,null)
//                ft?.addToBackStack(null)
//                ft?.commit()
//            }
//        }
//
//        lstComPro.setOnItemClickListener(
//            AdapterView.OnItemClickListener
//        { parent, view, position, id ->
//            fila=position
//            //asignamos los valores a cada control
////            spProComPro.setText(""+(registrocompraProducto as ArrayList<CompraProducto>).get(fila).producto)
////            spProvComPro.setText(""+(registrocompraProducto as ArrayList<CompraProducto>).get(fila).proveedor)
//            txtCantComPro.setText(""+(registrocompraProducto as ArrayList<CompraProducto>).get(fila).cantidad)
//        })
        //
        //

        return raiz
    }

    fun MostrarComboProducto(context: Context?){
        val call= productoService!!.MostrarProductoPersonalizado()
        call!!.enqueue(object :Callback<List<Producto>?>{
            override fun onResponse(
                call: Call<List<Producto>?>,
                response: Response<List<Producto>?>
            ) {
                if(response.isSuccessful){
                    registroproducto=response.body()
                    spProComPro.adapter=AdaptadorComboProducto(context,registroproducto)


                }
            }

            override fun onFailure(call: Call<List<Producto>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarComboProveedor(context: Context?){
        val call= proveedorService!!.MostrarProveedorPersonalizado()
        call!!.enqueue(object :Callback<List<Proveedor>?>{
            override fun onResponse(
                call: Call<List<Proveedor>?>,
                response: Response<List<Proveedor>?>
            ) {
                if(response.isSuccessful){
                    registroproveedor=response.body()
                    spProvComPro.adapter=AdaptadorComboProveedor(context,registroproveedor)


                }
            }

            override fun onFailure(call: Call<List<Proveedor>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarCompraProducto(context: Context?){
        val call= compraProductoService!!.MostrarCompraProducto()
        call!!.enqueue(object : Callback<List<CompraProducto>?> {
            override fun onResponse(
                call: Call<List<CompraProducto>?>,
                response: Response<List<CompraProducto>?>
            ) {
                if(response.isSuccessful){
                    registrocompraProducto=response.body()
                    lstComPro.adapter= AdaptadorCompraProducto(context,registrocompraProducto)
                }
            }

            override fun onFailure(call: Call<List<CompraProducto>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun RegistrarCompraProducto(context: Context?, p: CompraProducto?){
        val call= compraProductoService!!.RegistrarCompraProducto(p)
        call!!.enqueue(object : Callback<CompraProducto?> {
            override fun onResponse(call: Call<CompraProducto?>, response: Response<CompraProducto?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro la compra del producto")
                }
            }

            override fun onFailure(call: Call<CompraProducto?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}