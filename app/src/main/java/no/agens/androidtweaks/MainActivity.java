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

import no.agens.androidtweaks.Models.Collection;
import no.agens.androidtweaks.Models.Group;
import no.agens.androidtweaks.Models.Tweak;
import no.agens.androidtweaks.Models.TweakStore;

public class MainActivity extends AppCompatActivity {
    private static String COLLECTION_ID = "collectionId";
    private List<Collection> collections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Tweak tweak1 = new Tweak("Dark");
        Tweak tweak2 = new Tweak("Big");
        Tweak tweak3 = new Tweak("Small");
        Tweak tweak4 = new Tweak("Medium");
        Tweak tweak5 = new Tweak("Light");
        List<Tweak> tweaks1 = new ArrayList<>();
        tweaks1.add(tweak1);
        tweaks1.add(tweak5);
        List<Tweak> tweaks2 = new ArrayList<>();
        tweaks2.add(tweak2);
        tweaks2.add(tweak3);
        tweaks2.add(tweak4);
        List<Tweak> tweaks3 = new ArrayList<>();
        Group group1 = new Group("Theme", tweaks1);
        Group group2 = new Group("Fonts", tweaks2);
        Group group3 = new Group("Other", tweaks3);
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        Collection collection = new Collection("Styling", groups);
        List<Collection> collectionsList = new ArrayList<>();
        collectionsList.add(collection);
        final TweakStore tweakStore = TweakStore.getInstance();
        tweakStore.setName("Tweak Store");
        tweakStore.setCollections(collectionsList);

        TextView tweakStoreNameTV = (TextView) findViewById(R.id.tweaksStore_name_textView);
        tweakStoreNameTV.setText(tweakStore.getName());

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
                Intent intent = new Intent(getApplicationContext(), GroupList.class);
                intent.putExtra(COLLECTION_ID, i);
                startActivity(intent);
            }
        });

    }
}
