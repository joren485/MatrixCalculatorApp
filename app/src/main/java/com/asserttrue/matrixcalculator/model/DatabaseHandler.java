package com.asserttrue.matrixcalculator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TestDatabase";


    private static final String TABLE_NAME = "Saved_Matrices";

    private static final String COLUMN_NAME_MATRIX_NAME = "name";
    private static final String COLUMN_NAME_MATRIX = "matrix";

    private static final String COLUMN_NAME_NROFCOLUMNS = "columns";

    // TODO Can be removed
    private static final String COLUMN_NAME_NROFROWS = "ROWS";

    private static final String COLUMN_NAME_AUGMENTEDLINE = "augmented_line";


    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(
                "CREATE TABLE " + TABLE_NAME
                        + "("
                        + COLUMN_NAME_MATRIX_NAME + " TEXT PRIMARY KEY NOT NULL,"
                        + COLUMN_NAME_MATRIX + " TEXT NOT NULL,"
                        + COLUMN_NAME_NROFCOLUMNS + " INTEGER NOT NULL,"
                        + COLUMN_NAME_NROFROWS + " INTEGER NOT NULL,"
                        + COLUMN_NAME_AUGMENTEDLINE + " INTEGER NOT NULL"
                        + ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // CRUD METHODS

    public void createMatrix(Matrix m, String name){

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_MATRIX_NAME, getUniqueName(name));
        values.put(COLUMN_NAME_MATRIX, flattenMatrix(m));
        values.put(COLUMN_NAME_NROFCOLUMNS, m.getNrColumns());
        values.put(COLUMN_NAME_NROFROWS, m.getNrRows());
        values.put(COLUMN_NAME_AUGMENTEDLINE, m.getAugmentedColumnIndex());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public Matrix readMatrixbyName(String name){

        SQLiteDatabase db = getReadableDatabase();
        Matrix m;

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[] {COLUMN_NAME_MATRIX, COLUMN_NAME_NROFCOLUMNS, COLUMN_NAME_NROFROWS, COLUMN_NAME_AUGMENTEDLINE},
                COLUMN_NAME_MATRIX_NAME + "=?",
                new String[] {String.valueOf(name)},
                null,
                null,
                null);

        db.close();
        if (cursor.moveToFirst()){

            m = buildMatrix(name, cursor.getString(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));

            cursor.close();
        } else{
            m = null;
        }


        return m;
    }


    public void updateMatrix(Matrix m, String name) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_MATRIX_NAME, name);
        values.put(COLUMN_NAME_MATRIX, flattenMatrix(m));
        values.put(COLUMN_NAME_NROFCOLUMNS, m.getNrColumns());
        values.put(COLUMN_NAME_NROFROWS, m.getNrRows());
        values.put(COLUMN_NAME_AUGMENTEDLINE, m.getAugmentedColumnIndex());

        db.update(TABLE_NAME, values, COLUMN_NAME_MATRIX_NAME + "=?", new String[]{name});

        db.close();
    }

    public void deleteMatrix(String name){

        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_NAME,
                COLUMN_NAME_MATRIX_NAME + "=?",
                new String[] {name});

        db.close();
    }

    public Matrix[] getAllMatrices(){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[] {COLUMN_NAME_MATRIX_NAME, COLUMN_NAME_MATRIX, COLUMN_NAME_NROFCOLUMNS, COLUMN_NAME_NROFROWS, COLUMN_NAME_AUGMENTEDLINE},
                null,
                null,
                null,
                null,
                null);


        Matrix[] matrices = new Matrix[cursor.getCount()];

        cursor.moveToFirst();
        for (int i = 0; i < matrices.length; i++){

            matrices[i] = buildMatrix(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return matrices;
    }

    public boolean isUniqueName(String name){

        if (name.isEmpty()) {
            return false;
        }

        SQLiteDatabase db = getReadableDatabase();

         long nameMatches = DatabaseUtils.queryNumEntries(
                 db,
                 TABLE_NAME,
                 COLUMN_NAME_MATRIX_NAME + "=?",
                 new String[] {String.valueOf(name)}
        );


        db.close();

        return nameMatches == 0;

    }


    // TODO May be better to move to matrix class
    // TODO Encryption?
    private static String flattenMatrix(Matrix m){

        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < m.getNrRows(); y++){
            for (int x = 0; x < m.getNrColumns(); x++){
                sb.append(String.format("%s/%s ", String.valueOf(m.getValueAt(x, y).getNumerator()),
                        String.valueOf(m.getValueAt(x, y).getDenominator())));
            }
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static Matrix buildMatrix(String name, String s, int nrofcolumns, int nrofrows, int augmentedLine){
        Rational[][] matrix2d = new Rational[nrofrows][nrofcolumns];

        String[] stringRationals = s.split(" ");

        for(int row = 0; row < nrofrows; row++) {
            for(int column = 0; column < nrofcolumns; column++) {
                String[] rationalParts = stringRationals[row * nrofcolumns + column].split("/");

                matrix2d[row][column] = new Rational(Long.parseLong(rationalParts[0]),
                        Long.parseLong(rationalParts[1]));
            }
        }

        return new Matrix(matrix2d, augmentedLine, name);
    }

    private String getUniqueName(String name) {
        String uniqueName = name;
        for (int i = 1; !isUniqueName(uniqueName); i++) {
            uniqueName = name + i;
        }
        return uniqueName;
    }
}
