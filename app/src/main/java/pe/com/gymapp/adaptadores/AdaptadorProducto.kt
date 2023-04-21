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
    private var listaFiltrada: List<Producto>? = null

    init {
        layoutInflater = LayoutInflater.from(context)
        listaFiltrada = listaproducto
    }

    fun filter(texto: String) {
        listaFiltrada = if (texto.isEmpty()) {
            listaproducto
        } else {
            listaproducto?.filter { it.nombre!!.toLowerCase().contains(texto.toLowerCase())}
        }
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return listaFiltrada!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaFiltrada!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_producto, p2, false)
        }
            val objproducto = getItem(p0) as Producto
            //creamos los controles
            val lstCodCat = vista!!.findViewById<TextView>(R.id.lstCodPro)
            val lstNomCat = vista!!.findViewById<TextView>(R.id.lstNomPro)
            val lstPreComPro = vista!!.findViewById<TextView>(R.id.lstPreComPro)
            val lstPreVenPro = vista!!.findViewById<TextView>(R.id.lstPreVenPro)
            val lstCantPro = vista!!.findViewById<TextView>(R.id.lstCantPro)
            val lstEstCat = vista!!.findViewById<TextView>(R.id.lstEstPro)
            //agregamos valores a los contrales
            lstCodCat.text = "" + objproducto.idproducto
            lstNomCat.text = "" + objproducto.nombre
            lstPreComPro.text = "" + objproducto.preciocompra
            lstPreVenPro.text = "" + objproducto.precioventa
            lstCantPro.text = "" + objproducto.cantidad.toInt()
            if (objproducto.estado == true) {
                lstEstCat.text = "Habilitado"
            } else {
                lstEstCat.text = "Deshabilitado"
            }

        return vista!!
    }
}