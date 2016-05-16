/**
 * Created by joren on 5/9/16.
 */
public class main {
    public static void main(String[] args) {

        Matrix m = new Matrix(3,3);
        m.fillSpot(2, 1, 9);
        m.fillSpot(1, 1, 4);
        m.fillSpot(0, 2, 7);
        m.fillSpot(1, 0, 3);
        m.fillSpot(0, 0, 5);
        m.fillSpot(2, 2, 2);

        System.out.println(m.toString());

        Computation c = new Determininant(m);
    }
}
