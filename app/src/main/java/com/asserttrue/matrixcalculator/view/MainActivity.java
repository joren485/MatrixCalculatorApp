package com.asserttrue.matrixcalculator.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.storedMatricesTab.EditMatrixActivity;
import com.asserttrue.matrixcalculator.view.storedMatricesTab.EditMatrixAdapter;
import com.asserttrue.matrixcalculator.view.storedMatricesTab.EditMatrixSingleton;

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

    public void addMatrix(View v) {
        EditMatrixSingleton settings = EditMatrixSingleton.getInstance();
        settings.setVariables(new Matrix(2, 2), "", false, false, false);
        Intent i = new Intent(this, EditMatrixActivity.class);
        startActivity(i);
    }
}
