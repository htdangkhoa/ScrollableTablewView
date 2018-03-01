package com.github.htdangkhoa.scrollabletableview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.htdangkhoa.library.OnScrollableTableViewListener;
import com.github.htdangkhoa.library.ScrollableTablewView;
import com.github.htdangkhoa.library.Utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnScrollableTableViewListener {
    ArrayList<String> strings = new ArrayList<>();
    int HEIGHT;

    ScrollableTablewView tablewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        for (int i = 0; i < 10; i++) {
            strings.add(String.valueOf(i));
        }

        TextView corner = new TextView(this);
        corner.setText("NO");

        HEIGHT = ScreenUtils.convertDIPToPixels(this, 44);
        tablewView = new ScrollableTablewView(this)
                .setCornerSize(ScreenUtils.convertDIPToPixels(this, 51), HEIGHT)
                .setCornerChildView(corner)
                .setOnScrollableTableViewListener(this)
                .build();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(tablewView, params);
    }

    @Override
    public void onBindColumnHeaders(ViewGroup parent) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(2, 0, 0, 0);

        for (int i = 0; i < strings.size(); i++) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setText("Column " + strings.get(i));
            textView.setBackgroundColor(Color.DKGRAY);
            textView.setTextColor(Color.WHITE);

            parent.addView(textView, params);
        }
    }

    @Override
    public void onBindRowHeaders(ViewGroup parent) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 2, 0, 0);

        for (int i = 0; i < strings.size(); i++) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setText(strings.get(i));
            textView.setBackgroundColor(Color.DKGRAY);
            textView.setTextColor(Color.WHITE);

            parent.addView(textView, params);
        }
    }

    @Override
    public void onBindCells(ViewGroup parent) {
        Random r = new Random();

        for (int i = 0; i < strings.size(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setId(View.generateViewId());

            TableRow.LayoutParams params = new TableRow.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(2, 2, 0, 0);

            for (int j = 0; j < strings.size(); j++) {
                TextView textView = new TextView(this);
                textView.setBackgroundColor(Color.LTGRAY);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(5, 5, 5, 5);

                if (j % 2 == 1) {
                    textView.setText("Row " + strings.get(i) + " Col " + strings.get(j) + " \n" + String.valueOf (r.nextInt(9999999) + 32));
                } else {
                    textView.setText("Row " + strings.get(i) + " Col " + strings.get(j) + " " + String.valueOf (r.nextInt(9999999) + 32));
                }

                tableRow.addView(textView, params);
            }

            parent.addView(tableRow);
        }
    }
}
