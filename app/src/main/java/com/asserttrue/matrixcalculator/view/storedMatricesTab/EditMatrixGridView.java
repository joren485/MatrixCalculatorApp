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
        this.paint.setColor(Color.BLACK);
        this.paint.setStrokeWidth(5);
    }

    public void onDraw(Canvas c) {
        super.onDraw(c);
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
