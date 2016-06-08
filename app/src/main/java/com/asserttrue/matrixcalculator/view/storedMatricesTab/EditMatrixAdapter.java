package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

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

    public void updateNrOfColumns(int nrOfColumns) {
        setNotEditing();
        matrix = new Matrix(matrix, nrOfColumns, matrix.getNrRows());
        notifyDataSetChanged();
    }


    public void updateNrOfRows(int nrOfRows) {
        setNotEditing();
        matrix = new Matrix(matrix, matrix.getNrColumns(), nrOfRows);
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

            if (convertView instanceof EditText){
                editText = (EditText) convertView;
            }

            else{
                editText = new EditText(mContext);
            }
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

                }

                @Override
                public void afterTextChanged(Editable s) {

                    String text = s.toString();

                    if (!text.isEmpty() && !text.equals(".") && !text.equals("-")) {

                        try{
                            matrix.setValue(x, y, new Rational(text));
                        }
                        catch (NumberFormatException e){
                            Toast.makeText(mContext, "Number too long.", Toast.LENGTH_SHORT).show();
                            s.clear();
                            matrix.setValue(x, y, new Rational(0));
                        }
                    }
                }
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