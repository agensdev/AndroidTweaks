package no.agens.androidtweakslibrary.Models;

import android.support.annotation.NonNull;

public class Tweak implements Comparable<Tweak> {
    private String collectionName;
    private String groupName;
    private String tweakName;

    public Tweak(String collectionName, String groupName, String tweakName) {
        this.collectionName = collectionName;
        this.groupName = groupName;
        this.tweakName = tweakName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getName() {
        return tweakName;
    }

    @Override
    public int compareTo(@NonNull Tweak tweak) {
        return tweakName.compareToIgnoreCase(tweak.getName());
    }
}
