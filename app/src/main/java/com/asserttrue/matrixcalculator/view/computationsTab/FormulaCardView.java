package com.asserttrue.matrixcalculator.view.computationsTab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;

public class FormulaCardView extends CardView {

    public FormulaCardView(Context context, AttributeSet attrs) {
        super(context);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs,
                R.styleable.ComputationCard, 0, 0);

        String descriptionText = styledAttrs.getString(R.styleable.ComputationCard_computation_name);
        int imageResource = styledAttrs.getResourceId(R.styleable.ComputationCard_android_src, R.drawable.determinant_image);

        styledAttrs.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_formula, this, true);

        TextView computationText = (TextView) findViewById(R.id.computation_text);
        computationText.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/cmr17.ttf"));
        computationText.setText(descriptionText);

        ImageView computationImage = (ImageView) findViewById(R.id.computation_image);
        computationImage.setImageResource(imageResource);
    }
}
