package pe.com.gymapp

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import pe.com.gymapp.adaptadores.AdaptadorCliente
import pe.com.gymapp.adaptadores.AdaptadorProducto
import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Producto
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.ClienteService
import pe.com.gymapp.servicios.ProductoService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentoBuscarProducto.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarProducto : Fragment() {
    //declaramos los controles
    private lateinit var txtBusProd: SearchView
    private lateinit var lblCodProd: TextView
    private lateinit var btnHabilitarProd: Button
    private lateinit var btnDeshabilitarProd: Button
    private lateinit var lstProd: ListView

    //cremamos un objeto de la clase categoria
    private val objproducto= Producto()

    //creamos variables
    private var cod=0L
    private var est=false
    private var fila=-1
    private var nom=""
    private var cri=""

    //llamamos al servicio
    private var productoService: ProductoService?=null

    //creamos una lista de tipo categoria
    private var registroproducto:List<Producto>?=null

    //creamos un objeto de la clase Util
    private val objutilidad= Util()

    //creams una variable para actualizar el fragmento
    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val raiz=inflater.inflate(R.layout.fragmento_buscar_producto,container,false)
        //creamos los controles
        txtBusProd=raiz.findViewById(R.id.txtBusProd)
        lblCodProd=raiz.findViewById(R.id.lblCodProd)
        btnHabilitarProd=raiz.findViewById(R.id.btnHabilitarProd)
        btnDeshabilitarProd=raiz.findViewById(R.id.btnDeshabilitarProd)
        lstProd=raiz.findViewById(R.id.lstProd)

        //creamos el registro categoria
        registroproducto=ArrayList()
        //implementamos el servicio
        productoService= ApiUtil.productoService
        //mostramos los clientes
        MostrarProducto(raiz.context)

        //eventos
        txtBusProd.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstProd.adapter as AdaptadorProducto).filter(newText ?: "")
                return true
            }
        })

        lstProd.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

            lblCodProd.setText(""+(registroproducto as ArrayList<Producto>).get(fila).idproducto)
        }

        btnHabilitarProd.setOnClickListener {
            if(fila>=0){
                cod=lblCodProd.text.toString().toLong()

                EnableProducto(raiz.context,cod)
                val fbcliente=FragmentoBuscarProducto()
                DialogoCRUD("Habilitar Producto","Se habilitó el producto correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstProd.requestFocus()
            }
        }

        btnDeshabilitarProd.setOnClickListener {
            if(fila>=0){
                cod=lblCodProd.text.toString().toLong()

                EliminarProducto(raiz.context,cod)
                val fbcliente=FragmentoBuscarProducto()
                DialogoCRUD("Deshabilitar Producto","Se deshabilitó el producto correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstProd.requestFocus()
            }
        }
        return raiz
    }

    fun MostrarProducto(context: Context?){
        val call= productoService!!.MostrarProducto()
        call!!.enqueue(object : Callback<List<Producto>?> {
            override fun onResponse(
                call: Call<List<Producto>?>,
                response: Response<List<Producto>?>
            ) {
                if(response.isSuccessful){
                    registroproducto=response.body()
                    lstProd.adapter= AdaptadorProducto(context,registroproducto)


                }
            }

            override fun onFailure(call: Call<List<Producto>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarProducto(context: Context?, id:Long){
        val call= productoService!!.EliminarProducto(id)
        call!!.enqueue(object : Callback<Producto?> {
            override fun onResponse(call: Call<Producto?>, response: Response<Producto?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Producto?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EnableProducto(context: Context?, id:Long){
        val call= productoService!!.EnableProducto(id)
        call!!.enqueue(object : Callback<Producto?> {
            override fun onResponse(call: Call<Producto?>, response: Response<Producto?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se habilitó")
                }
            }

            override fun onFailure(call: Call<Producto?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentoBuscarCliente.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentoBuscarCliente().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}