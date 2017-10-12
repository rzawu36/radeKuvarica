package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import model.Recept;

/**
 * Created by rza on 11.10.17..
 */

public class DatabaseUtility extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "recepti";
    private static final String TABELA_RECEPTI = "recepti";

    private static final String ID = "id";
    private static final String NAZIV = "naziv";
    private static final String SADRZAJ = "sadrzaj";

    public DatabaseUtility(Context context) {
        super(context, DATABASE_NAME, null,  DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String script = "CREATE TABLE " + TABELA_RECEPTI +
                "(" + ID + " INTEGER PRIMARY KEY, " +
                NAZIV + " TEXT, "
                + SADRZAJ + " TEXT)";
        sqLiteDatabase.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_RECEPTI);
        onCreate(sqLiteDatabase);
    }

    public void addRecipe(Recept recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAZIV, recipe.getIme());
        values.put(SADRZAJ, recipe.getSadrzaj());
        db.insert(TABELA_RECEPTI, null, values);
        db.close();
    }

    public ArrayList<Recept> getAllRecipes() {
        ArrayList<Recept> recipes = new ArrayList<>();
        String script = "SELECT naziv, sadrzaj from " + TABELA_RECEPTI + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(script, null);

        Recept recept;
        c.moveToFirst();
        do {
            recept = new Recept();
            recept.setIme(c.getString(0));
            recept.setSadrzaj(c.getString(1));
            recipes.add(recept);
            Log.d("Info", recept.toString());
        }
        while (c.moveToNext());

        c.close();
        return recipes;
    }

    public void deleteRecipes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABELA_RECEPTI);
    }

    public Recept getRecipeById(String id) {
        Recept recept = new Recept();
        SQLiteDatabase db = this.getReadableDatabase();
        String script = "SELECT naziv, sadrzaj from " + TABELA_RECEPTI + " where id = " + id + ";";
        Cursor cursor = db.rawQuery(script, null);

        cursor.moveToFirst();
        recept.setIme(cursor.getString(0));
        recept.setSadrzaj(cursor.getString(1));
        cursor.close();
        return recept;

    }
}
