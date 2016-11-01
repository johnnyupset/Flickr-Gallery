package it.univr.android.news;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SinglePaneNewsLayout extends FrameLayout implements NewsLayout {

    private FragmentManager getFragmentManager() {
        return ((Activity) getContext()).getFragmentManager();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        FragmentManager fragmentManager = getFragmentManager();
        // Show the headlines fragment at start
        if (fragmentManager.findFragmentById(R.id.news_layout_container) == null)
            fragmentManager.beginTransaction()
                    .add(R.id.news_layout_container, new HeadlinesFragment()).commit();
    }

    @Override
    public void onArticleSelected(int position) {
        // Create fragment and give it an argument for the selected article
        getFragmentManager().beginTransaction()
            // Replace whatever is in the fragment_container view with this fragment
            .replace(R.id.news_layout_container, ArticleFragment.mkInstance(position))
            // Add the transaction to the back stack so the user can navigate back
            .addToBackStack(null)
            // Commit the transaction
            .commit();
    }

    public SinglePaneNewsLayout(Context context) {
        super(context);
    }
    public SinglePaneNewsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}