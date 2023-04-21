package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Proveedor

class AdaptadorProveedor (context: Context?, private val listaproveedor:List<Proveedor>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Proveedor>? = null

    init {
        layoutInflater = LayoutInflater.from(context)
        listaFiltrada = listaproveedor
    }

    fun filter(texto: String) {
        listaFiltrada = if (texto.isEmpty()) {
            listaproveedor
        } else {
            listaproveedor?.filter { it.direccion!!.toLowerCase().contains(texto.toLowerCase()) ||
                    it.telefono!!.contains(texto) ||
                    it.nombre!!.lowercase().contains(texto.lowercase())
            }
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
            vista = layoutInflater.inflate(R.layout.elemento_lista_proveedor, p2, false)
        }
            val objproveedor = getItem(p0) as Proveedor
            //creamos los controles
            val lstCodProv = vista!!.findViewById<TextView>(R.id.lstCodProv)
            val lstNomProv = vista!!.findViewById<TextView>(R.id.lstNomProv)
            val lstTelfProv = vista!!.findViewById<TextView>(R.id.lstTelfProv)
            val lstCorrProv = vista!!.findViewById<TextView>(R.id.lstCorrProv)
            val lstDirProv = vista!!.findViewById<TextView>(R.id.lstDirProv)
            val lstEstProv = vista!!.findViewById<TextView>(R.id.lstEstProv)
            //agregamos valores a los contrales
            lstCodProv.text = "Proveedor: " + objproveedor.idproveedor
            lstNomProv.text = "Nombre: " + objproveedor.nombre
            lstTelfProv.text = "Teléfono: " + objproveedor.telefono
            lstCorrProv.text = "Correo: " + objproveedor.correo
            lstDirProv.text = "Dirección: " + objproveedor.direccion
            if (objproveedor.estado == true) {
                lstEstProv.text = "Estado: Habilitado"
            } else {
                lstEstProv.text = "Estado: Deshabilitado"
            }

        return vista!!
    }
}