# AndroidTweaks

AndroidTweaks library provides an easy way of adjusting your Android app UI in a debug build.  
Would you like to know instantly, if fonts in your app should be bigger or a different theme should be used, without constantly compiling the project in Android Studio? Just create some Tweaks, enable the TweakStore and you’re ready to go!

## Overview

Create some Tweaks like this:

`public static final Tweak tweak = new TweakBoolean("Styling", "Fonts", "Big", false);`

Tweaks are currently boolean values. Every Tweak needs to be assigned to a right group and collection, also a default value is required.  
TweakStore can be enabled in a debug build, in other build types default values are applied.

## How to install

* Add ` compile ‘com.github.agensdev:AndroidTweaks:1.1.0’` to your application Gradle dependencies,
* Create a new class with your public static final TweakBooleans:

```java
public class MyTweaks {

    public static final TweakBoolean darkTheme = new TweakBoolean("Styling", "Theme", "Dark", false);
    public static final TweakBoolean bigFonts = new TweakBoolean("Styling", "Fonts", "Big", true);
    public static final List<Tweak> tweaks = new ArrayList<Tweak>() {{
        add(darkTheme);
        add(bigFonts);
    }};
}
```

* In your app main activity add Tweaks to the TweakStore and enable TweakStore in a debug build:

```java
TweakStore tweakStore = TweakStore.getInstance(this);
tweakStore.setTweaks(MyTweaks.tweaks);
tweakStore.setEnabled(BuildConfig.TWEAKS_ENABLED);
```

* Bind Tweaks to app’s UI elements:

```java
tweakStore.bind(MyTweaks.darkTheme, new TweaksBindingBoolean() {
    @Override
    public void value(Boolean value) {
        // do something here
    }
});
```

* Create a notification like this:

```java
private void showNotification() {
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
    
    notificationBuilder.setSmallIcon(app_icon_from_resources);
    notificationBuilder.setContentTitle(text_from_resources);
    notificationBuilder.setPriority(Notification.PRIORITY_MAX);
    notificationBuilder.setWhen(0);
    notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
    
    Intent intent = new Intent(this, TweakStoreActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    notificationBuilder.addAction(button_icon_from_resources, button_text_from_resources, pendingIntent);
    
    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify(1, notificationBuilder.build());
}
```

and pass it to the NotificationManager (don’t forget to cancel notification in onStop() method in your app),

* Make sure you're showing the notification when TweakStore is enabled:

```java
if (tweakStore.isEnabled()) {
    showNotification();
}
```

Enjoy!

## FAQ

### Can I have multiple TweakStores?

Yeap! Just pass a tweakName as an identifier, while creating TweakStore::  
`TweakStore tweakStore = TweakStore.getInstance(this, tweakName);`  

and pass the name of a TweakStore to an intent in `showNotification() {}`:  
`intent.putExtra("tweakStoreName", tweakStore.getTweakStoreName());`

## Thanks to:

AndroidTweaks was inspired by a great SwiftTweaks library and also FBTweaks.  
We were already using SwiftTweaks in our app, which is React Native based and we needed something similar for the Android part. We tried too keep the architecture as close to SwiftTweaks as it was possible for Android in order to provide consistent approach for both platforms.

## Feedback

Your opinion is important to us. We would love to hear what you think about AndroidTweaks. Please let us know [here](https://github.com/agensdev/AndroidTweaks/issues) if you have any ideas how to improve it, we’ll appreciate it!

AndroidTweaks is a first library from [Blanka Kulik](https://github.com/blashca) guided by [Håvard Fossli](https://twitter.com/hfossli) and with a great help from [Arild Jacobsen](https://github.com/Ehyeh-Asher-Ehyeh).

[<img src="http://static.agens.no/images/agens_logo_w_slogan_avenir_medium.png" width="340" />](http://agens.no/)
