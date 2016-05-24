package com.asserttrue.matrixcalculator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MatrixView extends LinearLayout {

    private final Paint paint = new Paint();

    private Matrix matrix;
    private int width, height, augmentedColumnIndex;
    private Context mContext;

    ArrayList<LinearLayout> columns;

    public MatrixView (Context c, Matrix matrix) {
        super(c);
        this.mContext = c;
        this.matrix = matrix;
        this.width = matrix.getWidth();
        this.height = matrix.getHeight();

        //  TESTING:
        this.augmentedColumnIndex = 1;//matrix.getAugmentedColumnIndex();
        this.setPadding(20, 0, 20, 0);
        this.paint.setColor(Color.BLACK);
        this.paint.setStrokeWidth(5);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.columns = new ArrayList<>(width);
        init();
    }

    private void init() {
        for (int x = 0; x < width; x++) {

            LinearLayout column = new LinearLayout(mContext);
            column.setPadding(10, 0, 10, 0);
            column.setOrientation(LinearLayout.VERTICAL);
            for (int y = 0; y < height; y++) {
                TextView view = new TextView(mContext);
                view.setGravity(Gravity.CENTER);
                view.setPadding(0, 10, 0, 10);
                view.setText(parseDouble(matrix.getValueAt(x, y)));
                column.addView(view);
            }
            this.addView(column);
            columns.add(column);
        }
    }

    private void updateFields() {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                ((TextView) columns.get(x).getChildAt(y)).setText(parseDouble(matrix.getValueAt(x, y)));

    }

    private String parseDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String result = df.format(d);
        return result.equals("-0") ? "0" : result;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
        //updateFields();
    }

    public void dispatchDraw(Canvas c) {
        super.dispatchDraw(c);
        int width = this.getWidth();
        int height = this.getHeight();



        // LEFT BRACKET
        c.drawLine(1, 1, 19, 1, paint);
        c.drawLine(1, 1, 1, height-2, paint);
        c.drawLine(1, height-2, 19, height-2, paint);

        // RIGHT BRACKET
        c.drawLine(width - 20, 1, width - 2, 1, paint);
        c.drawLine(width - 2, 1, width - 2, height - 2, paint);
        c.drawLine(width - 2, height - 2, width - 20, height - 2, paint);
    }
}
