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
import pe.com.gymapp.clases.CompraProducto
import pe.com.gymapp.clases.Producto
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.CompraProductoService
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


    private var cod=0
    private var pro=""
    private var prov=null
    private var cant=0.0
    private var fila=-1

    private var compraProductoService: CompraProductoService?=null
    private var registrocompraProducto:List<CompraProducto>?=null

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

        //implementamos el servicio
        compraProductoService= ApiUtil.compraProductoService

        //mostramos las categorias
        MostrarCompraProducto(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(spProComPro.adapter.toString() =="" || spProvComPro.adapter.toString() =="" || txtCantComPro.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                spProComPro.requestFocus()
            }else{
                //capturando valores
                pro=spProComPro.adapter.toString()
                prov=spProvComPro.adapter.toString()
                cant= txtCantComPro.getText().toString().toDouble()

                //enviamos los valores a la clase
                objcompraproducto.producto=pro
                objcompraproducto.proveedor=prov
                objcompraproducto.cantidad=cant
                //llamamos al metodo para registrar
                RegistrarProducto(raiz.context,objcompraproducto)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmCompProd) as ViewGroup)
                //actualizamos el fragmento
                val fcompproducto=FragmentoCompraProducto()
                ft=fragmentManager?.beginTransaction()
                ft?.replace(R.id.contenedor,fcompproducto,null)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }

        lstComPro.setOnItemClickListener(
            AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            //asignamos los valores a cada control
            lblCodPro.setText(""+(registroproducto as ArrayList<Producto>).get(fila).idproducto)
            txtNomPro.setText(""+(registroproducto as ArrayList<Producto>).get(fila).nombre)
            txtPreComPro.setText(""+(registroproducto as ArrayList<Producto>).get(fila).preciocompra)
            txtPreVenPro.setText(""+(registroproducto as ArrayList<Producto>).get(fila).precioventa)
            txtCantPro.setText(""+(registroproducto as ArrayList<Producto>).get(fila).cantidad)
            if((registroproducto as ArrayList<Producto>).get(fila).estado){
                chkEstPro.setChecked(true)
            }else{
                chkEstPro.setChecked(false)
            }
        })

        return raiz
    }

    fun MostrarProducto(context: Context?){
        val call= productoService!!.MostrarProductoPersonalizado()
        call!!.enqueue(object : Callback<List<Producto>?> {
            override fun onResponse(
                call: Call<List<Producto>?>,
                response: Response<List<Producto>?>
            ) {
                if(response.isSuccessful){
                    registroproducto=response.body()
                    lstPro.adapter= AdaptadorProducto(context,registroproducto)
                }
            }

            override fun onFailure(call: Call<List<Producto>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun RegistrarProducto(context: Context?, p: CompraProducto?){
        val call= productoService!!.RegistrarProducto(p)
        call!!.enqueue(object : Callback<Producto?> {
            override fun onResponse(call: Call<Producto?>, response: Response<Producto?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro el producto")
                }
            }

            override fun onFailure(call: Call<Producto?>, t: Throwable) {
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