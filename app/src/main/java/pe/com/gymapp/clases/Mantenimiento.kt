package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Mantenimiento {
    @SerializedName("idmantenimiento")
    @Expose
    var idmantenimiento:Long=0
    @SerializedName("maquina")
    @Expose
    var maquina:Maquina?=null
    @SerializedName("fecha")
    @Expose
    var fecha:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(idmantenimiento: Long, maquina: Maquina?, fecha: String?, estado: Boolean) {
        this.idmantenimiento = idmantenimiento
        this.maquina = maquina
        this.fecha = fecha
        this.estado = estado
    }

}