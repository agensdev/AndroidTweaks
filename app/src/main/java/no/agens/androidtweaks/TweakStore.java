package no.agens.androidtweaks;

import java.util.ArrayList;
import java.util.List;

public class TweakStore {
    private static TweakStore tweakStore;
    private String name = "";
    private List<Collection> collections = new ArrayList<>();

    private TweakStore() {}

    public static TweakStore getInstance() {
        if (tweakStore == null) {
            tweakStore = new TweakStore();
        }

        return tweakStore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    public String getName() {
        return name;
    }

    public List<Collection> getCollections() {
        return collections;
    }
}
