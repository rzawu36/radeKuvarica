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
    /*
    ovo ti je klasa koja obavlja sve operacije vezane za bazu
    u nastavku cu ti napisati sta koja metoda radi a ti posle obrisi
     */

    private static final int DATABASE_VERSION = 9; //ovo ti je samo verzija baze (nebitno)
    private static final String DATABASE_NAME = "recepti"; //ime baze (nebitno)
    private static final String TABELA_RECEPTI = "recepti"; //ime jedine tabele koju imas u bazi

    private static final String ID = "id"; //obelezje id u tabeli 'recepti' koje ti sluzi kao primarni kljuc
    private static final String NAZIV = "naziv"; //obelezje naziv za recept
    private static final String SADRZAJ = "sadrzaj"; //obelezje sadrzaj za recept

    public DatabaseUtility(Context context) {
        super(context, DATABASE_NAME, null,  DATABASE_VERSION); //konstruktor za 'kreiranje baze' takoreci
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //ova metoda se izvrsava kad se baza pravi, u ovoj metodi se kreira tabela pod nazivom 'recepti' sa svojim obelezjima
        String script = "CREATE TABLE " + TABELA_RECEPTI +
                "(" + ID + " INTEGER PRIMARY KEY, " +
                NAZIV + " TEXT, "
                + SADRZAJ + " TEXT)";
        sqLiteDatabase.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //ovo nisam siguran sta radi, ali po nazivu mozes da pretpostavis
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_RECEPTI);
        onCreate(sqLiteDatabase);
    }

    public void addRecipe(Recept recipe) { //ovo ti je metoda za dodavanje recepta u bazu, znaci kad te pita, ajde pokazi mi gde dodajes recept u bazu, pokazes mu ovo
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAZIV, recipe.getIme());
        values.put(SADRZAJ, recipe.getSadrzaj());
        db.insert(TABELA_RECEPTI, null, values);
        db.close();
    }

    public ArrayList<Recept> getAllRecipes() { //ova metoda ti vraca sve recepte iz baze
        ArrayList<Recept> recipes = new ArrayList<>();
        String script = "SELECT naziv, sadrzaj from " + TABELA_RECEPTI + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(script, null);

        Recept recept = null;
        if (c.moveToFirst())
        {
            do {
                recept = new Recept();
                recept.setIme(c.getString(0));
                recept.setSadrzaj(c.getString(1));
                recipes.add(recept);
                Log.d("Info", recept.toString());
            }
            while (c.moveToNext());

        }
        return recipes;

    }

    public void deleteRecipes() { //ova metoda BRISE sve recepte iz baze
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABELA_RECEPTI);
    }

    public Recept getRecipeById(String id) { //ova vraca jedan recept za prosledjeni id
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
