package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Proveedor {
    @SerializedName("idproveedor")
    @Expose
    var idproveedor:Long=0
    @SerializedName("nombre")
    @Expose
    var nombre:String?=null
    @SerializedName("telefono")
    @Expose
    var telefono:String?=null
    @SerializedName("correo")
    @Expose
    var correo:String?=null
    @SerializedName("direccion")
    @Expose
    var direccion:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(
        idproveedor: Long,
        nombre: String?,
        telefono: String?,
        correo: String?,
        direccion: String?,
        estado: Boolean
    ) {
        this.idproveedor = idproveedor
        this.nombre = nombre
        this.telefono = telefono
        this.correo = correo
        this.direccion = direccion
        this.estado = estado
    }

}