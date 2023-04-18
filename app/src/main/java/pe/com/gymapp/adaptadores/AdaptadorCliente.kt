package pe.com.gymapp.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.gymapp.R
import pe.com.gymapp.clases.Cliente

class AdaptadorCliente (context: Context?, private val listacliente:List<Cliente>?):
    BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater= LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listacliente!!.size
    }

    override fun getItem(p0: Int): Any {
        return listacliente!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_cliente,p2,false)
            val objcliente=getItem(p0) as Cliente
            //creamos los controles
            val lstCodCli= vista!!.findViewById<TextView>(R.id.lstCodCli)
            val lstNomCli= vista!!.findViewById<TextView>(R.id.lstNomCli)
            val lstApPatCli= vista!!.findViewById<TextView>(R.id.lstApPatCli)
            val lstApMatCli= vista!!.findViewById<TextView>(R.id.lstApMatCli)
            val lstTelfCli= vista!!.findViewById<TextView>(R.id.lstTelfCli)
            val lstCorrCli= vista!!.findViewById<TextView>(R.id.lstCorrCli)
            val lstDirCli= vista!!.findViewById<TextView>(R.id.lstDirCli)
            val lstGenCli= vista!!.findViewById<TextView>(R.id.lstGenCli)
            val lstMemCli= vista!!.findViewById<TextView>(R.id.lstMemCli)
            val lstEstCli= vista!!.findViewById<TextView>(R.id.lstEstCli)
            //agregamos los valores a la lista
            lstCodCli.text="Código: "+objcliente.idcliente
            lstNomCli.text="Nombre: "+objcliente.nombre
            lstApPatCli.text="Apellido Paterno: "+objcliente.apepaterno
            lstApMatCli.text="Apellido Materno: "+objcliente.apematerno
            lstTelfCli.text="Teléfono: "+objcliente.telefono
            lstCorrCli.text="Correo: "+objcliente.correo
            lstDirCli.text="Dirección: "+objcliente.direccion
            lstGenCli.text="Género: "+objcliente.genero
            lstMemCli.text="Membresía: "+objcliente.membresia
            if(objcliente.estado==true){
                lstEstCli.text="Estado: "+"Habilitado"
            }else{
                lstEstCli.text="Estado: "+"Deshabilitado"
            }
            lstGenCli.text="Género: "+objcliente.genero!!.genero
            lstMemCli.text="Membresía: "+ objcliente.membresia!!.tiempo
        }
        return vista!!
    }
}
