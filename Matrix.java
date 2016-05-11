/**
 * Created by joren on 5/9/16.
 */

public class Matrix {

    private final double[][] matrix_array;

    public Matrix(double [][] array){
        matrix_array = array;
    }

    public Matrix(int width, int height){
        matrix_array = new double[width][height];

        for (int y =0; y< height; y++){
            for (int x = 0; x < width; x++){
                fill_spot(x, y, 0);
            }
        }
    }

    public Matrix(Matrix copy){
        this(copy.getInternalArray());
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

    public void fill_spot(int x, int y, double number){
        matrix_array[y][x] = number;
    }

    public double get_spot(int x, int y){
        return matrix_array[y][x];
    }

    public String toString(){

        StringBuilder sb = new StringBuilder();

        for (int y =0; y< getHeight(); y++){
            for (int x = 0; x < getWidth(); x++){
                sb.append(' ').append(get_spot(x, y)).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
