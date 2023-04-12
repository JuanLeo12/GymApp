package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AsistenciaCliente {
    @SerializedName("idasiscli")
    @Expose
    var idasiscli:Long=0
    @SerializedName("cliente")
    @Expose
    var cliente:Cliente?=null
    @SerializedName("fecha")
    @Expose
    var fecha: String? =null

    constructor(){}
    constructor(idasiscli: Long, cliente: Cliente?, fecha: String?) {
        this.idasiscli = idasiscli
        this.cliente = cliente
        this.fecha = fecha
    }

}