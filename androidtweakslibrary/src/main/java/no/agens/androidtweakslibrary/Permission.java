package no.agens.androidtweakslibrary;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class Permission {
    public final static int ACTION_MANAGE_OVERLAY_PERMISSION = 123;
    private static boolean isPermissionGranted = false;
    private static boolean canDrawOverlays;

    public static void checkResult(Context context, int requestCode) {
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION) {
            if (!canDrawOverlays) {
                checkPermission(context);
            } else {
                isPermissionGranted = true;
            }
        }
    }

    public static void checkPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            canDrawOverlays = Settings.canDrawOverlays(context);

            if (!canDrawOverlays) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.getPackageName()));
                ((Activity) context).startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION);
            } else {
                isPermissionGranted = true;
            }
        }
    }

    public static boolean isPermissionGranted() {
        return isPermissionGranted;
    }
}
