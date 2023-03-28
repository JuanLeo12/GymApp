package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Genero {
    @SerializedName("idgenero")
    @Expose
    var idgenero:Long=0
    @SerializedName("genero")
    @Expose
    var genero:String=""
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(idgenero: Long, genero: String, estado: Boolean) {
        this.idgenero = idgenero
        this.genero = genero
        this.estado = estado
    }

}