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
import pe.com.gymapp.adaptadores.AdaptadorMaquina
import pe.com.gymapp.clases.Maquina
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.MaquinaService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoMaquina : Fragment() {
    private lateinit var txtNomMaq: EditText
    private lateinit var txtPreComMaq: EditText
    private lateinit var txtCanMaq: EditText
    private lateinit var chkEstMaq: CheckBox
    private lateinit var lblCodMaq: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstMaq: ListView

    val objmaquina= Maquina()

    private var cod=0L
    private var nom=""
    private var precom=0.0
    private var can=0.0
    private var est=false
    private var fila=-1

    private var maquinaService: MaquinaService?=null

    private var registromaquina:List<Maquina>?=null

    var objutilidad= Util()

    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoMaquina? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento_maquina,container,false)
        //creamos los controles
        txtNomMaq=raiz.findViewById(R.id.txtNomMaq)
        txtPreComMaq=raiz.findViewById(R.id.txtPreComMaq)
        txtCanMaq=raiz.findViewById(R.id.txtCanMaq)
        chkEstMaq=raiz.findViewById(R.id.chkEstMaq)
        lblCodMaq=raiz.findViewById(R.id.lblCodMaq)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstMaq=raiz.findViewById(R.id.lstMaq)

        registromaquina=ArrayList()

        //implementamos el servicio
        maquinaService= ApiUtil.maquinaService

        //mostramos las categorias
        MostrarMaquina(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtNomMaq.getText().toString()==""||txtPreComMaq.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                txtNomMaq.requestFocus()
            }else{
                //capturando valores
                nom=txtNomMaq.getText().toString()
                precom=txtPreComMaq.getText().toString().toDouble()
                est=if(chkEstMaq.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objmaquina.nombre=nom
                objmaquina.preciocompramaq=precom
                objmaquina.cantidad=can
                objmaquina.estado=est
                //llamamos al metodo para registrar
                RegistrarMaquina(raiz.context,objmaquina)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmMaquina) as ViewGroup)
                //actualizamos el fragmento
                val fmaquina=FragmentoMaquina()
                DialogoCRUD("Registro de Máquina","Se registró $nom",fmaquina)
            }
        }

        lstMaq.setOnItemClickListener(
            AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            //asignamos los valores a cada control
            lblCodMaq.setText(""+(registromaquina as ArrayList<Maquina>).get(fila).idmaquina)
            txtNomMaq.setText(""+(registromaquina as ArrayList<Maquina>).get(fila).nombre)
            txtPreComMaq.setText(""+(registromaquina as ArrayList<Maquina>).get(fila).preciocompramaq)
            txtCanMaq.setText(""+(registromaquina as ArrayList<Maquina>).get(fila).cantidad)
            if((registromaquina as ArrayList<Maquina>).get(fila).estado){
                chkEstMaq.setChecked(true)
            }else{
                chkEstMaq.setChecked(false)
            }
        })

        btnActualizar.setOnClickListener {
            if(fila>=0){
                cod=lblCodMaq.getText().toString().toLong()
                nom=txtNomMaq.getText().toString()
                precom=txtPreComMaq.getText().toString().toDouble()
                can=txtCanMaq.getText().toString().toDouble()
                est=if(chkEstMaq.isChecked){
                    true
                }else{
                    false
                }
                objmaquina.idmaquina=cod
                objmaquina.nombre=nom
                objmaquina.preciocompramaq=precom
                objmaquina.cantidad=can
                objmaquina.estado=est

                ActualizarMaquina(raiz.context,objmaquina,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmMaquina) as ViewGroup)
                val fmaquina=FragmentoMaquina()
                DialogoCRUD("Actualización de Máquina","Se actualizó la Máquina",fmaquina)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstMaq.requestFocus()
            }
        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodMaq.getText().toString().toLong()

                objmaquina.idmaquina=cod

                EliminarMaquina(raiz.context,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmMaquina) as ViewGroup)
                val fmaquina=FragmentoMaquina()
                DialogoCRUD("Eliminación de Maquina","Se eliminó la Máquina",fmaquina)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstMaq.requestFocus()
            }
        }

        return raiz

    }

    fun MostrarMaquina(context: Context?){
        val call= maquinaService!!.MostrarMaquina()
        call!!.enqueue(object : Callback<List<Maquina>?> {
            override fun onResponse(
                call: Call<List<Maquina>?>,
                response: Response<List<Maquina>?>
            ) {
                if(response.isSuccessful){
                    registromaquina=response.body()
                    lstMaq.adapter= AdaptadorMaquina(context,registromaquina)
                }
            }

            override fun onFailure(call: Call<List<Maquina>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar categoria
    fun RegistrarMaquina(context: Context?, m: Maquina?){
        val call= maquinaService!!.RegistrarMaquina(m)
        call!!.enqueue(object : Callback<Maquina?> {
            override fun onResponse(call: Call<Maquina?>, response: Response<Maquina?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<Maquina?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    //creamos una funcion para actualizar
    fun ActualizarMaquina(context: Context?,m: Maquina?,id:Long){
        val call= maquinaService!!.ActualizarMaquina(id,m)
        call!!.enqueue(object :Callback<Maquina?>{
            override fun onResponse(call: Call<Maquina?>, response: Response<Maquina?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<Maquina?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarMaquina(context: Context?,id:Long){
        val call= maquinaService!!.EliminarMaquina(id)
        call!!.enqueue(object :Callback<Maquina?>{
            override fun onResponse(call: Call<Maquina?>, response: Response<Maquina?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Maquina?>, t: Throwable) {
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