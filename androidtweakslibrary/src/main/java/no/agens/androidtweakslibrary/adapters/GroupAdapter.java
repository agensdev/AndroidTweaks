package no.agens.androidtweakslibrary.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import no.agens.androidtweakslibrary.R;
import no.agens.androidtweakslibrary.models.Group;
import no.agens.androidtweakslibrary.models.Tweak;
import no.agens.androidtweakslibrary.models.TweakBoolean;
import no.agens.androidtweakslibrary.models.TweakClosure;
import no.agens.androidtweakslibrary.models.TweakStore;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Tweak> tweaks = new ArrayList<>();
    private TweakStore tweakStore;

    public GroupAdapter(Context context, Group group, TweakStore tweakStore) {
        this.context = context;
        this.tweaks = group.getTweaks();
        this.tweakStore = tweakStore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_drawer_recycler_view_item, parent, false);
        return new GroupAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((GroupAdapter.ViewHolder) holder).drawerTweaksLinearLayout.removeAllViews();

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

                ((GroupAdapter.ViewHolder) holder).drawerTweaksLinearLayout.addView(view);
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

                ((GroupAdapter.ViewHolder) holder).drawerTweaksLinearLayout.addView(view);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout drawerTweaksLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            drawerTweaksLinearLayout = itemView.findViewById(R.id.group_drawer_tweaks_linearLayout);
        }
    }
}
