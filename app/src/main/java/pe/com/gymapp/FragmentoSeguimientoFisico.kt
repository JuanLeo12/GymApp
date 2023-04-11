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
import pe.com.gymapp.adaptadores.AdaptadorComboMaquina
import pe.com.gymapp.adaptadores.AdaptadorMantenimiento
import pe.com.gymapp.adaptadores.AdaptadorSeguimientoFisico
import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Mantenimiento
import pe.com.gymapp.clases.Maquina
import pe.com.gymapp.clases.SeguimientoFisico
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.ClienteService
import pe.com.gymapp.servicios.MantenimientoService
import pe.com.gymapp.servicios.MaquinaService
import pe.com.gymapp.servicios.SeguimientoFisicoService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentoSeguimientoFisico : Fragment() {
    private lateinit var spCliSegFis: Spinner
    private lateinit var txtPesoSegFis: EditText
    private lateinit var txtCuelloSegFis: EditText
    private lateinit var txtHombSegFis: EditText
    private lateinit var txtPechoSegFis: EditText
    private lateinit var txtCinturaSegFis: EditText
    private lateinit var txtBISegFis: EditText
    private lateinit var txtBDSegFis: EditText
    private lateinit var txtAISegFis: EditText
    private lateinit var txtADSegFis: EditText
    private lateinit var txtGlutSegFis: EditText
    private lateinit var txtMISegFis: EditText
    private lateinit var txtMDSegFis: EditText
    private lateinit var txtPISegFis: EditText
    private lateinit var txtPDSegFis: EditText
    private lateinit var txtFechSegFis: EditText
    private lateinit var chkEstSegFis: CheckBox
    private lateinit var lblCodSegFis: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstSegFis: ListView

    private val objsegfis= SeguimientoFisico()

    private val objCliente= Cliente()


    private var cod=0L
    private var peso=""
    private var cuello=""
    private var hombros=""
    private var pecho=""
    private var cintura=""
    private var bicepI=""
    private var bicepD=""
    private var antebI=""
    private var antebD=""
    private var gluteos=""
    private var musloI=""
    private var musloD=""
    private var pantI=""
    private var pantD=""
    private var fech= ""
    private var cli=""
    private var est=false
    private var codcli=0L
    private var fila=-1
    private var indice=-1
    private var pos=-1

    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null



    private var seguimientoFisicoService: SeguimientoFisicoService?=null
    private var registroSeguimientoFisico:List<SeguimientoFisico>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_seguimiento_fisico,container,false)
        //creamos los controles
        spCliSegFis=raiz.findViewById(R.id.spCliSegFis)
        txtPesoSegFis=raiz.findViewById(R.id.txtPesoSegFis)
        txtCuelloSegFis=raiz.findViewById(R.id.txtCuelloSegFis)
        txtHombSegFis=raiz.findViewById(R.id.txtHombSegFis)
        txtPechoSegFis=raiz.findViewById(R.id.txtPechoSegFis)
        txtCinturaSegFis=raiz.findViewById(R.id.txtCinturaSegFis)
        txtBISegFis=raiz.findViewById(R.id.txtBISegFis)
        txtBDSegFis=raiz.findViewById(R.id.txtBDSegFis)
        txtAISegFis=raiz.findViewById(R.id.txtAISegFis)
        txtADSegFis=raiz.findViewById(R.id.txtADSegFis)
        txtGlutSegFis=raiz.findViewById(R.id.txtGlutSegFis)
        txtMISegFis=raiz.findViewById(R.id.txtMISegFis)
        txtMDSegFis=raiz.findViewById(R.id.txtMDSegFis)
        txtPISegFis=raiz.findViewById(R.id.txtPISegFis)
        txtPDSegFis=raiz.findViewById(R.id.txtPDSegFis)
        txtFechSegFis=raiz.findViewById(R.id.txtFechSegFis)
        chkEstSegFis=raiz.findViewById(R.id.chkEstSegFis)
        lblCodSegFis=raiz.findViewById(R.id.lblCodSegFis)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstSegFis=raiz.findViewById(R.id.lstSegFis)

        registroSeguimientoFisico=ArrayList()
        registroCliente=ArrayList()

        //implementamos el servicio
        seguimientoFisicoService= ApiUtil.seguimientoFisicoService
        clienteService= ApiUtil.clienteService

        //mostramos las categorias
        MostrarSeguimientoFisico(raiz.context)
        MostrarComboCliente(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(spCliSegFis.adapter.toString() =="" ||
                txtPesoSegFis.text.toString()==""  ||
                txtCuelloSegFis.text.toString()==""  ||
                txtHombSegFis.text.toString()==""  ||
                txtPechoSegFis.text.toString()==""  ||
                txtCinturaSegFis.text.toString()==""  ||
                txtBISegFis.text.toString()==""  ||
                txtBDSegFis.text.toString()==""  ||
                txtAISegFis.text.toString()==""  ||
                txtADSegFis.text.toString()==""  ||
                txtGlutSegFis.text.toString()==""  ||
                txtMISegFis.text.toString()==""  ||
                txtMDSegFis.text.toString()==""  ||
                txtPISegFis.text.toString()==""  ||
                txtPDSegFis.text.toString()==""  ||
                txtFechSegFis.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Faltan Datos")
                spCliSegFis.requestFocus()
            }else{
                //capturando valores
                peso=txtPesoSegFis.text.toString()
                cuello=txtCuelloSegFis.text.toString()
                hombros=txtHombSegFis.text.toString()
                pecho=txtPechoSegFis.text.toString()
                cintura=txtCinturaSegFis.text.toString()
                bicepI=txtBISegFis.text.toString()
                bicepD=txtBDSegFis.text.toString()
                antebI=txtAISegFis.text.toString()
                antebD=txtADSegFis.text.toString()
                gluteos=txtGlutSegFis.text.toString()
                musloI=txtMISegFis.text.toString()
                musloD=txtMDSegFis.text.toString()
                pantI=txtPISegFis.text.toString()
                pantD=txtPDSegFis.text.toString()
                fech=txtFechSegFis.text.toString()
                pos=spCliSegFis.selectedItemPosition
                cli= (registroCliente as ArrayList<Cliente>).get(pos).nombre.toString()
                codcli= (registroCliente as ArrayList<Cliente>).get(pos).idcliente
                est=if(chkEstSegFis.isChecked){
                    true
                }else{
                    false
                }


                //enviamos los valores a la clase
                objsegfis.peso=peso
                objsegfis.cuello=cuello
                objsegfis.hombros=hombros
                objsegfis.pecho=pecho
                objsegfis.cintura=cintura
                objsegfis.bicepizq=bicepI
                objsegfis.bicepder=bicepD
                objsegfis.antebrazoizq=antebI
                objsegfis.antebrazoder=antebD
                objsegfis.gluteos=gluteos
                objsegfis.musloizq=musloI
                objsegfis.musloder=musloD
                objsegfis.pantorrillaizq=pantI
                objsegfis.pantorrillader=pantD
                objsegfis.fecha=fech

                objCliente.idcliente=codcli
                objsegfis.cliente=objCliente

                objsegfis.estado=est

                //llamamos al metodo para registrar
                RegistrarSeguimientoFisico(raiz.context,objsegfis)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmSegFisico) as ViewGroup)
                //actualizamos el fragmento
                val fsegfis=FragmentoSeguimientoFisico()
                DialogoCRUD("Registro de Seguimiento Físico","Se registró con éxito",fsegfis)
            }
        }

        lstSegFis.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodSegFis.text = ""+ (registroSeguimientoFisico as ArrayList<SeguimientoFisico>).get(fila).idsegfis
            txtPesoSegFis.setText (""+(registroSeguimientoFisico as ArrayList<SeguimientoFisico>) .get(fila).peso)
            txtFechSegFis.setText (""+(registroSeguimientoFisico as ArrayList<SeguimientoFisico>) .get(fila).fecha)
            for(x in (registroCliente as ArrayList<Cliente>).indices){
                if((registroCliente as ArrayList<Cliente>).get(x).nombre== (registroSeguimientoFisico as ArrayList<SeguimientoFisico>).get(fila).cliente?.nombre){
                    indice=x
                }
            }
            spCliSegFis.setSelection(indice)

            if((registroSeguimientoFisico as ArrayList<SeguimientoFisico>).get(fila).estado){
                chkEstSegFis.setChecked(true)
            }else{
                chkEstSegFis.setChecked(false)
            }
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodSegFis.text.toString().toLong()
                peso=txtPesoSegFis.text.toString()
                cuello=txtCuelloSegFis.text.toString()
                hombros=txtHombSegFis.text.toString()
                pecho=txtPechoSegFis.text.toString()
                cintura=txtCinturaSegFis.text.toString()
                bicepI=txtBISegFis.text.toString()
                bicepD=txtBDSegFis.text.toString()
                antebI=txtAISegFis.text.toString()
                antebD=txtADSegFis.text.toString()
                gluteos=txtGlutSegFis.text.toString()
                musloI=txtMISegFis.text.toString()
                musloD=txtMDSegFis.text.toString()
                pantI=txtPISegFis.text.toString()
                pantD=txtPDSegFis.text.toString()
                fech=txtFechSegFis.text.toString()
                pos=spCliSegFis.selectedItemPosition
                cli= (registroCliente as ArrayList<Cliente>).get(pos).nombre.toString()
                codcli= (registroCliente as ArrayList<Cliente>).get(pos).idcliente
                est=if(chkEstSegFis.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objsegfis.idsegfis=cod
                objsegfis.peso=peso
                objsegfis.cuello=cuello
                objsegfis.hombros=hombros
                objsegfis.pecho=pecho
                objsegfis.cintura=cintura
                objsegfis.bicepizq=bicepI
                objsegfis.bicepder=bicepD
                objsegfis.antebrazoizq=antebI
                objsegfis.antebrazoder=antebD
                objsegfis.gluteos=gluteos
                objsegfis.musloizq=musloI
                objsegfis.musloder=musloD
                objsegfis.pantorrillaizq=pantI
                objsegfis.pantorrillader=pantD
                objsegfis.fecha=fech

                objCliente.idcliente=codcli
                objsegfis.cliente=objCliente

                objsegfis.estado=est

                //llamamos a la funcion para registrar
                ActualizarSeguimientoFisico(raiz.context,objsegfis,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmSegFisico) as ViewGroup)
                val fsegfis=FragmentoSeguimientoFisico()
                DialogoCRUD("Actualización de Seguimiento Físico","Se actualizó correctamente",fsegfis)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstSegFis.requestFocus()
            }

        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodSegFis.text.toString().toLong()


                //llamamos a la funcion para registrar
                EliminarSeguimientoFisico(raiz.context,cod)
                val fsegfis=FragmentoSeguimientoFisico()
                DialogoEliminar("Eliminacion de Seguimiento Físico","¿Desea eliminarlo?",fsegfis)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstSegFis.requestFocus()
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
                    spCliSegFis.adapter= AdaptadorComboCliente(context,registroCliente)


                }
            }

            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarSeguimientoFisico(context: Context?){
        val call= seguimientoFisicoService!!.MostrarSeguimientoFisico()
        call!!.enqueue(object : Callback<List<SeguimientoFisico>?> {
            override fun onResponse(
                call: Call<List<SeguimientoFisico>?>,
                response: Response<List<SeguimientoFisico>?>
            ) {
                if(response.isSuccessful){
                    registroSeguimientoFisico=response.body()
                    lstSegFis.adapter= AdaptadorSeguimientoFisico(context,registroSeguimientoFisico)
                }
            }

            override fun onFailure(call: Call<List<SeguimientoFisico>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun RegistrarSeguimientoFisico(context: Context?, sf: SeguimientoFisico?){
        val call= seguimientoFisicoService!!.RegistrarSeguimientoFisico(sf)
        call!!.enqueue(object : Callback<SeguimientoFisico?> {
            override fun onResponse(call: Call<SeguimientoFisico?>, response: Response<SeguimientoFisico?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<SeguimientoFisico?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    fun ActualizarSeguimientoFisico(context: Context?, sf: SeguimientoFisico?, id:Long){
        val call= seguimientoFisicoService!!.ActualizarSeguimientoFisico(id,sf)
        call!!.enqueue(object : Callback<SeguimientoFisico?> {
            override fun onResponse(call: Call<SeguimientoFisico?>, response: Response<SeguimientoFisico?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<SeguimientoFisico?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarSeguimientoFisico(context: Context?, id:Long){
        val call= seguimientoFisicoService!!.EliminarSeguimientoFisico(id)
        call!!.enqueue(object : Callback<SeguimientoFisico?> {
            override fun onResponse(call: Call<SeguimientoFisico?>, response: Response<SeguimientoFisico?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<SeguimientoFisico?>, t: Throwable) {
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