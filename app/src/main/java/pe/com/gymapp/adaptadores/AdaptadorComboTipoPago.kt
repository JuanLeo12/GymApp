package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.TipoPago

class AdaptadorComboTipoPago (context: Context?, private val listatipopago:List<TipoPago>?) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listatipopago!!.size
    }

    override fun getItem(p0: Int): Any {
        return listatipopago!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista=layoutInflater.inflate(R.layout.elemento_combo_tipopago,p2,false)
            val objtipopago=getItem(p0) as TipoPago
            //creamos los controles
            val lblNomTPago= vista!!.findViewById<TextView>(R.id.lblNomTPago)

            //agregamos los valores a la lista
            lblNomTPago.text=""+objtipopago.tipopago
        }
        return vista!!
    }
}