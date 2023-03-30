package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Cliente

class AdaptadorComboCliente (context: Context?, private val listacliente:List<Cliente>?) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listacliente!!.size
    }

    override fun getItem(p0: Int): Any {
        return listacliente!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista=layoutInflater.inflate(R.layout.elemento_combo_cliente,p2,false)
            val objcliente=getItem(p0) as Cliente
            //creamos los controles
            val lblNomCli= vista!!.findViewById<TextView>(R.id.lblNomCli)

            //agregamos los valores a la lista
            lblNomCli.text=""+objcliente.nombre
        }
        return vista!!
    }
}