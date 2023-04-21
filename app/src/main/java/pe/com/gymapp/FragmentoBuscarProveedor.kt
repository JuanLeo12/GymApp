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
import pe.com.gymapp.adaptadores.AdaptadorProveedor
import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Proveedor
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.ClienteService
import pe.com.gymapp.servicios.ProductoService
import pe.com.gymapp.servicios.ProveedorService
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
 * Use the [FragmentoBuscarProveedor.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarProveedor : Fragment() {
    //declaramos los controles
    private lateinit var txtBusProv: SearchView
    private lateinit var lblCodProv: TextView
    private lateinit var btnHabilitarProv: Button
    private lateinit var btnDeshabilitarProv: Button
    private lateinit var lstProv: ListView

    //cremamos un objeto de la clase categoria
    private val objproveedor= Proveedor()

    //creamos variables
    private var cod=0L
    private var est=false
    private var fila=-1
    private var nom=""
    private var cri=""

    //llamamos al servicio
    private var proveedorService: ProveedorService?=null

    //creamos una lista de tipo categoria
    private var registroproveedor:List<Proveedor>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_buscar_proveedor,container,false)
        //creamos los controles
        txtBusProv=raiz.findViewById(R.id.txtBusProv)
        lblCodProv=raiz.findViewById(R.id.lblCodProv)
        btnHabilitarProv=raiz.findViewById(R.id.btnHabilitarProv)
        btnDeshabilitarProv=raiz.findViewById(R.id.btnDeshabilitarProv)
        lstProv=raiz.findViewById(R.id.lstProv)

        //creamos el registro categoria
        registroproveedor=ArrayList()
        //implementamos el servicio
        proveedorService= ApiUtil.proveedorService
        //mostramos los clientes
        MostrarProveedor(raiz.context)

        //eventos
        txtBusProv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstProv.adapter as AdaptadorProveedor).filter(newText ?: "")
                return true
            }
        })

        lstProv.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

            lblCodProv.setText(""+(registroproveedor as ArrayList<Proveedor>).get(fila).idproveedor)
        }

        btnHabilitarProv.setOnClickListener {
            if(fila>=0){
                cod=lblCodProv.text.toString().toLong()

                EnableProveedor(raiz.context,cod)
                val fbcliente=FragmentoBuscarProveedor()
                DialogoCRUD("Habilitar Proveedor","Se habilitó al proveedor correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstProv.requestFocus()
            }
        }

        btnDeshabilitarProv.setOnClickListener {
            if(fila>=0){
                cod=lblCodProv.text.toString().toLong()

                EliminarProveedor(raiz.context,cod)
                val fbcliente=FragmentoBuscarProveedor()
                DialogoCRUD("Deshabilitar Proveedor","Se deshabilitó al proveedor correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstProv.requestFocus()
            }
        }
        return raiz
    }

    fun MostrarProveedor(context: Context?){
        val call= proveedorService!!.MostrarProveedor()
        call!!.enqueue(object : Callback<List<Proveedor>?> {
            override fun onResponse(
                call: Call<List<Proveedor>?>,
                response: Response<List<Proveedor>?>
            ) {
                if(response.isSuccessful){
                    registroproveedor=response.body()
                    lstProv.adapter= AdaptadorProveedor(context,registroproveedor)


                }
            }

            override fun onFailure(call: Call<List<Proveedor>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarProveedor(context: Context?, id:Long){
        val call= proveedorService!!.EliminarProveedor(id)
        call!!.enqueue(object : Callback<Proveedor?> {
            override fun onResponse(call: Call<Proveedor?>, response: Response<Proveedor?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Proveedor?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EnableProveedor(context: Context?, id:Long){
        val call= proveedorService!!.EnableProveedor(id)
        call!!.enqueue(object : Callback<Proveedor?> {
            override fun onResponse(call: Call<Proveedor?>, response: Response<Proveedor?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se habilitó")
                }
            }

            override fun onFailure(call: Call<Proveedor?>, t: Throwable) {
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