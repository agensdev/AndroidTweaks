package no.agens.androidtweakslibrary.lowercase.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static android.content.Context.MODE_PRIVATE;


public class TweakStore {
    public static final String SHARED_PREFERENCES = "sharedPreferences";
    private static TweakStore tweakStore;
    private String tweakStoreName;
    private List<Collection> collections = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    private TweakStore(Context context, String tweakStoreName) {
        this.tweakStoreName = tweakStoreName;
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
    }

    public static TweakStore getInstance(Context context, String tweakStoreName) {
        if (tweakStore == null || !tweakStore.getTweakStoreName().equals(tweakStoreName)) {
            tweakStore = new TweakStore(context, tweakStoreName);
        }

        return tweakStore;
    }

    public void setValue(TweakBoolean tweakBoolean, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getTweakBooleanKey(tweakBoolean), value);
        editor.apply();
    }

    public Boolean getValue(TweakBoolean tweakBoolean) {
        return sharedPreferences.getBoolean(getTweakBooleanKey(tweakBoolean), tweakBoolean.getDefaultValue());
    }

    public String getTweakStoreName() {
        return tweakStoreName;
    }

    public void setTweaks(List<Tweak> tweakBooleanList) {
        List<Group> groups;
        Collections.sort(tweakBooleanList);

        for (Tweak tweak : tweakBooleanList) {
            String groupName = tweak.getGroupName();
            String collectionName = tweak.getCollectionName();
            Collection collection;

            if (getCollectionIndexByName(collections, collectionName) != null) {
                int index = getCollectionIndexByName(collections, collectionName);
                collection = collections.get(index);
            } else {
                collection = new Collection(collectionName);
                collections.add(collection);
            }

            Collections.sort(collections);
            groups = collection.getGroups();

            if (getGroupIndexByName(groups, groupName) != null) {
                int index = getGroupIndexByName(groups, groupName);
                Group group = groups.get(index);

                if (!tweakExists(group.getTweaks(), tweak.getName())){
                    group.addTweak(tweak);
                }

            } else {
                List<Tweak> tweaks = new ArrayList<>();
                tweaks.add(tweak);
                Group group = new Group(groupName, tweaks);
                if (groups != null) {
                    groups.add(group);
                } else {
                    groups = new ArrayList<>();
                    groups.add(group);
                }
            }

            Collections.sort(groups);
            collection.addGroups(groups);
        }
    }

    public List<Collection> getCollections() {
        return collections;
    }

    private String getTweakBooleanKey(TweakBoolean tweakBoolean) {
        return tweakStoreName + "_" + tweakBoolean.getCollectionName() + "_" + tweakBoolean.getGroupName() + "_" + tweakBoolean.getName() + "_" + tweakBoolean.getTweakType();
    }

    private Integer getGroupIndexByName(List<Group> groups, String groupName) {
        Integer index = null;

        if (groups != null) {
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getName().equals(groupName)) {
                    index = i;
                }
            }
        }

        return index;
    }

    private Integer getCollectionIndexByName(List<Collection> collections, String collectionName) {
        Integer index = null;

        for (int i = 0; i < collections.size(); i++) {
            if (collections.get(i).getName().equals(collectionName)) {
                index = i;
            }
        }

        return index;
    }

    private Boolean tweakExists(List<Tweak> tweaks, String tweakName) {
        Boolean tweakExists = false;

        for (Tweak tweak : tweaks) {
            if (tweak.getName().equals(tweakName)) {
                tweakExists = true;
            }
        }

        return tweakExists;
    }
}
