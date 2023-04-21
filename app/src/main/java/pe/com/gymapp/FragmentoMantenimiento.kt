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
import pe.com.gymapp.adaptadores.AdaptadorComboMaquina
import pe.com.gymapp.adaptadores.AdaptadorMantenimiento
import pe.com.gymapp.clases.*
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.*
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class FragmentoMantenimiento : Fragment() {
    private lateinit var spMaqMant: Spinner
    private lateinit var txtfechMant: EditText
    private lateinit var chkEstMant: CheckBox
    private lateinit var lblCodMant: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstMant: ListView

    private val objmant= Mantenimiento()

    private val objMaquina= Maquina()


    private var cod=0L
    private var fech= ""
    private var maq=""
    private var est=false
    private var codmaq=0L
    private var fila=-1
    private var indice=-1
    private var pos=-1

    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null



    private var mantenimientoService: MantenimientoService?=null
    private var registroMantenimiento:List<Mantenimiento>?=null

    private var maquinaService: MaquinaService?=null
    private var registroMaquina:List<Maquina>?=null




    private val objutilidad= Util()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento_mantenimiento,container,false)
        //creamos los controles
        spMaqMant=raiz.findViewById(R.id.spMaqMant)
        txtfechMant=raiz.findViewById(R.id.txtfechMant)
        chkEstMant=raiz.findViewById(R.id.chkEstMant)
        lblCodMant=raiz.findViewById(R.id.lblCodMant)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstMant=raiz.findViewById(R.id.lstMant)

        registroMantenimiento=ArrayList()
        registroMaquina=ArrayList()

        //implementamos el servicio
        mantenimientoService= ApiUtil.mantenimientoService
        maquinaService= ApiUtil.maquinaService

        //mostramos las categorias
        MostrarMantenimiento(raiz.context)
        MostrarComboMaquina(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(spMaqMant.adapter.toString() =="" || txtfechMant.text.toString()=="" ){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                spMaqMant.requestFocus()
            }else{
                //capturando valores
                fech=txtfechMant.text.toString()
                pos=spMaqMant.selectedItemPosition
                maq= (registroMaquina as ArrayList<Maquina>).get(pos).nombre.toString()
                codmaq= (registroMaquina as ArrayList<Maquina>).get(pos).idmaquina
                est=if(chkEstMant.isChecked){
                    true
                }else{
                    false
                }


                //enviamos los valores a la clase
                objmant.fecha=fech

                objMaquina.idmaquina=codmaq
                objmant.maquina=objMaquina

                objmant.estado=est

                //llamamos al metodo para registrar
                RegistrarMantenimiento(raiz.context,objmant)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmMantenimiento) as ViewGroup)
                //actualizamos el fragmento
                val fmant=FragmentoMantenimiento()
                DialogoCRUD("Registro de Mantenimiento de Máquina","Se registró el mantenimiento",fmant)
            }
        }

        lstMant.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodMant.text = ""+ (registroMantenimiento as ArrayList<Mantenimiento>).get(fila).idmantenimiento
            txtfechMant.setText (""+(registroMantenimiento as ArrayList<Mantenimiento>) .get(fila).fecha)
            for(x in (registroMaquina as ArrayList<Maquina>).indices){
                if((registroMaquina as ArrayList<Maquina>).get(x).nombre== (registroMantenimiento as ArrayList<Mantenimiento>).get(fila).maquina?.nombre){
                    indice=x
                }
            }
            spMaqMant.setSelection(indice)

            if((registroMantenimiento as ArrayList<Mantenimiento>).get(fila).estado){
                chkEstMant.setChecked(true)
            }else{
                chkEstMant.setChecked(false)
            }
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodMant.text.toString().toLong()
                fech=txtfechMant.text.toString()
                pos=spMaqMant.selectedItemPosition
                maq= (registroMaquina as ArrayList<Maquina>).get(pos).nombre.toString()
                codmaq= (registroMaquina as ArrayList<Maquina>).get(pos).idmaquina
                est=if(chkEstMant.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objmant.idmantenimiento=cod
                objmant.fecha=fech

                objMaquina.idmaquina=codmaq
                objmant.maquina=objMaquina

                objmant.estado=est

                //llamamos a la funcion para registrar
                ActualizarMantenimiento(raiz.context,objmant,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmMantenimiento) as ViewGroup)
                val fmant=FragmentoMantenimiento()
                DialogoCRUD("Actualización del Mantenimiento","Se actualizó el mantenimiento correctamente",fmant)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstMant.requestFocus()
            }

        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodMant.text.toString().toLong()


                //llamamos a la funcion para registrar
                EliminarMantenimiento(raiz.context,cod)
                val fmant=FragmentoMantenimiento()
                DialogoEliminar("Eliminacion de Mantenimiento","¿Desea eliminar el mantenimiento?",fmant)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstMant.requestFocus()
            }
        }

        return raiz
    }

    fun MostrarComboMaquina(context: Context?){
        val call= maquinaService!!.MostrarMaquinaPersonalizado()
        call!!.enqueue(object : Callback<List<Maquina>?> {
            override fun onResponse(
                call: Call<List<Maquina>?>,
                response: Response<List<Maquina>?>
            ) {
                if(response.isSuccessful){
                    registroMaquina=response.body()
                    spMaqMant.adapter= AdaptadorComboMaquina(context,registroMaquina)


                }
            }

            override fun onFailure(call: Call<List<Maquina>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarMantenimiento(context: Context?){
        val call= mantenimientoService!!.MostrarMantenimientoPersonalizado()
        call!!.enqueue(object : Callback<List<Mantenimiento>?> {
            override fun onResponse(
                call: Call<List<Mantenimiento>?>,
                response: Response<List<Mantenimiento>?>
            ) {
                if(response.isSuccessful){
                    registroMantenimiento=response.body()
                    lstMant.adapter= AdaptadorMantenimiento(context,registroMantenimiento)
                }
            }

            override fun onFailure(call: Call<List<Mantenimiento>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun RegistrarMantenimiento(context: Context?, m: Mantenimiento?){
        val call= mantenimientoService!!.RegistrarMantenimiento(m)
        call!!.enqueue(object : Callback<Mantenimiento?> {
            override fun onResponse(call: Call<Mantenimiento?>, response: Response<Mantenimiento?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<Mantenimiento?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    fun ActualizarMantenimiento(context: Context?, m: Mantenimiento?, id:Long){
        val call= mantenimientoService!!.ActualizarMantenimiento(id,m)
        call!!.enqueue(object : Callback<Mantenimiento?> {
            override fun onResponse(call: Call<Mantenimiento?>, response: Response<Mantenimiento?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<Mantenimiento?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarMantenimiento(context: Context?, id:Long){
        val call= mantenimientoService!!.EliminarMantenimiento(id)
        call!!.enqueue(object : Callback<Mantenimiento?> {
            override fun onResponse(call: Call<Mantenimiento?>, response: Response<Mantenimiento?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Mantenimiento?>, t: Throwable) {
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
    }





}