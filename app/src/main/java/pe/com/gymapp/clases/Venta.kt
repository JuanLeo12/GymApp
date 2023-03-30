package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Venta {

    @SerializedName("idventa")
    @Expose
    var idventa:Long=0
    @SerializedName("producto")
    @Expose
    var producto:Producto?=null
    @SerializedName("tipopago")
    @Expose
    var tipopago:TipoPago?=null
    @SerializedName("cantidad")
    @Expose
    var cantidad:Double=0.0
    @SerializedName("cliente")
    @Expose
    var cliente:Cliente?=null
    @SerializedName("empleado")
    @Expose
    var empleado:Empleado?=null

    constructor(){}
    constructor(
        idventa: Long,
        producto: Producto?,
        tipopago: TipoPago?,
        cantidad: Double,
        cliente: Cliente?,
        empleado: Empleado?
    ) {
        this.idventa = idventa
        this.producto = producto
        this.tipopago = tipopago
        this.cantidad = cantidad
        this.cliente = cliente
        this.empleado = empleado
    }


}