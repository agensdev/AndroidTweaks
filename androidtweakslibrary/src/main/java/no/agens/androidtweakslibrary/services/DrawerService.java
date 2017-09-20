package no.agens.androidtweakslibrary.services;

import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import no.agens.androidtweakslibrary.R;
import no.agens.androidtweakslibrary.adapter.DrawerAdapter;
import no.agens.androidtweakslibrary.models.Collection;
import no.agens.androidtweakslibrary.models.Group;
import no.agens.androidtweakslibrary.models.TweakStore;


public class DrawerService extends Service implements View.OnTouchListener, ValueAnimator.AnimatorUpdateListener {
    private static String TWEAK_STORE_NAME = "tweakStoreName";
    private static String COLLECTION_NAME = "collectionName";
    private static String GROUP_NAME = "groupName";
    private static final int MIN_VISIBLE_WIDTH = 40;
    private static final int MARGIN = 10;

    private View view;
    private WindowManager windowManager;
    private FrameLayout rootView;
    private Point displaySize = new Point();
    private WindowManager.LayoutParams params;
    private ValueAnimator animator = new ValueAnimator();
    private float actionDownX;

    @Override
    public void onCreate() {
        animator.addUpdateListener(this);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(displaySize);

        rootView = new FrameLayout(this);
        view = View.inflate(this, R.layout.drawer_layout, null);
        rootView.addView(view);

        params = new WindowManager.LayoutParams(
                displaySize.x - MARGIN, displaySize.y / 3,      // width, height
                -displaySize.x + MIN_VISIBLE_WIDTH, MARGIN,     // right, bottom
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        windowManager.addView(rootView, params);

        rootView.setOnTouchListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String groupName = "";
        String collectionName = "";
        String tweakStoreName = "";

        if (intent.getExtras() != null) {
            groupName = intent.getExtras().getString(GROUP_NAME);
            collectionName = intent.getExtras().getString(COLLECTION_NAME);
            tweakStoreName = intent.getExtras().getString(TWEAK_STORE_NAME);
        }

        TextView groupNameTextView = view.findViewById(R.id.drawer_group_name_textView);
        groupNameTextView.setText(groupName);

        ImageButton imageButton = view.findViewById(R.id.drawer_close_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });

        TweakStore tweakStore = TweakStore.getInstance(this, tweakStoreName);

        List<Collection> collections = tweakStore.getCollections();
        Collection collection = null;

        for (Collection c : collections) {
            if (c.getName().equals(collectionName)) {
                collection = c;
            }
        }

        List<Group> groups = collection.getGroups();
        Group group = null;

        for (Group g : groups) {
            if (g.getName().equals(groupName)) {
                group = g;
            }
        }

        RecyclerView recyclerView = view.findViewById(R.id.drawer_group_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new DrawerAdapter(this, group, tweakStore);
        recyclerView.setAdapter(adapter);

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
        return true;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        params.x = (Integer) animation.getAnimatedValue();
        windowManager.updateViewLayout(rootView, params);
    }
}
