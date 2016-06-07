package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class StepAdapter extends ArrayAdapter<Step> {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<Step> steps;


    public StepAdapter(Context context, List<Step> steps) {
        // No TextView id is needed, so -1 is passed on.
        super(context, -1);
        this.context = context;
        this.steps = steps;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        Step step = steps.get(position);

        if(convertView == null) {
            convertView = step.toView(context);
        }
        else{
            step.setViewContent(convertView);
        }

        /*

        if(convertView == null) {
            switch (step.getLayoutType()){
                case Step.TYPE_DET_SCALAR:
                    convertView = new DetScalarView(context, (DetScalarStep) step);
                    break;
                case Step.TYPE_DET_RESULT:
                    convertView = new DetResultView(context, (DetScalarStep) step);
                    break;
                case Step.TYPE_JUST_MATRIX:
                    convertView = new SingleMatrixView(context, (SingleMatrixStep) step);
                    break;
                case Step.TYPE_INV_RESULT:
                    convertView = new InvResultView(context, (SingleMatrixStep) step);
                    break;
                default: throw new UnsupportedOperationException("Viewtype " + step.getLayoutType() + " not implemented yet.");
            }
        }
        else {
            switch (step.getLayoutType()) {
                case Step.TYPE_DET_SCALAR:
                    ((DetScalarView) convertView).recycle(step);
                    break;
                case Step.TYPE_DET_RESULT:
                    ((DetResultView) convertView).recycle(step);
                    break;
                case Step.TYPE_JUST_MATRIX:
                    ((SingleMatrixView) convertView).recycle(step);
                    break;
                case Step.TYPE_INV_RESULT:
                    ((InvResultView) convertView).recycle(step);
            }
        }

        */

        return convertView;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public int getViewTypeCount() {
        return Step.NR_TYPES;
    }

    @Override
    public int getItemViewType(int position) {
        return steps.get(position).getLayoutType();
    }
}
