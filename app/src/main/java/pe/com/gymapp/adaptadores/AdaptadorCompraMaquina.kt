package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.CompraMaquina

class AdaptadorCompraMaquina (context: Context?, private val listacompramaquina:List<CompraMaquina>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listacompramaquina!!.size
    }

    override fun getItem(p0: Int): Any {
        return listacompramaquina!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_compramaquina, p2, false)
            val objcompramaquina = getItem(p0) as CompraMaquina
            //creamos los controles
            val lstCodComMaq = vista!!.findViewById<TextView>(R.id.lstCodComMaq)
            val lstMaqCom = vista!!.findViewById<TextView>(R.id.lstMaqCom)
            val lstProvComMaq = vista!!.findViewById<TextView>(R.id.lstProvComMaq)
            val lstCanComMaq = vista!!.findViewById<TextView>(R.id.lstCanComMaq)
            val lstTotComMaq = vista!!.findViewById<TextView>(R.id.lstTotComMaq)
            val lstPreCComMaq=vista!!.findViewById<TextView>(R.id.lstPreCComMaq)
            //agregamos valores a los contrales
            lstCodComMaq.text = "" + objcompramaquina.idcompmaq
            lstMaqCom.text = "" + objcompramaquina.maquina!!.nombre
            lstProvComMaq.text = "" + objcompramaquina.proveedor!!.nombre
            lstCanComMaq.text = "" + objcompramaquina.cantidad.toInt()
            lstPreCComMaq.text = "" + objcompramaquina.maquina!!.preciocompramaq
            lstTotComMaq.text = "" + (objcompramaquina.cantidad * objcompramaquina.maquina!!.preciocompramaq)
        }
        return vista!!
    }
}