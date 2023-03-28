package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Empleado

class AdaptadorComboEmpleado (context: Context?, private val listaempleado:List<Empleado>?) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaempleado!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaempleado!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista=layoutInflater.inflate(R.layout.elemento_combo_empleado,p2,false)
            val objempleado=getItem(p0) as Empleado
            //creamos los controles
            val lblNomEmp= vista!!.findViewById<TextView>(R.id.lblNomEmp)

            //agregamos los valores a la lista
            lblNomEmp.text=""+objempleado.nombre
        }
        return vista!!
    }
}