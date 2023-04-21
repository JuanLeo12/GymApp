package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Usuario {
    @SerializedName("idusuario")
    @Expose
    var idusuario:Long=0
    @SerializedName("usuario")
    @Expose
    var usuario:String?=null
    @SerializedName("contrasena")
    @Expose
    var contrasena:String?=null
    @SerializedName("empleado")
    @Expose
    var empleado:Empleado?=null
    @SerializedName("estado")
    @Expose
    var estado=false

    constructor(){}
    constructor(
        idusuario: Long,
        usuario: String?,
        contrasena: String?,
        empleado: Empleado?,
        estado: Boolean
    ) {
        this.idusuario = idusuario
        this.usuario = usuario
        this.contrasena = contrasena
        this.empleado = empleado
        this.estado = estado
    }

}