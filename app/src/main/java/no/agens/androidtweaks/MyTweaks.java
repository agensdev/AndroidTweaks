package no.agens.androidtweaks;


import java.util.ArrayList;
import java.util.List;

import no.agens.androidtweakslibrary.models.Tweak;
import no.agens.androidtweakslibrary.models.TweakBoolean;

public class MyTweaks {
    public static final TweakBoolean uppercase = new TweakBoolean("Styling", "Fonts", "Uppercase", false);
    public static final TweakBoolean greenButton = new TweakBoolean("Styling", "Colors", "Green", false);


    public static final List<Tweak> tweaks = new ArrayList<Tweak>() {{
        add(uppercase);
        add(greenButton);
    }};
}
