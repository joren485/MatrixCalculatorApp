package com.asserttrue.matrixcalculator.view.computationsTab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;

public class FormulaInfoActivity extends AppCompatActivity {
    private static final String MULTIPLICATION =
            "In a matrix product, the (i,j)'th place of the result is found by multiplying the values in the the i'th row of the left-hand matrix (going left to right) with the values in the j-th column of the right-hand matrix (top to bottom) and adding up these results.\nSo the value at (i,j) is the dot product of row i (transposed) and column j.\n\nThis product is only defined, when the left-hand matrix has just as many rows as the right-hand one has columns.\n\nWith this definition, matrix multiplication behaves just like function composition:\nIf T and U are linear functions with matrices A and B, then TU = T o U has the matrix AB.\n\nSince matrices also correspond uniquely to linear functions, the two notions are really 'identical';\nThat is, the rings of linear function and of matrices are isomorphic to one another.";
    private static final String ADDITION =
            "The sum of two matrices taken component-wise. The two matrices need to be of precisely the same size for the sum to be defined, unlike with multiplication.\n\nSince scalar addition is associative and commutative, so is matrix addition. Matrix addition also distributes over multiplication.\n\nFurthermore, addition on matrices behaves just like addition of linear functions:\nIf T and U are linear functions with matrices A and B, then T + U will have the matrix A + B.";
    private static final String INVERSE =
            "All square matrices M with a nonzero determinant (equivalently, all onto and one-to-one square matrices), have a uniquely defined inverse. Even though matrix multiplication is not commutative, every left-inverse is also the right-inverse, and vice versa.\n\nThe inverse is computed here using a row reduction algorithm. The row operations used correspond to multiplying the augmented matrix on the left with one of the elementary operation matrices. If a matrix is invertible, it is can always be transformed into the identity matrix this way.\n\nThere are several different ways to compute the determinant besides the one used here; for example, by means of the cofactor matrix (classical adjoint). These will usually require considerably more time to compute than row reduction, especially for large matrices (due to higher complexity of the algorithm).";
    private static final String DETERMINANT =
            "The determinant is a function from the set of matrices to the (real, rational or complex) numbers. It reveals several important qualities of the matrix all at once. Most importantly, a square matrix has determinant 0 if and only if it is not invertible. Also, a determinant of a 3-dimensional matrix gives the factor with which it rescales the unit cube.\n\nThe determinant is usually defined recursively with Laplace expansion. Although this definition provides an explicit way to compute the determinant, this is rarely used; there are faster methods available.\n\nThe alogirthm used here applies row reduction make the matrix upper-triangular, because the determinant of an upper-triangular matrix is its trace (in this case 1, because the diangonal is all ones). Adding one row to another does not change the value of the determinant since the determinant is an n-linear function on the rows (and columns) of a matrix. We can divide a row by any nonzero number, if we multiply the result by that number. Swapping two rows changes the sign of the outcome.";
    private static final String KERNEL =
            "The kernel of a matrix is the set of vectors that it maps onto one. Since this is vector space, it is always possible to find a basis for it.\n\nSince the kernel of a matrix does not change under application of elementary row operations, we can first bring the matrix into reduced row echelon form. The resulting system of linear equations is much simplified. We find it by using the fact that if we add -1 to the free columns along the diagonal, and insert zeroes into them for eaach previous free column, then they form a basis for the kernel.\n\nIt is of course possible to write out the system of equations manually, and this basis is far from unique.";
    private static final String RREF =
            "The reduced row echelon form is a simplified form of a matrix, in which the solution to a system of linear equations is easier to read off.\n\nAs the name implies, the row echelon form is reached by only using row operations. The row operations correspond to adding multiples of one equation to another. The solution space for the matrix in the end is the same as the one of the matrix one started with.";
    private static final String SCALAR_MULT =
            "Scalar multiplication of a matrix or vector is done by multiplying each individual cell with that number. It distributes over scalar multiplication.";
    private static final String EXPONENTIATION =
            "The whole-number exponent of matrix can be computed simply by multiplying it by itself that number of times. Note that it does not matter whether you multiply on the right or left.\n\nAlthough real exponentiation for matrices is also defined, it requires somewhat advance computation.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_info);

        int index = getIntent().getExtras().getInt("index");
        TextView title = ((TextView) findViewById(R.id.text_title));
        TextView body = ((TextView) findViewById(R.id.text_body));

        String computationName = getIntent().getStringExtra("computation");

        switch (computationName) {
            case "kernel":
                title.setText("Kernel Span");
                body.setText(KERNEL);
                break;
            case "product":
                title.setText("Multiplication");
                body.setText(MULTIPLICATION);
                break;
            case "inverse":
                title.setText("Inverse");
                body.setText(INVERSE);
                break;
            case "determinant":
                title.setText("Determinant");
                body.setText(DETERMINANT);
                break;
            case "sum":
                title.setText("Addition");
                body.setText(ADDITION);
                break;
            case("rref"):
                title.setText("Row Echelon Form");
                body.setText(RREF);
                break;
            case("exponent"):
                title.setText("Exponentiation");
                body.setText(EXPONENTIATION);
                break;
            case("scalar mult"):
                title.setText("Scalar Multiplication");
                body.setText(SCALAR_MULT);
            default:
                title.setVisibility(View.GONE);
                body.setVisibility(View.GONE);
        }
    }
}
