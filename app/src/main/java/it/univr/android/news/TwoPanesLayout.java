package it.univr.android.news;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TwoPanesLayout extends LinearLayout implements PaneEventsListener {

    @Override
    public void onActivityCreated() {
        // we make the clicked item remain visually highlighted
        ((ListFragment) ((Activity) getContext()).getFragmentManager().findFragmentById(R.id.headlines_fragment))
            .getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onArticleSelected(int position) {
        // Capture the article fragment from the activity layout
        ArticleFragment articleFrag = (ArticleFragment) ((Activity) getContext())
            .getFragmentManager().findFragmentById(R.id.article_fragment);

        articleFrag.updateArticle(position);
    }

    public TwoPanesLayout(Context context) {
        super(context);
    }

    public TwoPanesLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoPanesLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
