package no.agens.androidtweaks;

import java.util.List;

public class Group {
    private String name;
    private List<Tweak> tweaks;

    public Group(String name, List<Tweak> tweaks) {
        this.name = name;
        this.tweaks = tweaks;
    }

    public String getName() {
        return name;
    }

    public List<Tweak> getTweaks() {
        return tweaks;
    }

    public Integer getCounOfTweaks() {
        return tweaks.size();
    }
}
