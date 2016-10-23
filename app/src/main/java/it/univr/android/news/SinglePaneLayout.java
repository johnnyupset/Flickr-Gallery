package it.univr.android.news;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SinglePaneLayout extends FrameLayout implements PaneEventsListener {

    @Override
    public void onActivityCreated() {
        FragmentManager fragmentManager = ((Activity) getContext()).getFragmentManager();
        if (fragmentManager.findFragmentById(R.id.layout_content) == null)
            fragmentManager.beginTransaction()
                    .add(R.id.layout_content, new HeadlinesFragment()).commit();
    }

    @Override
    public void onArticleSelected(int position) {
        // Create fragment and give it an argument for the selected article
        ((Activity) getContext()).getFragmentManager().beginTransaction()
            // Replace whatever is in the fragment_container view with this fragment
            .replace(R.id.layout_content, ArticleFragment.mkInstance(position))
            // Add the transaction to the back stack so the user can navigate back
            .addToBackStack(null)
            // Commit the transaction
            .commit();
    }

    public SinglePaneLayout(Context context) {
        super(context);
    }

    public SinglePaneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SinglePaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}