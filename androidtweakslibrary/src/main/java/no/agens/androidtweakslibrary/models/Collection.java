package no.agens.androidtweakslibrary.models;

import android.support.annotation.NonNull;

import java.util.List;

public class Collection implements Comparable<Collection> {
    private String name;
    private List<Group> groups;

    public Collection(String name) {
        this.name = name;
    }

    public Collection(String name, List<Group> groups) {
        this.name = name;
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Integer getCountOfTweaks() {
        Integer countOfTweaks = 0;

        for (Group group : groups) {
            countOfTweaks += group.getCountOfTweaks();
        }

        return countOfTweaks;
    }

    public void addGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public int compareTo(@NonNull Collection collection) {
        return name.compareToIgnoreCase(collection.getName());
    }
}
