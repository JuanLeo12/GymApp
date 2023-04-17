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
    private lateinit var txtVal: EditText
    private lateinit var btnBuscarCli: Button
    private lateinit var cboCriterio: Spinner
    private lateinit var lblCodCli: TextView
    private lateinit var btnHabilitarCli: Button
    private lateinit var btnDeshabilitarCli: Button
    private lateinit var lstCli: ListView

    //cremamos un objeto de la clase categoria
    private val objcliente= Cliente()

    //creamos variables
    private var cod=0L
    private var fila=-1
    private var poscri=-1
    private var cri=""
    //valores del Spinner
    val criterios= arrayOf<String>(
        "Seleccione un critero",
        "Codigo",
        "Telefono",
        "Nombre",
        "Apellido Paterno",
        "Apellido Materno"
    )

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
        txtVal=raiz.findViewById(R.id.txtVal)
        cboCriterio=raiz.findViewById(R.id.cboCriterio)
        lblCodCli=raiz.findViewById(R.id.lblCodCli)
        btnHabilitarCli=raiz.findViewById(R.id.btnHabilitarCli)
        btnDeshabilitarCli=raiz.findViewById(R.id.btnDeshabilitarCli)
        btnBuscarCli=raiz.findViewById(R.id.btnBuscarCli)
        lstCli=raiz.findViewById(R.id.lstCli)

        //creamos el registro categoria
        registrocliente=ArrayList()
        //implementamos el servicio
        clienteService= ApiUtil.clienteService
        //cargamos los valores al Spinner
        cboCriterio.adapter=
            ArrayAdapter(raiz.context,android.R.layout.simple_spinner_item,criterios)
        //mostramos las categorias
        MostrarCliente(raiz.context)

        //eventos
        btnBuscarCli.setOnClickListener {
            cri=txtVal.text.toString()
            Log.e("criterio: ", cri)
            poscri=cboCriterio.selectedItemPosition
            Log.e("posicion criterio: ", poscri.toString())

            if(poscri==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione un criterio de busqueda")
            }else{
                var x=cboCriterio.selectedItemPosition
                Log.e("x: ", x.toString())
                when(x){
                    2->{
                        Log.e("criterio: ", "criterio 1")
                        objcliente.telefono=cri
                        //BuscarXDniCliente(raiz.context,objcliente)
                        val fbuscarcliente=FragmentoBuscarCliente()
                        ft=fragmentManager?.beginTransaction()
                        ft?.replace(R.id.contenedor,fbuscarcliente,null)
                        ft?.addToBackStack(null)
                        ft?.commit()

                    }
                }
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

//    fun BuscarXTelefonoCliente(context: Context?,cl: Cliente?){
//        val call= clienteService!!.BuscarXTelefonoCliente(cl)
//        call!!.enqueue(object : Callback<List<Cliente>?> {
//            override fun onResponse(
//                call: Call<List<Cliente>?>,
//                response: Response<List<Cliente>?>
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//
//        })
//    }



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