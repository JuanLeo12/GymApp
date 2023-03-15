package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Membresia

class AdaptadorMembresia (context: Context?, private val listamembresia:List<Membresia>?):
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
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_membresia, p2, false)
            val objmembresia = getItem(p0) as Membresia
            //creamos los controles
            val lstCodMem = vista!!.findViewById<TextView>(R.id.lstCodMem)
            val lstTiempoMem = vista!!.findViewById<TextView>(R.id.lstTiempoMem)
            val lstPreMem = vista!!.findViewById<TextView>(R.id.lstPreMem)
            val lstEstMem = vista!!.findViewById<TextView>(R.id.lstEstMem)
            //agregamos valores a los contrales
            lstCodMem.text = "" + objmembresia.idmembresia
            lstTiempoMem.text = "" + objmembresia.tiempo
            lstPreMem.text="" + objmembresia.precio
            if (objmembresia.estado == true) {
                lstEstMem.text = "Habilitado"
            } else {
                lstEstMem.text = "Deshabilitado"
            }
        }
        return vista!!
    }
}