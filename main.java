/**
 * Created by joren on 5/9/16.
 */
public class main {
    public static void main(String[] args) {

        matrix m = new matrix(3,3);
        m.fill_spot(2, 1, 9);
        m.fill_spot(0, 2, 7);

        System.out.println(m.toString());

        computation c = new comp_transpose(m);

        System.out.println(c.compute().toString());

    }
}
