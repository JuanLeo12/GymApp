package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Mantenimiento
import pe.com.gymapp.clases.SeguimientoFisico

class AdaptadorSeguimientoFisico (context: Context?, private val listasegfis:List<SeguimientoFisico>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listasegfis!!.size
    }

    override fun getItem(p0: Int): Any {
        return listasegfis!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_seguimiento_fisico, p2, false)
            val objsegfis = getItem(p0) as SeguimientoFisico
            //creamos los controles
            val lstCodSegFis = vista!!.findViewById<TextView>(R.id.lstCodSegFis)
            val lstCliSegFis = vista!!.findViewById<TextView>(R.id.lstCliSegFis)
            val lstFechSegFis = vista!!.findViewById<TextView>(R.id.lstFechSegFis)
            val lstPesoSegFis = vista!!.findViewById<TextView>(R.id.lstPesoSegFis)
            val lstEstSegFis = vista!!.findViewById<TextView>(R.id.lstEstSegFis)
            //agregamos valores a los contrales
            lstCodSegFis.text = "" + objsegfis.idsegfis
            lstCliSegFis.text = "" + objsegfis.cliente!!.nombre
            lstFechSegFis.text = "" + objsegfis.fecha
            lstPesoSegFis.text = "" + objsegfis.peso + "kg"
            if(objsegfis.estado==true){
                lstEstSegFis.text="Habilitado"
            }else{
                lstEstSegFis.text="Deshabilitado"
            }
        }
        return vista!!
    }
}