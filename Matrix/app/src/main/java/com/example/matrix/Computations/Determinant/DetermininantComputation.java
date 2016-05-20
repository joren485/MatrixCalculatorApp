package com.example.matrix.Computations.Determinant;
import com.example.matrix.Computations.Computation;
import com.example.matrix.Matrix;

/**
 * Created by joren on 5/16/16.
 */
public class DetermininantComputation extends Computation {

    private final Matrix matrix;

    public DetermininantComputation(Matrix A){
        matrix = A;

        if (validate()){
            compute();

        }
        else{
        }

        done();

    }

    protected void compute(){
        recursiveDeterminant(matrix);
    }

    protected boolean validate(){
        return matrix.getHeight() == matrix.getWidth();
    }

    private static double recursiveDeterminant(Matrix A){

        if(A.getWidth() == 1){
            return A.getSpot(0,0);
        }

        if (A.getWidth() == 2){

            // AD - BC
            return A.getSpot(0, 0) * A.getSpot(1,1) - A.getSpot(1, 0) * A.getSpot(0, 1);
        }

        double tempResult = 0;

        for(int row = 0; row < A.getHeight(); row++){

            Matrix part = new Matrix(A.getWidth() - 1, A.getHeight() - 1);

            for(int x = 1; x < A.getWidth(); x++){
                for (int y = 0; y < A.getHeight(); y++){

                    if (y < row){
                        part.fillSpot(x - 1, y, A.getSpot(x, y));
                    }

                    if (y > row){
                        part.fillSpot(x - 1, y - 1, A.getSpot(x, y));
                    }

                }
            }

            tempResult += Math.pow(-1, row) * A.getSpot(0, row) * recursiveDeterminant(part);

        }

        return tempResult;
    }

}
