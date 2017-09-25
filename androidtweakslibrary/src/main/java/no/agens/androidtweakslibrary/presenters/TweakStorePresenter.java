package no.agens.androidtweakslibrary.presenters;


import android.content.Context;
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
import no.agens.androidtweakslibrary.services.TweakStoreService;

public class TweakStorePresenter {

    public static View createTweakStoreView(final Context context, final TweakStore tweakStore) {
        View view = View.inflate(context, R.layout.tweak_store_layout, null);

        final TextView textView = view.findViewById(R.id.tweak_store_name_textView);
        textView.setText(tweakStore.getTweakStoreName());

        final List<Collection> collections = tweakStore.getCollections();
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        for (int i = 0; i < collections.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", collections.get(i).getName());
            hashMap.put("number", collections.get(i).getCountOfTweaks().toString());
            arrayList.add(hashMap);
        }

        String[] from = {"name", "number"};
        int[] to = {R.id.collection_name, R.id.collection_count};
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, arrayList, R.layout.tweak_store_list_item, from, to);
        ListView tweakStoreListView = view.findViewById(R.id.tweak_store_listView);
        tweakStoreListView.setAdapter(simpleAdapter);

        tweakStoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                View collectionView = CollectionPresenter.createCollectionView(context, collections.get(i), tweakStore);
                TweakStoreService.replaceView(collectionView);
            }
        });

        return view;
    }
}
