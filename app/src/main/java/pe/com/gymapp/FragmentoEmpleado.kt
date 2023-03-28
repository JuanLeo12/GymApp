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
import pe.com.gymapp.adaptadores.AdaptadorComboGenero
import pe.com.gymapp.adaptadores.AdaptadorComboRol
import pe.com.gymapp.adaptadores.AdaptadorEmpleado
import pe.com.gymapp.clases.Empleado
import pe.com.gymapp.clases.Genero
import pe.com.gymapp.clases.Rol
import pe.com.gymapp.databinding.FragmentoEmpleadoBinding
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.EmpleadoService
import pe.com.gymapp.servicios.GeneroService
import pe.com.gymapp.servicios.RolService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoEmpleado : Fragment() {
    //declaramos los controles
    private lateinit var txtNomEmp: EditText
    private lateinit var txtApPatEmp: EditText
    private lateinit var txtApMatEmp: EditText
    private lateinit var txtTelfEmp: EditText
    private lateinit var txtCorrEmp: EditText
    private lateinit var spGenEmp: Spinner
    private lateinit var txtDirEmp: EditText
    private lateinit var spRolEmp: Spinner
    private lateinit var lblCodEmp: TextView
    private lateinit var chkEstEmp: CheckBox
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstEmp: ListView

    //cremamos un objeto de la clase categoria
    private val objgenero=Genero()
    private val objrol= Rol()
    private val objempleado=Empleado()

    //creamos variables
    private var cod=0L
    private var nom=""
    private var appat=""
    private var apmat=""
    private var telf=""
    private var corr=""
    private var idgen=0L
    private var gen=""
    private var dir=""
    private var codrol=0L
    private var nomrol=""
    private var est=false
    private var fila=-1
    private var indice=-1
    private var pos=-1


    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null



    //llamamos al servicio
    private var generoService: GeneroService?=null
    private var rolService: RolService?=null
    private var empleadoService: EmpleadoService?=null

    //creamos una lista de tipo categoria
    private var registrogenero:List<Genero>?=null
    private var registrorol:List<Rol>?=null
    private var registroempleado:List<Empleado>?=null

    //creamos un objeto de la clase Util
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
        // Inflate the layout for this fragment
        val raiz=inflater.inflate(R.layout.fragmento_empleado, container, false)


        //creamos los controles
        txtNomEmp=raiz.findViewById(R.id.txtNomEmp)
        txtApPatEmp=raiz.findViewById(R.id.txtApPatEmp)
        txtApMatEmp=raiz.findViewById(R.id.txtApMatEmp)
        txtTelfEmp=raiz.findViewById(R.id.txtTelfEmp)
        txtCorrEmp=raiz.findViewById(R.id.txtCorrEmp)
        spGenEmp=raiz.findViewById(R.id.spGenEmp)
        txtDirEmp=raiz.findViewById(R.id.txtDirEmp)
        spRolEmp=raiz.findViewById(R.id.spRolEmp)
        chkEstEmp=raiz.findViewById(R.id.chkEstEmp)
        lblCodEmp=raiz.findViewById(R.id.lblCodEmp)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstEmp=raiz.findViewById(R.id.lstEmp)


        //creamos el registro categoria
        registrogenero=ArrayList()
        registrorol=ArrayList()
        registroempleado=ArrayList()
        //implementamos el servicio
        generoService=ApiUtil.generoService
        rolService= ApiUtil.rolService
        empleadoService= ApiUtil.empleadoService

        //cargamos el combo categoria
        MostrarComboGenero(raiz.context)
        MostrarComboRol(raiz.context)
        MostrarEmpleado(raiz.context)

        //llamamos a los eventos
        btnRegistrar.setOnClickListener {
            if(txtNomEmp.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el nombre")
                txtNomEmp.requestFocus()
            }else if(txtApPatEmp.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el apellido paterno")
                txtApPatEmp.requestFocus()
            }else if(txtApMatEmp.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el apellido materno")
                txtApMatEmp.requestFocus()
            }else if(txtTelfEmp.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el teléfono")
                txtTelfEmp.requestFocus()
            }else if(txtCorrEmp.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el correo")
                txtCorrEmp.requestFocus()
            }else if(spGenEmp.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione un género")
                spGenEmp.requestFocus()
            }else if(txtDirEmp.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa la dirección")
                txtDirEmp.requestFocus()
            }else if(spRolEmp.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione un rol")
                spRolEmp.requestFocus()
            }else{
                //capturando valores
                nom=txtNomEmp.text.toString()
                appat=txtApPatEmp.text.toString()
                apmat=txtApMatEmp.text.toString()
                telf=txtTelfEmp.text.toString()
                corr=txtCorrEmp.text.toString()
                dir=txtDirEmp.text.toString()
                pos=spRolEmp.selectedItemPosition
                codrol= (registrorol as ArrayList<Rol>).get(pos).idrol
                nomrol= (registrorol as ArrayList<Rol>).get(pos).rol.toString()
                idgen=(registrogenero as ArrayList<Genero>).get(pos).idgenero
                gen= (registrogenero as ArrayList<Genero>).get(pos).genero
                est=if(chkEstEmp.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objempleado.nombre=nom
                objempleado.apepaterno=appat
                objempleado.apematerno=apmat
                objempleado.telefono=telf
                objempleado.correo=corr
                objempleado.direccion=dir

                objgenero.idgenero=idgen
                objempleado.genero= objgenero

                objrol.idrol=codrol
                objempleado.rol=objrol

                objempleado.estado=est

                //llamamos a la funcion para registrar
                RegistrarEmpleado(raiz.context,objempleado)
                val fempleado=FragmentoEmpleado()
                DialogoCRUD("Registro de Empleado","Se registro el empleado correctamente",fempleado)
            }
        }

        lstEmp.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodEmp.setText(""+ (registroempleado as ArrayList<Empleado>).get(fila).idempleado)
            txtNomEmp.setText(""+ (registroempleado as ArrayList<Empleado>).get(fila).nombre)
            txtApPatEmp.setText(""+ (registroempleado as ArrayList<Empleado>).get(fila).apepaterno)
            txtApMatEmp.setText(""+ (registroempleado as ArrayList<Empleado>).get(fila).apematerno)
            txtTelfEmp.setText(""+ (registroempleado as ArrayList<Empleado>).get(fila).telefono)
            txtCorrEmp.setText(""+ (registroempleado as ArrayList<Empleado>).get(fila).correo)
            for(x in (registrorol as ArrayList<Rol>).indices){
                if((registrorol as ArrayList<Rol>).get(x).rol== (registroempleado as ArrayList<Empleado>).get(fila).rol?.rol){
                    indice=x
                }
            }
            spRolEmp.setSelection(indice)
            for(x in (registrogenero as ArrayList<Genero>).indices){
                if((registrogenero as ArrayList<Genero>).get(x).genero== (registroempleado as ArrayList<Empleado>).get(fila).genero?.genero){
                    indice=x
                }
            }
            spGenEmp.setSelection(indice)
            if((registroempleado as ArrayList<Empleado>).get(fila).estado){
                chkEstEmp.setChecked(true)
            }else{
                chkEstEmp.setChecked(false)
            }
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodEmp.text.toString().toLong()
                nom=txtNomEmp.text.toString()
                appat=txtApPatEmp.text.toString()
                apmat=txtApMatEmp.text.toString()
                telf=txtTelfEmp.text.toString()
                corr=txtCorrEmp.text.toString()
                dir=txtDirEmp.text.toString()
                pos=spRolEmp.selectedItemPosition
                codrol= (registrorol as ArrayList<Rol>).get(pos).idrol
                nomrol= (registrorol as ArrayList<Rol>).get(pos).rol.toString()
                idgen=(registrogenero as ArrayList<Genero>).get(pos).idgenero
                gen= (registrogenero as ArrayList<Genero>).get(pos).genero
                est=if(chkEstEmp.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objempleado.idempleado=cod
                objempleado.nombre=nom
                objempleado.apepaterno=appat
                objempleado.apematerno=apmat
                objempleado.telefono=telf
                objempleado.correo=corr
                objempleado.direccion=dir

                objgenero.idgenero=idgen
                objempleado.genero= objgenero

                objrol.idrol=codrol
                objempleado.rol=objrol

                objempleado.estado=est

                //llamamos a la funcion para registrar
                ActualizarEmpleado(raiz.context,objempleado,cod)
                val fempleado=FragmentoEmpleado()
                DialogoCRUD("Actualizacion de Empleado","Se actualizo el empleado correctamente",fempleado)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstEmp.requestFocus()
            }

        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodEmp.text.toString().toLong()
                nom=txtNomEmp.text.toString()


                //llamamos a la funcion para registrar
                EliminarEmpleado(raiz.context,cod)
                val fempleado=FragmentoEmpleado()
                DialogoEliminar("Eliminacion de Empleado","¿Desea eliminar al empleado?",fempleado)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstEmp.requestFocus()
            }
        }

        return raiz
    }

    //creamos una funcion para mostrar el combo de la categoria
    fun MostrarComboGenero(context: Context?){
        val call= generoService!!.MostrarGeneroPersonalizado()
        call!!.enqueue(object : Callback<List<Genero>?> {
            override fun onResponse(
                call: Call<List<Genero>?>,
                response: Response<List<Genero>?>
            ) {
                if(response.isSuccessful){
                    registrogenero=response.body()
                    spGenEmp.adapter=AdaptadorComboGenero(context,registrogenero)


                }
            }

            override fun onFailure(call: Call<List<Genero>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarComboRol(context: Context?){
        val call= rolService!!.MostrarRolPersonalizado()
        call!!.enqueue(object : Callback<List<Rol>?> {
            override fun onResponse(
                call: Call<List<Rol>?>,
                response: Response<List<Rol>?>
            ) {
                if(response.isSuccessful){
                    registrorol=response.body()
                    spRolEmp.adapter=AdaptadorComboRol(context,registrorol)


                }
            }

            override fun onFailure(call: Call<List<Rol>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para mostrar los producto
    fun MostrarEmpleado(context: Context?){
        val call= empleadoService!!.MostrarEmpleado()
        call!!.enqueue(object : Callback<List<Empleado>?> {
            override fun onResponse(
                call: Call<List<Empleado>?>,
                response: Response<List<Empleado>?>
            ) {
                if(response.isSuccessful){
                    registroempleado=response.body()
                    lstEmp.adapter=AdaptadorEmpleado(context,registroempleado)
                }
            }

            override fun onFailure(call: Call<List<Empleado>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar producto
    fun RegistrarEmpleado(context: Context?, e: Empleado?){
        val call= empleadoService!!.RegistrarEmpleado(e)
        call!!.enqueue(object : Callback<Empleado?> {
            override fun onResponse(call: Call<Empleado?>, response: Response<Empleado?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<Empleado?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar producto
    fun ActualizarEmpleado(context: Context?, e: Empleado?, id:Long){
        val call= empleadoService!!.ActualizarEmpleado(id,e)
        call!!.enqueue(object : Callback<Empleado?> {
            override fun onResponse(call: Call<Empleado?>, response: Response<Empleado?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<Empleado?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
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

    //creamos una funcion para los cuadros de dialogo del CRUD
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

    fun DialogoEliminar(titulo:String,mensaje:String,fragmento:Fragment){
        dialogo= AlertDialog.Builder(context)
        dialogo!!.setTitle(titulo)
        dialogo!!.setMessage(mensaje)
        dialogo!!.setCancelable(false)
        dialogo!!.setPositiveButton("Si"){
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