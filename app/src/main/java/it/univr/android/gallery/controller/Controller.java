package it.univr.android.gallery.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

public class Controller extends IntentService {
    private final static String ACTION_FETCH_LIST_OF_PICTURES = "fetch list of pictures";
    private final static String PARAM_HOW_MANY = "how many";
    private final static String ACTION_FETCH_BITMAP = "fetch bitmap";
    private final static String PARAM_POSITION = "position";
    static int taskCounter;

    public Controller() {
        super("gallery controller");
    }

    public static void fetchListOfPictures(Context context, int howMany) {
        Intent intent = new Intent(context, Controller.class);
        intent.setAction(ACTION_FETCH_LIST_OF_PICTURES);
        intent.putExtra(PARAM_HOW_MANY, howMany);
        synchronized (Controller.class) {
            taskCounter++;
        }
        context.startService(intent);
    }

    public static void fetchPicture(Context context, int position) {
        Intent intent = new Intent(context, Controller.class);
        intent.setAction(ACTION_FETCH_BITMAP);
        intent.putExtra(PARAM_POSITION, position);
        synchronized (Controller.class) {
            taskCounter++;
        }
        context.startService(intent);
    }

    public static synchronized boolean isIdle() {
        return taskCounter == 0;
    }

    @Override
    public void onDestroy() {
        taskCounter = 0;
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getAction()) {
            case ACTION_FETCH_LIST_OF_PICTURES:
                new ListOfPicturesFetcher(intent.getIntExtra(PARAM_HOW_MANY, 40));
                break;
            case ACTION_FETCH_BITMAP:
                new BitmapFetcher(intent.getIntExtra(PARAM_POSITION, -1));
                break;
        }
    }
}
