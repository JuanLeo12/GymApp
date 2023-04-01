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


class FragmentoCompraMaquina : Fragment() {
    private lateinit var spMaq: Spinner
    private lateinit var spProvComMaq: Spinner
    private lateinit var txtCantComMaq: EditText
    private lateinit var lblCodCMaq: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var lstComMaq: ListView

    private val objcompramaquina= CompraMaquina()

    private val objmaquina= Maquina()
    private val objproveedor= Proveedor()

    private var cod=0L
    private var cant=0
    private var maq=""
    private var prov=""
    private var codmaq=0L
    private var codprov=0L
    private var fila=-1
    private var indicemaq=-1
    private var indiceprov=-1
    private var posmaq=-1
    private var posprov=-1

    private var compraMaquinaService: CompraMaquinaService?=null
    private var registrocompraMaquina:List<CompraMaquina>?=null

    private var maquinaService: MaquinaService?=null
    private var registroMaquina:List<Maquina>?=null

    private var proveedorService: ProveedorService?=null
    private var registroproveedor:List<Proveedor>?=null

    private val objutilidad= Util()



    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoCompraMaquina?=null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento_compra_maquina,container,false)
        //creamos los controles
        spMaq=raiz.findViewById(R.id.spMaq)
        spProvComMaq=raiz.findViewById(R.id.spProvComMaq)
        txtCantComMaq=raiz.findViewById(R.id.txtCantComMaq)
        lblCodCMaq=raiz.findViewById(R.id.lblCodCMaq)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        lstComMaq=raiz.findViewById(R.id.lstComMaq)

        registrocompraMaquina=ArrayList()
        registroMaquina=ArrayList()
        registroproveedor=ArrayList()

        //implementamos el servicio
        compraMaquinaService= ApiUtil.compraMaquinaService
        maquinaService= ApiUtil.maquinaService
        proveedorService= ApiUtil.proveedorService

        //mostramos las categorias
        MostrarCompraMaquina(raiz.context)
        MostrarComboMaquina(raiz.context)
        MostrarComboProveedor(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(spMaq.adapter.toString() =="" || spProvComMaq.adapter.toString() =="" || txtCantComMaq.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                spMaq.requestFocus()
            }else{
                //capturando valores
                cant= txtCantComMaq.text.toString().toInt()
                posmaq=spMaq.selectedItemPosition
                maq= (registroMaquina as ArrayList<Maquina>).get(posmaq).nombre.toString()
                codmaq= (registroMaquina as ArrayList<Maquina>).get(posmaq).idmaquina
                posprov=spProvComMaq.selectedItemPosition
                prov= (registroproveedor as ArrayList<Proveedor>).get(posprov).nombre.toString()
                codprov= (registroproveedor as ArrayList<Proveedor>).get(posprov).idproveedor


                //enviamos los valores a la clase
                objcompramaquina.cantidad=cant.toDouble()

                objmaquina.idmaquina=codmaq
                objcompramaquina.maquina=objmaquina

                objproveedor.idproveedor=codprov
                objcompramaquina.proveedor=objproveedor
                //llamamos al metodo para registrar
                RegistrarCompraMaquina(raiz.context,objcompramaquina)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmCompMaq) as ViewGroup)
                //actualizamos el fragmento
                val fcompmaq=FragmentoCompraMaquina()
                DialogoCRUD("Registro de Compra de Máquina","Se registró la compra corrrectamente",fcompmaq)
            }
        }

        lstComMaq.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodCMaq.setText(""+ (registrocompraMaquina as ArrayList<CompraMaquina>).get(fila).idcompmaq)
            txtCantComMaq.setText(""+ (registrocompraMaquina as ArrayList<CompraMaquina>).get(fila).cantidad.toInt())
            for(x in (registroMaquina as ArrayList<Maquina>).indices){
                if((registroMaquina as ArrayList<Maquina>).get(x).nombre== (registrocompraMaquina as ArrayList<CompraMaquina>).get(fila).maquina?.nombre){
                    indicemaq=x
                }
            }
            spProvComMaq.setSelection(indicemaq)
            for(x in (registroproveedor as ArrayList<Proveedor>).indices){
                if((registroproveedor as ArrayList<Proveedor>).get(x).nombre== (registrocompraMaquina as ArrayList<CompraMaquina>).get(fila).proveedor?.nombre){
                    indiceprov=x
                }
            }
            spProvComMaq.setSelection(indiceprov)
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodCMaq.text.toString().toLong()
                cant= txtCantComMaq.text.toString().toInt()
                posmaq=spMaq.selectedItemPosition
                maq= (registroMaquina as ArrayList<Maquina>).get(posmaq).nombre.toString()
                codmaq= (registroMaquina as ArrayList<Maquina>).get(posmaq).idmaquina
                posprov=spProvComMaq.selectedItemPosition
                prov= (registroproveedor as ArrayList<Proveedor>).get(posprov).nombre.toString()
                codprov= (registroproveedor as ArrayList<Proveedor>).get(posprov).idproveedor

                //enviamos los valores a la clase
                objcompramaquina.idcompmaq=cod
                objcompramaquina.cantidad=cant.toDouble()


                objmaquina.idmaquina=codmaq
                objcompramaquina.maquina=objmaquina

                objproveedor.idproveedor=codprov
                objcompramaquina.proveedor=objproveedor

                //llamamos a la funcion para registrar
                ActualizarCompraMaquina(raiz.context,objcompramaquina,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmCompMaq) as ViewGroup)
                val fcompramaq=FragmentoCompraMaquina()
                DialogoCRUD("Actualización de la Compra","Se actualizó la compra correctamente",fcompramaq)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstComMaq.requestFocus()
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
                    spMaq.adapter= AdaptadorComboMaquina(context,registroMaquina)


                }
            }

            override fun onFailure(call: Call<List<Maquina>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarComboProveedor(context: Context?){
        val call= proveedorService!!.MostrarProveedorPersonalizado()
        call!!.enqueue(object : Callback<List<Proveedor>?> {
            override fun onResponse(
                call: Call<List<Proveedor>?>,
                response: Response<List<Proveedor>?>
            ) {
                if(response.isSuccessful){
                    registroproveedor=response.body()
                    spProvComMaq.adapter= AdaptadorComboProveedor(context,registroproveedor)


                }
            }

            override fun onFailure(call: Call<List<Proveedor>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarCompraMaquina(context: Context?){
        val call= compraMaquinaService!!.MostrarCompraMaquina()
        call!!.enqueue(object : Callback<List<CompraMaquina>?> {
            override fun onResponse(
                call: Call<List<CompraMaquina>?>,
                response: Response<List<CompraMaquina>?>
            ) {
                if(response.isSuccessful){
                    registrocompraMaquina=response.body()
                    lstComMaq.adapter= AdaptadorCompraMaquina(context,registrocompraMaquina)
                }
            }

            override fun onFailure(call: Call<List<CompraMaquina>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun RegistrarCompraMaquina(context: Context?, cm: CompraMaquina?){
        val call= compraMaquinaService!!.RegistrarCompraMaquina(cm)
        call!!.enqueue(object : Callback<CompraMaquina?> {
            override fun onResponse(call: Call<CompraMaquina?>, response: Response<CompraMaquina?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro la compra de la máquina")
                }
            }

            override fun onFailure(call: Call<CompraMaquina?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    fun ActualizarCompraMaquina(context: Context?, cm: CompraMaquina?, id:Long){
        val call= compraMaquinaService!!.ActualizarCompraMaquina(id,cm)
        call!!.enqueue(object : Callback<CompraMaquina?> {
            override fun onResponse(call: Call<CompraMaquina?>, response: Response<CompraMaquina?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<CompraMaquina?>, t: Throwable) {
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
        _binding = null
    }
}