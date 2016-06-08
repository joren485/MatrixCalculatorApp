package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.GridView;

public class EditMatrixGridView extends GridView {

    private final Paint paint = new Paint();

    public EditMatrixGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
    }

    public void onDraw(Canvas c) {
        super.onDraw(c);

        int width = getWidth();
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            height += getChildAt(i).getHeight();
        }

        height = Math.min(getHeight(), height / getNumColumns());

        // LEFT BRACKET
        c.drawLine(1, 1, 19, 1, paint);
        c.drawLine(1, 1, 1, height - 2, paint);
        c.drawLine(1, height - 2, 19, height - 2, paint);

        // RIGHT BRACKET
        c.drawLine(width - 20, 1, width - 2, 1, paint);
        c.drawLine(width - 2, 1, width - 2, height - 2, paint);
        c.drawLine(width - 2, height - 2, width - 20, height - 2, paint);

        // AUGMENTED LINE
        int augmentedIndex = ((EditMatrixAdapter) getAdapter()).getMatrix().getAugmentedColumnIndex();
        int x = augmentedIndex * (getWidth() / getNumColumns());

        if (augmentedIndex > 0 && augmentedIndex < getNumColumns()) {
            paint.setStrokeWidth(2);
            c.drawLine(x, 1, x, height - 2, paint);
            paint.setStrokeWidth(5);
        }
    }
}
