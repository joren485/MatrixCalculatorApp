package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class EditMatrixAdapter extends BaseAdapter {

    private Context mContext;
    private Matrix matrix;
    private int editing = -1;
    private EditText editText;

    public EditMatrixAdapter(Context c, Matrix m) {
        this.mContext = c;
        this.matrix = m;

        editText = new EditText(c);
    }

    public void updateWidth(int newWidth) {
        updateEditing(-1);
        Matrix m = new Matrix(newWidth, matrix.getNrRows());
        for (int y = 0; y < matrix.getNrRows(); y++) {
            for (int x = 0; x < Math.min(newWidth, matrix.getNrColumns()); x++) {
                m.setValue(x, y, matrix.getValueAt(x, y));
            }
        }
        this.matrix = m;
        notifyDataSetChanged();
    }

    public void updateHeight(int newHeight) {
        updateEditing(-1);
        Matrix m = new Matrix(matrix.getNrColumns(), newHeight);
        for (int y = 0; y < Math.min(newHeight, matrix.getNrRows()); y++) {
            for (int x = 0; x < matrix.getNrColumns(); x++) {
                m.setValue(x, y, matrix.getValueAt(x, y));
            }
        }
        this.matrix = m;
        notifyDataSetChanged();
    }

    public void updateEditing(int newEditing) {
        int x = editing % matrix.getNrColumns();
        int y = editing / matrix.getNrColumns();
        if (x == 0 && y == 0)
            Log.d("editMatrix", "Now it should not work");
        String text = editText.getText().toString();
        Log.d("editMatrix", "Extracted text: " + text);
        if (!text.isEmpty() && editing != -1) {
            Rational r = new Rational(text);
            matrix.setValue(x, y, r);
        }
        editing = newEditing;
        notifyDataSetChanged();
    }

    public void setZeroMatrix() {
        this.matrix = new Matrix(matrix.getNrColumns(), matrix.getNrRows());
        notifyDataSetChanged();
    }

    public void setIdentityMatrix() {
        if (!isSquare())
            return;
        this.matrix = Matrix.identity(matrix.getNrColumns());
        notifyDataSetChanged();
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public boolean isSquare() {
        return matrix.getNrColumns() == matrix.getNrRows();
    }

    @Override
    public int getCount() {
        return matrix.getNrRows() * matrix.getNrColumns();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int x = position % matrix.getNrColumns();
        int y = position / matrix.getNrColumns();
        if (position == editing) {
            if (convertView instanceof EditText)
                editText = (EditText) convertView;
            else
                editText = new EditText(mContext);
            editText.setFocusableInTouchMode(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 110));
            editText.setGravity(Gravity.CENTER);
            editText.setTextColor(Color.BLACK);
            editText.setHintTextColor(Color.DKGRAY);
            editText.setHint(matrix.getValueAt(x, y).toString());

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.i("editMatrix", "Current text: " + s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        updateEditing(-1);
                    }
                    return false;
                }
            });
            return editText;
        } else {
            TextView v;
            if (convertView != null && !(convertView instanceof EditText))
                v = (TextView) convertView;
            else
                v = new TextView(mContext);
            v.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 110));
            v.setText(matrix.getValueAt(x, y).toString());
            v.setGravity(Gravity.CENTER);
            return v;
        }
    }
}
