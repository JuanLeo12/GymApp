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
import pe.com.gymapp.adaptadores.AdaptadorCliente
import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.ClienteService
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
 * Use the [FragmentoBuscarCliente.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarCliente : Fragment() {
    //declaramos los controles
    private lateinit var txtBusCli: SearchView
    private lateinit var lblCodCli: TextView
    private lateinit var btnHabilitarCli: Button
    private lateinit var btnDeshabilitarCli: Button
    private lateinit var lstCli: ListView

    //cremamos un objeto de la clase categoria
    private val objcliente= Cliente()

    //creamos variables
    private var cod=0L
    private var est=false
    private var fila=-1
    private var nom=""
    private var cri=""

    //llamamos al servicio
    private var clienteService: ClienteService?=null

    //creamos una lista de tipo categoria
    private var registrocliente:List<Cliente>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_buscar_cliente,container,false)
        //creamos los controles
        txtBusCli=raiz.findViewById(R.id.txtBusCli)
        lblCodCli=raiz.findViewById(R.id.lblCodCli)
        btnHabilitarCli=raiz.findViewById(R.id.btnHabilitarCli)
        btnDeshabilitarCli=raiz.findViewById(R.id.btnDeshabilitarCli)
        lstCli=raiz.findViewById(R.id.lstCli)

        //creamos el registro categoria
        registrocliente=ArrayList()
        //implementamos el servicio
        clienteService= ApiUtil.clienteService
        //mostramos los clientes
        MostrarCliente(raiz.context)

        //eventos
        txtBusCli.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstCli.adapter as AdaptadorCliente).filter(newText ?: "")
                return true
            }
        })

        lstCli.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

            lblCodCli.setText(""+(registrocliente as ArrayList<Cliente>).get(fila).idcliente)
        }

        btnHabilitarCli.setOnClickListener {
            if(fila>=0){
                cod=lblCodCli.text.toString().toLong()

                EnableCliente(raiz.context,cod)
                val fbcliente=FragmentoBuscarCliente()
                DialogoCRUD("Habilitar Cliente","Se habilitó al cliente correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstCli.requestFocus()
            }
        }

        btnDeshabilitarCli.setOnClickListener {
            if(fila>=0){
                cod=lblCodCli.text.toString().toLong()

                EliminarCliente(raiz.context,cod)
                val fbcliente=FragmentoBuscarCliente()
                DialogoCRUD("Deshabilitar Cliente","Se deshabilitó al cliente correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstCli.requestFocus()
            }
        }
        return raiz
    }

    fun MostrarCliente(context: Context?){
        val call= clienteService!!.MostrarCliente()
        call!!.enqueue(object : Callback<List<Cliente>?> {
            override fun onResponse(
                call: Call<List<Cliente>?>,
                response: Response<List<Cliente>?>
            ) {
                if(response.isSuccessful){
                    registrocliente=response.body()
                    lstCli.adapter= AdaptadorCliente(context,registrocliente)


                }
            }

            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarCliente(context: Context?, id:Long){
        val call= clienteService!!.EliminarCliente(id)
        call!!.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EnableCliente(context: Context?, id:Long){
        val call= clienteService!!.EnableCliente(id)
        call!!.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se habilitó")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
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