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

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaproveedor!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaproveedor!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        if (vista == null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_proveedor, p2, false)
            val objproveedor = getItem(p0) as Proveedor
            //creamos los controles
            val lstCodProv = vista!!.findViewById<TextView>(R.id.lstCodProv)
            val lstNomProv = vista!!.findViewById<TextView>(R.id.lstNomProv)
            val lstTelfProv = vista!!.findViewById<TextView>(R.id.lstTelfProv)
            val lstCorrProv = vista!!.findViewById<TextView>(R.id.lstCorrProv)
            val lstDirProv = vista!!.findViewById<TextView>(R.id.lstDirProv)
            val lstEstProv = vista!!.findViewById<TextView>(R.id.lstEstProv)
            //agregamos valores a los contrales
            lstCodProv.text = "" + objproveedor.idproveedor
            lstNomProv.text = "" + objproveedor.nombre
            lstTelfProv.text = "" + objproveedor.telefono
            lstCorrProv.text = "" + objproveedor.correo
            lstDirProv.text = "" + objproveedor.direccion
            if (objproveedor.estado == true) {
                lstEstProv.text = "Habilitado"
            } else {
                lstEstProv.text = "Deshabilitado"
            }
        }
        return vista!!
    }
}