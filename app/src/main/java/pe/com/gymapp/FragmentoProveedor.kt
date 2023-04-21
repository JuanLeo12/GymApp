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
import pe.com.gymapp.adaptadores.AdaptadorProveedor
import pe.com.gymapp.clases.Proveedor
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.ProveedorService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoProveedor : Fragment() {

    private lateinit var txtNomProv: EditText
    private lateinit var txtTelfProv: EditText
    private lateinit var txtCorrProv: EditText
    private lateinit var txtDirProv: EditText
    private lateinit var chkEstProv: CheckBox
    private lateinit var lblCodProv: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstProv: ListView

    val objproveedor= Proveedor()

    private var cod=0L
    private var nom=""
    private var telf=""
    private var corr=""
    private var dir=""
    private var est=false
    private var fila=-1

    private var proveedorService: ProveedorService?=null

    private var registroproveedor:List<Proveedor>?=null

    var objutilidad= Util()

    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoProveedor? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento_proveedor,container,false)
        //creamos los controles
        txtNomProv=raiz.findViewById(R.id.txtNomProv)
        txtTelfProv=raiz.findViewById(R.id.txtTelfProv)
        txtCorrProv=raiz.findViewById(R.id.txtCorrProv)
        txtDirProv=raiz.findViewById(R.id.txtDirProv)
        chkEstProv=raiz.findViewById(R.id.chkEstProv)
        lblCodProv=raiz.findViewById(R.id.lblCodProv)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstProv=raiz.findViewById(R.id.lstProv)

        registroproveedor=ArrayList()

        //implementamos el servicio
        proveedorService= ApiUtil.proveedorService

        //mostramos las categorias
        MostrarProveedor(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtNomProv.getText().toString()==""||txtTelfProv.getText().toString()==""||txtCorrProv.getText().toString()==""||txtDirProv.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Faltan datos")
                txtNomProv.requestFocus()
            }else{
                //capturando valores
                nom=txtNomProv.getText().toString()
                telf=txtTelfProv.getText().toString()
                corr=txtCorrProv.getText().toString()
                dir=txtDirProv.getText().toString()
                est=if(chkEstProv.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objproveedor.nombre=nom
                objproveedor.telefono=telf
                objproveedor.correo=corr
                objproveedor.direccion=dir
                objproveedor.estado=est
                //llamamos al metodo para registrar
                RegistrarProveedor(raiz.context,objproveedor)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmProveedor) as ViewGroup)
                //actualizamos el fragmento
                val fproveedor=FragmentoProveedor()
                ft=fragmentManager?.beginTransaction()
                ft?.replace(R.id.contenedor,fproveedor,null)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }

        lstProv.setOnItemClickListener(
            AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            //asignamos los valores a cada control
            lblCodProv.setText(""+(registroproveedor as ArrayList<Proveedor>).get(fila).idproveedor)
            txtNomProv.setText(""+(registroproveedor as ArrayList<Proveedor>).get(fila).nombre)
            txtTelfProv.setText(""+(registroproveedor as ArrayList<Proveedor>).get(fila).telefono)
            txtCorrProv.setText(""+(registroproveedor as ArrayList<Proveedor>).get(fila).correo)
            txtDirProv.setText(""+(registroproveedor as ArrayList<Proveedor>).get(fila).direccion)
            if((registroproveedor as ArrayList<Proveedor>).get(fila).estado){
                chkEstProv.setChecked(true)
            }else{
                chkEstProv.setChecked(false)
            }
        })

        btnActualizar.setOnClickListener {
            if(fila>=0){
                cod=lblCodProv.getText().toString().toLong()
                nom=txtNomProv.getText().toString()
                telf=txtTelfProv.getText().toString()
                corr=txtCorrProv.getText().toString()
                dir=txtDirProv.getText().toString()
                est=if(chkEstProv.isChecked){
                    true
                }else{
                    false
                }
                objproveedor.idproveedor=cod
                objproveedor.nombre=nom
                objproveedor.telefono=telf
                objproveedor.correo=corr
                objproveedor.direccion=dir
                objproveedor.estado=est

                ActualizarProveedor(raiz.context,objproveedor,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmProveedor) as ViewGroup)
                val fproveedor=FragmentoProveedor()
                DialogoCRUD("Actualización de Proveedor","Se actualizó el Proveedor",fproveedor)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstProv.requestFocus()
            }
        }

        btnEliminar.setOnClickListener {
            if(fila>=0){
                cod=lblCodProv.getText().toString().toLong()

                objproveedor.idproveedor=cod

                EliminarProveedor(raiz.context,cod)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmProveedor) as ViewGroup)
                val fproveedor=FragmentoProveedor()
                DialogoEliminar("Eliminación de Proveedor","¿Desea eliminar el proveedor?",fproveedor)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstProv.requestFocus()
            }
        }

        return raiz

    }

    fun MostrarProveedor(context: Context?){
        val call= proveedorService!!.MostrarProveedorPersonalizado()
        call!!.enqueue(object : Callback<List<Proveedor>?> {
            override fun onResponse(
                call: Call<List<Proveedor>?>,
                response: Response<List<Proveedor>?>
            ) {
                if(response.isSuccessful){
                    registroproveedor=response.body()
                    lstProv.adapter= AdaptadorProveedor(context,registroproveedor)
                }
            }

            override fun onFailure(call: Call<List<Proveedor>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar
    fun RegistrarProveedor(context: Context?, p: Proveedor?){
        val call= proveedorService!!.RegistrarProveedor(p)
        call!!.enqueue(object : Callback<Proveedor?> {
            override fun onResponse(call: Call<Proveedor?>, response: Response<Proveedor?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registró")
                }
            }

            override fun onFailure(call: Call<Proveedor?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar
    fun ActualizarProveedor(context: Context?,p: Proveedor?,id:Long){
        val call= proveedorService!!.ActualizarProveedor(id,p)
        call!!.enqueue(object :Callback<Proveedor?>{
            override fun onResponse(call: Call<Proveedor?>, response: Response<Proveedor?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizó")
                }
            }

            override fun onFailure(call: Call<Proveedor?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarProveedor(context: Context?,id:Long){
        val call= proveedorService!!.EliminarProveedor(id)
        call!!.enqueue(object :Callback<Proveedor?>{
            override fun onResponse(call: Call<Proveedor?>, response: Response<Proveedor?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Proveedor?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

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