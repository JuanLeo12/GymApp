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
import pe.com.gymapp.adaptadores.AdaptadorComboCliente
import pe.com.gymapp.adaptadores.AdaptadorComboEmpleado
import pe.com.gymapp.adaptadores.AdaptadorIncidencia
import pe.com.gymapp.clases.AsistenciaCliente
import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Empleado
import pe.com.gymapp.clases.Incidencia
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.AsistenciaClienteService
import pe.com.gymapp.servicios.ClienteService
import pe.com.gymapp.servicios.EmpleadoService
import pe.com.gymapp.servicios.IncidenciaService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoAsistenciaCliente : Fragment() {
    private lateinit var spCliAsisCli: Spinner
    private lateinit var btnAsistencia: Button

    private val objAsistenciaCliente= AsistenciaCliente()

    private val objcliente= Cliente()


    private var cod=0L
    private var fech=""
    private var cli=""
    private var codcli=0L
    private var fila=-1
    private var indicecli=-1
    private var poscli=-1

    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null



    private var asistenciaClienteService: AsistenciaClienteService?=null
    private var registroAsistenciaCliente:List<AsistenciaCliente>?=null

    private var clienteService: ClienteService?=null
    private var registroCliente:List<Cliente>?=null


    private val objutilidad= Util()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento_asistencia_cliente,container,false)
        //creamos los controles
        spCliAsisCli=raiz.findViewById(R.id.spCliAsisCli)
        btnAsistencia=raiz.findViewById(R.id.btnAsistencia)

        registroAsistenciaCliente=ArrayList()
        registroCliente=ArrayList()

        //implementamos el servicio
        asistenciaClienteService= ApiUtil.asistenciaClienteService
        clienteService= ApiUtil.clienteService

        //mostramos las categorias
        MostrarComboCliente(raiz.context)

        //agregamos los eventos
        btnAsistencia.setOnClickListener {
            if(spCliAsisCli.adapter.toString() ==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                spCliAsisCli.requestFocus()
            }else{
                //capturando valores
                poscli=spCliAsisCli.selectedItemPosition
                cli= (registroCliente as ArrayList<Cliente>).get(poscli).nombre.toString()
                codcli= (registroCliente as ArrayList<Cliente>).get(poscli).idcliente


                //enviamos los valores a la clase
                objcliente.idcliente=codcli
                objAsistenciaCliente.cliente=objcliente

                //llamamos al metodo para registrar
                RegistrarAsistenciaCliente(raiz.context,objAsistenciaCliente)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmAsisCliente) as ViewGroup)
                //actualizamos el fragmento
                val fasiscli=FragmentoAsistenciaCliente()
                DialogoCRUD("Asistencia de Cliente","Se registró la asistencia",fasiscli)
            }
        }

        return raiz
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
                    spCliAsisCli.adapter= AdaptadorComboCliente(context,registroCliente)


                }
            }

            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    fun RegistrarAsistenciaCliente(context: Context?, ac: AsistenciaCliente?){
        val call= asistenciaClienteService!!.RegistrarAsistenciaCliente(ac)
        call!!.enqueue(object : Callback<AsistenciaCliente?> {
            override fun onResponse(call: Call<AsistenciaCliente?>, response: Response<AsistenciaCliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<AsistenciaCliente?>, t: Throwable) {
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
        dialogo!!.setPositiveButton("Sí"){
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
    }
}