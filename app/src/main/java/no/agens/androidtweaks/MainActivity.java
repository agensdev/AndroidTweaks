package no.agens.androidtweaks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import no.agens.androidtweakslibrary.Interface.TweakBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, Class.forName("no.agens.androidtweakslibrary.Activities.TweakStoreActivity"));
        startActivity(intent);
    }


//    @Override
//    public void value(Boolean value) {
//
//    }
}
