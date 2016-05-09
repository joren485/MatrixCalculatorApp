/**
 * Created by joren on 5/9/16.
 */
public class comp_transpose extends computation<matrix> {

    private matrix A;

    public comp_transpose(matrix m){
        this.A = m;
    }

    public matrix compute(){

        matrix result = new matrix(A.getWidth(), A.getHeight());

        for (int y = 0; y < A.getWidth(); y++)
            for (int x = 0; x < A.getHeight(); x++)
                result.fill_spot(x, y, A.get_spot(y, x));

        return result;
    }
}
