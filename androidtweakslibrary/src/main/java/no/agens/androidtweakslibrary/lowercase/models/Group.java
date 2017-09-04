package no.agens.androidtweakslibrary.lowercase.models;

import android.support.annotation.NonNull;

import java.util.List;

public class Group implements Comparable<Group> {
    private String name;
    private List<Tweak> tweaks;

    public Group(String name, List<Tweak> tweaks) {
        this.name = name;
        this.tweaks = tweaks;
    }

    public void addTweak(Tweak tweak) {
        tweaks.add(tweak);
    }

    public String getName() {
        return name;
    }

    public List<Tweak> getTweaks() {
        return tweaks;
    }

    public Integer getCountOfTweaks() {
        return tweaks.size();
    }

    @Override
    public int compareTo(@NonNull Group group) {
        return name.compareToIgnoreCase(group.getName());
    }
}
