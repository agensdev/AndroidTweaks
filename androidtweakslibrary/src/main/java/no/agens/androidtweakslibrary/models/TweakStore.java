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

package no.agens.androidtweakslibrary.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import no.agens.androidtweakslibrary.R;
import no.agens.androidtweakslibrary.interfaces.TweaksBindingBoolean;

import static android.content.Context.MODE_PRIVATE;


public class TweakStore {
    public static final String SHARED_PREFERENCES = "AndroidTweaks";
    private static TweakStore tweakStore;
    private final String tweakStoreName;
    private boolean isEnabled = false;
    private final List<Collection> collections = new ArrayList<>();
    private final SharedPreferences sharedPreferences;
    private final List<Callback> callbacks = new ArrayList<>();

    public interface Callback {
        void callback();
    }

    private TweakStore(Context context, String tweakStoreName) {
        this.tweakStoreName = tweakStoreName;
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES + "." + tweakStoreName, MODE_PRIVATE);
    }

    public static TweakStore getInstance(Context context) {
        return getInstance(context, context.getString(R.string.tweak_store_default_name));
    }

    public static TweakStore getInstance(Context context, String tweakStoreName) {
        if (tweakStore == null || !tweakStore.getTweakStoreName().equals(tweakStoreName)) {
            tweakStore = new TweakStore(context, tweakStoreName);
        }

        return tweakStore;
    }

    public void setEnabled(boolean value) {
        isEnabled = value;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setValue(TweakBoolean tweakBoolean, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getTweakBooleanKey(tweakBoolean), value);
        editor.apply();

        for (Callback callback : callbacks) {
            callback.callback();
        }
    }

    public boolean getValue(TweakBoolean tweakBoolean) {
        if (tweakStore.isEnabled) {
            return sharedPreferences.getBoolean(getTweakBooleanKey(tweakBoolean), tweakBoolean.getDefaultValue());
        } else {
            return tweakBoolean.getDefaultValue();
        }
    }

    public Callback subscribeToAnyChange(Callback callback) {
        callbacks.add(callback);
        return callback;
    }

    private void unsubscribe(Callback callback) {
        for (Callback cb : callbacks) {
            if (cb == callback) {
                callbacks.remove(cb);
            }
        }
    }

    public Callback bind(final TweakBoolean tweak, final TweaksBindingBoolean binding) {
        final boolean current[] = {getValue(tweak)};
        binding.value(current[0]);

        return subscribeToAnyChange(new Callback() {
            @Override
            public void callback() {
                boolean newValue = getValue(tweak);
                if (current[0] != newValue) {
                    current[0] = newValue;
                    binding.value(newValue);
                }
            }
        });
    }

    public void unbind(Callback callback) {
        unsubscribe(callback);
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
        return tweakBoolean.getCollectionName() + "_" + tweakBoolean.getGroupName() + "_"
                + tweakBoolean.getName() + "_" + tweakBoolean.getClass();
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

    private boolean tweakExists(List<Tweak> tweaks, String tweakName) {
        boolean tweakExists = false;

        for (Tweak tweak : tweaks) {
            if (tweak.getName().equals(tweakName)) {
                tweakExists = true;
            }
        }

        return tweakExists;
    }
}
