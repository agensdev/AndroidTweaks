package no.agens.androidtweaks;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.widget.Button;
import android.widget.TextView;

import no.agens.androidtweakslibrary.Permission;
import no.agens.androidtweakslibrary.activities.TweakStoreActivity;
import no.agens.androidtweakslibrary.interfaces.TweaksBindingBoolean;
import no.agens.androidtweakslibrary.models.TweakStore;
import no.agens.androidtweakslibrary.services.DrawerService;


public class MainActivity extends AppCompatActivity {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private static String PERMISSION = "permission";
    private TweakStore tweakStore;
    private NotificationManager notificationManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tweakStore = TweakStore.getInstance(this);
        tweakStore.setTweaks(MyTweaks.tweaks);
        tweakStore.setEnabled(true);

        final Button button = (Button) findViewById(R.id.my_button);
        final TextView textView = (TextView) findViewById(R.id.my_textView);
        final TextView textView2 = (TextView) findViewById(R.id.my_textView2);

        tweakStore.bind(MyTweaks.greenButton, new TweaksBindingBoolean() {

            @Override
            public void value(boolean value) {
                if (value)  {
                    button.setBackgroundResource(R.drawable.button_teal);
                    button.setTextColor(Color.BLACK);
                } else {
                    button.setBackgroundResource(R.drawable.button_navy);
                    button.setTextColor(Color.WHITE);
                }
            }
        });

        tweakStore.bind(MyTweaks.uppercase, new TweaksBindingBoolean() {

            @Override
            public void value(boolean value) {
                if (value)  {
                    textView.setAllCaps(true);
                } else {
                    textView.setAllCaps(false);
                }
            }
        });

        intent = new Intent(this, DrawerService.class);
        MyTweaks.addUser.setCallback(new TweakStore.Callback() {
            @Override
            public void callback() {
                count += 1;
                textView2.setText("" + count);
            }
        });

        MyTweaks.deleteUserData.setCallback(new TweakStore.Callback() {
            @Override
            public void callback() {
                count -= 1;

                if (count >= 0) {
                    textView2.setText("" + count);
                } else {
                    count = 0;
                    textView2.setText("" + count);
                }
            }
        });

        if (tweakStore.isEnabled()) {
            Permission.checkPermission(this);
            showNotification();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (tweakStore.isEnabled()) {
            Permission.checkPermission(this);
            showNotification();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        notificationManager.cancelAll();
        stopService(intent);
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
        intent.putExtra(PERMISSION, Permission.isPermissionGranted());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.arrow_down_bold_box_outline_2,
                getResources().getString(R.string.open_tweak_store), pendingIntent);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Permission.checkResult(this, requestCode);
    }
}