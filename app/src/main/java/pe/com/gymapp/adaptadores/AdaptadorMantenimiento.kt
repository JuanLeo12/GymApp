package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Mantenimiento

class AdaptadorMantenimiento (context: Context?, private val listamantenimiento:List<Mantenimiento>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listamantenimiento!!.size
    }

    override fun getItem(p0: Int): Any {
        return listamantenimiento!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_mantenimiento, p2, false)
            val objmant = getItem(p0) as Mantenimiento
            //creamos los controles
            val lstCodMant = vista!!.findViewById<TextView>(R.id.lstCodMant)
            val lstMaqMant = vista!!.findViewById<TextView>(R.id.lstMaqMant)
            val lstFechMant = vista!!.findViewById<TextView>(R.id.lstFechMant)
            val lstEstMant = vista!!.findViewById<TextView>(R.id.lstEstMant)
            //agregamos valores a los contrales
            lstCodMant.text = "" + objmant.idmantenimiento
            lstMaqMant.text = "" + objmant.maquina!!.nombre
            lstFechMant.text = "" + objmant.fecha
            if(objmant.estado==true){
                lstEstMant.text="Habilitado"
            }else{
                lstEstMant.text="Deshabilitado"
            }
        }
        return vista!!
    }
}