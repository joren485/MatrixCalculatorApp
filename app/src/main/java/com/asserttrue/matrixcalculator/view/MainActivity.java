package com.asserttrue.matrixcalculator.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.computations.determinant.DeterminantComputation;
import com.asserttrue.matrixcalculator.view.computationsTab.ComputationActivity;
import com.asserttrue.matrixcalculator.view.computationsTab.CurrentComputation;
import com.asserttrue.matrixcalculator.view.computationsTab.StepAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager tabSwipe = (ViewPager) findViewById(R.id.view_pager);
        tabSwipe.setAdapter(new MatrixFragmentPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(tabSwipe);
    }

    public void computeDeterminant(View view) {

    }
}
