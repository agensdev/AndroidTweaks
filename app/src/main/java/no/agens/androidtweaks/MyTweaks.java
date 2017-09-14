package no.agens.androidtweaks;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import no.agens.androidtweakslibrary.models.Tweak;
import no.agens.androidtweakslibrary.models.TweakBoolean;
import no.agens.androidtweakslibrary.models.TweakClosure;
import no.agens.androidtweakslibrary.models.TweakStore;

public class MyTweaks {
    public static final TweakBoolean uppercase = new TweakBoolean("Styling", "Fonts", "Uppercase", false);
    public static final TweakBoolean greenButton = new TweakBoolean("Styling", "Colors", "Green", false);
    public static final TweakBoolean useEmojis = new TweakBoolean("User", "User data", "Use emoji instead of photo", false);
    public static final TweakClosure deleteUserData = new TweakClosure("User", "User data", "Delete user data", new TweakStore.Callback() {
        @Override
        public void callback() {
            Log.i("LOG", "User data has been deleted");
        }
    });

    public static final List<Tweak> tweaks = new ArrayList<Tweak>() {{
        add(uppercase);
        add(greenButton);
        add(useEmojis);
        add(deleteUserData);
    }};
}
