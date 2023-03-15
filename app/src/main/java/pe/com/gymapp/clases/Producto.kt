package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Producto {
    @SerializedName("idproducto")
    @Expose
    var idproducto:Long=0
    @SerializedName("nombre")
    @Expose
    var nombre:String?=null
    @SerializedName("preciocompra")
    @Expose
    var preciocompra:Double=0.0
    @SerializedName("precioventa")
    @Expose
    var precioventa:Double=0.0
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(
        idproducto: Long,
        nombre: String?,
        preciocompra: Double,
        precioventa: Double,
        estado: Boolean
    ) {
        this.idproducto = idproducto
        this.nombre = nombre
        this.preciocompra = preciocompra
        this.precioventa = precioventa
        this.estado = estado
    }

}