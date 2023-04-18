package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Empleado

class AdaptadorEmpleado (context: Context?, private val listaempleado:List<Empleado>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater= LayoutInflater.from(context)
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
            vista=layoutInflater.inflate(R.layout.elemento_lista_empleado,p2,false)
            val objempleado=getItem(p0) as Empleado
            //creamos los controles
            val lstCodEmp= vista!!.findViewById<TextView>(R.id.lstCodEmp)
            val lstNomEmp= vista!!.findViewById<TextView>(R.id.lstNomEmp)
            val lstApPatEmp= vista!!.findViewById<TextView>(R.id.lstApPatEmp)
            val lstApMatEmp= vista!!.findViewById<TextView>(R.id.lstApMatEmp)
            val lstTelfEmp= vista!!.findViewById<TextView>(R.id.lstTelfEmp)
            val lstCorrEmp= vista!!.findViewById<TextView>(R.id.lstCorrEmp)
            val lstGenEmp= vista!!.findViewById<TextView>(R.id.lstGenEmp)
            val lstDirEmp= vista!!.findViewById<TextView>(R.id.lstDirEmp)
            val lstRolEmp= vista!!.findViewById<TextView>(R.id.lstRolEmp)
            val lstEstEmp= vista!!.findViewById<TextView>(R.id.lstEstEmp)
            //agregamos los valores a la lista
            lstCodEmp.text="Código: "+objempleado.idempleado
            lstNomEmp.text="Nombre: "+objempleado.nombre
            lstApPatEmp.text="Apellido Paterno: "+objempleado.apepaterno
            lstApMatEmp.text="Apellido Materno: "+objempleado.apematerno
            lstTelfEmp.text="Teléfono: "+objempleado.telefono
            lstCorrEmp.text="Correo: "+objempleado.correo
            lstGenEmp.text="Género: "+objempleado.genero
            lstDirEmp.text="Dirección: "+objempleado.direccion
            lstRolEmp.text="Rol: "+objempleado.rol
            if(objempleado.estado==true){
                lstEstEmp.text="Estado: "+"Habilitado"
            }else{
                lstEstEmp.text="Estado: "+"Deshabilitado"
            }
            lstGenEmp.text="Género: "+objempleado.genero!!.genero
            lstRolEmp.text="Rol: "+ objempleado.rol!!.rol
        }
        return vista!!
    }
}
