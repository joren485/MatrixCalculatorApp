package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.asserttrue.matrixcalculator.R;

public class EditMatrixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_matrix);

        Spinner width = (Spinner) findViewById(R.id.numWidth);
        Spinner height = (Spinner) findViewById(R.id.numHeight);

        Integer[] items = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, items);
        width.setAdapter(adapter);
        height.setAdapter(adapter);

        GridView grid = (GridView) findViewById(R.id.gridView);

    }
}
