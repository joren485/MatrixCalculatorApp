package com.asserttrue.matrixcalculator.view.computationsTab;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.computations.DetScalarStep;
import com.asserttrue.matrixcalculator.model.computations.SingleMatrixStep;
import com.asserttrue.matrixcalculator.model.computations.Step;
import com.asserttrue.matrixcalculator.view.MatrixView;

import java.util.Locale;

public class SingleMatrixView extends LinearLayout {
    private final RelativeLayout matrixContainer;
    private final MatrixView matrixView;
    private final TextView explanation;
    private final Context context;

    public SingleMatrixView(Context context, SingleMatrixStep step) {
        super(context);

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_single_matrix, this, true);

        matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, step.getMatrix());
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);
        explanation = (TextView) findViewById(R.id.explanation);
        explanation.setText(step.getExplanation());
    }

    public void recycle(Step step) {
        SingleMatrixStep detStep = (SingleMatrixStep) step;
        matrixView.setMatrix(((SingleMatrixStep) step).getMatrix());
        explanation.setText(detStep.getExplanation());
    }
}
