package com.asserttrue.matrixcalculator.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;

public class MathTitleTextView  extends TextView{
    private static final String MATH_TYPEFACE =  "fonts/cmr17.ttf";

    public MathTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextAppearance(R.style.math_font_large);
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), MATH_TYPEFACE));
    }
}
