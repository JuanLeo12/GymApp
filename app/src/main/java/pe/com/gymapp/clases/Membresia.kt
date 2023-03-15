package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Membresia {
    @SerializedName("idmembresia")
    @Expose
    var idmembresia :Long=0
    @SerializedName("tiempo")
    @Expose
    var tiempo:String?=null
    @SerializedName("precio")
    @Expose
    var precio:Double=0.0
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(idmembresia: Long, tiempo: String?, precio: Double, estado: Boolean) {
        this.idmembresia = idmembresia
        this.tiempo = tiempo
        this.precio = precio
        this.estado = estado
    }

}