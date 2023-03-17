package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Cliente {
    @SerializedName("idcliente")
    @Expose
    var idcliente:Long=0
    @SerializedName("nombre")
    @Expose
    var nombre:String?=null
    @SerializedName("apepaterno")
    @Expose
    var apepaterno:String?=null
    @SerializedName("apematerno")
    @Expose
    var apematerno:String?=null
    @SerializedName("telefono")
    @Expose
    var telefono:String?=null
    @SerializedName("correo")
    @Expose
    var correo:String?=null
    @SerializedName("genero")
    @Expose
    var genero:String?=null
    @SerializedName("direccion")
    @Expose
    var direccion:String?=null
    @SerializedName("membresia")
    @Expose
    var membresia:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(
        idcliente: Long,
        nombre: String?,
        apepaterno: String?,
        apematerno: String?,
        telefono: String?,
        correo: String?,
        genero: String?,
        direccion: String?,
        membresia: String?,
        estado: Boolean
    ) {
        this.idcliente = idcliente
        this.nombre = nombre
        this.apepaterno = apepaterno
        this.apematerno = apematerno
        this.telefono = telefono
        this.correo = correo
        this.genero = genero
        this.direccion = direccion
        this.membresia = membresia
        this.estado = estado
    }

}