package com.example.matrix;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by gijs on 11-5-16.
 */
public class MatrixAdapter extends BaseAdapter {

    private Context mContext;
    private double[][] matrix;
    int width = 0;
    int height;

    public MatrixAdapter(Context c, double[][] matrix) {
        this.mContext = c;
        this.matrix = matrix;
        this.height = matrix.length;
        if (height > 0) {
            width = matrix[0].length;
        }

    }


    @Override
    public int getCount() {
        return width * height;
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
        TextView view;
        if (convertView == null) {
            view = new TextView(mContext);
        } else {
            view = (TextView) convertView;
        }
        double d = matrix[position / width][position % width];
        int i = (int) d;
        view.setText(d > i ? d + "" : i + "");
        view.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
        //view.setBackgroundColor(0xffff0000);
        return view;
    }
}
