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

class AlarmNotificationCompraProducto : BroadcastReceiver() {

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

        val notification = NotificationCompat.Builder(context, ActividadIngreso.MY_CHANNEL_ID)
            .setContentTitle("Compra de productos")
            .setContentText("Recuerda revisar el stock de los productos")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Hola, recuerda revisar de manera diaria los stocks de los productos para que el almacén no se quede sin suministros")
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

}