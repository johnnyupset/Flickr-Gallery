package it.univr.android.gallery.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import it.univr.android.gallery.MVC;

public class ControllerService extends IntentService {
    private final static String ACTION_FETCH_LIST_OF_PICTURES = "fetch list of pictures";
    private final static String PARAM_HOW_MANY = "how many";
    private final static String ACTION_FETCH_BITMAP = "fetch bitmap";
    private final static String PARAM_POSITION = "position";

    public ControllerService() {
        super("gallery controller");
    }

    static void fetchListOfPictures(Context context, int howMany) {
        Intent intent = new Intent(context, ControllerService.class);
        intent.setAction(ACTION_FETCH_LIST_OF_PICTURES);
        intent.putExtra(PARAM_HOW_MANY, howMany);
        context.startService(intent);
    }

    static void fetchPicture(Context context, int position) {
        Intent intent = new Intent(context, ControllerService.class);
        intent.setAction(ACTION_FETCH_BITMAP);
        intent.putExtra(PARAM_POSITION, position);
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        MVC.controller.resetTaskCounter();
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
