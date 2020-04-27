package lk.gov.arogya.nearbypeopletracker;

import static lk.gov.arogya.support.FileUtils.writeToStorage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import lk.gov.arogya.R;

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "FCM Received");
        String UIDHash = remoteMessage.getData().get("UIDHash");
        String epidemic = remoteMessage.getData().get("Epidemic");
        String filePath = getApplicationContext().getFilesDir().toString() + "/contacts/";
        writeToStorage(filePath, "alerts.txt", UIDHash + "---" + epidemic + "\n");
        sendNotification(UIDHash, epidemic);
    }

    private void sendNotification(String UIDHash, String epidemic) {
        Intent intent = new Intent(this, DiseaseContactTraceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("UIDHash", UIDHash);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "channel_id";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_heartbeat);
        NotificationCompat.Builder notificationBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.ic_heartbeat)
                            .setLargeIcon(icon)
                            .setColor(getResources().getColor(R.color.colorPrimary))
                            .setContentTitle("Arogya")
                            .setContentText("Disease Contact Alert")
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);
        } else {
            notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.ic_heartbeat)
                            .setContentTitle("Arogya")
                            .setContentText("Disease Contact Alert")
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);
        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
