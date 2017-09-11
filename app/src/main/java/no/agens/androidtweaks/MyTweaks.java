package no.agens.androidtweaks;


import java.util.ArrayList;
import java.util.List;

import no.agens.androidtweakslibrary.models.Tweak;
import no.agens.androidtweakslibrary.models.TweakBoolean;

public class MyTweaks {
    public static final TweakBoolean darkTheme = new TweakBoolean("Styling", "Theme", "Dark", false);
    public static final TweakBoolean bigFonts = new TweakBoolean("Styling", "Fonts", "Big", true);
    public static final TweakBoolean smallFonts = new TweakBoolean("Styling", "Fonts", "Small", false);
    public static final TweakBoolean newOne = new TweakBoolean("Paramppppppppppppppp", "Pampam", "Piggy", false);
    public static final TweakBoolean newTwo = new TweakBoolean("Paramppp", "Pampam", "Piggy", false);
    public static final TweakBoolean newThree = new TweakBoolean("P", "Pampam", "Piggy", false);
    public static final TweakBoolean newFour = new TweakBoolean("Parampppppppppppppppjjujjuhcxxgxxgxxgxgxxxx", "Pampam", "Piggyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy", true);

    public static final List<Tweak> tweaks = new ArrayList<Tweak>() {{
        add(darkTheme);
        add(bigFonts);
        add(smallFonts);
        add(newOne);
        add(newTwo);
        add(newThree);
        add(newFour);
    }};
}
