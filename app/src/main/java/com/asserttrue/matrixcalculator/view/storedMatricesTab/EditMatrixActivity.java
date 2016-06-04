package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.MathTextButton;

public class EditMatrixActivity extends AppCompatActivity {

    private EditMatrixGridView grid;
    private EditMatrixAdapter editMatrixAdapter;
    private CheckBox saveMatrixBox;
    private Button finishEditingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_matrix);

        final EditText matrixName = (EditText) findViewById(R.id.matrixName);
        Spinner width = (Spinner) findViewById(R.id.numWidth);
        Spinner height = (Spinner) findViewById(R.id.numHeight);
        final MathTextButton zeroButton = (MathTextButton) findViewById(R.id.zeroMatrixButton);
        final MathTextButton identityButton = (MathTextButton) findViewById(R.id.identityMatrixButton);
        saveMatrixBox = (CheckBox) findViewById(R.id.saveMatrixCheckbox);
        finishEditingButton = (Button) findViewById(R.id.finishEditingButton);

        finishEditingButton.setEnabled(false);

        Integer[] items = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, items);
        width.setAdapter(adapter);
        height.setAdapter(adapter);

        grid = (EditMatrixGridView) findViewById(R.id.gridView);
        grid.setNumColumns(2);

        EditMatrixSingleton matrixSettings = EditMatrixSingleton.getInstance();

        if (matrixSettings.forceSave) {
            saveMatrixBox.setChecked(true);
            saveMatrixBox.setEnabled(false);
        }

        editMatrixAdapter = new EditMatrixAdapter(this, matrixSettings.editMatrix);
        grid.setAdapter(editMatrixAdapter);

        width.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editMatrixAdapter.updateWidth(position + 1);
                grid.setNumColumns(position + 1);
                if (editMatrixAdapter.isSquare())
                    identityButton.setEnabled(true);
                else
                    identityButton.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        height.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editMatrixAdapter.updateHeight(position + 1);
                if (editMatrixAdapter.isSquare())
                    identityButton.setEnabled(true);
                else
                    identityButton.setEnabled(false);
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

        zeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMatrixAdapter.setZeroMatrix();
            }
        });

        identityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMatrixAdapter.setIdentityMatrix();
            }
        });

        finishEditingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMatrix();
            }
        });

        matrixName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (matrixName.getText().toString().isEmpty())
                    finishEditingButton.setEnabled(false);
                else
                    finishEditingButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        width.setSelection(matrixSettings.editMatrix.getNrColumns() - 1);
        height.setSelection(matrixSettings.editMatrix.getNrRows() - 1);
    }

    private void saveMatrix() {
        if (saveMatrixBox.isChecked()) {
            // Save to database
        }
        EditMatrixSingleton settings = EditMatrixSingleton.getInstance();
        settings.editMatrix = editMatrixAdapter.getMatrix();
        settings.isResult = true;
        finish();
    }
}
