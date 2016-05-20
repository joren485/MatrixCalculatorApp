package com.asserttrue.matrixcalculator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MathTextView extends TextView{
    protected static final String MATH_TYPEFACE =  "fonts/cmr17.ttf";

    public MathTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextAppearance(R.style.math_font);
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/cmr17.ttf"));
    }
}
