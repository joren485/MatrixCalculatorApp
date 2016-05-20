package com.example.matrix;

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
        return matrix.getHeight() * matrix.getWidth();
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
        int x = position % matrix.getWidth();
        int y = position / matrix.getWidth();

        if (convertView == null) {
            if (position == editing) {
                EditText v = new EditText(mContext);
                v.setRawInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                return v;
            } else {

            }
        } else {
            return convertView;
        }
    }
}
