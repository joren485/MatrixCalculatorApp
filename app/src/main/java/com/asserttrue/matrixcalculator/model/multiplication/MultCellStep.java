package com.asserttrue.matrixcalculator.model.multiplication;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;

import java.util.List;

public class MultCellStep extends SingleMatrixStep {
    private final List<Rational> leftTerms;
    private final List<Rational> rightTerms;
    private final Rational result;
    private final int row;
    private final int column;

    public MultCellStep(Matrix matrix, Rational result, List<Rational> leftTerms1, List<Rational> rightTerms, int row, int column) {
        super(matrix);
        this.result = result;
        this.leftTerms = leftTerms1;
        this.rightTerms = rightTerms;
        this.row = row;
        this.column = column;
    }

    @Override
    protected String getExplanation() {
        StringBuilder sb = new StringBuilder();

        sb.append("Calculate cell (").append(row + 1).append(", ").append(column + 1).append(");\n");

        sb.append(leftTerms.get(0).toString()).append(" * ").append(rightTerms.get(0).toString());

        for(int i = 1; i < leftTerms.size(); i++) {
            if( ! (leftTerms.get(i).equals(new Rational(0)) || rightTerms.get(i).equals(new Rational(0))) )
            sb.append(" + ").append(leftTerms.get(i).toString()).append(" * ").append(rightTerms.get(i).toString());
        }

        sb.append(" = ").append(result.toString());

        return sb.toString();
    }
}
