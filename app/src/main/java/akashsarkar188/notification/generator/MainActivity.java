package akashsarkar188.notification.generator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    String title = "Congrats ! You are awesome !!";
    String content = "Hey buddy ! If you can see this that means you are amazing ! you are doing a great job !! All the best for your future :)";
    String CHANNEL_ID = "NotificationGenerator_Simple";
    String name = "for API 26+";
    String description = "Just to show notification above API 26";
    Intent intent;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CREATE AN INTENT TO DIRECT USER TO SPECIFIC ACTIVITY
        intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

    }

    /**
     * THIS METHOD WILL TRIGGER A SIMPLE NOTIFICATION WITH A TITLE AND MESSAGE
     *
     * @param view : for direct OnClick event
     */
    public void simpleNotification(View view) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                // THIS STYLE CAN BE ADDED IF THE CONTENT TEXT IS LARGER THAN 1 LINE
                // AND USING THIS THE NOTIFICATION CAN BE EXPANDED
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setContentText(content)
                .setColorized(true)
                .setVibrate(new long[]{0,500,100,500,100,500})
                .setSound(Settings.System.DEFAULT_RINGTONE_URI)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // AFTER GIVING ATTRIBUTES TO THE NOTIFICATION
        // WE WILL SIMPLY CREATE A CHANNEL IF API IS 26+
        // ELSE JUST BUILD AND TRIGGER THE NOTIFICATION
        createNotificationChannel(builder);

    }

    private void createNotificationChannel(NotificationCompat.Builder builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            channel.setShowBadge(true);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(1, builder.build());
        } else {
            // THIS IS THE WAY TO USE SYSTEM SERVICE BEFORE API 22
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
        }
        // IN SOME CASES THE DEFAULT METHOD .setVibrate() MIGHT
        // NOT WORK SO, YOU CAN IMPLEMENT THIS METHOD
        /*Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);*/
    }

    public void imageNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                // THIS STYLE CAN BE ADDED IF THE CONTENT TEXT IS LARGER THAN 1 LINE
                // AND USING THIS THE NOTIFICATION CAN BE EXPANDED
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),  R.drawable.android_image)))
                .setContentText(content)
                .setColorized(true)
                .setVibrate(new long[]{0,500,100,500,100,500})
                .setSound(Settings.System.DEFAULT_RINGTONE_URI)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // AFTER GIVING ATTRIBUTES TO THE NOTIFICATION
        // WE WILL SIMPLY CREATE A CHANNEL IF API IS 26+
        // ELSE JUST BUILD AND TRIGGER THE NOTIFICATION
        createNotificationChannel(builder);
    }
}
