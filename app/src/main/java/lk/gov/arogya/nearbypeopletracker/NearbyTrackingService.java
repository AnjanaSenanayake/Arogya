package lk.gov.arogya.nearbypeopletracker;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;
import static lk.gov.arogya.support.FileUtils.writeToStorage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.Calendar;
import lk.gov.arogya.R;
import lk.gov.arogya.support.ContentHolder;
import lk.gov.arogya.support.FileUtils;

public class NearbyTrackingService extends Service {

    private final String TAG = "NearByMessageService";
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private MessageListener messageListener;
    private Message myUserUIDMessage;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myUserUIDMessage = new Message(FileUtils.getHash(ContentHolder.getUID()).getBytes());
        messageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                String contactedUserUIDHash = new String(message.getContent());
                Log.d(TAG, "Found user: " + contactedUserUIDHash);
                LocationRequest mLocationRequest = new LocationRequest();
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                getFusedLocationProviderClient(getApplicationContext()).getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                String filePath = getApplicationContext().getFilesDir().toString() + "/contacts/";
                                String content = contactedUserUIDHash + "---" + location.getLatitude()
                                        + "---" + location.getLongitude() + "---" + Calendar
                                        .getInstance().getTime() + "\n";
                                writeToStorage(filePath, "contacts.txt", content);
                                FirebaseMessaging.getInstance().subscribeToTopic(contactedUserUIDHash);
                            }
                        });
            }

            @Override
            public void onLost(Message message) {
                Log.d(TAG, "Closed application");
                String metUserUID = new String(message.getContent());
                Log.d(TAG, "Lost sight of user: " + metUserUID);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Starting nearby people tracking", Toast.LENGTH_LONG).show();
        Nearby.getMessagesClient(this).publish(myUserUIDMessage);
        Nearby.getMessagesClient(this).subscribe(messageListener);
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, DiseaseContactTraceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Nearby people tracking")
                .setSmallIcon(R.drawable.ic_pickup_point)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Nearby.getMessagesClient(this).unpublish(myUserUIDMessage);
        Nearby.getMessagesClient(this).unsubscribe(messageListener);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
