package no.agens.androidtweakslibrary.presenters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import no.agens.androidtweakslibrary.R;
import no.agens.androidtweakslibrary.adapters.GroupAdapter;
import no.agens.androidtweakslibrary.models.Collection;
import no.agens.androidtweakslibrary.models.Group;
import no.agens.androidtweakslibrary.models.TweakStore;
import no.agens.androidtweakslibrary.services.TweakStoreService;

public class GroupPresenter {
    private static View view;

    public static View createGroupView(final Context context, Group group, final Collection collection, final TweakStore tweakStore) {
        view = View.inflate(context, R.layout.group_drawer_layout, null);

        TextView groupNameTextView = view.findViewById(R.id.drawer_group_name_textView);
        groupNameTextView.setText(group.getName());

        ImageButton closeButton = view.findViewById(R.id.drawer_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View collectionView = CollectionPresenter.createCollectionView(context, collection, tweakStore);
                TweakStoreService.replaceView(collectionView);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.drawer_group_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new GroupAdapter(context, group, tweakStore);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public static View getView() {
        return view;
    }
}
