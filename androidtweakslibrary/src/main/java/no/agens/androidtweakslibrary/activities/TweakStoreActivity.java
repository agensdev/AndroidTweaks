package no.agens.androidtweakslibrary.activities;

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

import no.agens.androidtweakslibrary.R;
import no.agens.androidtweakslibrary.models.Collection;
import no.agens.androidtweakslibrary.models.TweakStore;


public class TweakStoreActivity extends AppCompatActivity {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private static String COLLECTION_ID = "collectionId";
    private String tweakStoreName;
    private List<Collection> collections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweak_store);

        if (getIntent().getExtras() != null) {
            tweakStoreName = getIntent().getExtras().getString(TWEAK_STORE_NAME);
        }

        TextView tweakStoreNameTV = (TextView) findViewById(R.id.tweaksStore_name_textView);
        tweakStoreNameTV.setText(tweakStoreName);

        TweakStore tweakStore = TweakStore.getInstance(this, tweakStoreName);
        collections = tweakStore.getCollections();
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        for (int i = 0; i < collections.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", collections.get(i).getName());
            hashMap.put("number", collections.get(i).getCountOfTweaks() + "   >");
            arrayList.add(hashMap);
        }

        String[] from = {"name", "number"};
        int[] to = {R.id.collection_name, R.id.collection_count};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.tweak_store_list_item, from, to);
        ListView tweakStoreListView = (ListView) findViewById(R.id.tweak_store_ListView);
        tweakStoreListView.setAdapter(simpleAdapter);

        tweakStoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(), CollectionActvity.class);
                intent.putExtra(TWEAK_STORE_NAME, tweakStoreName);
                intent.putExtra(COLLECTION_ID, i);
                startActivity(intent);
            }
        });
    }
}
