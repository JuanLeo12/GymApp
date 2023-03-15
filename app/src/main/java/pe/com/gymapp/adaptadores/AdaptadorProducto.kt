package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Producto

class AdaptadorProducto (context: Context?, private val listaproducto:List<Producto>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaproducto!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaproducto!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_producto, p2, false)
            val objproducto = getItem(p0) as Producto
            //creamos los controles
            val lstCodCat = vista!!.findViewById<TextView>(R.id.lstCodPro)
            val lstNomCat = vista!!.findViewById<TextView>(R.id.lstNomPro)
            val lstPreComPro = vista!!.findViewById<TextView>(R.id.lstPreComPro)
            val lstPreVenPro = vista!!.findViewById<TextView>(R.id.lstPreVenPro)
            val lstEstCat = vista!!.findViewById<TextView>(R.id.lstEstPro)
            //agregamos valores a los contrales
            lstCodCat.text = "" + objproducto.idproducto
            lstNomCat.text = "" + objproducto.nombre
            lstPreComPro.text = "" + objproducto.preciocompra
            lstPreVenPro.text = "" + objproducto.precioventa
            if (objproducto.estado == true) {
                lstEstCat.text = "Habilitado"
            } else {
                lstEstCat.text = "Deshabilitado"
            }
        }
        return vista!!
    }
}