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
        else {
            step.setViewContent(convertView);
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
