package com.asserttrue.matrixcalculator.view.stepViews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.DatabaseHandler;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.MatrixView;

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
