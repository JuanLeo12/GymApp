package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Incidencia
import java.text.SimpleDateFormat
import java.util.*

class AdaptadorIncidencia (context: Context?, private val listaincidencia:List<Incidencia>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaincidencia!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaincidencia!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_incidencia, p2, false)
            val objincidencia = getItem(p0) as Incidencia
            //creamos los controles
            val lstCodInc = vista!!.findViewById<TextView>(R.id.lstCodInc)
            val lstCliInc = vista!!.findViewById<TextView>(R.id.lstCliInc)
            val lstEmpInc = vista!!.findViewById<TextView>(R.id.lstEmpInc)
            val lstDescInc = vista!!.findViewById<TextView>(R.id.lstDescInc)
            val lstFechaInc = vista!!.findViewById<TextView>(R.id.lstFechaInc)
            //agregamos valores a los contrales
            lstCodInc.text = "" + objincidencia.idincidencia
            lstCliInc.text = "" + objincidencia.cliente!!.nombre
            lstEmpInc.text = "" + objincidencia.empleado!!.nombre
            lstDescInc.text = "" + objincidencia.descripcion
            val fecha = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(objincidencia.fecha)
            lstFechaInc.text =  SimpleDateFormat("dd/MM/yyyy").format(fecha)
        }
        return vista!!
    }
}