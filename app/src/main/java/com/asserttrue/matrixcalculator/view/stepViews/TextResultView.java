package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;

public class TextResultView extends LinearLayout {
    private final TextView textView;

    public TextResultView(Context context, String text) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_text_result, this, true);

        textView = (TextView) findViewById(R.id.explanation);
        textView.setText(text);
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
