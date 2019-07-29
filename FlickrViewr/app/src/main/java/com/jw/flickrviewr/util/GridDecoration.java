package com.jw.flickrviewr.util;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * GridDecoration class to decorate RecyclerView of Gallery and Favorites screens.
 */
public class GridDecoration extends RecyclerView.ItemDecoration {

    private int dp_5;

    private int dp_10;

    private int dp_15;

    public GridDecoration(Context context) {
        this.dp_5 = dpToPx(context, 5);
        this.dp_10 = dpToPx(context, 10);
        this.dp_15 = dpToPx(context, 15);
    }

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        // Setting top and bottom offsets.
        if(position == 0 || position == 1) {
            // Items on first row.
            // High DP value for first row top to go below toolbar.
            outRect.top = dp_15;
            outRect.bottom = dp_10;
        } else {
            outRect.bottom = dp_10;
        }

        // spanIndex = 0 -> left
        // spanIndex = 1 -> right
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = lp.getSpanIndex();

        if(spanIndex == 0) {
            // Item on left side.
            outRect.left = dp_10;
            outRect.right = dp_5;
        } else if(spanIndex == 1) {
            // Item on right right.
            outRect.left = dp_5;
            outRect.right = dp_10;
        }
    }

    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}