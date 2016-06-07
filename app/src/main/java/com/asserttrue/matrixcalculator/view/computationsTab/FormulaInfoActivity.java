package com.asserttrue.matrixcalculator.view.computationsTab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;

public class FormulaInfoActivity extends AppCompatActivity {

    public static final String[] TITLES =
            {
                    "Multiplication",
                    "Addition",
                    "Determinant",
                    "Inverse",
                    "Kernel",
                    "Cramer's Rule"
            };

    public static final String[] TEXTS =
            {
                    "In a matrix product, the (i,j)'th place of the result is found by multiplying the values in the the i'th row of the left-hand matrix (going left to right) with the values in the j-th column of the right-hand matrix (top to bottom) and adding up these results.\nSo the value at (i,j) is the dot product of row i (transposed) and column j.\n\nThis product is only defined, when the left-hand matrix has just as many rows as the right-hand one has columns.\n\nWith this definition, matrix multiplication behaves just like function composition:\nIf T and U are linear functions with matrices A and B, then TU = T o U has the matrix AB.\n\nSince matrices also correspond uniquely to linear functions, the two notions are really 'identical';\nThat is, the rings of linear function and of matrices are isomorphic to one another.",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_info);

        int index = getIntent().getExtras().getInt("index");

        ((TextView) findViewById(R.id.text_title)).setText(TITLES[index]);
        ((TextView) findViewById(R.id.text_body)).setText(TEXTS[index]);
    }
}
