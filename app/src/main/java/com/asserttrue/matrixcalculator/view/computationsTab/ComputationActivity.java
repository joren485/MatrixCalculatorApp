package com.asserttrue.matrixcalculator.view.computationsTab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.view.stepViews.StepAdapter;

public class ComputationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computation);

        ListView stepList = (ListView) findViewById(R.id.step_list);
        stepList.setAdapter(new StepAdapter(this, CurrentComputation.getInstance().getSteps()));
    }


}
