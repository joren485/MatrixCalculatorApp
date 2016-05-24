package com.asserttrue.matrixcalculator.model;

public class Matrix {

    private final double[][] matrix_array;
    private int augmentedColumnIndex;

    public Matrix(double [][] array, int augColInd){
        matrix_array = array;
        augmentedColumnIndex = augColInd;
    }

    public Matrix(double [][] array){
        this(array, -1);
    }

    public Matrix(int width, int height, int augColInd){
        matrix_array = new double[height][width];
        augmentedColumnIndex = augColInd;

        for (int y =0; y < height; y++){
            for (int x = 0; x < width; x++){
                setPositionValue(x, y, 0);
            }
        }
    }

    public static Matrix identity(int width) {
        Matrix id = new Matrix(width, width);

        for(int n = 0; n < width; n++)
            id.setPositionValue(n, n, 1.0);

        return id;
    }

    /**
     * Constructor for augmented matrix, containing left and right matrix.
     */
    public Matrix(Matrix left, Matrix right) {
        if(left.getNrRows() != right.getNrRows()) {
            throw new IllegalArgumentException("Left and right matrices must have same height.");
        }

        matrix_array = new double[left.getNrRows()][left.getNrColumns() + right.getNrColumns()];
        augmentedColumnIndex = left.getNrColumns();

        for(int row = 0; row < left.getNrRows(); row++) {
            for(int column = 0; column < left.getNrColumns(); column++) {
                setPositionValue(column, row, left.getValueAt(column, row));
            }

            for(int column = 0; column < right.getNrColumns(); column++) {
                setPositionValue(column + left.getNrColumns(), row, right.getValueAt(column, row));
            }
        }
    }

    public Matrix(int width, int height){
        this(width, height, -1);
    }

    public Matrix(Matrix matrix){
        int height = matrix.getNrRows();
        int width = matrix.getNrColumns();
        augmentedColumnIndex = matrix.getAugmentedColumnIndex();

        matrix_array = new double[height][width];
        // Copy the whole 2D array of the matrix into this one.
        for(int row = 0; row < height; row++) {
            System.arraycopy(matrix.getInternalArray()[row], 0, matrix_array[row], 0, width);
        }
    }

    public double[][] getInternalArray(){
        return matrix_array;
    }

    public int getNrColumns(){
        return matrix_array[0].length;
    }

    public int getNrRows(){
        return matrix_array.length;
    }

    public void setPositionValue(int column, int row, double number){
        matrix_array[row][column] = number;
    }

    public double getValueAt(int column, int row){
        return matrix_array[row][column];
    }

    public int getAugmentedColumnIndex(){
        return augmentedColumnIndex;
    }

    // Row reduction tools:

    /**
     * Add a scalar multiple of row 'from' to row 'to'. This is used in row elimination algorithms.
     */
    public void addRow(int from, int to, double scalar) {
        if(!(isRowIndex(from) && isRowIndex(to))) {
            throw new IllegalArgumentException("Row indices are outside of the matrix dimensions.");
        }

        for(int column = 0; column < getNrColumns(); column++) {
            matrix_array[to][column] += matrix_array[from][column] * scalar;
        }
    }

    public void addRow(int from, int to) {
        addRow(from, to, 1.0);
    }

    /**
     * Multiply an entire row with a scalar value.
     */
    public void multiplyRow(int row, double scalar) {
        if(!isRowIndex(row)) {
            throw new IllegalArgumentException("Row index is outside of the matrix dimensions.");
        }
        if(scalar == 0.0) {
            throw new IllegalArgumentException("Row multiplication by zero is does not preserve matrix properties.");
        }

        for(int column = 0; column < getNrColumns(); column++) {
            matrix_array[row][column] *= scalar;
        }
    }

    public void swapRows(int row1, int row2) {
        if(!(isRowIndex(row1) && isRowIndex(row2))) {
            throw new IllegalArgumentException("Row indices are outside of the matrix dimensions.");
        }

        double[] temporary = matrix_array[row1];
        matrix_array[row1] = matrix_array[row2];
        matrix_array[row2] = temporary;
    }

    public boolean isRowIndex(int row) {
        return row >= 0 && row < getNrRows();
    }

    public boolean isColumnIndex(int column) {
        return column >= 0 && column < getNrColumns();
    }

    public boolean isSquareMatrix() {
        return getNrRows() == getNrColumns();
    }

    public String toString(){

        StringBuilder sb = new StringBuilder();

        for (int y = 0; y< getNrRows(); y++){
            for (int x = 0; x < getNrColumns(); x++){
                sb.append(String.format(" %.2f ", getValueAt(x, y)));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public Matrix getRightMatrix() {
        if(augmentedColumnIndex == -1) {
            throw new UnsupportedOperationException("getAugmentedMatrix on a matrix that is not augmented");
        }

        Matrix aug = new Matrix(getNrColumns() - augmentedColumnIndex, getNrRows());
        for(int column = 0; column < augmentedColumnIndex; column++)
            for(int row = 0; row < getNrRows(); row++)
                aug.setPositionValue(column, row, getValueAt(column + augmentedColumnIndex, row));

        return aug;
    }
}
