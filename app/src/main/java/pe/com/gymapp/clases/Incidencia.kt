package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

class Incidencia {
    @SerializedName("idincidencia")
    @Expose
    var idincidencia:Long=0
    @SerializedName("cliente")
    @Expose
    var cliente:Cliente?=null
    @SerializedName("empleado")
    @Expose
    var empleado:Empleado?=null
    @SerializedName("descripcion")
    @Expose
    var descripcion:String?=null
    @SerializedName("fecha")
    @Expose
    var fecha:String?=null

    constructor(){}
    constructor(
        idincidencia: Long,
        cliente: Cliente?,
        empleado: Empleado?,
        descripcion: String?,
        fecha: String?
    ) {
        this.idincidencia = idincidencia
        this.cliente = cliente
        this.empleado = empleado
        this.descripcion = descripcion
        this.fecha = fecha
    }

}