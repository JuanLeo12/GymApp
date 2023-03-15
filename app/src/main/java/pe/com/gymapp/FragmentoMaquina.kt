package pe.com.gymapp

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
    private lateinit var chkEstMaq: CheckBox
    private lateinit var lblCodMaq: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstMaq: ListView

    val objmaquina= Maquina()

    private var cod=0
    private var nom=""
    private var precom=0.0
    private var est=false
    private var fila=-1

    private var maquinaService: MaquinaService?=null

    private var registromaquina:List<Maquina>?=null

    var objutilidad= Util()

    var ft: FragmentTransaction?=null

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
                objmaquina.preciocompra=precom
                objmaquina.estado=est
                //llamamos al metodo para registrar
                RegistrarMaquina(raiz.context,objmaquina)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmMaquina) as ViewGroup)
                //actualizamos el fragmento
                val fmaquina=FragmentoMaquina()
                ft=fragmentManager?.beginTransaction()
                ft?.replace(R.id.contenedor,fmaquina,null)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }

        lstMaq.setOnItemClickListener(
            AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            //asignamos los valores a cada control
            lblCodMaq.setText(""+(registromaquina as ArrayList<Maquina>).get(fila).idmaquina)
            txtNomMaq.setText(""+(registromaquina as ArrayList<Maquina>).get(fila).nombre)
            txtPreComMaq.setText(""+(registromaquina as ArrayList<Maquina>).get(fila).preciocompra)
            if((registromaquina as ArrayList<Maquina>).get(fila).estado){
                chkEstMaq.setChecked(true)
            }else{
                chkEstMaq.setChecked(false)
            }
        })

        return raiz

    }

    fun MostrarMaquina(context: Context?){
        val call= maquinaService!!.MostrarMaquinaPersonalizado()
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
                    objutilidad.MensajeToast(context!!,"Se registro la máquina")
                }
            }

            override fun onFailure(call: Call<Maquina?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}