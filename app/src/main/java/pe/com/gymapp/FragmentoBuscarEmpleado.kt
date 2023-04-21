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
import pe.com.gymapp.adaptadores.AdaptadorEmpleado
import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Empleado
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.ClienteService
import pe.com.gymapp.servicios.EmpleadoService
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
 * Use the [FragmentoBuscarEmpleado.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarEmpleado : Fragment() {
    private lateinit var txtBusEmp: SearchView
    private lateinit var lblCodEmp: TextView
    private lateinit var btnHabilitarEmp: Button
    private lateinit var btnDeshabilitarEmp: Button
    private lateinit var lstEmp: ListView

    //cremamos un objeto de la clase categoria
    private val objempleado= Empleado()

    //creamos variables
    private var cod=0L
    private var est=false
    private var fila=-1
    private var nom=""
    private var cri=""

    //llamamos al servicio
    private var empleadoService: EmpleadoService?=null

    //creamos una lista de tipo categoria
    private var registroempleado:List<Empleado>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_buscar_empleado,container,false)
        //creamos los controles
        txtBusEmp=raiz.findViewById(R.id.txtBusEmp)
        lblCodEmp=raiz.findViewById(R.id.lblCodEmp)
        btnHabilitarEmp=raiz.findViewById(R.id.btnHabilitarEmp)
        btnDeshabilitarEmp=raiz.findViewById(R.id.btnDeshabilitarEmp)
        lstEmp=raiz.findViewById(R.id.lstEmp)

        //creamos el registro categoria
        registroempleado=ArrayList()
        //implementamos el servicio
        empleadoService= ApiUtil.empleadoService
        //mostramos los clientes
        MostrarEmpleado(raiz.context)

        //eventos
        txtBusEmp.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstEmp.adapter as AdaptadorEmpleado).filter(newText ?: "")
                return true
            }
        })

        lstEmp.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

            lblCodEmp.setText(""+(registroempleado as ArrayList<Empleado>).get(fila).idempleado)
        }

        btnHabilitarEmp.setOnClickListener {
            if(fila>=0){
                cod=lblCodEmp.text.toString().toLong()

                EnableEmpleado(raiz.context,cod)
                val fbcliente=FragmentoBuscarEmpleado()
                DialogoCRUD("Habilitar Empleado","Se habilitó al empleado correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstEmp.requestFocus()
            }
        }

        btnDeshabilitarEmp.setOnClickListener {
            if(fila>=0){
                cod=lblCodEmp.text.toString().toLong()

                EliminarEmpleado(raiz.context,cod)
                val fbcliente=FragmentoBuscarEmpleado()
                DialogoCRUD("Deshabilitar Empleado","Se deshabilitó al empleado correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstEmp.requestFocus()
            }
        }
        return raiz
    }

    fun MostrarEmpleado(context: Context?){
        val call= empleadoService!!.MostrarEmpleado()
        call!!.enqueue(object : Callback<List<Empleado>?> {
            override fun onResponse(
                call: Call<List<Empleado>?>,
                response: Response<List<Empleado>?>
            ) {
                if(response.isSuccessful){
                    registroempleado=response.body()
                    lstEmp.adapter= AdaptadorEmpleado(context,registroempleado)


                }
            }

            override fun onFailure(call: Call<List<Empleado>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarEmpleado(context: Context?, id:Long){
        val call= empleadoService!!.EliminarEmpleado(id)
        call!!.enqueue(object : Callback<Empleado?> {
            override fun onResponse(call: Call<Empleado?>, response: Response<Empleado?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Empleado?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EnableEmpleado(context: Context?, id:Long){
        val call= empleadoService!!.EnableEmpleado(id)
        call!!.enqueue(object : Callback<Empleado?> {
            override fun onResponse(call: Call<Empleado?>, response: Response<Empleado?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se habilitó")
                }
            }

            override fun onFailure(call: Call<Empleado?>, t: Throwable) {
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