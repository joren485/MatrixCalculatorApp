public class Transpose extends Computation {

    public Transpose(Matrix matrix){

        Matrix result = new Matrix(matrix.getWidth(), matrix.getHeight());

        for (int y = 0; y < matrix.getWidth(); y++)
            for (int x = 0; x < matrix.getHeight(); x++)
                result.fill_spot(x, y, matrix.get_spot(y, x));

        iterSteps.add(result);

    }
}
