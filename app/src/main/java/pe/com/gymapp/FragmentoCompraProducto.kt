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
    private lateinit var lblCodCPro: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var lstComPro: ListView

    private val objcompraproducto= CompraProducto()

    private val objproducto=Producto()
    private val objproveedor=Proveedor()

    private var cod=0L
    private var cant=0
    private var prod=""
    private var prov=""
    private var codprod=0L
    private var codprov=0L
    private var fila=-1
    private var indiceprod=-1
    private var indiceprov=-1
    private var posprod=-1
    private var posprov=-1

    private var compraProductoService: CompraProductoService?=null
    private var registrocompraProducto:List<CompraProducto>?=null

    private var productoService: ProductoService?=null
    private var registroproducto:List<Producto>?=null

    private var proveedorService: ProveedorService?=null
    private var registroproveedor:List<Proveedor>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_compra_producto,container,false)
        //creamos los controles
        spProComPro=raiz.findViewById(R.id.spProComPro)
        spProvComPro=raiz.findViewById(R.id.spProvComPro)
        txtCantComPro=raiz.findViewById(R.id.txtCantComPro)
        lblCodCPro=raiz.findViewById(R.id.lblCodCPro)
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
        btnRegistrar.setOnClickListener {
            if(spProComPro.adapter.toString() =="" || spProvComPro.adapter.toString() =="" || txtCantComPro.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                spProComPro.requestFocus()
            }else{
                //capturando valores
                cant= txtCantComPro.text.toString().toInt()
                posprod=spProComPro.selectedItemPosition
                prod= (registroproducto as ArrayList<Producto>).get(posprod).nombre.toString()
                codprod= (registroproducto as ArrayList<Producto>).get(posprod).idproducto
                posprov=spProvComPro.selectedItemPosition
                prov= (registroproveedor as ArrayList<Proveedor>).get(posprov).nombre.toString()
                codprov= (registroproveedor as ArrayList<Proveedor>).get(posprov).idproveedor


                //enviamos los valores a la clase
                objcompraproducto.cantidad=cant.toDouble()
                objcompraproducto.idcomppro=cod

                objproducto.idproducto=codprod
                objcompraproducto.producto=objproducto

                objproveedor.idproveedor=codprov
                objcompraproducto.proveedor=objproveedor
                //llamamos al metodo para registrar
                RegistrarCompraProducto(raiz.context,objcompraproducto)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmCompProd) as ViewGroup)
                //actualizamos el fragmento
                val fcompproducto=FragmentoCompraProducto()
                DialogoRegistrar("Registro de Compra de Producto","¿Está seguro de registrar la compra?, no se podrá modificar los datos",fcompproducto)
            }
        }

        lstComPro.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodCPro.setText(""+ (registrocompraProducto as ArrayList<CompraProducto>).get(fila).idcomppro)
            txtCantComPro.setText(""+ (registrocompraProducto as ArrayList<CompraProducto>).get(fila).cantidad.toInt())
            for(x in (registroproducto as ArrayList<Producto>).indices){
                if((registroproducto as ArrayList<Producto>).get(x).nombre== (registrocompraProducto as ArrayList<CompraProducto>).get(fila).producto?.nombre){
                    indiceprod=x
                }
            }
            spProComPro.setSelection(indiceprod)
            for(x in (registroproveedor as ArrayList<Proveedor>).indices){
                if((registroproveedor as ArrayList<Proveedor>).get(x).nombre== (registrocompraProducto as ArrayList<CompraProducto>).get(fila).proveedor?.nombre){
                    indiceprov=x
                }
            }
            spProComPro.setSelection(indiceprov)
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodCPro.text.toString().toLong()
                cant= txtCantComPro.text.toString().toInt()
                posprod=spProComPro.selectedItemPosition
                prod= (registroproducto as ArrayList<Producto>).get(posprod).nombre.toString()
                codprod= (registroproducto as ArrayList<Producto>).get(posprod).idproducto
                posprov=spProvComPro.selectedItemPosition
                prov= (registroproveedor as ArrayList<Proveedor>).get(posprov).nombre.toString()
                codprov= (registroproveedor as ArrayList<Proveedor>).get(posprov).idproveedor

                //enviamos los valores a la clase
                objcompraproducto.idcomppro=cod
                objcompraproducto.cantidad=cant.toDouble()


                objproducto.idproducto=codprod
                objcompraproducto.producto=objproducto

                objproveedor.idproveedor=codprov
                objcompraproducto.proveedor=objproveedor

                //llamamos a la funcion para registrar
                ActualizarCompraProducto(raiz.context,objcompraproducto,cod)
                val fcompraprod=FragmentoCompraProducto()
                DialogoCRUD("Actualizacion de la Compra","Se actualizo la compra correctamente",fcompraprod)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstComPro.requestFocus()
            }

        }


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

    fun RegistrarCompraProducto(context: Context?, cp: CompraProducto?){
        val call= compraProductoService!!.RegistrarCompraProducto(cp)
        call!!.enqueue(object : Callback<CompraProducto?> {
            override fun onResponse(call: Call<CompraProducto?>, response: Response<CompraProducto?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<CompraProducto?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    fun ActualizarCompraProducto(context: Context?,cp: CompraProducto?,id:Long){
        val call= compraProductoService!!.ActualizarCompraProducto(id,cp)
        call!!.enqueue(object :Callback<CompraProducto?>{
            override fun onResponse(call: Call<CompraProducto?>, response: Response<CompraProducto?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<CompraProducto?>, t: Throwable) {
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