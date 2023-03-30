package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TipoPago {

    @SerializedName("idtipopago")
    @Expose
    var idtipopago:Long=0
    @SerializedName("tipopago")
    @Expose
    var tipopago:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(idtipopago: Long, tipopago: String?, estado: Boolean) {
        this.idtipopago = idtipopago
        this.tipopago = tipopago
        this.estado = estado
    }

}