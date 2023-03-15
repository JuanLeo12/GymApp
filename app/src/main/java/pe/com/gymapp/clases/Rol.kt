package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rol {
    @SerializedName("idrol")
    @Expose
    var idrol:Long=0
    @SerializedName("rol")
    @Expose
    var rol:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    //constructores
    constructor(){}
    constructor(idrol: Long, rol: String?, estado: Boolean) {
        this.idrol = idrol
        this.rol = rol
        this.estado = estado
    }
}