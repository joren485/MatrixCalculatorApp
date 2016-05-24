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
        matrix_array = new double[width][height];
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
            id.setPositionValue(n, n, 1);

        return id;
    }

    /**
     * Constructor for augmented matrix, containing left and right matrix.
     */
    public Matrix(Matrix left, Matrix right) {
        if(left.getHeight() != right.getHeight()) {
            throw new IllegalArgumentException("Left and right matrices must have same height.");
        }

        matrix_array = new double[left.getWidth() + right.getWidth()][left.getWidth()];
        augmentedColumnIndex = left.getWidth();

        for(int y = 0; y < left.getHeight(); y++) {
            for(int x = 0; x < left.getWidth(); x++)
                setPositionValue(x, y, left.getValueAt(x, y));

            for(int x = 0; x < right.getWidth(); x++)
                setPositionValue(x + left.getWidth(), y, right.getValueAt(x, y));
        }
    }

    public Matrix(int width, int height){
        this(width, height, -1);
    }

    public Matrix(Matrix matrix){
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        augmentedColumnIndex = 1;//matrix.getAugmentedColumnIndex();

        matrix_array = new double[width][height];
        // Copy the whole 2D array of the matrix into this one.
        for(int y = 0; y < height; y++) {
            System.arraycopy(matrix.getInternalArray()[y], 0, matrix_array[y], 0, width);
        }
    }

    public double[][] getInternalArray(){
        return matrix_array;
    }

    public int getWidth(){
        return matrix_array[0].length;
    }

    public int getHeight(){
        return matrix_array.length;
    }

    public void setPositionValue(int x, int y, double number){
        matrix_array[y][x] = number;
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

        for(int column = 0; column < getWidth(); column++) {
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

        for(int column = 0; column < getWidth(); column++) {
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
        return row >= 0 && row < getHeight();
    }

    public boolean isColumnIndex(int column) {
        return column >= 0 && column < getWidth();
    }

    public boolean isSquareMatrix() {
        return getHeight() == getWidth();
    }

    public String toString(){

        StringBuilder sb = new StringBuilder();

        for (int y =0; y< getHeight(); y++){
            for (int x = 0; x < getWidth(); x++){
                sb.append(String.format(" %.2f ", getValueAt(x, y)));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
