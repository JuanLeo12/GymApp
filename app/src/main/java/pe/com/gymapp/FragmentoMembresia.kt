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
import pe.com.gymapp.adaptadores.AdaptadorMembresia
import pe.com.gymapp.clases.Membresia
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.MembresiaService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoMembresia : Fragment() {
    private lateinit var txtTiempoMem: EditText
    private lateinit var txtPreMem: EditText
    private lateinit var chkEstMem: CheckBox
    private lateinit var lblCodMem: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstMem: ListView

    val objmembresia= Membresia()

    private var cod=0L
    private var tiem=""
    private var pre=0.0
    private var est=false
    private var fila=-1

    private var membresiaService: MembresiaService?=null

    private var registromembresia:List<Membresia>?=null

    var objutilidad= Util()

    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoMembresia? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento_membresia,container,false)
        //creamos los controles
        txtTiempoMem=raiz.findViewById(R.id.txtTiempoMem)
        txtPreMem=raiz.findViewById(R.id.txtPreMem)
        chkEstMem=raiz.findViewById(R.id.chkEstMem)
        lblCodMem=raiz.findViewById(R.id.lblCodMem)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstMem=raiz.findViewById(R.id.lstMem)

        registromembresia=ArrayList()

        //implementamos el servicio
        membresiaService= ApiUtil.membresiaService

        //mostramos las categorias
        MostrarMembresia(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtTiempoMem.getText().toString()=="" || txtPreMem.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                txtTiempoMem.requestFocus()
            }else{
                //capturando valores
                tiem=txtTiempoMem.getText().toString()
                pre=txtPreMem.getText().toString().toDouble()
                est=if(chkEstMem.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objmembresia.tiempo=tiem
                objmembresia.precio=pre
                objmembresia.estado=est
                //llamamos al metodo para registrar
                RegistrarMembresia(raiz.context,objmembresia)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmMembresia) as ViewGroup)
                //actualizamos el fragmento
                val fmembresia=FragmentoMembresia()
                DialogoCRUD("Registro de Membresía","Se registro la membresía correctamente",fmembresia)
            }
        }

        lstMem.setOnItemClickListener(
            AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            //asignamos los valores a cada control
            lblCodMem.setText(""+(registromembresia as ArrayList<Membresia>).get(fila).idmembresia)
            txtTiempoMem.setText(""+(registromembresia as ArrayList<Membresia>).get(fila).tiempo)
            txtPreMem.setText(""+(registromembresia as ArrayList<Membresia>).get(fila).precio)
            if((registromembresia as ArrayList<Membresia>).get(fila).estado){
                chkEstMem.setChecked(true)
            }else{
                chkEstMem.setChecked(false)
            }
        })

        btnActualizar.setOnClickListener {
            if(fila>=0){
                cod=lblCodMem.getText().toString().toLong()
                tiem=txtTiempoMem.getText().toString()
                pre=txtPreMem.getText().toString().toDouble()
                est=if(chkEstMem.isChecked){
                    true
                }else{
                    false
                }
                objmembresia.idmembresia=cod
                objmembresia.tiempo=tiem
                objmembresia.precio=pre
                objmembresia.estado=est

                ActualizarMembresia(raiz.context,objmembresia,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmMembresia) as ViewGroup)
                val fmembresia=FragmentoMembresia()
                DialogoCRUD("Actualización de Membresía","Se actualizó la Membresía",fmembresia)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstMem.requestFocus()
            }
        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodMem.getText().toString().toLong()

                objmembresia.idmembresia=cod

                EliminarMembresia(raiz.context,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmMembresia) as ViewGroup)
                val fmembresia=FragmentoMembresia()
                DialogoCRUD("Eliminación de Membresía","Se eliminó la Membresía",fmembresia)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstMem.requestFocus()
            }
        }

        return raiz

    }

    fun MostrarMembresia(context: Context?){
        val call= membresiaService!!.MostrarMembresia()
        call!!.enqueue(object : Callback<List<Membresia>?> {
            override fun onResponse(
                call: Call<List<Membresia>?>,
                response: Response<List<Membresia>?>
            ) {
                if(response.isSuccessful){
                    registromembresia=response.body()
                    lstMem.adapter= AdaptadorMembresia(context,registromembresia)
                }
            }

            override fun onFailure(call: Call<List<Membresia>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar categoria
    fun RegistrarMembresia(context: Context?, m: Membresia?){
        val call= membresiaService!!.RegistrarMembresia(m)
        call!!.enqueue(object : Callback<Membresia?> {
            override fun onResponse(call: Call<Membresia?>, response: Response<Membresia?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<Membresia?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    //creamos una funcion para actualizar
    fun ActualizarMembresia(context: Context?,m: Membresia?,id:Long){
        val call= membresiaService!!.ActualizarMembresia(id,m)
        call!!.enqueue(object :Callback<Membresia?>{
            override fun onResponse(call: Call<Membresia?>, response: Response<Membresia?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<Membresia?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarMembresia(context: Context?,id:Long){
        val call= membresiaService!!.EliminarMembresia(id)
        call!!.enqueue(object :Callback<Membresia?>{
            override fun onResponse(call: Call<Membresia?>, response: Response<Membresia?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Membresia?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }



    //creamos una función para los cuadros de dialogo del CRUD
    fun DialogoCRUD(titulo:String,mensaje:String,fragmento:Fragment){
        dialogo=AlertDialog.Builder(context)
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
        _binding = null
    }
}