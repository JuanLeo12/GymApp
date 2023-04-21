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
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import pe.com.gymapp.adaptadores.AdaptadorCliente
import pe.com.gymapp.adaptadores.AdaptadorMaquina
import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Maquina
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.ClienteService
import pe.com.gymapp.servicios.MaquinaService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentoBuscarMaquina.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarMaquina : Fragment() {
    //declaramos los controles
    private lateinit var txtBusMaq: SearchView
    private lateinit var lblCodMaq: TextView
    private lateinit var btnHabilitarMaq: Button
    private lateinit var btnDeshabilitarMaq: Button
    private lateinit var lstMaq: ListView

    //cremamos un objeto de la clase categoria
    private val objmaquina= Maquina()

    //creamos variables
    private var cod=0L
    private var est=false
    private var fila=-1
    private var nom=""
    private var cri=""

    //llamamos al servicio
    private var maquinaService: MaquinaService?=null

    //creamos una lista de tipo categoria
    private var registromaquina:List<Maquina>?=null

    //creamos un objeto de la clase Util
    private val objutilidad= Util()

    //creams una variable para actualizar el fragmento
    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val raiz=inflater.inflate(R.layout.fragmento_buscar_maquina,container,false)
        //creamos los controles
        txtBusMaq=raiz.findViewById(R.id.txtBusMaq)
        lblCodMaq=raiz.findViewById(R.id.lblCodMaq)
        btnHabilitarMaq=raiz.findViewById(R.id.btnHabilitarMaq)
        btnDeshabilitarMaq=raiz.findViewById(R.id.btnDeshabilitarMaq)
        lstMaq=raiz.findViewById(R.id.lstMaq)

        //creamos el registro categoria
        registromaquina=ArrayList()
        //implementamos el servicio
        maquinaService= ApiUtil.maquinaService
        //mostramos los clientes
        MostrarMaquina(raiz.context)

        //eventos
        txtBusMaq.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstMaq.adapter as AdaptadorMaquina).filter(newText ?: "")
                return true
            }
        })

        lstMaq.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

            lblCodMaq.setText(""+(registromaquina as ArrayList<Maquina>).get(fila).idmaquina)
        }

        btnHabilitarMaq.setOnClickListener {
            if(fila>=0){
                cod=lblCodMaq.text.toString().toLong()

                EnableMaquina(raiz.context,cod)
                val fbcliente=FragmentoBuscarMaquina()
                DialogoCRUD("Habilitar Máquina","Se habilitó la máquina correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstMaq.requestFocus()
            }
        }

        btnDeshabilitarMaq.setOnClickListener {
            if(fila>=0){
                cod=lblCodMaq.text.toString().toLong()

                EliminarMaquina(raiz.context,cod)
                val fbcliente=FragmentoBuscarMaquina()
                DialogoCRUD("Deshabilitar Máquina","Se deshabilitó la máquina correctamente",fbcliente)
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

    fun EliminarMaquina(context: Context?, id:Long){
        val call= maquinaService!!.EliminarMaquina(id)
        call!!.enqueue(object : Callback<Maquina?> {
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

    fun EnableMaquina(context: Context?, id:Long){
        val call= maquinaService!!.EnableMaquina(id)
        call!!.enqueue(object : Callback<Maquina?> {
            override fun onResponse(call: Call<Maquina?>, response: Response<Maquina?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se habilitó")
                }
            }

            override fun onFailure(call: Call<Maquina?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentoBuscarCliente.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentoBuscarCliente().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}