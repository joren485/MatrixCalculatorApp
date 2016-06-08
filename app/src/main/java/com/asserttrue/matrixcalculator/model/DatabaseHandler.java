package com.asserttrue.matrixcalculator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    // Names of the database
    private static final String DATABASE_NAME = "MatrixDatabase";
    private static final String TABLE_NAME = "Saved_Matrices";

    // The names of the columns
    private static final String COLUMN_MATRIX_NAME = "name";
    private static final String COLUMN_MATRIX = "matrix";
    private static final String COLUMN_NRCOLUMNS = "columns";
    private static final String COLUMN_NRROWS = "rows";
    private static final String COLUMN_AUGMENTEDLINE = "augmented_line";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created.
     * @param db A writable SQLite object.
     */
    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(
                "CREATE TABLE " + TABLE_NAME
                        + "("
                        + COLUMN_MATRIX_NAME + " TEXT PRIMARY KEY NOT NULL,"
                        + COLUMN_MATRIX + " TEXT NOT NULL,"
                        + COLUMN_NRCOLUMNS + " INTEGER NOT NULL,"
                        + COLUMN_NRROWS + " INTEGER NOT NULL,"
                        + COLUMN_AUGMENTEDLINE + " INTEGER NOT NULL"
                        + ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // The necessary CRUD methods (Create Read Update Delete)

    public void createMatrix(Matrix m, String name){

        ContentValues values = new ContentValues();

        values.put(COLUMN_MATRIX_NAME, getUniqueName(name));
        values.put(COLUMN_MATRIX, m.toString());
        values.put(COLUMN_NRCOLUMNS, m.getNrColumns());
        values.put(COLUMN_NRROWS, m.getNrRows());
        values.put(COLUMN_AUGMENTEDLINE, m.getAugmentedColumnIndex());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public Matrix[] readAllMatrices(){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_MATRIX_NAME + " ASC");


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


    public void updateMatrix(Matrix m, String name) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_MATRIX_NAME, name);
        values.put(COLUMN_MATRIX, m.toString());
        values.put(COLUMN_NRCOLUMNS, m.getNrColumns());
        values.put(COLUMN_NRROWS, m.getNrRows());
        values.put(COLUMN_AUGMENTEDLINE, m.getAugmentedColumnIndex());

        db.update(TABLE_NAME, values, COLUMN_MATRIX_NAME + "=?", new String[]{name});

        db.close();
    }

    public void deleteMatrix(String name){

        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_NAME,
                COLUMN_MATRIX_NAME + "=?",
                new String[] {name});

        db.close();
    }

    /**
     * @param name A string that may or may not be unique.
     * @return Whether name is a unique string in the databseor not.
     */
    public boolean isUniqueName(String name){

        if (name.trim().isEmpty()) {
            return false;
        }

        SQLiteDatabase db = getReadableDatabase();

         long nameMatches = DatabaseUtils.queryNumEntries(
                 db,
                 TABLE_NAME,
                 COLUMN_MATRIX_NAME + "=?",
                 new String[] {String.valueOf(name)}
        );


        db.close();

        return nameMatches == 0;

    }

    /**
     * Get a name that is unique in the database.
     * @param name The name that needs to be unique.
     * @return A name that is unique.
     */
    private String getUniqueName(String name) {
        String uniqueName = name;

        for (int i = 1; !isUniqueName(uniqueName); i++) {
            uniqueName = name + i;
        }

        return uniqueName;
    }

    /**
     * Build a Matrix from a row in the database.
     * @param name The name from the database.
     * @param matrixString The string representing the matrix.
     * @param nrColumns The number of columns
     * @param nrRows The number of rows
     * @param augmentedLine The index of the separator line in an augmented matrix.
     * @return A matrix object with the
     */
    private static Matrix buildMatrix(String name, String matrixString, int nrColumns, int nrRows, int augmentedLine){
        Rational[][] matrix2d = new Rational[nrRows][nrColumns];

        String[] stringRationals = matrixString.split(" ");

        for(int row = 0; row < nrRows; row++) {
            for(int column = 0; column < nrColumns; column++) {
                String[] rationalParts = stringRationals[row * nrColumns + column].split("/");

                matrix2d[row][column] = new Rational(Long.parseLong(rationalParts[0]),
                        Long.parseLong(rationalParts[1]));
            }
        }

        return new Matrix(matrix2d, augmentedLine, name);
    }

}
