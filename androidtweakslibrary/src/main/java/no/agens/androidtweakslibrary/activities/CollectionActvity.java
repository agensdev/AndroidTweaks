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


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import no.agens.androidtweakslibrary.adapter.TweaksAdapter;
import no.agens.androidtweakslibrary.models.Collection;
import no.agens.androidtweakslibrary.models.TweakStore;
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
