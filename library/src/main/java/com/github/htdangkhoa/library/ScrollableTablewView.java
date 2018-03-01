package com.github.htdangkhoa.library;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.github.htdangkhoa.library.Utils.ScreenUtils;

/**
 * Created by dangkhoa on 2/28/18.
 */

public class ScrollableTablewView extends RelativeLayout {
    Context context;

    /**
     * TableScrollView
     * */
    TableHorizontalScrollView horizontalScrollViewColumnHeader;
    TableHorizontalScrollView horizontalScrollViewCell;
    TableVerticalScrollView verticalScrollViewRowHeader;
    TableVerticalScrollView verticalScrollViewCell;

    LinearLayout columnHeader, rowHeader;
    RelativeLayout cornerHeader;
    TableLayout tableCell;

    int HEIGHT;
    int WIDTH;

    View cornerChildView;

    OnScrollableTableViewListener onScrollableTableViewListener;

    public ScrollableTablewView(Context context) {
        super(context);
        this.context = context;
        HEIGHT = ScreenUtils.convertDIPToPixels(context, 32);
        WIDTH = ScreenUtils.convertDIPToPixels(context, 32);
    }

    public ScrollableTablewView setCornerSize(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
        return this;
    }

    public ScrollableTablewView setCornerChildView(View view) {
        this.cornerChildView = view;
        return this;
    }

    public ScrollableTablewView setOnScrollableTableViewListener(OnScrollableTableViewListener onScrollableTableViewListener) {
        this.onScrollableTableViewListener = onScrollableTableViewListener;
        return this;
    }

    public ScrollableTablewView build() {
        initialize();

        return this;
    }

    /**
     * Internal function.
     * */
    private void initialize() {
        horizontalScrollViewColumnHeader = new TableHorizontalScrollView(context);
        horizontalScrollViewColumnHeader.setId(View.generateViewId());
        horizontalScrollViewColumnHeader.setTag(Tags.HORIZONTAL_SCROLL_VIEW_COLUMN_HEADER);

        verticalScrollViewRowHeader = new TableVerticalScrollView(context);
        verticalScrollViewRowHeader.setId(View.generateViewId());
        verticalScrollViewRowHeader.setTag(Tags.VERTICAL_SCROLL_VIEW_ROW_HEADER);

        horizontalScrollViewCell = new TableHorizontalScrollView(context);
        horizontalScrollViewCell.setId(View.generateViewId());
        horizontalScrollViewCell.setTag(Tags.HORIZONTAL_SCROLL_VIEW_CELL);

        verticalScrollViewCell = new TableVerticalScrollView(context);
        verticalScrollViewCell.setId(View.generateViewId());
        verticalScrollViewCell.setTag(Tags.VERTICAL_SCROLL_VIEW_CELL);

        initializeHeader();

        RelativeLayout.LayoutParams columnHeaderParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HEIGHT);
        columnHeaderParams.addRule(RIGHT_OF, cornerHeader.getId());
        addView(horizontalScrollViewColumnHeader, columnHeaderParams);

        RelativeLayout.LayoutParams rowHeaderParams = new LayoutParams(WIDTH, ViewGroup.LayoutParams.MATCH_PARENT);
        rowHeaderParams.addRule(BELOW, cornerHeader.getId());
        addView(verticalScrollViewRowHeader, rowHeaderParams);

        tableCell = new TableLayout(context);
        RelativeLayout.LayoutParams cellParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cellParams.addRule(BELOW, horizontalScrollViewColumnHeader.getId());
        cellParams.addRule(RIGHT_OF, verticalScrollViewRowHeader.getId());

        horizontalScrollViewCell.addView(tableCell);
        verticalScrollViewCell.addView(horizontalScrollViewCell);
        addView(verticalScrollViewCell, cellParams);

        setListener();
        matchSize();
    }

    private void initializeHeader() {
        cornerHeader = new RelativeLayout(context);
        cornerHeader.setId(View.generateViewId());
        cornerHeader.setBackgroundColor(Color.RED);
        RelativeLayout.LayoutParams cornerHeaderParams = new LayoutParams(WIDTH, HEIGHT);
        cornerHeader.setLayoutParams(cornerHeaderParams);

        if (cornerChildView != null) {
            cornerHeader.addView(cornerChildView);
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) cornerChildView.getLayoutParams();
            p.addRule(CENTER_IN_PARENT);
        }


        columnHeader = new LinearLayout(context);
        columnHeader.setOrientation(LinearLayout.HORIZONTAL);
        horizontalScrollViewColumnHeader.addView(columnHeader);

        rowHeader = new LinearLayout(context);
        rowHeader.setOrientation(LinearLayout.VERTICAL);
        verticalScrollViewRowHeader.addView(rowHeader);

        addView(cornerHeader);
    }

    private void setListener() {
        onScrollableTableViewListener.onBindColumnHeaders(columnHeader);
        onScrollableTableViewListener.onBindRowHeaders(rowHeader);
        onScrollableTableViewListener.onBindCells(tableCell);
    }

    private void matchWidth() {
        int count = columnHeader.getChildCount();

        final TableRow tableRowOnCell = (TableRow) tableCell.getChildAt(0);

        // Duyệt 9 cái
        for (int i = 0; i < count; i++) {
            final View view = (View) columnHeader.getChildAt(i);

            final int finalI = i;
            tableRowOnCell.getChildAt(i).post(new Runnable() {
                @Override
                public void run() {
                    int h = tableRowOnCell.getChildAt(finalI).getWidth();

                    ViewGroup.LayoutParams p = view.getLayoutParams();
                    p.width = h;
                    view.setLayoutParams(p);
                }
            });
        }
    }

    private void matchHeight() {
        int count = rowHeader.getChildCount();

        // Duyet 9 cai
        for (int i = 0; i < count; i++) {
            final TableRow tableRowOnCell = (TableRow) tableCell.getChildAt(i);

            final View view = (View) rowHeader.getChildAt(i);

            final int finalI = i;
            tableRowOnCell.getChildAt(i).post(new Runnable() {
                @Override
                public void run() {
                    int h = tableRowOnCell.getChildAt(finalI).getHeight();

                    ViewGroup.LayoutParams p = view.getLayoutParams();
                    p.height = h;
                    view.setLayoutParams(p);
                }
            });
        }
    }

    private void matchSize() {
        matchWidth();
        matchHeight();
    }

    /**
     * Horizontal scroll view
     * */
    class TableHorizontalScrollView extends HorizontalScrollView {

        public TableHorizontalScrollView(Context context) {
            super(context);
            setHorizontalScrollBarEnabled(false);
        }

        public TableHorizontalScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setHorizontalScrollBarEnabled(false);
        }

        public TableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setHorizontalScrollBarEnabled(false);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public TableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            setHorizontalScrollBarEnabled(false);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) this.getTag();

            if (tag.equals(Tags.HORIZONTAL_SCROLL_VIEW_COLUMN_HEADER)) {
                horizontalScrollViewCell.scrollTo(l, 0);
            } else {
                horizontalScrollViewColumnHeader.scrollTo(l, 0);
            }
        }
    }

    /**
     * Vertical scroll view
     * */
    class TableVerticalScrollView extends ScrollView {

        public TableVerticalScrollView(Context context) {
            super(context);
            setVerticalScrollBarEnabled(false);
        }

        public TableVerticalScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setVerticalScrollBarEnabled(false);
        }

        public TableVerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setVerticalScrollBarEnabled(false);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public TableVerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            setVerticalScrollBarEnabled(false);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) this.getTag();

            if (tag.equals(Tags.VERTICAL_SCROLL_VIEW_ROW_HEADER)) {
                verticalScrollViewCell.scrollTo(0, t);
            } else {
                verticalScrollViewRowHeader.scrollTo(0, t);
            }
        }
    }
}
