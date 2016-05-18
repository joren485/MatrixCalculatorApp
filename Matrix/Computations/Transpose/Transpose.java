package Matrix.Computations.Transpose;
import Matrix.Computations.Computation;
import Matrix.Matrix;

public class Transpose extends Computation {

    private final Matrix matrix;

    public Transpose(Matrix A){
        matrix = A;

        if (validate()){
            compute();

        }
        else{
        }
        done();
    }

    protected void compute(){

        Matrix result = new Matrix(matrix.getWidth(), matrix.getHeight());

        for (int y = 0; y < matrix.getWidth(); y++)
            for (int x = 0; x < matrix.getHeight(); x++)
                result.fillSpot(x, y, matrix.getSpot(y, x));

        steps.add(result);
    }

    protected boolean validate(){
        return true;
    }
}
