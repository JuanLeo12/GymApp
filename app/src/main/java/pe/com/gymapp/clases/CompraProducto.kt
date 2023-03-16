package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CompraProducto {
    @SerializedName("idcomppro")
    @Expose
    var idcomppro:Long=0
    @SerializedName("producto")
    @Expose
    var producto:Producto?=null
    @SerializedName("proveedor")
    @Expose
    var proveedor:Proveedor?=null
    @SerializedName("cantidad")
    @Expose
    var cantidad:Double=0.0

    constructor(){}
    constructor(idcomppro: Long, producto: Producto?, proveedor: Proveedor?, cantidad: Double) {
        this.idcomppro = idcomppro
        this.producto = producto
        this.proveedor = proveedor
        this.cantidad = cantidad
    }

}