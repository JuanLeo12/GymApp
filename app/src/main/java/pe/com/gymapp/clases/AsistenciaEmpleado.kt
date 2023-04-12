package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AsistenciaEmpleado {
    @SerializedName("idasisemp")
    @Expose
    var idasisemp:Long=0
    @SerializedName("empleado")
    @Expose
    var empleado:Empleado?=null
    @SerializedName("fecha")
    @Expose
    var fecha: String? =null

    constructor(){}
    constructor(idasisemp: Long, empleado: Empleado?, fecha: String?) {
        this.idasisemp = idasisemp
        this.empleado = empleado
        this.fecha = fecha
    }

}