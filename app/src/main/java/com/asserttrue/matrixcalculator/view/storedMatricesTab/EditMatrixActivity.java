package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.DatabaseHandler;
import com.asserttrue.matrixcalculator.view.MathTextButton;

public class EditMatrixActivity extends AppCompatActivity {

    private EditMatrixGridView grid;
    private EditMatrixAdapter editMatrixAdapter;
    private CheckBox saveMatrixBox;
    private Button finishEditingButton;
    private EditText matrixName;
    private SeekBar augmentedLineSeekbar;
    private DatabaseHandler hDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_matrix);

        hDB = new DatabaseHandler(getApplicationContext());

        matrixName = (EditText) findViewById(R.id.matrixName);

        Spinner columnSpinner = (Spinner) findViewById(R.id.numColumns);
        Spinner rowSpinner = (Spinner) findViewById(R.id.numRows);

        final MathTextButton zeroButton = (MathTextButton) findViewById(R.id.zeroMatrixButton);
        final MathTextButton identityButton = (MathTextButton) findViewById(R.id.identityMatrixButton);
        final CheckBox augmentedCheckbox = (CheckBox) findViewById(R.id.augmentedCheckbox);

        saveMatrixBox = (CheckBox) findViewById(R.id.saveMatrixCheckbox);
        finishEditingButton = (Button) findViewById(R.id.finishEditingButton);

        augmentedLineSeekbar = (SeekBar) findViewById(R.id.augmentedLineBar);

        Integer[] items = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.spinner_item , items);

        columnSpinner.setAdapter(adapter);
        rowSpinner.setAdapter(adapter);

        grid = (EditMatrixGridView) findViewById(R.id.gridView);

        EditMatrixSingleton matrixSettings = EditMatrixSingleton.getInstance();

        if (matrixSettings.forceSave) {

            TextView saveMatrixTextView = (TextView) findViewById(R.id.saveMatrixText);
            saveMatrixTextView.setVisibility(View.GONE);
            saveMatrixBox.setVisibility(View.GONE);

            saveMatrixBox.setChecked(true);
            saveMatrixBox.setEnabled(false);
        } else {
            matrixName.setVisibility(View.GONE);
        }

        if (matrixSettings.editingExisting) {
            matrixName.setText(matrixSettings.matrixName);
            matrixName.setEnabled(false);
        }

        if (matrixName.getText().toString().isEmpty() && saveMatrixBox.isChecked())
            finishEditingButton.setEnabled(false);

        if (matrixSettings.editMatrix.getAugmentedColumnIndex() >= 0 && matrixSettings.editMatrix.getAugmentedColumnIndex() < matrixSettings.editMatrix.getNrColumns()) {
            augmentedCheckbox.setChecked(true);
        } else {
            augmentedLineSeekbar.setVisibility(View.GONE);
        }

        editMatrixAdapter = new EditMatrixAdapter(this, matrixSettings.editMatrix);
        grid.setAdapter(editMatrixAdapter);

        augmentedLineSeekbar.setMax(matrixSettings.editMatrix.getNrColumns());
        augmentedLineSeekbar.setProgress(matrixSettings.editMatrix.getAugmentedColumnIndex());

        augmentedLineSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editMatrixAdapter.updateAugmentedIndex(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        columnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editMatrixAdapter.updateNrOfColumns(position + 1);
                grid.setNumColumns(position + 1);
                augmentedLineSeekbar.setMax(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        rowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editMatrixAdapter.updateNrOfRows(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editMatrixAdapter.setEditingPosition(position);
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

                if (editMatrixAdapter.isSquare()){
                    editMatrixAdapter.setIdentityMatrix();
                }
                else{
                    Toast.makeText(EditMatrixActivity.this, "Matrix is not square.", Toast.LENGTH_SHORT).show();
                }
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

                String name = matrixName.getText().toString();

                if ((name.isEmpty() && saveMatrixBox.isChecked())) {
                    finishEditingButton.setEnabled(false);
                }
                else {
                    finishEditingButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveMatrixBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String name = matrixName.getText().toString();

                if (saveMatrixBox.isChecked()) {
                    if (name.isEmpty())
                        finishEditingButton.setEnabled(false);
                    else
                        finishEditingButton.setEnabled(true);

                    matrixName.setVisibility(View.VISIBLE);
                } else {
                    finishEditingButton.setEnabled(true);
                    matrixName.setVisibility(View.GONE);
                }
            }
        });

        augmentedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                augmentedLineSeekbar.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                if (!isChecked) {
                    editMatrixAdapter.updateAugmentedIndex(-1);
                    augmentedLineSeekbar.setProgress(0  );
                }
            }
        });

        columnSpinner.setSelection(matrixSettings.editMatrix.getNrColumns() - 1);
        rowSpinner.setSelection(matrixSettings.editMatrix.getNrRows() - 1);
    }

    private void saveMatrix() {

        EditMatrixSingleton settings = EditMatrixSingleton.getInstance();

        if (saveMatrixBox.isChecked()) {
            editMatrixAdapter.getMatrix().setName(matrixName.getText().toString());
            if (settings.editingExisting)
                hDB.updateMatrix(editMatrixAdapter.getMatrix(), matrixName.getText().toString());
            else
                hDB.createMatrix(editMatrixAdapter.getMatrix(), matrixName.getText().toString());
        }

        settings.setVariables(editMatrixAdapter.getMatrix(), matrixName.getText().toString(), false, true, false);
        finish();
    }
}
