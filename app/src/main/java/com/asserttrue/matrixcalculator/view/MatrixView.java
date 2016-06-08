package com.asserttrue.matrixcalculator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.model.Matrix;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MatrixView extends LinearLayout {

    private final Paint paint = new Paint();

    private Matrix matrix;

    private int width, height;

    private Context mContext;

    private ArrayList<LinearLayout> columns;

    public MatrixView (Context c, Matrix matrix) {
        super(c);
        this.mContext = c;
        this.matrix = matrix;
        this.width = matrix.getNrColumns();
        this.height = matrix.getNrRows();
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
            column.setOrientation(LinearLayout.VERTICAL);
            for (int y = 0; y < height; y++) {
                TextView view = new TextView(mContext);
                view.setGravity(Gravity.CENTER);
                view.setPadding(10, 10, 10, 10);
                view.setTextSize(18 - matrix.getNrColumns());
                view.setText(matrix.getValueAt(x, y).toString());
                column.addView(view);
            }
            if (x == matrix.getAugmentedColumnIndex()) {
                View v = new View(mContext);

                if (x < width)
                    column.setPadding(10, 0, 0, 0);
                if (x > 0)
                    columns.get(x - 1).setPadding(0, 0, 10, 0);

                v.setPadding(5, 5, 5, 5);
                v.setLayoutParams(new ViewGroup.LayoutParams(3, ViewGroup.LayoutParams.MATCH_PARENT));
                v.setBackgroundColor(Color.BLACK);
                this.addView(v);
            }

            this.addView(column);
            columns.add(column);
        }
    }

    private void updateFields() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                ((TextView) columns.get(x).getChildAt(y))
                        .setText(matrix.getValueAt(x, y).toString());
            }
        }
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
        updateFields();


        for(LinearLayout column : columns) {
            for(int i = 0; i < column.getChildCount(); i++) {
                View v = column.getChildAt(i);
                v.setVisibility(GONE);
                v.setVisibility(VISIBLE);
            }
        }

        setVisibility(GONE);
        setVisibility(VISIBLE);
        invalidate();
    }

    public Matrix getContentMatrix() {
        return matrix;
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