package no.agens.androidtweaks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import no.agens.androidtweakslibrary.Interface.TweakBinding;
import no.agens.androidtweakslibrary.Models.Collection;
import no.agens.androidtweakslibrary.Models.Tweak;
import no.agens.androidtweakslibrary.Models.TweakBoolean;
import no.agens.androidtweakslibrary.Models.TweakStore;

public class MainActivity extends AppCompatActivity {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private static String COLLECTION_ID = "collectionId";
    private List<Collection> collections;
    private TweakStore tweakStore;

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
