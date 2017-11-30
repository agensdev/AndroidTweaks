package no.agens.androidtweakslibrary.services;

import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import no.agens.androidtweakslibrary.R;
import no.agens.androidtweakslibrary.models.TweakStore;
import no.agens.androidtweakslibrary.presenters.GroupPresenter;
import no.agens.androidtweakslibrary.presenters.TweakStorePresenter;


public class TweakStoreService extends Service implements View.OnTouchListener, ValueAnimator.AnimatorUpdateListener {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private static final int MIN_VISIBLE_WIDTH = 30;
    private static final int MARGIN = 10;
    private static FrameLayout rootView;

    private static WindowManager windowManager;
    private static Point displaySize = new Point();
    private static WindowManager.LayoutParams params;
    private ValueAnimator animator = new ValueAnimator();
    private float actionDownX;
    private String tweakStoreName;
    private static View drawerPull;

    @Override
    public void onCreate() {
        animator.addUpdateListener(this);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(displaySize);

        rootView = new FrameLayout(this);

        params = new WindowManager.LayoutParams(
                displaySize.x, displaySize.y - 48,         // width, height
                -displaySize.x + MIN_VISIBLE_WIDTH, 0,     // right, top
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        windowManager.addView(rootView, params);

        rootView.setOnTouchListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getExtras() != null) {
            tweakStoreName = intent.getExtras().getString(TWEAK_STORE_NAME);
        }

        TweakStore tweakStore = TweakStore.getInstance(this, tweakStoreName);
        View view = TweakStorePresenter.createTweakStoreView(this, tweakStore);
        rootView.addView(view);
        drawerPull = view.findViewById(R.id.drag_view);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        windowManager.removeView(rootView);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        if (((ViewGroup)v).getChildAt(0) == GroupPresenter.getView()) {

            if (action == MotionEvent.ACTION_DOWN) {
                animator.cancel();
                actionDownX = event.getX();

            } else if (action == MotionEvent.ACTION_MOVE) {
                int newRight = (int) (actionDownX - event.getRawX());
                params.x = Math.max(-displaySize.x + MIN_VISIBLE_WIDTH + MARGIN, Math.min(newRight, 0));
                windowManager.updateViewLayout(rootView, params);

            } else if (action == MotionEvent.ACTION_UP) {
                int startX = params.x;
                int endX = startX < (-displaySize.x + MIN_VISIBLE_WIDTH + MARGIN) / 2? -displaySize.x + MIN_VISIBLE_WIDTH + MARGIN : 0;
                animator.setIntValues(startX, endX);
                animator.setDuration(500 * Math.abs(endX - startX) / ((displaySize.x - MIN_VISIBLE_WIDTH) / 2))
                        .start();
            }

        } else {

            if (action == MotionEvent.ACTION_DOWN) {
                animator.cancel();
                actionDownX = event.getX();

            } else if (action == MotionEvent.ACTION_MOVE) {
                int newRight = (int) (actionDownX - event.getRawX());
                params.x = Math.max(-displaySize.x + MIN_VISIBLE_WIDTH, Math.min(newRight, 0));
                windowManager.updateViewLayout(rootView, params);

            } else if (action == MotionEvent.ACTION_UP) {
                int startX = params.x;
                int endX = startX < (-displaySize.x + MIN_VISIBLE_WIDTH) / 2? -displaySize.x + MIN_VISIBLE_WIDTH : 0;
                animator.setIntValues(startX, endX);
                animator.setDuration(500 * Math.abs(endX - startX) / ((displaySize.x - MIN_VISIBLE_WIDTH) / 2))
                        .start();
            }
        }

        return true;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        params.x = (Integer) animation.getAnimatedValue();
        windowManager.updateViewLayout(rootView, params);
        drawerPull.setAlpha((float) (-displaySize.x - params.x) / -displaySize.x);
    }

    public static void replaceView(View newView) {
        rootView.removeAllViews();
        adjustViewSize(newView);
        rootView.addView(newView);
        drawerPull = newView.findViewById(R.id.drag_view);
    }

    private static void adjustViewSize(View view) {
        if (view == GroupPresenter.getView()) {
            params = new WindowManager.LayoutParams(
                    displaySize.x - MARGIN, displaySize.y / 3,      // width, height
                    0, MARGIN,                                      // right, bottom
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        } else {
            params = new WindowManager.LayoutParams(
                    displaySize.x, displaySize.y - 48,              // width, height
                    0, 0,                                           // right, top
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.TOP | Gravity.RIGHT;
        }

        windowManager.updateViewLayout(rootView, params);
    }
}
