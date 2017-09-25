package no.agens.androidtweaks;


import java.util.ArrayList;
import java.util.List;

import no.agens.androidtweakslibrary.models.Tweak;
import no.agens.androidtweakslibrary.models.TweakBoolean;
import no.agens.androidtweakslibrary.models.TweakClosure;

public class MyTweaks {
    public static final TweakBoolean uppercase = new TweakBoolean("Styling", "Fonts", "Uppercase", false);
    public static final TweakBoolean greenButton = new TweakBoolean("Styling", "Colors", "Green", false);
    public static final TweakBoolean useEmojis = new TweakBoolean("User", "User data", "Use emoji instead of photo", false);
    public static final TweakClosure addUser = new TweakClosure("User", "User data", "Add user");
    public static final TweakClosure deleteUserData = new TweakClosure("User", "User data", "Delete user data");

    public static final List<Tweak> tweaks = new ArrayList<Tweak>() {{
        add(uppercase);
        add(greenButton);
        add(useEmojis);
        add(addUser);
        add(deleteUserData);
    }};
}
