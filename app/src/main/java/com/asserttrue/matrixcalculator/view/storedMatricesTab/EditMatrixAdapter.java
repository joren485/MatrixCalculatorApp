package com.asserttrue.matrixcalculator.view.storedMatricesTab;

import com.asserttrue.matrixcalculator.model.Matrix;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

public class EditMatrixAdapter extends BaseAdapter {

    private Context mContext;
    private Matrix matrix;
    private int editing = 0;

    public EditMatrixAdapter(Context c, Matrix m) {
        this.mContext = c;
        this.matrix = m;
    }

    public void updateWidth(int newWidth) {

    }

    public void updateHeight(int newHeight) {

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

        if (convertView == null) {
            if (position == editing) {
                EditText v = new EditText(mContext);
                v.setRawInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                return v;
            }
            else {
                //TODO
                return null;
            }
        } else {
            return convertView;
        }
    }
}
