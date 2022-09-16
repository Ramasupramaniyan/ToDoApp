package `in`.lightspeed.todo
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate

class MyService : Service() {
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
