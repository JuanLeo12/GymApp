package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Genero

class AdaptadorComboGenero (context: Context?, private val listagenero:List<Genero>?) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listagenero!!.size
    }

    override fun getItem(p0: Int): Any {
        return listagenero!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_combo_genero,p2,false)
            val objgenero=getItem(p0) as Genero
            //creamos los controles
            val lblGenero= vista!!.findViewById<TextView>(R.id.lblGenero)

            //agregamos los valores a la lista
            lblGenero.text=""+objgenero.genero
        }
        return vista!!
    }
}