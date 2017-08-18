package no.agens.androidtweaks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class GroupList extends AppCompatActivity {
    private static String COLLECTION_ID = "collectionId";
    private int collectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        if (getIntent().getExtras() != null) {
            collectionId = getIntent().getExtras().getInt(COLLECTION_ID);
        }

        TweakStore tweakStore = TweakStore.getInstance();
        Collection collection = tweakStore.getCollections().get(collectionId);

        TextView collectionNameTV = (TextView) findViewById(R.id.collection_name_textView);
        collectionNameTV.setText(collection.getName());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.group_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new CustomRecyclerViewAdapter(this, collection);
        recyclerView.setAdapter(adapter);
    }
}
