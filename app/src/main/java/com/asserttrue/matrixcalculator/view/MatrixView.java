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

import java.util.ArrayList;

public class MatrixView extends LinearLayout {

    private final Paint paint = new Paint();

    private Matrix matrix;

    private int width, height;

    private Context mContext;

    private ArrayList<LinearLayout> columns;

    private boolean inDotMode;
    private boolean canBeDotMode;

    private String[][] dotViewStrings = {{"", "", "\u22ef"}, {"", "", "\u22ef"}, {"\u22ee", "\u22ee", "\u22f1"}};

    public MatrixView (Context c, Matrix matrix, boolean dotModePossible) {
        super(c);

        mContext = c;
        this.matrix = matrix;
        width = matrix.getNrColumns();
        height = matrix.getNrRows();
        setPadding(20, 0, 20, 0);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        columns = new ArrayList<>(width);

        canBeDotMode = dotModePossible;
        inDotMode = width > 4 && canBeDotMode;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchDotMode();
            }
        });

        init();
    }

    public MatrixView (Context c, Matrix matrix) {
        this(c, matrix, false);
    }

    private void init() {
        removeAllViews();
        for (int x = 0; x < (inDotMode ? Math.min(3, width) : width); x++) {
            LinearLayout column = new LinearLayout(mContext);
            column.setOrientation(LinearLayout.VERTICAL);
            for (int y = 0; y < (inDotMode ? Math.min(3, height) : height); y++) {
                TextView view = new TextView(mContext);
                view.setGravity(Gravity.CENTER);
                view.setPadding(10, 10, 10, 10);
                if (inDotMode && (x == 2 || y == 2))
                    view.setText(dotViewStrings[y][x]);
                else
                    view.setText(matrix.getValueAt(x, y).toString());
                if (!inDotMode)
                    view.setTextSize(18 - matrix.getNrColumns());
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

    private void switchDotMode() {
        if (!canBeDotMode)
            return;
        inDotMode = !inDotMode;
        init();
        invalidate();
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
        invalidate();
    }

    public Matrix getContentMatrix() {
        return matrix;
    }

    public boolean isInDotMode(){
        return inDotMode;
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