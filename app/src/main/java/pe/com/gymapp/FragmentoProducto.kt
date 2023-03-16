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
import pe.com.gymapp.adaptadores.AdaptadorProducto
import pe.com.gymapp.clases.Producto
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.ProductoService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoProducto : Fragment() {

    private lateinit var txtNomPro: EditText
    private lateinit var txtPreComPro: EditText
    private lateinit var txtPreVenPro: EditText
    private lateinit var txtCantPro: EditText
    private lateinit var chkEstPro: CheckBox
    private lateinit var lblCodPro: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstPro: ListView

    val objproducto= Producto()


    private var cod=0
    private var nom=""
    private var precom=0.0
    private var preven=0.0
    private var cant=0.0
    private var est=false
    private var fila=-1

    private var productoService:ProductoService?=null
    private var registroproducto:List<Producto>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_producto,container,false)
        //creamos los controles
        txtNomPro=raiz.findViewById(R.id.txtNomPro)
        txtPreComPro=raiz.findViewById(R.id.txtPreComPro)
        txtPreVenPro=raiz.findViewById(R.id.txtPreVenPro)
        txtCantPro=raiz.findViewById(R.id.txtCantPro)
        chkEstPro=raiz.findViewById(R.id.chkEstPro)
        lblCodPro=raiz.findViewById(R.id.lblCodPro)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstPro=raiz.findViewById(R.id.lstPro)

        registroproducto=ArrayList()

        //implementamos el servicio
        productoService= ApiUtil.productoService

        //mostramos las categorias
        MostrarProducto(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtNomPro.getText().toString()=="" || txtPreComPro.getText().toString()=="" || txtPreVenPro.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                txtNomPro.requestFocus()
            }else{
                //capturando valores
                nom=txtNomPro.getText().toString()
                precom= txtPreComPro.getText().toString().toDouble()
                preven= txtPreVenPro.getText().toString().toDouble()
                //cant= txtCantPro.getText().toString().toDouble()

                est=if(chkEstPro.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objproducto.nombre=nom
                objproducto.preciocompra=precom
                objproducto.precioventa=preven
                objproducto.cantidad=cant
                objproducto.estado=est
                //llamamos al metodo para registrar
                RegistrarProducto(raiz.context,objproducto)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmProducto) as ViewGroup)
                //actualizamos el fragmento
                val fproducto=FragmentoProducto()
                ft=fragmentManager?.beginTransaction()
                ft?.replace(R.id.contenedor,fproducto,null)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }

        lstPro.setOnItemClickListener(AdapterView.OnItemClickListener
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

    fun RegistrarProducto(context: Context?, p: Producto?){
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