package com.asserttrue.matrixcalculator.view.stepViews;
import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

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

    class DetResultStep implements Step {
        private final Matrix matrix;
        private final Rational scalar;

        public DetResultStep(Matrix matrix, Rational scalar) {
            this.matrix = matrix;
            this.scalar = scalar;
        }

        @Override
        public View toView(Context context) {
            return new DetResultView(context, matrix, scalar);
        }

        @Override
        public void setViewContent(View view) {
            if(!(view instanceof DetResultView)) {
                throw new IllegalArgumentException("Wrong view passed to step");
            }

            DetResultView cardView = (DetResultView) view;
            cardView.setMatrix(matrix);
            cardView.setScalar(scalar);
        }

        @Override
        public int getLayoutType() {
            return DET_RESULT;
        }
    }

    class InvErrorStep implements Step{
        private final Matrix matrix;

        public InvErrorStep(Matrix matrix) {
            this.matrix = matrix;
        }

        @Override
        public View toView(Context context) {
            //TODO make a new cardview showing the user that their input was erroneous (needs square matrix).
            throw new UnsupportedOperationException();
        }

        @Override
        public void setViewContent(View view) {
            //TODO make a new cardview showing the user that their input was erroneous (needs square matrix).
            throw new UnsupportedOperationException();
        }

        @Override
        public int getLayoutType() {
            return SINGLE_MATRIX;
        }
    }

    class InvResultStep implements Step {
        private final Matrix matrix;

        public InvResultStep(Matrix matrix) {
            this.matrix = matrix;
        }

        @Override
        public View toView(Context context) {
            return new MatrixResultView(context, matrix);
        }

        @Override
        public void setViewContent(View view) {
            if( ! (view instanceof MatrixResultView) ) {
                throw new IllegalArgumentException("Wrong view passed to step");
            }

            MatrixResultView cardView = (MatrixResultView) view;

            cardView.setMatrix(matrix);
        }

        @Override
        public int getLayoutType() {
            return MATRIX_RESULT;
        }
    }

    class MultResultStep implements Step {
        private final Matrix matrix;

        public MultResultStep(Matrix matrix) {
            this.matrix = matrix;
        }

        @Override
        public View toView(Context context) {
            return new MatrixResultView(context, matrix);
        }

        @Override
        public void setViewContent(View view) {
            if( ! (view instanceof MatrixResultView) ) {
                throw new IllegalArgumentException("Wrong view passed to step");
            }

            MatrixResultView cardView = (MatrixResultView) view;
            cardView.setMatrix(matrix);
        }

        @Override
        public int getLayoutType() {
            return MATRIX_RESULT;
        }
    }
}