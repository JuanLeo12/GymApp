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
import pe.com.gymapp.adaptadores.AdaptadorUsuario
import pe.com.gymapp.clases.Cliente
import pe.com.gymapp.clases.Usuario
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.ClienteService
import pe.com.gymapp.servicios.UsuarioService
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
 * Use the [FragmentoBuscarUsuario.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarUsuario : Fragment() {
    //declaramos los controles
    private lateinit var txtBusUsu: SearchView
    private lateinit var lblCodUsu: TextView
    private lateinit var btnHabilitarUsu: Button
    private lateinit var btnDeshabilitarUsu: Button
    private lateinit var lstUsu: ListView

    //cremamos un objeto de la clase categoria
    private val objusuario= Usuario()

    //creamos variables
    private var cod=0L
    private var est=false
    private var fila=-1
    private var nom=""
    private var cri=""

    //llamamos al servicio
    private var usuarioService: UsuarioService?=null

    //creamos una lista de tipo categoria
    private var registrousuario:List<Usuario>?=null

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
        val raiz=inflater.inflate(R.layout.fragmento_buscar_usuario,container,false)
        //creamos los controles
        txtBusUsu=raiz.findViewById(R.id.txtBusUsu)
        lblCodUsu=raiz.findViewById(R.id.lblCodUsu)
        btnHabilitarUsu=raiz.findViewById(R.id.btnHabilitarUsu)
        btnDeshabilitarUsu=raiz.findViewById(R.id.btnDeshabilitarUsu)
        lstUsu=raiz.findViewById(R.id.lstUsu)

        //creamos el registro categoria
        registrousuario=ArrayList()
        //implementamos el servicio
        usuarioService= ApiUtil.usuarioService
        //mostramos los clientes
        MostrarUsuario(raiz.context)

        //eventos
        txtBusUsu.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstUsu.adapter as AdaptadorUsuario).filter(newText ?: "")
                return true
            }
        })

        lstUsu.setOnItemClickListener { adapterView, view, i, l ->
            fila=i

            lblCodUsu.setText(""+(registrousuario as ArrayList<Usuario>).get(fila).idusuario)
        }

        btnHabilitarUsu.setOnClickListener {
            if(fila>=0){
                cod=lblCodUsu.text.toString().toLong()

                EnableUsuario(raiz.context,cod)
                val fbcliente=FragmentoBuscarUsuario()
                DialogoCRUD("Habilitar Usuario","Se habilitó al usuario correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstUsu.requestFocus()
            }
        }

        btnDeshabilitarUsu.setOnClickListener {
            if(fila>=0){
                cod=lblCodUsu.text.toString().toLong()

                EliminarUsuario(raiz.context,cod)
                val fbcliente=FragmentoBuscarUsuario()
                DialogoCRUD("Deshabilitar Usuario","Se deshabilitó al usuario correctamente",fbcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstUsu.requestFocus()
            }
        }
        return raiz
    }

    fun MostrarUsuario(context: Context?){
        val call= usuarioService!!.MostrarUsuario()
        call!!.enqueue(object : Callback<List<Usuario>?> {
            override fun onResponse(
                call: Call<List<Usuario>?>,
                response: Response<List<Usuario>?>
            ) {
                if(response.isSuccessful){
                    registrousuario=response.body()
                    lstUsu.adapter= AdaptadorUsuario(context,registrousuario)


                }
            }

            override fun onFailure(call: Call<List<Usuario>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarUsuario(context: Context?, id:Long){
        val call= usuarioService!!.EliminarUsuario(id)
        call!!.enqueue(object : Callback<Usuario?> {
            override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se eliminó")
                }
            }

            override fun onFailure(call: Call<Usuario?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EnableUsuario(context: Context?, id:Long){
        val call= usuarioService!!.EnableUsuario(id)
        call!!.enqueue(object : Callback<Usuario?> {
            override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se habilitó")
                }
            }

            override fun onFailure(call: Call<Usuario?>, t: Throwable) {
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