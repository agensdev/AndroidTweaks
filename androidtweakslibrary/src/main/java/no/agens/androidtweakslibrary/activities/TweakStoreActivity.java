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

import no.agens.androidtweakslibrary.models.Collection;
import no.agens.androidtweakslibrary.models.Tweak;
import no.agens.androidtweakslibrary.models.TweakBoolean;
import no.agens.androidtweakslibrary.models.TweakStore;
import no.agens.androidtweakslibrary.R;


public class TweakStoreActivity extends AppCompatActivity {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private static String COLLECTION_ID = "collectionId";
    private List<Collection> collections;
    private TweakStore tweakStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweak_store);

        Tweak tweak1 = new TweakBoolean("Styling", "Theme", "Dark", false);
        Tweak tweak2 = new TweakBoolean("Styling", "Fonts", "Big", true);
        Tweak tweak3 = new TweakBoolean("Styling", "Fonts", "Small", false);
        Tweak tweak4 = new TweakBoolean("Styling", "Fonts", "Medium", false);
        Tweak tweak5 = new TweakBoolean("Styling", "Theme", "Light", false);
        Tweak tweak6 = new TweakBoolean("Drawing", "Color", "Pink", false);
        Tweak tweak7 = new TweakBoolean("Styling", "Theme", "Custom", false);
        Tweak tweak8 = new TweakBoolean("Drawing", "Color", "Black", false);

        List<Tweak> tweaks = new ArrayList<>();
        tweaks.add(tweak1);
        tweaks.add(tweak2);
        tweaks.add(tweak3);
        tweaks.add(tweak4);
        tweaks.add(tweak5);
        tweaks.add(tweak6);
        tweaks.add(tweak7);
        tweaks.add(tweak8);

        tweakStore = TweakStore.getInstance(this, "Tweaks");
        tweakStore.setTweaks(tweaks);


        TextView tweakStoreNameTV = (TextView) findViewById(R.id.tweaksStore_name_textView);
        tweakStoreNameTV.setText(tweakStore.getTweakStoreName());

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
                Intent intent = new Intent(getApplicationContext(), Collection.class);
                intent.putExtra(TWEAK_STORE_NAME, tweakStore.getTweakStoreName());
                intent.putExtra(COLLECTION_ID, i);
                startActivity(intent);
            }
        });
    }
}
