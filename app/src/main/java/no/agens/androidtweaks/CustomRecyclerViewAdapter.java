package no.agens.androidtweaks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;


public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Context context;
    private List<Group> groups = new ArrayList<>();

    public CustomRecyclerViewAdapter(Context context, Collection collection) {
        this.context = context;
        this.groups = collection.getGroups();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_recycler_view_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String groupName = groups.get(position).getName();
        ((ViewHolder) holder).textView.setText(groupName);

        List<Tweak> tweaks = groups.get(position).getTweaks();

        ((ViewHolder) holder).linearLayout.removeAllViews();

        for (int i = 0; i < tweaks.size(); i++) {
            String tweakName = tweaks.get(i).getName();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.tweak_item, null);
            TextView tweakTextView = (TextView) view.findViewById(R.id.tweak_name_textView);
            tweakTextView.setText(tweakName);

            ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.toggle_button);
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                }
            });

            ((ViewHolder) holder).linearLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);

        if (position == TYPE_HEADER) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.tweaks_linearLayout);
            textView = (TextView) itemView.findViewById(R.id.group_name_textView);
        }
    }
}
