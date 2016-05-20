package com.example.matrix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Matrix m = new Matrix(5, 5);

        m.fillSpot(2, 1, 2345.3333333333333333333333333333);
        m.fillSpot(1, 1, 4);
        m.fillSpot(0, 2, 7);
        m.fillSpot(1, 0, 3);
        m.fillSpot(0, 0, 5);
        m.fillSpot(2, 2, 2);

        m.fillSpot(3,3,3);

        MatrixView view = new MatrixView(this, m);
        LinearLayout content = (LinearLayout) findViewById(R.id.content);
        content.addView(view);
    }

    public void editMatrix(View v) {
        Intent i = new Intent(this, EditMatrixActivity.class);
        startActivity(i);
    }
}
