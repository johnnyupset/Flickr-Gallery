package it.univr.android.news;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TwoPanesNewsLayout extends LinearLayout implements NewsLayout {

    private FragmentManager getFragmentManager() {
        return ((Activity) getContext()).getFragmentManager();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Make the clicked item remain visually highlighted
        ((ListFragment) getFragmentManager().findFragmentById(R.id.headlines_fragment))
            .getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onArticleSelected(int position) {
        // Capture the article fragment from the activity layout
        ((ArticleFragment) getFragmentManager().findFragmentById(R.id.article_fragment))
            .updateArticle(position);
    }

    public TwoPanesNewsLayout(Context context) {
        super(context);
    }
    public TwoPanesNewsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
