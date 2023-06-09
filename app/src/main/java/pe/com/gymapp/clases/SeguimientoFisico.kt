package pe.com.gymapp.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SeguimientoFisico {
    @SerializedName("idsegfis")
    @Expose
    var idsegfis:Long=0
    @SerializedName("cliente")
    @Expose
    var cliente:Cliente?=null
    @SerializedName("peso")
    @Expose
    var peso:String?=null
    @SerializedName("cuello")
    @Expose
    var cuello:String?=null
    @SerializedName("hombros")
    @Expose
    var hombros:String?=null
    @SerializedName("pecho")
    @Expose
    var pecho:String?=null
    @SerializedName("cintura")
    @Expose
    var cintura:String?=null
    @SerializedName("bicepizq")
    @Expose
    var bicepizq:String?=null
    @SerializedName("bicepder")
    @Expose
    var bicepder:String?=null
    @SerializedName("antebrazoizq")
    @Expose
    var antebrazoizq:String?=null
    @SerializedName("antebrazoder")
    @Expose
    var antebrazoder:String?=null
    @SerializedName("gluteos")
    @Expose
    var gluteos:String?=null
    @SerializedName("musloizq")
    @Expose
    var musloizq:String?=null
    @SerializedName("musloder")
    @Expose
    var musloder:String?=null
    @SerializedName("pantorrillaizq")
    @Expose
    var pantorrillaizq:String?=null
    @SerializedName("pantorrillader")
    @Expose
    var pantorrillader:String?=null
    @SerializedName("fecha")
    @Expose
    var fecha:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    constructor(){}
    constructor(
        idsegfis: Long,
        cliente: Cliente?,
        peso: String?,
        cuello: String?,
        hombros: String?,
        pecho: String?,
        cintura: String?,
        bicepizq: String?,
        bicepder: String?,
        antebrazoizq: String?,
        antebrazoder: String?,
        gluteos: String?,
        musloizq: String?,
        musloder: String?,
        pantorrillaizq: String?,
        pantorrillader: String?,
        fecha: String?,
        estado: Boolean
    ) {
        this.idsegfis = idsegfis
        this.cliente = cliente
        this.peso = peso
        this.cuello = cuello
        this.hombros = hombros
        this.pecho = pecho
        this.cintura = cintura
        this.bicepizq = bicepizq
        this.bicepder = bicepder
        this.antebrazoizq = antebrazoizq
        this.antebrazoder = antebrazoder
        this.gluteos = gluteos
        this.musloizq = musloizq
        this.musloder = musloder
        this.pantorrillaizq = pantorrillaizq
        this.pantorrillader = pantorrillader
        this.fecha = fecha
        this.estado = estado
    }


}