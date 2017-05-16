package com.example.unaizrehmani.finalcookbook;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

/**
 * Created by unaizrehmani on 2016-11-24.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ingredients.db";
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_INGREDIENTNAME = "ingredientName";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_INGREDIENTS + "( "
                + COLUMN_ID + ", INTEGER PRIMARY KEY AUTOINCREMENT "
                + COLUMN_INGREDIENTNAME + " TEXT "
                + ");";

        //Creates a new Table.
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_INGREDIENTS);
        onCreate(sqLiteDatabase);
    }

    //Add a new row to the database.
    public void addIngredient(Ingredient ingredient){
        ContentValues values = new ContentValues();
        values.put(COLUMN_INGREDIENTNAME, ingredient.get_IngredientName());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_INGREDIENTS,null,values);
        sqLiteDatabase.close();
    }

    //Delete an Ingredient from the database.
    public void deleteIngredient(String ingredientName){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "
                + TABLE_INGREDIENTS + " WHERE " + COLUMN_INGREDIENTNAME +  "=\""
                + ingredientName + "\";" );

    }

    //Print out the database as a string
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INGREDIENTS + " WHERE 1";

        //Cursor points to location in your results.
        Cursor c  = sqLiteDatabase.rawQuery(query, null);

        //Move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_INGREDIENTNAME))!= null){
                dbString += c.getString(c.getColumnIndex(COLUMN_INGREDIENTNAME));
                dbString += "\n";
            }
        }
        sqLiteDatabase.close();

        return dbString;
    }

}
