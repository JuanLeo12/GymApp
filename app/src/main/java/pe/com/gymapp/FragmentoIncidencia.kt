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
import pe.com.gymapp.adaptadores.*
import pe.com.gymapp.clases.*
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.*
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class FragmentoIncidencia : Fragment() {
    private lateinit var spCliInc: Spinner
    private lateinit var spEmpInc: Spinner
    //private lateinit var txtFechaInc: EditText
    private lateinit var txtDescInc: EditText
    private lateinit var lblCodInc: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var lstInc: ListView

    private val objincidencia= Incidencia()

    private val objcliente= Cliente()
    private val objempleado= Empleado()


    private var cod=0L
    private var fech=""
    private var desc=""
    private var cli=""
    private var emp=""
    private var codcli=0L
    private var codemp=0L
    private var fila=-1
    private var indicecli=-1
    private var indiceemp=-1
    private var poscli=-1
    private var posemp=-1

    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null



    private var incidenciaService: IncidenciaService?=null
    private var registroIncidencia:List<Incidencia>?=null

    private var clienteService: ClienteService?=null
    private var registroCliente:List<Cliente>?=null

    private var empleadoService: EmpleadoService?=null
    private var registroEmpleado:List<Empleado>?=null



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
        val raiz=inflater.inflate(R.layout.fragmento_incidencia,container,false)
        //creamos los controles
        spCliInc=raiz.findViewById(R.id.spCliInc)
        spEmpInc=raiz.findViewById(R.id.spEmpInc)
        //txtFechaInc=raiz.findViewById(R.id.txtFechaInc)
        txtDescInc=raiz.findViewById(R.id.txtDescInc)
        lblCodInc=raiz.findViewById(R.id.lblCodInc)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        lstInc=raiz.findViewById(R.id.lstInc)

        registroIncidencia=ArrayList()
        registroCliente=ArrayList()
        registroEmpleado=ArrayList()

        //implementamos el servicio
        incidenciaService= ApiUtil.incidenciaService
        clienteService= ApiUtil.clienteService
        empleadoService= ApiUtil.empleadoService

        //mostramos las categorias
        MostrarIncidencia(raiz.context)
        MostrarComboCliente(raiz.context)
        MostrarComboEmpleado(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(spCliInc.adapter.toString() =="" || spEmpInc.adapter.toString() =="" || txtDescInc.getText().toString()=="" ){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                spCliInc.requestFocus()
            }else{
                //capturando valores
                //fech=txtFechaInc.text.toString()
                desc= txtDescInc.text.toString()
                poscli=spCliInc.selectedItemPosition
                cli= (registroCliente as ArrayList<Cliente>).get(poscli).nombre.toString()
                codcli= (registroCliente as ArrayList<Cliente>).get(poscli).idcliente
                posemp=spEmpInc.selectedItemPosition
                emp= (registroEmpleado as ArrayList<Empleado>).get(posemp).nombre.toString()
                codemp= (registroEmpleado as ArrayList<Empleado>).get(posemp).idempleado


                //enviamos los valores a la clase
                //objincidencia.fecha=fech
                objincidencia.descripcion=desc

                objcliente.idcliente=codcli
                objincidencia.cliente=objcliente

                objempleado.idempleado=codemp
                objincidencia.empleado=objempleado
                //llamamos al metodo para registrar
                RegistrarIncidencia(raiz.context,objincidencia)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmIncidencia) as ViewGroup)
                //actualizamos el fragmento
                val fincidencia=FragmentoIncidencia()
                DialogoCRUD("Registro de Incidencia","Se registró la incidencia corrrectamente",fincidencia)
            }
        }

        lstInc.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodInc.text = ""+ (registroIncidencia as ArrayList<Incidencia>).get(fila).idincidencia
            //txtFechaInc.setText (""+(registroIncidencia as ArrayList<Incidencia>) .get(fila).fecha)
            txtDescInc.setText (""+(registroIncidencia as ArrayList<Incidencia>) .get(fila).descripcion)
            for(x in (registroCliente as ArrayList<Cliente>).indices){
                if((registroCliente as ArrayList<Cliente>).get(x).nombre== (registroIncidencia as ArrayList<Incidencia>).get(fila).cliente?.nombre){
                    indicecli=x
                }
            }
            spCliInc.setSelection(indicecli)
            for(x in (registroEmpleado as ArrayList<Empleado>).indices){
                if((registroEmpleado as ArrayList<Empleado>).get(x).nombre== (registroIncidencia as ArrayList<Incidencia>).get(fila).empleado?.nombre){
                    indiceemp=x
                }
            }
            spEmpInc.setSelection(indicecli)
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodInc.text.toString().toLong()
                //fech=txtFechaInc.text.toString()
                desc= txtDescInc.text.toString()
                poscli=spCliInc.selectedItemPosition
                cli= (registroCliente as ArrayList<Cliente>).get(poscli).nombre.toString()
                codcli= (registroCliente as ArrayList<Cliente>).get(poscli).idcliente
                posemp=spEmpInc.selectedItemPosition
                emp= (registroEmpleado as ArrayList<Empleado>).get(posemp).nombre.toString()
                codemp= (registroEmpleado as ArrayList<Empleado>).get(posemp).idempleado

                //enviamos los valores a la clase
                objincidencia.idincidencia=cod
                //objincidencia.fecha=fech
                objincidencia.descripcion=desc

                objcliente.idcliente=codcli
                objincidencia.cliente=objcliente

                objempleado.idempleado=codemp
                objincidencia.empleado=objempleado

                //llamamos a la funcion para registrar
                ActualizarIncidencia(raiz.context,objincidencia,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmIncidencia) as ViewGroup)
                val fincidencia=FragmentoIncidencia()
                DialogoCRUD("Actualización de la Incidencia","Se actualizó la incidencia correctamente",fincidencia)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstInc.requestFocus()
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
                    spCliInc.adapter= AdaptadorComboCliente(context,registroCliente)


                }
            }

            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarComboEmpleado(context: Context?){
        val call= empleadoService!!.MostrarEmpleadoPersonalizado()
        call!!.enqueue(object : Callback<List<Empleado>?> {
            override fun onResponse(
                call: Call<List<Empleado>?>,
                response: Response<List<Empleado>?>
            ) {
                if(response.isSuccessful){
                    registroEmpleado=response.body()
                    spEmpInc.adapter= AdaptadorComboEmpleado(context,registroEmpleado)


                }
            }

            override fun onFailure(call: Call<List<Empleado>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarIncidencia(context: Context?){
        val call= incidenciaService!!.MostrarIncidencia()
        call!!.enqueue(object : Callback<List<Incidencia>?> {
            override fun onResponse(
                call: Call<List<Incidencia>?>,
                response: Response<List<Incidencia>?>
            ) {
                if(response.isSuccessful){
                    registroIncidencia=response.body()
                    lstInc.adapter= AdaptadorIncidencia(context,registroIncidencia)
                }
            }

            override fun onFailure(call: Call<List<Incidencia>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun RegistrarIncidencia(context: Context?, i: Incidencia?){
        val call= incidenciaService!!.RegistrarIncidencia(i)
        call!!.enqueue(object : Callback<Incidencia?> {
            override fun onResponse(call: Call<Incidencia?>, response: Response<Incidencia?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<Incidencia?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    fun ActualizarIncidencia(context: Context?, i: Incidencia?, id:Long){
        val call= incidenciaService!!.ActualizarIncidencia(id,i)
        call!!.enqueue(object : Callback<Incidencia?> {
            override fun onResponse(call: Call<Incidencia?>, response: Response<Incidencia?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<Incidencia?>, t: Throwable) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}