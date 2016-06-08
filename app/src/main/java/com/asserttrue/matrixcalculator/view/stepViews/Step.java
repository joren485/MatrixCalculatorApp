package com.asserttrue.matrixcalculator.view.stepViews;
import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

/**
 * Class containing some information about a particular moment in the computation.
 * Contains methods to wrap the data in a cardView, and to update the information inside this view.
 */
public interface Step {
    /**
     * The kind of cardview used to display Steps of a given type (used by adapters).
     */
    int SINGLE_MATRIX = 0;
    int MATRIX_RESULT = 1;
    int DET_MATRIX_SCALAR = 2;
    int DET_RESULT = 3;
    int VECTOR_SPAN = 4;
    int TEXT_RESULT = 5;
    int NR_TYPES = 6;

    /**
     * Get a view explaining this step.
     * @param context the context in which to inflate the view
     * @return a view explaining this step
     */
    View toView(Context context);

    /**
     * When given a view of the appropriate type, fill the content with the information
     * about this step.
     * @param view The view to update
     */
    void setViewContent(View view);

    /**
     * Return one of the static ints in this class signaling the kind of cardview required to display this step.
     */
    int getLayoutType();

    /**
     * Step showing the result of the determinant computation.
     */
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
            if (!(view instanceof DetResultView)) {
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

    /**
     * Step containing the result of the inverse computation.
     */
    class ResultStep implements Step {
        private final Matrix matrix;

        public ResultStep(Matrix matrix) {
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