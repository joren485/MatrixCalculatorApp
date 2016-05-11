/**
 * Created by joren on 5/9/16.
 */
public class main {
    public static void main(String[] args) {

        Matrix m = new Matrix(3,3);
        m.fill_spot(2, 1, 9);
        m.fill_spot(0, 2, 7);
        m.fill_spot(1, 0, 3);
        m.fill_spot(0, 0, 5);


        System.out.println(m.toString());

        Computation c = new Transpose(m);

        System.out.println(c.nextStep());
    }
}
