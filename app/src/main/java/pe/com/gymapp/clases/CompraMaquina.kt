package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CompraMaquina {
    @SerializedName("idcompmaq")
    @Expose
    var idcompmaq:Long=0
    @SerializedName("maquina")
    @Expose
    var maquina:Maquina?=null
    @SerializedName("proveedor")
    @Expose
    var proveedor:Proveedor?=null
    @SerializedName("cantidad")
    @Expose
    var cantidad:Double=0.0

    constructor(){}
    constructor(idcompmaq: Long, maquina: Maquina?, proveedor: Proveedor?, cantidad: Double) {
        this.idcompmaq = idcompmaq
        this.maquina = maquina
        this.proveedor = proveedor
        this.cantidad = cantidad
    }

}