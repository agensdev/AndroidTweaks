package no.agens.androidtweakslibrary.lowercase.models;

public class TweakBoolean extends Tweak {
    private Boolean defaultValue;

    public TweakBoolean(String collectionName, String groupName, String tweakName, Boolean defaultValue) {
        super(collectionName, groupName, tweakName);
        this.defaultValue = defaultValue;
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public String getTweakType() {
        return "Boolean";
    }
}
