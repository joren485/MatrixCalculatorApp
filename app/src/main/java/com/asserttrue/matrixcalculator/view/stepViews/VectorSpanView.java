package com.asserttrue.matrixcalculator.view.stepViews;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.MathTextView;
import com.asserttrue.matrixcalculator.view.MatrixView;

import java.util.List;

public class VectorSpanView extends LinearLayout {
    private static final String COMMA = ", ";

    public VectorSpanView(Context context, List<Matrix> vectors) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_vector_span, this, true);

        LinearLayout vectorsView = (LinearLayout)findViewById(R.id.vectors_commas);

        if(vectors.isEmpty()) {
            ImageView empty = new ImageView(context);
            empty.setImageResource(R.drawable.zero_vector);
            empty.setMaxHeight(80);
            empty.setAdjustViewBounds(true);
            empty.setScaleType(ImageView.ScaleType.FIT_XY);
            vectorsView.addView(empty);

            return;
        }

        vectorsView.addView(new MatrixView(context, vectors.get(0)));

        for(int i = 1; i < vectors.size(); i++) {
            MathTextView mtv = new MathTextView(context, COMMA);
            mtv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            vectorsView.addView(mtv);
            vectorsView.addView(new MatrixView(context, vectors.get(i)));
        }
    }

}
