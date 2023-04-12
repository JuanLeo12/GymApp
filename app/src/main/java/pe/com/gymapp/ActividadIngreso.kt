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
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import pe.com.gymapp.clases.AlarmNotificationMantenimiento
import pe.com.gymapp.clases.AlarmNotificationMantenimiento.Companion.NOTIFICATION_ID
import pe.com.gymapp.utilidad.Util
import java.util.*

class ActividadIngreso : AppCompatActivity() {
    //creamos un objeto de la clase utilidad
    private val objutilidad= Util()
    //declaramos variales
    private var usu=""
    private var cla=""


    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_ingreso)

        //declarando los controles
        val txtUsu=findViewById<EditText>(R.id.txtInputUsu)
        val txtCla=findViewById<EditText>(R.id.txtInputCla)
        val btnIngresar=findViewById<Button>(R.id.btnIngresar)
        val btnSalir=findViewById<Button>(R.id.btnSalir)

        createChannel()

        //agregamos eventos a los botones
        btnIngresar.setOnClickListener {
            if(txtUsu.getText().toString()==""){
                objutilidad.MensajeToast(this,"Ingrese el usuario")
                txtUsu.requestFocus()
            }else if(txtCla.getText().toString()==""){
                objutilidad.MensajeToast(this,"Ingrese la clave")
                txtCla.requestFocus()
            }else{
                usu=txtUsu.getText().toString()
                cla=txtCla.getText().toString()
                if(usu.equals("admin")&&cla.equals("admin")){
                    objutilidad.MensajeToast(this,"Bienvenidos al Sistema")
                    val formulario= Intent(this,ActividadPrincipal::class.java)
                    startActivity(formulario)
                    this.finish()
                    scheduleNotification()
                }else{
                    objutilidad.MensajeToast(this,"Usuario o Clave no valido")
                    objutilidad.Limpiar((findViewById<View>(R.id.frmIngreso)as ViewGroup))
                    txtUsu.requestFocus()

                }
            }


        }

        btnSalir.setOnClickListener {
            objutilidad.SalirSistema(this)
        }

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