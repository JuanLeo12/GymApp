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
    var preciocompramaq:Double=0.0
    @SerializedName("cantidad")
    @Expose
    var cantidad:Double=0.0
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(
        idmaquina: Long,
        nombre: String?,
        preciocompramaq: Double,
        cantidad: Double,
        estado: Boolean
    ) {
        this.idmaquina = idmaquina
        this.nombre = nombre
        this.preciocompramaq = preciocompramaq
        this.cantidad = cantidad
        this.estado = estado
    }


}