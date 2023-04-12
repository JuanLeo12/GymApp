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
import android.widget.Spinner
import androidx.fragment.app.FragmentTransaction
import pe.com.gymapp.adaptadores.AdaptadorComboCliente
import pe.com.gymapp.adaptadores.AdaptadorComboEmpleado
import pe.com.gymapp.clases.AsistenciaCliente
import pe.com.gymapp.clases.AsistenciaEmpleado
import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Empleado
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.AsistenciaClienteService
import pe.com.gymapp.servicios.AsistenciaEmpleadoService
import pe.com.gymapp.servicios.ClienteService
import pe.com.gymapp.servicios.EmpleadoService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoAsistenciaEmpleado : Fragment() {
    private lateinit var spEmpAsisEmp: Spinner
    private lateinit var btnAsistencia: Button

    private val objAsistenciaEmpleado= AsistenciaEmpleado()

    private val objEmpleado= Empleado()


    private var cod=0L
    private var fech=""
    private var emp=""
    private var codemp=0L
    private var fila=-1
    private var indiceemp=-1
    private var posemp=-1

    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null



    private var asistenciaEmpleadoService: AsistenciaEmpleadoService?=null
    private var registroAsistenciaEmpleado:List<AsistenciaEmpleado>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_asistencia_empleado,container,false)
        //creamos los controles
        spEmpAsisEmp=raiz.findViewById(R.id.spEmpAsisEmp)
        btnAsistencia=raiz.findViewById(R.id.btnAsistencia)

        registroAsistenciaEmpleado=ArrayList()
        registroEmpleado=ArrayList()

        //implementamos el servicio
        asistenciaEmpleadoService= ApiUtil.asistenciaEmpleadoService
        empleadoService= ApiUtil.empleadoService

        //mostramos las categorias
        MostrarComboEmpleado(raiz.context)

        //agregamos los eventos
        btnAsistencia.setOnClickListener {
            if(spEmpAsisEmp.adapter.toString() ==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                spEmpAsisEmp.requestFocus()
            }else{
                //capturando valores
                posemp=spEmpAsisEmp.selectedItemPosition
                emp= (registroEmpleado as ArrayList<Empleado>).get(posemp).nombre.toString()
                codemp= (registroEmpleado as ArrayList<Empleado>).get(posemp).idempleado


                //enviamos los valores a la clase
                objEmpleado.idempleado=codemp
                objAsistenciaEmpleado.empleado=objEmpleado

                //llamamos al metodo para registrar
                RegistrarAsistenciaEmpleado(raiz.context,objAsistenciaEmpleado)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmAsisEmp) as ViewGroup)
                //actualizamos el fragmento
                val fasisemp=FragmentoAsistenciaEmpleado()
                DialogoCRUD("Asistencia de Empleado","Se registró la asistencia",fasisemp)
            }
        }

        return raiz
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
                    spEmpAsisEmp.adapter= AdaptadorComboEmpleado(context,registroEmpleado)


                }
            }

            override fun onFailure(call: Call<List<Empleado>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    fun RegistrarAsistenciaEmpleado(context: Context?, ae: AsistenciaEmpleado?){
        val call= asistenciaEmpleadoService!!.RegistrarAsistenciaEmpleado(ae)
        call!!.enqueue(object : Callback<AsistenciaEmpleado?> {
            override fun onResponse(call: Call<AsistenciaEmpleado?>, response: Response<AsistenciaEmpleado?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<AsistenciaEmpleado?>, t: Throwable) {
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
        dialogo= AlertDialog.Builder(context)
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