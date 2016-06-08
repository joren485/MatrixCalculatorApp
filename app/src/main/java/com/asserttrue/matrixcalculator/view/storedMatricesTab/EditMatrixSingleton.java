package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import com.asserttrue.matrixcalculator.model.Matrix;

/**
 * Created by gijs on 4-6-16.
 */
public class EditMatrixSingleton {

    private static EditMatrixSingleton instance;
    public boolean forceSave, isResult, editingExisting;
    public Matrix editMatrix;
    public String matrixName;

    private EditMatrixSingleton() {
        this.setVariables(new Matrix(2, 2), "", false, false, false);
    }

    public void setVariables(Matrix m, String matrixName, boolean forceSave, boolean isResult, boolean editingExisting) {
        this.editMatrix = m;
        this.matrixName = matrixName;
        this.forceSave = forceSave;
        this.isResult = isResult;
        this.editingExisting = editingExisting;
    }

    public static EditMatrixSingleton getInstance() {
        if (instance == null) {
            instance = new EditMatrixSingleton();
        }
        return instance;
    }
}
