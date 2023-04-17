package pe.com.gymapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import pe.com.gymapp.clases.AlarmNotificationMantenimiento
import pe.com.gymapp.clases.AlarmNotificationMantenimiento.Companion.NOTIFICATION_ID
import pe.com.gymapp.clases.Usuario
import pe.com.gymapp.remoto.ApiUtil
import pe.com.gymapp.servicios.UsuarioService
import pe.com.gymapp.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ActividadIngreso : AppCompatActivity() {
    private lateinit var txtUsu:EditText
    private lateinit var txtCla:EditText
    private lateinit var btnIngresar:Button
    private lateinit var btnSalir:Button
    private lateinit var btnNoti:Button
    //creamos un objeto de la clase usuario
    val objusuario= Usuario()

    //creamos el servicio
    private var usuarioService: UsuarioService?=null
    //creamos un objeto de la clase Util
    private val objutilidad=Util()
    //declaramos variables para el usuario y la clave
    private var usu=""
    private var cla=""

    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_ingreso)
        //creamos los controles
        txtUsu=findViewById(R.id.txtInputUsu)
        txtCla=findViewById(R.id.txtInputCla)
        btnIngresar=findViewById(R.id.btnIngresar)
        btnNoti=findViewById(R.id.btnNoti)
        btnSalir=findViewById(R.id.btnSalir)
        //creamos una variable para el contexto
        val context=this

        //implementamos el servicio
        usuarioService= ApiUtil.usuarioService

        createChannel()
        btnNoti.setOnClickListener {
            scheduleNotification()

        }

        //agregamos eventos para los botones
        btnIngresar.setOnClickListener {
            if(txtUsu.getText().toString()==""){
                objutilidad.MensajeToast(this,"Ingrese el usuario")
                txtUsu.requestFocus()
            }else if(txtCla.getText().toString()==""){
                objutilidad.MensajeToast(this,"Ingrese la clave")
                txtCla.requestFocus()
            }else{
                //capturamos los valores
                usu=txtUsu.getText().toString()
                cla=txtCla.getText().toString()

                //enviamos los datos a la clase
                objusuario.usuario=usu
                objusuario.contrasena=cla

                //llamamos al metodo para validar
                ValidarUsuario(context,objusuario)


            }

        }

        btnSalir.setOnClickListener {
            objutilidad.SalirSistema(this)
        }
    }



    fun ValidarUsuario(context: Context?, u: Usuario?){
        val call= usuarioService!!.ValidarUsuario(u)
        call!!.enqueue(object : Callback<Boolean?> {
            override fun onResponse(call: Call<Boolean?>, response: Response<Boolean?>) {
                if(response.isSuccessful){
                    Log.e("respuesta",""+response.body())
                    if(response.body().toString()=="true"){
                        if (context != null) {
                            objutilidad.MensajeToast(context,"Bienvenidos al Sistema")
                        }
                        //creamos una constante para llamar a la actividad que vamos abrir
                        val formulario=Intent(context,ActividadPrincipal::class.java)
                        //iniciamos la actividad nueva
                        startActivity(formulario)
                        //cerramos la actividad correspondiente
                        finish()
                        Log.e("mensaje","Se valido")
                    }else{
                        if (context != null) {
                            objutilidad.MensajeToast(context,"Usuario o Clave no valida")
                        }
                        objutilidad.Limpiar(findViewById<View>(R.id.frmIngreso) as ViewGroup)
                        txtUsu.requestFocus()
                    }

                }
            }

            override fun onFailure(call: Call<Boolean?>, t: Throwable) {

                Log.e("Error: ", t.message!!)
            }


        })
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, AlarmNotificationMantenimiento::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis + 5000, pendingIntent)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "MySuperChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "SUSCRIBETE"
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

}