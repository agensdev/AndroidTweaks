package no.agens.androidtweaks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import no.agens.androidtweakslibrary.activities.TweakStoreActivity;
import no.agens.androidtweakslibrary.models.Tweak;
import no.agens.androidtweakslibrary.models.TweakBoolean;
import no.agens.androidtweakslibrary.models.TweakStore;

//import no.agens.androidtweakslibrary.Interface.TweakBinding;

public class MainActivity extends AppCompatActivity {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private TweakStore tweakStore;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TweakBoolean tweak1 = new TweakBoolean("Styling", "Theme", "Dark", false);
        TweakBoolean tweak2 = new TweakBoolean("Styling", "Fonts", "Big", true);

        List<Tweak> tweaks = new ArrayList<>();
        tweaks.add(tweak1);
        tweaks.add(tweak2);

        tweakStore = TweakStore.getInstance(this, "Tweaks");
        tweakStore.setTweaks(tweaks);

        boolean tweak1Value = tweakStore.getValue(tweak1);
        Log.d("BOB", "tweak1Value " + tweak1Value);
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


//    @Override
//    public void value(Boolean value) {
//
//    }

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
        notificationBuilder.addAction(R.drawable.arrow_down_bold_box_outline_2, getResources().getString(R.string.open_tweak_store), pendingIntent);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
}