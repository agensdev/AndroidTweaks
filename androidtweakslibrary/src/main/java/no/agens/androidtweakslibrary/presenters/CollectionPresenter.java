package no.agens.androidtweakslibrary.presenters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import no.agens.androidtweakslibrary.R;
import no.agens.androidtweakslibrary.adapters.TweaksAdapter;
import no.agens.androidtweakslibrary.models.Collection;
import no.agens.androidtweakslibrary.models.TweakStore;
import no.agens.androidtweakslibrary.services.TweakStoreService;

public class CollectionPresenter {

    public static View createCollectionView(final Context context, Collection collection, final TweakStore tweakStore) {
        View view = View.inflate(context, R.layout.collection_layout, null);

        TextView textView = view.findViewById(R.id.collection_name_textView);
        textView.setText(collection.getName());

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View tweakStoreView = TweakStorePresenter.createTweakStoreView(context, tweakStore);
                TweakStoreService.replaceView(tweakStoreView);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.collection_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new TweaksAdapter(context, collection, tweakStore);
        recyclerView.setAdapter(adapter);

        return view;
    }
 }
