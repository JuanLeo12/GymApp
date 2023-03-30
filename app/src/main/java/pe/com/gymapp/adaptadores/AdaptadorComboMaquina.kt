package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Maquina

class AdaptadorComboMaquina (context: Context?, private val listamaquina:List<Maquina>?) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listamaquina!!.size
    }

    override fun getItem(p0: Int): Any {
        return listamaquina!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_combo_maquina,p2,false)
            val objmaquina=getItem(p0) as Maquina
            //creamos los controles
            val lblNomMaq= vista!!.findViewById<TextView>(R.id.lblNomMaq)

            //agregamos los valores a la lista
            lblNomMaq.text=""+objmaquina.nombre
        }
        return vista!!
    }
}