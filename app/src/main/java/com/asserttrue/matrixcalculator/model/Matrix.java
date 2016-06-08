package com.asserttrue.matrixcalculator.model;

public class Matrix {

    private final Rational[][] matrix_array;
    private int augmentedColumnIndex;

    private String name = "";

    public Matrix(Rational [][] array, int augColInd, String name){
        matrix_array = array;
        augmentedColumnIndex = augColInd;
        this.name = name;
    }

    public Matrix(Rational [][] array){
        this(array, -1, "");
    }

    public Matrix(int nrofcolumns, int nrofrows, int augColInd){
        matrix_array = new Rational[nrofrows][nrofcolumns];
        augmentedColumnIndex = augColInd;

        for (int y =0; y < nrofrows; y++){
            for (int x = 0; x < nrofcolumns; x++){
                setValue(x, y, new Rational(0));
            }
        }
    }

    public static Matrix identity(int size) {
        Matrix id = new Matrix(size, size);

        for(int n = 0; n < size; n++)
            id.setValue(n, n, new Rational(1));

        return id;
    }

    /**
     * Constructor for augmented matrix, containing left and right matrix.
     */
    public Matrix(Matrix left, Matrix right) {
        if(left.getNrRows() != right.getNrRows()) {
            throw new IllegalArgumentException("Left and right matrices must have same number of rows.");
        }

        matrix_array = new Rational[left.getNrRows()][left.getNrColumns() + right.getNrColumns()];
        augmentedColumnIndex = left.getNrColumns();

        for(int row = 0; row < left.getNrRows(); row++) {
            for(int column = 0; column < left.getNrColumns(); column++) {
                setValue(column, row, left.getValueAt(column, row));
            }

            for(int column = 0; column < right.getNrColumns(); column++) {
                setValue(column + left.getNrColumns(), row, right.getValueAt(column, row));
            }
        }
    }

    public Matrix(int nrofcolumns, int nrofrows){
        this(nrofcolumns, nrofrows, -1);
    }

    public Matrix(Matrix matrix){
        augmentedColumnIndex = matrix.getAugmentedColumnIndex();

        matrix_array = new Rational[matrix.getNrRows()][matrix.getNrColumns()];
        // Copy the whole 2D array of the matrix into this one.
        for(int row = 0; row < matrix.getNrRows(); row++) {
            for(int column = 0; column < matrix.getNrColumns(); column++) {
                setValue(column, row, matrix.getValueAt(column, row));
            }
        }
    }

    public Rational[][] getInternalArray(){
        return matrix_array;
    }

    public int getNrColumns(){
        return matrix_array[0].length;
    }

    public int getNrRows(){
        return matrix_array.length;
    }

    public void setValue(int column, int row, Rational number){
        matrix_array[row][column] = new Rational(number);
    }

    public Rational getValueAt(int column, int row){
        return new Rational( matrix_array[row][column] );
    }

    public int getAugmentedColumnIndex(){
        return augmentedColumnIndex;
    }

    // Row reduction tools:

    /**
     * Add a scalar multiple of row 'from' to row 'to'. This is used in row elimination algorithms.
     */
    public void addRow(int from, int to, Rational scalar) {
        if(!(isRowIndex(from) && isRowIndex(to))) {
            throw new IllegalArgumentException("Row indices are outside of the matrix dimensions.");
        }

        for(int column = 0; column < getNrColumns(); column++) {
            matrix_array[to][column].plusIs( matrix_array[from][column].times(scalar) );
        }
    }

    public void addRow(int from, int to) {
        addRow(from, to, new Rational(1));
    }

    /**
     * Multiply an entire row with a scalar value.
     */
    public void multiplyRow(int row, Rational scalar) {
        if(!isRowIndex(row)) {
            throw new IllegalArgumentException("Row index is outside of the matrix dimensions.");
        }
        if(scalar.equals(new Rational(0))) {
            throw new IllegalArgumentException("Row multiplication by zero is does not preserve matrix properties.");
        }

        for(int column = 0; column < getNrColumns(); column++) {
            matrix_array[row][column].timesIs(scalar);
        }
    }

    public void timesIs(Rational r) {
        for(Rational[] row : matrix_array)
            for(Rational cell : row)
                cell.timesIs(r);
    }

    public void swapRows(int row1, int row2) {
        if(!(isRowIndex(row1) && isRowIndex(row2))) {
            throw new IllegalArgumentException("Row indices are outside of the matrix dimensions.");
        }

        Rational[] temporary = matrix_array[row1];
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

    public Matrix getColumnVector(int n) {
        if( n < 0 || n >= getNrColumns()) {
            throw new IllegalArgumentException("Column index out of range.");
        }

        Matrix vector = new Matrix(1, getNrRows());

        for(int row = 0; row < getNrRows(); row++)
            vector.setValue(0, row, new Rational(getValueAt(n, row)));

        return vector;
    }

    public Matrix getRightMatrix() {
        if(augmentedColumnIndex == -1) {
            throw new UnsupportedOperationException("getAugmentedMatrix on a matrix that is not augmented");
        }

        Matrix aug = new Matrix(getNrColumns() - augmentedColumnIndex, getNrRows());
        for(int column = 0; column < augmentedColumnIndex; column++)
            for(int row = 0; row < getNrRows(); row++)
                aug.setValue(column, row, getValueAt(column + augmentedColumnIndex, row));

        return aug;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAugmentedColumnIndex(int i) {
        augmentedColumnIndex = i;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < getNrRows(); y++){
            for (int x = 0; x < getNrColumns(); x++){
                sb.append(String.format("%s/%s ",
                        String.valueOf(getValueAt(x, y).getNumerator()),
                        String.valueOf(getValueAt(x, y).getDenominator())));
            }
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
