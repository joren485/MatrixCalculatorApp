package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;

public class EditMatrixActivity extends AppCompatActivity {

    private EditMatrixGridView grid;
    private EditMatrixAdapter editMatrixAdapter;

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

        grid = (EditMatrixGridView) findViewById(R.id.gridView);
        grid.setNumColumns(2);

        editMatrixAdapter = new EditMatrixAdapter(this, new Matrix(2, 2));
        grid.setAdapter(editMatrixAdapter);

        width.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            boolean firstTime = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTime)
                    firstTime = false;
                else {
                    editMatrixAdapter.updateWidth(position + 1);
                    grid.setNumColumns(position + 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        height.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            boolean firstTime = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTime)
                    firstTime = false;
                else
                    editMatrixAdapter.updateHeight(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editMatrixAdapter.updateEditing(position);
            }
        });

        width.setSelection(1);
        height.setSelection(1);
    }
}
