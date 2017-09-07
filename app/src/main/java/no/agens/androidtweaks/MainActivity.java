package no.agens.androidtweaks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import no.agens.androidtweakslibrary.activities.TweakStoreActivity;
import no.agens.androidtweakslibrary.interfaces.TweaksBindingBoolean;
import no.agens.androidtweakslibrary.models.TweakStore;


public class MainActivity extends AppCompatActivity {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private TweakStore tweakStore;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tweakStore = TweakStore.getInstance(this);
        tweakStore.setTweaks(MyTweaks.tweaks);
        tweakStore.setEnabled(true);

        boolean tweak1Value = tweakStore.getValue(MyTweaks.darkTheme);
        Log.d("LOG", "tweak1Value " + tweak1Value);

        tweakStore.bind(MyTweaks.bigFonts, new TweaksBindingBoolean() {
            @Override
            public void value(Boolean value) {
                Log.d("LOG", "tweak2Value " + value);
            }
        });

        showNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotification();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notificationManager.cancelAll();
    }

    private void showNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        notificationBuilder.setSmallIcon(R.drawable.arrow_down_bold_box_outline_2);
        notificationBuilder.setContentTitle(getResources().getString(R.string.app_name));
        notificationBuilder.setPriority(Notification.PRIORITY_MAX);
        notificationBuilder.setWhen(0);
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);

        Intent intent = new Intent(this, TweakStoreActivity.class);
        intent.putExtra(TWEAK_STORE_NAME, tweakStore.getTweakStoreName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.arrow_down_bold_box_outline_2,
                getResources().getString(R.string.open_tweak_store), pendingIntent);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
}