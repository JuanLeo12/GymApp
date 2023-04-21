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

class FragmentoCliente : Fragment() {
    //declaramos los controles
    private lateinit var txtNomCli: EditText
    private lateinit var txtApPatCli: EditText
    private lateinit var txtApMatCli: EditText
    private lateinit var txtTelfCli: EditText
    private lateinit var txtCorrCli: EditText
    private lateinit var spGenCli: Spinner
    private lateinit var txtDirCli: EditText
    private lateinit var spMemCli: Spinner
    private lateinit var lblCodCli: TextView
    private lateinit var chkEstCli: CheckBox
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstCli: ListView

    //cremamos un objeto de la clase categoria
    private val objgenero= Genero()
    private val objmembresia= Membresia()
    private val objcliente= Cliente()

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
    private var codmem=0L
    private var nommem=""
    private var est=false
    private var fila=-1
    private var indicegen=-1
    private var indicemem=-1
    private var posgen=-1
    private var posmem=-1


    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null

    //llamamos al servicio
    private var generoService: GeneroService?=null
    private var membresiaService: MembresiaService?=null
    private var clienteService: ClienteService?=null

    //creamos una lista de tipo categoria
    private var registrogenero:List<Genero>?=null
    private var registromembresia:List<Membresia>?=null
    private var registrocliente:List<Cliente>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_cliente, container, false)


        //creamos los controles
        txtNomCli=raiz.findViewById(R.id.txtNomCli)
        txtApPatCli=raiz.findViewById(R.id.txtApPatCli)
        txtApMatCli=raiz.findViewById(R.id.txtApMatCli)
        txtTelfCli=raiz.findViewById(R.id.txtTelfCli)
        txtCorrCli=raiz.findViewById(R.id.txtCorrCli)
        spGenCli=raiz.findViewById(R.id.spGenCli)
        txtDirCli=raiz.findViewById(R.id.txtDirCli)
        spMemCli=raiz.findViewById(R.id.spMemCli)
        chkEstCli=raiz.findViewById(R.id.chkEstCli)
        lblCodCli=raiz.findViewById(R.id.lblCodCli)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstCli=raiz.findViewById(R.id.lstCli)


        //creamos el registro categoria
        registrogenero=ArrayList()
        registromembresia=ArrayList()
        registrocliente=ArrayList()
        //implementamos el servicio
        generoService= ApiUtil.generoService
        membresiaService= ApiUtil.membresiaService
        clienteService= ApiUtil.clienteService

        //cargamos el combo categoria
        MostrarComboGenero(raiz.context)
        MostrarComboMembresia(raiz.context)
        MostrarCliente(raiz.context)

        //llamamos a los eventos
        btnRegistrar.setOnClickListener {
            if(txtNomCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el nombre")
                txtNomCli.requestFocus()
            }else if(txtApPatCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el apellido paterno")
                txtApPatCli.requestFocus()
            }else if(txtApMatCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el apellido materno")
                txtApMatCli.requestFocus()
            }else if(txtTelfCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el teléfono")
                txtTelfCli.requestFocus()
            }else if(txtCorrCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el correo")
                txtCorrCli.requestFocus()
            }else if(spGenCli.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione un género")
                spGenCli.requestFocus()
            }else if(txtDirCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa la dirección")
                txtDirCli.requestFocus()
            }else if(spMemCli.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una membresía")
                spMemCli.requestFocus()
            }else{
                //capturando valores
                nom=txtNomCli.text.toString()
                appat=txtApPatCli.text.toString()
                apmat=txtApMatCli.text.toString()
                telf=txtTelfCli.text.toString()
                corr=txtCorrCli.text.toString()
                dir=txtDirCli.text.toString()
                posmem=spMemCli.selectedItemPosition
                codmem= (registromembresia as ArrayList<Membresia>).get(posmem).idmembresia
                nommem= (registromembresia as ArrayList<Membresia>).get(posmem).tiempo.toString()
                posgen=spGenCli.selectedItemPosition
                idgen=(registrogenero as ArrayList<Genero>).get(posgen).idgenero
                gen= (registrogenero as ArrayList<Genero>).get(posgen).genero.toString()
                est=if(chkEstCli.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objcliente.nombre=nom
                objcliente.apepaterno=appat
                objcliente.apematerno=apmat
                objcliente.telefono=telf
                objcliente.correo=corr
                objcliente.direccion=dir

                objgenero.idgenero=idgen
                objcliente.genero= objgenero

                objmembresia.idmembresia=codmem
                objcliente.membresia=objmembresia

                objcliente.estado=est

                //llamamos a la funcion para registrar
                RegistrarCliente(raiz.context,objcliente)
                val fcliente=FragmentoCliente()
                DialogoCRUD("Registro de Cliente","Se registro el cliente correctamente",fcliente)
            }
        }

        lstCli.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).idcliente)
            txtNomCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).nombre)
            txtApPatCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).apepaterno)
            txtApMatCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).apematerno)
            txtTelfCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).telefono)
            txtCorrCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).correo)
            txtDirCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).direccion)
            for(x in (registromembresia as ArrayList<Membresia>).indices){
                if((registromembresia as ArrayList<Membresia>).get(x).tiempo== (registrocliente as ArrayList<Cliente>).get(fila).membresia?.tiempo){
                    indicemem=x
                }
            }
            spMemCli.setSelection(indicemem)
            for(x in (registrogenero as ArrayList<Genero>).indices){
                if((registrogenero as ArrayList<Genero>).get(x).genero== (registrocliente as ArrayList<Cliente>).get(fila).genero?.genero){
                    indicegen=x
                }
            }
            spGenCli.setSelection(indicegen)
            if((registrocliente as ArrayList<Cliente>).get(fila).estado){
                chkEstCli.setChecked(true)
            }else{
                chkEstCli.setChecked(false)
            }
        }

        btnActualizar.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodCli.text.toString().toLong()
                nom=txtNomCli.text.toString()
                appat=txtApPatCli.text.toString()
                apmat=txtApMatCli.text.toString()
                telf=txtTelfCli.text.toString()
                corr=txtCorrCli.text.toString()
                dir=txtDirCli.text.toString()
                posmem=spMemCli.selectedItemPosition
                codmem= (registromembresia as ArrayList<Membresia>).get(posmem).idmembresia
                nommem= (registromembresia as ArrayList<Membresia>).get(posmem).tiempo.toString()
                posgen=spGenCli.selectedItemPosition
                idgen=(registrogenero as ArrayList<Genero>).get(posgen).idgenero
                gen= (registrogenero as ArrayList<Genero>).get(posgen).genero.toString()
                est=if(chkEstCli.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objcliente.idcliente=cod
                objcliente.nombre=nom
                objcliente.apepaterno=appat
                objcliente.apematerno=apmat
                objcliente.telefono=telf
                objcliente.correo=corr
                objcliente.direccion=dir

                objgenero.idgenero=idgen
                objcliente.genero= objgenero

                objmembresia.idmembresia=codmem
                objcliente.membresia=objmembresia

                objcliente.estado=est

                //llamamos a la funcion para registrar
                ActualizarCliente(raiz.context,objcliente,cod)
                val fcliente=FragmentoCliente()
                DialogoCRUD("Actualizacion de Cliente","Se actualizo el clietne correctamente",fcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstCli.requestFocus()
            }

        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodCli.text.toString().toLong()
                nom=txtNomCli.text.toString()


                //llamamos a la funcion para registrar
                EliminarCliente(raiz.context,cod)
                val fcliente=FragmentoCliente()
                DialogoEliminar("Eliminacion de Cliente","¿Desea eliminar al clietne?",fcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstCli.requestFocus()
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
                    spGenCli.adapter= AdaptadorComboGenero(context,registrogenero)


                }
            }

            override fun onFailure(call: Call<List<Genero>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun MostrarComboMembresia(context: Context?){
        val call= membresiaService!!.MostrarMembresiaPersonalizado()
        call!!.enqueue(object : Callback<List<Membresia>?> {
            override fun onResponse(
                call: Call<List<Membresia>?>,
                response: Response<List<Membresia>?>
            ) {
                if(response.isSuccessful){
                    registromembresia=response.body()
                    spMemCli.adapter= AdaptadorComboMembresia(context,registromembresia)


                }
            }

            override fun onFailure(call: Call<List<Membresia>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para mostrar los producto
    fun MostrarCliente(context: Context?){
        val call= clienteService!!.MostrarClientePersonalizado()
        call!!.enqueue(object : Callback<List<Cliente>?> {
            override fun onResponse(
                call: Call<List<Cliente>?>,
                response: Response<List<Cliente>?>
            ) {
                if(response.isSuccessful){
                    registrocliente=response.body()
                    lstCli.adapter= AdaptadorCliente(context,registrocliente)
                }
            }

            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar
    fun RegistrarCliente(context: Context?, c: Cliente?){
        val call= clienteService!!.RegistrarCliente(c)
        call!!.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar
    fun ActualizarCliente(context: Context?, c: Cliente?, id:Long){
        val call= clienteService!!.ActualizarCliente(id,c)
        call!!.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarCliente(context: Context?, id:Long){
        val call= clienteService!!.EliminarCliente(id)
        call!!.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
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