package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Empleado {
    @SerializedName("idempleado")
    @Expose
    var idempleado:Long=0
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
    var genero:Genero?=null
    @SerializedName("direccion")
    @Expose
    var direccion:String?=null
    @SerializedName("rol")
    @Expose
    var rol:Rol?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(
        idempleado: Long,
        nombre: String?,
        apepaterno: String?,
        apematerno: String?,
        telefono: String?,
        correo: String?,
        genero: Genero?,
        direccion: String?,
        rol: Rol?,
        estado: Boolean
    ) {
        this.idempleado = idempleado
        this.nombre = nombre
        this.apepaterno = apepaterno
        this.apematerno = apematerno
        this.telefono = telefono
        this.correo = correo
        this.genero = genero
        this.direccion = direccion
        this.rol = rol
        this.estado = estado
    }


}