package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Proveedor

class AdaptadorComboProveedor (context: Context?, private val listaproveedor:List<Proveedor>?) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaproveedor!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaproveedor!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista=layoutInflater.inflate(R.layout.elemento_combo_proveedor,p2,false)
            val objproveedor=getItem(p0) as Proveedor
            //creamos los controles
            val lblNomProv= vista!!.findViewById<TextView>(R.id.lblNomProv)

            //agregamos los valores a la lista
            lblNomProv.text=""+objproveedor.nombre
        }
        return vista!!
    }
}