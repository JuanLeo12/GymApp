package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Maquina

class AdaptadorMaquina (context: Context?, private val listamaquina:List<Maquina>?):
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
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_maquina, p2, false)
            val objmaquina = getItem(p0) as Maquina
            //creamos los controles
            val lstCodMaq = vista!!.findViewById<TextView>(R.id.lstCodMaq)
            val lstNomMaq = vista!!.findViewById<TextView>(R.id.lstNomMaq)
            val lstPreComMaq = vista!!.findViewById<TextView>(R.id.lstPreComMaq)
            val lstCantMaq = vista!!.findViewById<TextView>(R.id.lstCantMaq)
            val lstEstMaq = vista!!.findViewById<TextView>(R.id.lstEstMaq)
            //agregamos valores a los contrales
            lstCodMaq.text = "" + objmaquina.idmaquina
            lstNomMaq.text = "" + objmaquina.nombre
            lstPreComMaq.text="" + objmaquina.preciocompramaq
            lstCantMaq.text = "" + objmaquina.cantidad.toInt()
            if (objmaquina.estado == true) {
                lstEstMaq.text = "Habilitado"
            } else {
                lstEstMaq.text = "Deshabilitado"
            }
        }
        return vista!!
    }
}