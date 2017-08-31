package no.agens.androidtweakslibrary.Activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import no.agens.androidtweakslibrary.Adapter.TweaksAdapter;
import no.agens.androidtweakslibrary.Models.Collection;
import no.agens.androidtweakslibrary.Models.TweakStore;
import no.agens.androidtweakslibrary.R;

public class CollectionActvity extends AppCompatActivity {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private static String COLLECTION_ID = "collectionId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        String tweakStoreName = "";
        int collectionId = 0;

        if (getIntent().getExtras() != null) {
            tweakStoreName = getIntent().getExtras().getString(TWEAK_STORE_NAME);
            collectionId = getIntent().getExtras().getInt(COLLECTION_ID);
        }

        TweakStore tweakStore = TweakStore.getInstance(this, tweakStoreName);
        Collection collection = tweakStore.getCollections().get(collectionId);

        TextView collectionNameTV = (TextView) findViewById(R.id.collection_name_textView);
        collectionNameTV.setText(collection.getName());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.group_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new TweaksAdapter(this, collection, tweakStore);
        recyclerView.setAdapter(adapter);
    }
}
