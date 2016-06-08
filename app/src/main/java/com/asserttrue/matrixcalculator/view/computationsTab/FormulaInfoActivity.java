package com.asserttrue.matrixcalculator.view.computationsTab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;

public class FormulaInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_info);

        TextView title = ((TextView) findViewById(R.id.text_title));
        TextView body = ((TextView) findViewById(R.id.text_body));

        String computationName = getIntent().getStringExtra("computation");

        switch (computationName) {
            case "kernel":
                title.setText("Kernel Span");
                body.setText(R.string.KernelExplanation);
                break;
            case "product":
                title.setText("Multiplication");
                body.setText(R.string.MultiplicationExplanation);
                break;
            case "inverse":
                title.setText("Inverse");
                body.setText(R.string.InverseExplanation);
                break;
            case "determinant":
                title.setText("Determinant");
                body.setText(R.string.DeterminantExplanation);
                break;
            case "sum":
                title.setText("Addition");
                body.setText(R.string.AdditionExplanation);
                break;
            case("rref"):
                title.setText("Row Echelon Form");
                body.setText(R.string.RREFExplanation);
                break;
            case("exponent"):
                title.setText("Exponentiation");
                body.setText(R.string.ExponentiationExplanation);
                break;
            case("scalar mult"):
                title.setText("Scalar Multiplication");
                body.setText(R.string.ScalarExplanation);
                break;
            case("transpose"):
                title.setText("Transpose");
                body.setText(R.string.TransposeExplanation);
                break;
            default:
                title.setVisibility(View.GONE);
                body.setVisibility(View.GONE);
        }
    }
}
