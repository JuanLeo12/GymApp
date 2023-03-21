package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Maquina {
    @SerializedName("idmaquina")
    @Expose
    var idmaquina:Long=0
    @SerializedName("nombre")
    @Expose
    var nombre:String?=null
    @SerializedName("preciocompra")
    @Expose
    var preciocompra:Double=0.0
    @Expose
    var cantidad:Double=0.0
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(idmaquina: Long, nombre: String?, preciocompra: Double, cantidad: Double, estado: Boolean) {
        this.idmaquina = idmaquina
        this.nombre = nombre
        this.preciocompra = preciocompra
        this.cantidad = cantidad
        this.estado = estado
    }

}