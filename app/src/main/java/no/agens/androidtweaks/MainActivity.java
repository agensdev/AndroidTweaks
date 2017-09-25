package no.agens.androidtweaks;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import no.agens.androidtweakslibrary.Permission;
import no.agens.androidtweakslibrary.interfaces.TweaksBindingBoolean;
import no.agens.androidtweakslibrary.models.TweakStore;
import no.agens.androidtweakslibrary.services.TweakStoreService;


public class MainActivity extends AppCompatActivity {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private TweakStore tweakStore;
    private Intent intent;
    private int count = 0;

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
                    button.setBackgroundResource(R.drawable.button_green);
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
            launchTweakStore();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (tweakStore.isEnabled()) {
            Permission.checkPermission(this);
            launchTweakStore();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Permission.checkResult(this, requestCode);
    }

    private void launchTweakStore() {
        intent = new Intent(this, TweakStoreService.class);
        intent.putExtra(TWEAK_STORE_NAME, tweakStore.getTweakStoreName());

        if (Permission.isPermissionGranted()) {
            startService(intent);
        }
    }
}