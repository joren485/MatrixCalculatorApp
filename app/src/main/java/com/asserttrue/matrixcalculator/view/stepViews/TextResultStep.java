package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.View;

import java.util.Locale;

/**
 * Step containing a result that, but no numbers or matrices to show; only text.
 */
public abstract class TextResultStep implements Step {

    @Override
    public View toView(Context context) {
        return new TextResultView(context, getExplanation());
    }

    @Override
    public void setViewContent(View view) {
        if( ! (view instanceof TextResultView) ) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        TextResultView cardView = (TextResultView) view;
        cardView.setText(getExplanation());
    }

    @Override
    public int getLayoutType() {
        return TEXT_RESULT;
    }
    /**
     * Get a string to show to the user as explanation. Varies per kind of computation.
     */
    protected abstract String getExplanation();


    /**
     * The following classes are implementations which share the same layout but have slightly
     * different explanations.
     */

    public static class InvErrorStep extends TextResultStep {

        @Override
        protected String getExplanation() {
            return "Only square matrices have an inverse.";
        }
    }

    public static class MultErrorStep extends TextResultStep {
        private final int nrColumnsLeft;
        private final int nrRowsRight;

        public MultErrorStep(int nrColumnsLeft, int nrRowsRight) {
            this.nrColumnsLeft = nrColumnsLeft;
            this.nrRowsRight = nrRowsRight;
        }

        @Override
        protected String getExplanation() {
            return String.format(Locale.US, "The first matrix has %d columns and the right has %d rows. These numbers need to be equal for multiplication to be defined", nrColumnsLeft, nrRowsRight);
        }
    }

    public static class AddErrorStep extends TextResultStep {

        @Override
        protected String getExplanation() {
            return "These matrices do, not have the same number of rows and columns, so addition is undefined.";
        }
    }

    public static class DetErrorStep extends TextResultStep {

        @Override
        protected String getExplanation() {
            return "The determinant is only defined on square matrices.";
        }
    }
}
