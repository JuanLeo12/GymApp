package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Usuario

class AdaptadorUsuario (context: Context?, private val listausuario:List<Usuario>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater= LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listausuario!!.size
    }

    override fun getItem(p0: Int): Any {
        return listausuario!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_usuario,p2,false)
            val objusuario=getItem(p0) as Usuario
            //creamos los controles
            val lstCodUsu= vista!!.findViewById<TextView>(R.id.lstCodUsu)
            val lstUsername= vista!!.findViewById<TextView>(R.id.lstUsername)
            val lstPass= vista!!.findViewById<TextView>(R.id.lstPass)
            val lstUsuEmp= vista!!.findViewById<TextView>(R.id.lstUsuEmp)
            val lstEstUsu= vista!!.findViewById<TextView>(R.id.lstEstUsu)

            //agregamos los valores a la lista
            lstCodUsu.text=""+objusuario.idusuario
            lstUsername.text=""+objusuario.usuario
            lstPass.text=""+objusuario.contrasena
            if(objusuario.estado==true){
                lstEstUsu.text="Habilitado"
            }else{
                lstEstUsu.text="Deshabilitado"
            }
            lstUsuEmp.text=""+ objusuario.empleado!!.nombre
        }
        return vista!!
    }
}
