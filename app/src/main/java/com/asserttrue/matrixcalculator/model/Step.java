package com.asserttrue.matrixcalculator.model;
import android.content.Context;
import android.view.View;

public interface Step {
    int SINGLE_MATRIX = 0;
    int MATRIX_RESULT = 1;
    int DET_MATRIX_SCALAR = 2;
    int DET_RESULT = 3;
    int VECTOR_SPAN = 4;
    int NR_TYPES = 5;

    View toView(Context context);

    void setViewContent(View view);

    int getLayoutType();

}