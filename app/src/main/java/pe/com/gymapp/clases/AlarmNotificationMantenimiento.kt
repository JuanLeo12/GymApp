package pe.com.gymapp.clases

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import pe.com.gymapp.ActividadIngreso
import pe.com.gymapp.FragmentoMantenimiento

class AlarmNotificationMantenimiento : BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context, p1: Intent?) {
        createSimpleNotification(context)
    }

    private fun createSimpleNotification(context: Context) {
        val intent = Intent(context, FragmentoMantenimiento::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

//        val notification = NotificationCompat.Builder(context, ActividadIngreso.MY_CHANNEL_ID)
//           .setContentTitle("Mantenimiento de Máquinas")
//            .setContentText("Recuerda revisar las fechas de mantenimiento")
//            .setStyle(
//                NotificationCompat.BigTextStyle()
//                    .bigText("Hola, recuerda revisar de manera diaria las fechas de mantenimiento de máquinas para que no se te pase ninguna fecha")
//            )
//            .setContentIntent(pendingIntent)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .build()
//
//        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(NOTIFICATION_ID, notification)
    }

}