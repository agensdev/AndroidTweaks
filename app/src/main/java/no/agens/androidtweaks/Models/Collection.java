package no.agens.androidtweaks.Models;

import java.util.List;

public class Collection {
    private String name;
    private List<Group> groups;

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
}
