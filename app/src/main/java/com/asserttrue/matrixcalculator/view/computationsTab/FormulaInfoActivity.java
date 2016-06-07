package com.asserttrue.matrixcalculator.view.computationsTab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;

public class FormulaInfoActivity extends AppCompatActivity {
    private static final String MULTIPLICATION =
            "In a matrix product, the (i,j)'th place of the result is found by multiplying the values in the the i'th row of the left-hand matrix (going left to right) with the values in the j-th column of the right-hand matrix (top to bottom) and adding up these results.\nSo the value at (i,j) is the dot product of row i (transposed) and column j.\n\nThis product is only defined, when the left-hand matrix has just as many rows as the right-hand one has columns.\n\nWith this definition, matrix multiplication behaves just like function composition:\nIf T and U are linear functions with matrices A and B, then TU = T o U has the matrix AB.\n\nSince matrices also correspond uniquely to linear functions, the two notions are really 'identical';\nThat is, the rings of linear function and of matrices are isomorphic to one another.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_info);

        int index = getIntent().getExtras().getInt("index");
        TextView title = ((TextView) findViewById(R.id.text_title));
        TextView body = ((TextView) findViewById(R.id.text_body));

        String computationName = getIntent().getStringExtra("computation");

        switch (computationName) {
            case "kernel":
                title.setText("Kernel Span");
                body.setText("");
                break;
            case "product":
                title.setText("Multiplication");
                body.setText(MULTIPLICATION);
                break;
            case "inverse":
                title.setText("Inverse");
                body.setText("");
                break;
            case "determinant":
                title.setText("Determinant");
                body.setText("");
                break;
            case "sum":
                title.setText("Addition");
                body.setText("");
                break;
            default:
                title.setVisibility(View.GONE);
                body.setVisibility(View.GONE);
        }
    }
}
