package com.asserttrue.matrixcalculator.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.asserttrue.matrixcalculator.R;

public class MathTextButton extends Button {

    protected static final String MATH_TYPEFACE =  "fonts/cmr17.ttf";

    public MathTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextAppearance(R.style.math_font);
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), MATH_TYPEFACE));
    }
}
