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
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import pe.com.gymapp.adaptadores.AdaptadorComboEmpleado
import pe.com.gymapp.adaptadores.AdaptadorUsuario
import pe.com.gymapp.clases.Empleado
import pe.com.gymapp.clases.Usuario
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.EmpleadoService
import pe.com.gymapp.servicios.UsuarioService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentoUsuario : Fragment() {

    private lateinit var txtUsername:EditText
    private lateinit var txtPass:EditText
    private lateinit var spUsuEmp:Spinner
    private lateinit var chkEstUsu:CheckBox
    private lateinit var lblCodUsu:TextView
    private lateinit var btnRegistrar:Button
    private lateinit var btnActualizar:Button
    private lateinit var btnEliminar:Button
    private lateinit var lstUsu:ListView

    private val objusuario=Usuario()
    private val objempleado=Empleado()

    private var cod=0L
    private var usu=""
    private var pass=""
    private var codemp=0L
    private var nomemp=""
    private var est=false
    private var fila=-1
    private var indice=-1
    private var pos=-1

    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null

    private var usuarioService: UsuarioService?=null
    private var empleadoService: EmpleadoService?=null

    private var registrousuario:List<Usuario>?=null
    private var registroempleado:List<Empleado>?=null

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
        val raiz= inflater.inflate(R.layout.fragmento_usuario, container, false)

        txtUsername=raiz.findViewById(R.id.txtUsername)
        txtPass=raiz.findViewById(R.id.txtPass)
        spUsuEmp=raiz.findViewById(R.id.spUsuEmp)
        chkEstUsu=raiz.findViewById(R.id.chkEstUsu)
        lblCodUsu=raiz.findViewById(R.id.lblCodUsu)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstUsu=raiz.findViewById(R.id.lstUsu)


        //creamos el registro categoria
        registrousuario=ArrayList()
        registroempleado=ArrayList()
        //implementamos el servicio
        usuarioService= ApiUtil.usuarioService
        empleadoService= ApiUtil.empleadoService

        //cargamos el combo categoria
        MostrarComboEmpleado(raiz.context)
        MostrarUsuario(raiz.context)

        //llamamos a los eventos
        btnRegistrar.setOnClickListener {
            if(txtUsername.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el usuario")
                txtUsername.requestFocus()
            }else if(txtPass.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa la contraseña")
                txtPass.requestFocus()
            }else if(spUsuEmp.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione un empleado")
                spUsuEmp.requestFocus()
            }else{
                //capturando valores
                usu=txtUsername.text.toString()
                pass=txtPass.text.toString()
                pos=spUsuEmp.selectedItemPosition
                codemp= (registroempleado as ArrayList<Empleado>).get(pos).idempleado
                nomemp= (registroempleado as ArrayList<Empleado>).get(pos).nombre.toString()
                est=if(chkEstUsu.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objusuario.usuario=usu
                objusuario.contrasena=pass

                objempleado.idempleado=codemp
                objusuario.empleado=objempleado

                objusuario.estado=est

                //llamamos a la funcion para registrar
                RegistrarUsuario(raiz.context,objusuario)
                val fusuario=FragmentoUsuario()
                DialogoCRUD("Registro de Usuario","Se registro el usuario correctamente",fusuario)
            }
        }

        lstUsu.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodUsu.setText(""+ (registrousuario as ArrayList<Usuario>).get(fila).idusuario)
            txtUsername.setText(""+ (registrousuario as ArrayList<Usuario>).get(fila).usuario)
            txtPass.setText(""+ (registrousuario as ArrayList<Usuario>).get(fila).contrasena)
            for(x in (registroempleado as ArrayList<Empleado>).indices){
                if((registroempleado as ArrayList<Empleado>).get(x).nombre== (registrousuario as ArrayList<Usuario>).get(fila).empleado?.nombre){
                    indice=x
                }
            }
            spUsuEmp.setSelection(indice)
            if((registrousuario as ArrayList<Usuario>).get(fila).estado){
                chkEstUsu.setChecked(true)
            }else{
                chkEstUsu.setChecked(false)
            }
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodUsu.text.toString().toLong()
                usu=txtUsername.text.toString()
                pass=txtPass.text.toString()
                pos=spUsuEmp.selectedItemPosition
                codemp= (registroempleado as ArrayList<Empleado>).get(pos).idempleado
                nomemp= (registroempleado as ArrayList<Empleado>).get(pos).nombre.toString()
                est=if(chkEstUsu.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objusuario.idusuario=cod
                objusuario.usuario=usu
                objusuario.contrasena=pass

                objempleado.idempleado=codemp
                objusuario.empleado=objempleado

                objusuario.estado=est

                //llamamos a la funcion para registrar
                ActualizarUsuario(raiz.context,objusuario,cod)
                val fusuario=FragmentoUsuario()
                DialogoCRUD("Actualizacion de Usuario","Se actualizó el usuario correctamente",fusuario)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstUsu.requestFocus()
            }

        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodUsu.text.toString().toLong()
                usu=txtUsername.text.toString()


                //llamamos a la funcion para registrar
                EliminarUsuario(raiz.context,cod)
                val fusuario=FragmentoUsuario()
                DialogoEliminar("Eliminacion de Usuario","¿Desea eliminar el usuario?",fusuario)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstUsu.requestFocus()
            }
        }

        return raiz
    }

    //creamos una funcion para mostrar el combo de la categoria
    fun MostrarComboEmpleado(context: Context?){
        val call= empleadoService!!.MostrarEmpleadoPersonalizado()
        call!!.enqueue(object : Callback<List<Empleado>?> {
            override fun onResponse(
                call: Call<List<Empleado>?>,
                response: Response<List<Empleado>?>
            ) {
                if(response.isSuccessful){
                    registroempleado=response.body()
                    spUsuEmp.adapter= AdaptadorComboEmpleado(context,registroempleado)


                }
            }

            override fun onFailure(call: Call<List<Empleado>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para mostrar los producto
    fun MostrarUsuario(context: Context?){
        val call= usuarioService!!.MostrarUsuarioPersonalizado()
        call!!.enqueue(object : Callback<List<Usuario>?> {
            override fun onResponse(
                call: Call<List<Usuario>?>,
                response: Response<List<Usuario>?>
            ) {
                if(response.isSuccessful){
                    registrousuario=response.body()
                    lstUsu.adapter=AdaptadorUsuario(context,registrousuario)
                }
            }

            override fun onFailure(call: Call<List<Usuario>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar producto
    fun RegistrarUsuario(context: Context?, u: Usuario?){
        val call= usuarioService!!.RegistrarUsuario(u)
        call!!.enqueue(object : Callback<Usuario?> {
            override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro")
                }
            }

            override fun onFailure(call: Call<Usuario?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar producto
    fun ActualizarUsuario(context: Context?, u: Usuario?, id:Long){
        val call= usuarioService!!.ActualizarUsuario(id,u)
        call!!.enqueue(object : Callback<Usuario?> {
            override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Usuario?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarUsuario(context: Context?, id:Long){
        val call= usuarioService!!.EliminarUsuario(id)
        call!!.enqueue(object : Callback<Usuario?> {
            override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Usuario?>, t: Throwable) {
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
        dialogo=AlertDialog.Builder(context)
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