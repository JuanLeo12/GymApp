package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Membresia

class AdaptadorComboMembresia (context: Context?, private val listamembresia:List<Membresia>?) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listamembresia!!.size
    }

    override fun getItem(p0: Int): Any {
        return listamembresia!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_combo_membresia,p2,false)
            val objmem=getItem(p0) as Membresia
            //creamos los controles
            val lblNomMem= vista!!.findViewById<TextView>(R.id.lblNomMem)

            //agregamos los valores a la lista
            lblNomMem.text=""+objmem.tiempo
        }
        return vista!!
    }
}