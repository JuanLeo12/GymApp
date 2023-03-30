package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Venta

class AdaptadorVenta (context: Context?, private val listaventa:List<Venta>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaventa!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaventa!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_venta, p2, false)
            val objventa = getItem(p0) as Venta
            //creamos los controles
            val lstCodVen = vista!!.findViewById<TextView>(R.id.lstCodVen)
            val lstProVen = vista!!.findViewById<TextView>(R.id.lstProVen)
            val lstTPVen = vista!!.findViewById<TextView>(R.id.lstTPVen)
            val lstCliVen = vista!!.findViewById<TextView>(R.id.lstCliVen)
            val lstEmpVen = vista!!.findViewById<TextView>(R.id.lstEmpVen)
            val lstCanVen=vista!!.findViewById<TextView>(R.id.lstCanVen)
            val lstPrecVenta = vista!!.findViewById<TextView>(R.id.lstPrecVenta)
            val lstTotVen=vista!!.findViewById<TextView>(R.id.lstTotVen)
            //agregamos valores a los contrales
            lstCodVen.text = "" + objventa.idventa
            lstProVen.text = "" + objventa.producto!!.nombre
            lstTPVen.text = "" + objventa.tipopago!!.tipopago
            lstCliVen.text = "" + objventa.cliente!!.nombre
            lstEmpVen.text = "" + objventa.empleado!!.nombre
            lstCanVen.text = "" + objventa.cantidad.toInt()
            lstPrecVenta.text = "" + objventa.producto!!.precioventa
            lstTotVen.text = "" + (objventa.cantidad * objventa.producto!!.precioventa)
        }
        return vista!!
    }
}