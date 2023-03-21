package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.CompraProducto
import pe.com.gymapp.clases.Producto
import pe.com.gymapp.clases.Proveedor

class AdaptadorCompraProducto (context: Context?, private val listacompraproducto:List<CompraProducto>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listacompraproducto!!.size
    }

    override fun getItem(p0: Int): Any {
        return listacompraproducto!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_compraproducto, p2, false)
            val objcompraproducto = getItem(p0) as CompraProducto
            //creamos los controles
            val lstCodComPro = vista!!.findViewById<TextView>(R.id.lstCodComPro)
            val lstProComPro = vista!!.findViewById<TextView>(R.id.lstProComPro)
            val lstProvComPro = vista!!.findViewById<TextView>(R.id.lstProvComPro)
            val lstCantComPro = vista!!.findViewById<TextView>(R.id.lstCantComPro)
            val lstTotComPro = vista!!.findViewById<TextView>(R.id.lstTotComPro)
            //agregamos valores a los contrales
            lstCodComPro.text = "" + objcompraproducto.idcomppro
            lstProComPro.text = "" + Producto().nombre
            lstProvComPro.text = "" + Proveedor().nombre
            lstCantComPro.text = "" + objcompraproducto.cantidad.toInt()
            lstTotComPro.text = "" + (objcompraproducto.cantidad * Producto().preciocompra)
        }
        return vista!!
    }
}