package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Producto

class AdaptadorComboProducto (context: Context?, private val listaproducto:List<Producto>?) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaproducto!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaproducto!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista=layoutInflater.inflate(R.layout.elemento_combo_producto,p2,false)
            val objproducto=getItem(p0) as Producto
            //creamos los controles
            val lblNomProd= vista!!.findViewById<TextView>(R.id.lblNomProd)

            //agregamos los valores a la lista
            lblNomProd.text=""+objproducto.nombre
        }
        return vista!!
    }
}