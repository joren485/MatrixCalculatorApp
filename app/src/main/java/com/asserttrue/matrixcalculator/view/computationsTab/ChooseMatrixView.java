package com.asserttrue.matrixcalculator.view.computationsTab;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.MatrixView;

public class ChooseMatrixView extends LinearLayout {

    private MatrixView matrixView;
    private TextView[] selectionTextViews;

    public ChooseMatrixView(Context context, Matrix matrix, String name, int requiredMatrices) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.choose_matrix, this, true);

        RelativeLayout matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, matrix, true);
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);

        TextView nameEquals = (TextView) findViewById(R.id.name_equals);
        if (!name.isEmpty())
            nameEquals.setText(name);
        else
            nameEquals.setVisibility(GONE);

        LinearLayout selectedIndices = (LinearLayout) findViewById(R.id.selectedIndices);
        selectionTextViews = new TextView[requiredMatrices];



        for (int i = 0; i < requiredMatrices; i++) {
            TextView view = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            params.setMarginStart(20);
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.circle);
            view.setTextColor(getContext().getColor(R.color.textColorPrimary));
            view.setVisibility(GONE);
            view.setText(String.valueOf(i + 1));
            view.setGravity(Gravity.CENTER);
            view.setPadding(10, 10, 10, 10);
            selectedIndices.addView(view);
            selectionTextViews[i] = view;
        }
    }

    public Matrix getContentMatrix() {
        return matrixView.getContentMatrix();
    }

    public boolean isInDotMode(){
        return matrixView.isInDotMode();
    }

    public void setIndex(int i, boolean show) {
        selectionTextViews[i].setVisibility(show ? VISIBLE : GONE);
    }

    public boolean isSelected(int i) {
        return selectionTextViews[i].getVisibility() == VISIBLE;
    }
}
