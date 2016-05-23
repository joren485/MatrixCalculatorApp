package com.asserttrue.matrixcalculator.view.computationsTab;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.asserttrue.matrixcalculator.model.computations.Step;
import com.asserttrue.matrixcalculator.model.computations.determinant.DetScalarStep;

import java.util.List;
import java.util.zip.Inflater;

public class StepAdapter extends ArrayAdapter<Step> {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<Step> steps;


    public StepAdapter(Context context, List<Step> steps) {
        super(context, -1);
        this.context = context;
        this.steps = steps;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        Step step = steps.get(position);

        if(convertView == null) {
            switch (step.getLayoutType()){
                case Step.TYPE_DET_SCALAR:
                    convertView = new DetScalarView(context, (DetScalarStep) step);
                    break;
                case Step.TYPE_DET_RESULT:
                    convertView = new DetResultView(context, (DetScalarStep) step);
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
            }
        }

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
