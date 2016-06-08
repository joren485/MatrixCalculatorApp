package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class EditMatrixAdapter extends BaseAdapter {

    private Context mContext;
    private Matrix matrix;
    private int editingPosition = -1;
    private EditText editText;

    public EditMatrixAdapter(Context c, Matrix m) {
        this.mContext = c;
        this.matrix = m;

        editText = new EditText(c);
    }

    // TODO maybe this behaviour could be a feature of the matrix class, not only the editor.
    public void updateNrOfColumns(int nrofColumns) {
        setNotEditing();
        Matrix m = new Matrix(nrofColumns, matrix.getNrRows());
        for (int y = 0; y < matrix.getNrRows(); y++) {
            for (int x = 0; x < Math.min(nrofColumns, matrix.getNrColumns()); x++) {
                m.setValue(x, y, matrix.getValueAt(x, y));
            }
        }
        m.setAugmentedColumnIndex(matrix.getAugmentedColumnIndex());
        matrix = m;
        notifyDataSetChanged();
    }


    public void updateNrOfRows(int nrofRows) {
        setNotEditing();
        Matrix m = new Matrix(matrix.getNrColumns(), nrofRows);
        for (int y = 0; y < Math.min(nrofRows, matrix.getNrRows()); y++) {
            for (int x = 0; x < matrix.getNrColumns(); x++) {
                m.setValue(x, y, matrix.getValueAt(x, y));
            }
        }
        m.setAugmentedColumnIndex(matrix.getAugmentedColumnIndex());
        matrix = m;
        notifyDataSetChanged();
    }

    private void setNotEditing(){
        setEditingPosition(-1);
    }

    public void setEditingPosition(int newPosition){
        editingPosition = newPosition;
        notifyDataSetChanged();
    }

    public void updateAugmentedIndex(int newIndex) {
        if (newIndex == 0 || newIndex >= matrix.getNrColumns())
            matrix.setAugmentedColumnIndex(-1);
        else
            matrix.setAugmentedColumnIndex(newIndex);
        notifyDataSetChanged();
    }

    public void setZeroMatrix() {
        int augmIndex = matrix.getAugmentedColumnIndex();
        matrix = new Matrix(matrix.getNrColumns(), matrix.getNrRows());
        matrix.setAugmentedColumnIndex(augmIndex);
        notifyDataSetChanged();
    }

    public void setIdentityMatrix() {
        if (!isSquare())
            return;
        int augmIndex = matrix.getAugmentedColumnIndex();
        matrix = Matrix.identity(matrix.getNrColumns());
        matrix.setAugmentedColumnIndex(augmIndex);
        notifyDataSetChanged();
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public boolean isSquare() {
        return matrix.isSquareMatrix();
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
        final int x = position % matrix.getNrColumns();
        final int y = position / matrix.getNrColumns();
        if (position == editingPosition) {

            if (convertView instanceof EditText)
                editText = (EditText) convertView;
            else
                editText = new EditText(mContext);

            editText.setFocusableInTouchMode(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 120));
            editText.setGravity(Gravity.CENTER);
            editText.setTextColor(Color.BLACK);
            editText.setHintTextColor(Color.DKGRAY);
            editText.setHint(matrix.getValueAt(x, y).toString());

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String text = s.toString();

                    if (!text.isEmpty() && !text.equals(".") && !text.equals("-")) {
                        matrix.setValue(x, y, new Rational(text));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });


            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        setNotEditing();
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
            v.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 120));
            v.setText(matrix.getValueAt(x, y).toString());
            v.setGravity(Gravity.CENTER);
            return v;
        }
    }
}
