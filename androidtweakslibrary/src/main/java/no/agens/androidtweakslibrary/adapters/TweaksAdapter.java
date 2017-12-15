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

package no.agens.androidtweakslibrary.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import no.agens.androidtweakslibrary.R;
import no.agens.androidtweakslibrary.models.Collection;
import no.agens.androidtweakslibrary.models.Group;
import no.agens.androidtweakslibrary.models.Tweak;
import no.agens.androidtweakslibrary.models.TweakBoolean;
import no.agens.androidtweakslibrary.models.TweakClosure;
import no.agens.androidtweakslibrary.models.TweakStore;
import no.agens.androidtweakslibrary.presenters.GroupPresenter;
import no.agens.androidtweakslibrary.services.TweakStoreService;


public class TweaksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Group> groups = new ArrayList<>();
    private Collection collection;
    private TweakStore tweakStore;

    public TweaksAdapter(Context context, Collection collection, TweakStore tweakStore) {
        this.context = context;
        this.groups = collection.getGroups();
        this.collection = collection;
        this.tweakStore = tweakStore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_recycler_view_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final String groupName = groups.get(position).getName();
        ((ViewHolder) holder).groupNameTextView.setText(groupName);
        ((ViewHolder) holder).imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View groupView = GroupPresenter.createGroupView(context, groups.get(holder.getAdapterPosition()), collection, tweakStore);
                TweakStoreService.replaceView(groupView);
            }
        });

        List<Tweak> tweaks = groups.get(position).getTweaks();

        ((ViewHolder) holder).tweaksLinearLayout.removeAllViews();

        for (int i = 0; i < tweaks.size(); i++) {
            final Tweak tweak = tweaks.get(i);
            String tweakName = tweak.getName();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (tweak instanceof TweakBoolean) {
                View view = inflater.inflate(R.layout.tweak_boolean_item, null);
                TextView tweakNameTextView = view.findViewById(R.id.tweak_boolean_name_textView);
                tweakNameTextView.setText(tweakName);

                SwitchCompat switchButton = view.findViewById(R.id.switch_button);
                boolean tweakBooleanValue = tweakStore.getValue((TweakBoolean) tweak);
                switchButton.setChecked(tweakBooleanValue);
                switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        tweakStore.setValue((TweakBoolean) tweak, b);
                    }
                });

                ((ViewHolder) holder).tweaksLinearLayout.addView(view);
            } else if (tweak instanceof TweakClosure) {
                View view = inflater.inflate(R.layout.tweak_closure_item, null);
                TextView tweakNameTextView = view.findViewById(R.id.tweak_closure_name_textView);
                tweakNameTextView.setText(tweakName);

                Button button = view.findViewById(R.id.closure_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((TweakClosure) tweak).getCallback().callback();
                    }
                });

                ((ViewHolder) holder).tweaksLinearLayout.addView(view);
            }
        }
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout tweaksLinearLayout;
        TextView groupNameTextView;
        ImageButton imageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            tweaksLinearLayout = itemView.findViewById(R.id.tweaks_linearLayout);
            groupNameTextView = itemView.findViewById(R.id.group_name_textView);
            imageButton = itemView.findViewById(R.id.minimize_button);
        }
    }
}
