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
import pe.com.gymapp.adaptadores.AdaptadorRol
import pe.com.gymapp.clases.Rol
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.RolService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoRol : Fragment() {
    private lateinit var txtRol: EditText
    private lateinit var chkEstRol: CheckBox
    private lateinit var lblCodRol: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstRol: ListView

    val objrol= Rol()

    private var cod=0L
    private var nom=""
    private var est=false
    private var fila=-1

    private var rolService: RolService?=null

    private var registrorol:List<Rol>?=null

    var objutilidad= Util()

    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoRol? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento_rol,container,false)
        //creamos los controles
        txtRol=raiz.findViewById(R.id.txtRol)
        chkEstRol=raiz.findViewById(R.id.chkEstRol)
        lblCodRol=raiz.findViewById(R.id.lblCodRol)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstRol=raiz.findViewById(R.id.lstRol)

        registrorol=ArrayList()

        //implementamos el servicio
        rolService= ApiUtil.rolService

        //mostramos las categorias
        MostrarRol(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtRol.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingrese el rol")
                txtRol.requestFocus()
            }else{
                //capturando valores
                nom=txtRol.getText().toString()
                est=if(chkEstRol.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objrol.rol=nom
                objrol.estado=est
                //llamamos al metodo para registrar
                RegistrarRol(raiz.context,objrol)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmRol) as ViewGroup)
                //actualizamos el fragmento
                val frol=FragmentoRol()
                DialogoCRUD("Registro de Rol","Se registró el Rol",frol)
            }
        }

        lstRol.setOnItemClickListener(
            AdapterView.OnItemClickListener
            { parent, view, position, id ->
                fila=position
                //asignamos los valores a cada control
                lblCodRol.setText(""+(registrorol as ArrayList<Rol>).get(fila).idrol)
                txtRol.setText(""+(registrorol as ArrayList<Rol>).get(fila).rol)
                if((registrorol as ArrayList<Rol>).get(fila).estado){
                    chkEstRol.setChecked(true)
                }else{
                    chkEstRol.setChecked(false)
                }
            })

        btnActualizar.setOnClickListener {
            if(fila>=0){

                cod=lblCodRol.getText().toString().toLong()
                nom=txtRol.getText().toString()
                est=if(chkEstRol.isChecked){
                    true
                }else{
                    false
                }

                objrol.idrol=cod
                objrol.rol=nom
                objrol.estado=est

                ActualizarRol(raiz.context, cod,objrol)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmRol) as ViewGroup)

                val frol=FragmentoRol()
                DialogoCRUD("Actualización de Rol","Se actualizó el Rol",frol)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstRol.requestFocus()


            }
        }


        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodRol.getText().toString().toLong()

                objrol.idrol=cod

                EliminarRol(raiz.context,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmRol) as ViewGroup)
                val frol=FragmentoRol()
                DialogoEliminar("Eliminación de Rol","¿Desea eliminar el rol?",frol)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstRol.requestFocus()
            }
        }



        return raiz

    }

    fun MostrarRol(context: Context?){
        val call= rolService!!.MostrarRol()
        call!!.enqueue(object : Callback<List<Rol>?> {
            override fun onResponse(
                call: Call<List<Rol>?>,
                response: Response<List<Rol>?>
            ) {
                if(response.isSuccessful){
                    registrorol=response.body()
                    lstRol.adapter= AdaptadorRol(context,registrorol)
                }
            }

            override fun onFailure(call: Call<List<Rol>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar categoria
    fun RegistrarRol(context: Context?, r: Rol?){
        val call= rolService!!.RegistrarRol(r)
        call!!.enqueue(object : Callback<Rol?> {
            override fun onResponse(call: Call<Rol?>, response: Response<Rol?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro el rol")
                }
            }

            override fun onFailure(call: Call<Rol?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun ActualizarRol(context: Context? ,id:Long,r: Rol?){
        val call=rolService!!.ActualizarRol(id,r)
        call!!.enqueue(object : Callback<Rol?>{
            override fun onResponse(call: Call<Rol?>, response: Response<Rol?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó el rol")
                }
            }

            override fun onFailure(call: Call<Rol?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }
        })
    }

    fun EliminarRol(context: Context?,id:Long){
        val call= rolService!!.EliminarRol(id)
        call!!.enqueue(object :Callback<Rol?>{
            override fun onResponse(call: Call<Rol?>, response: Response<Rol?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Rol?>, t: Throwable) {
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
        _binding = null
    }
}