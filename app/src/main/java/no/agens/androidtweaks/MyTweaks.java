package no.agens.androidtweaks;


import java.util.ArrayList;
import java.util.List;

import no.agens.androidtweakslibrary.models.Tweak;
import no.agens.androidtweakslibrary.models.TweakBoolean;

public class MyTweaks {
    public static final TweakBoolean darkTheme = new TweakBoolean("Styling", "Theme", "Dark", false);
    public static final TweakBoolean bigFonts = new TweakBoolean("Styling", "Fonts", "Big", true);
    public static final List<Tweak> tweaks = new ArrayList<Tweak>() {{
        add(darkTheme);
        add(bigFonts);
    }};
}
