package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import com.asserttrue.matrixcalculator.model.Matrix;

/**
 * Created by gijs on 4-6-16.
 */
public class EditMatrixSingleton {

    private static EditMatrixSingleton instance;
    public boolean forceSave;
    public Matrix editMatrix;
    public boolean isResult;

    private EditMatrixSingleton() {}

    public void setVariables(boolean forceSave, Matrix m, boolean isResult) {
        this.forceSave = forceSave;
        this.editMatrix = m;
        this.isResult = isResult;
    }

    public static EditMatrixSingleton getInstance() {
        if (instance == null) {
            instance = new EditMatrixSingleton();
            instance.setVariables(false, new Matrix(2, 2), false);
        }
        return instance;
    }
}
