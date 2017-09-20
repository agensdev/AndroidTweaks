//
//    Authors:
//    Blanka Kulik <blanka@agens.no>
//    Håvard Fossli <hfossli@gmail.com>
//
//    MIT License
//
//    Copyright (c) 2017 Agens AS (http://agens.no/)
//
//    Permission is hereby granted, free of charge, to any person obtaining a copy
//    of this software and associated documentation files (the "Software"), to deal
//    in the Software without restriction, including without limitation the rights
//    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//    copies of the Software, and to permit persons to whom the Software is
//    furnished to do so, subject to the following conditions:
//
//    The above copyright notice and this permission notice shall be included in all
//    copies or substantial portions of the Software.
//
//    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//    SOFTWARE.

package no.agens.androidtweakslibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.agens.androidtweakslibrary.R;
import no.agens.androidtweakslibrary.models.Collection;
import no.agens.androidtweakslibrary.models.TweakStore;


public class TweakStoreActivity extends AppCompatActivity {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private static String COLLECTION_ID = "collectionId";
    private static String PERMISSION = "permission";
    private String tweakStoreName;
    private List<Collection> collections;
    private boolean isPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweak_store);

        if (getIntent().getExtras() != null) {
            tweakStoreName = getIntent().getExtras().getString(TWEAK_STORE_NAME);
            isPermissionGranted = getIntent().getExtras().getBoolean(PERMISSION);
        }

        setTitle(tweakStoreName);

        TweakStore tweakStore = TweakStore.getInstance(this, tweakStoreName);
        collections = tweakStore.getCollections();
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        for (int i = 0; i < collections.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", collections.get(i).getName());
            hashMap.put("number", collections.get(i).getCountOfTweaks().toString());
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
                intent.putExtra(PERMISSION, isPermissionGranted);
                startActivity(intent);
                finish();
            }
        });
    }
}
