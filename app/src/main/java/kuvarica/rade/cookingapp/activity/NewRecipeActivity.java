package kuvarica.rade.cookingapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import db.DatabaseUtility;
import kuvarica.rade.cookingapp.R;
import model.Recept;

public class NewRecipeActivity extends AppCompatActivity {
    private EditText etNazivRecepta;
    private EditText etSadrzajRecepta;
    private Button btnDodajRecept;
    /*
    ovo ti je activity u kom dodajes novi recept u bazu
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        etNazivRecepta = (EditText) findViewById(R.id.et_recept_ime);
        etSadrzajRecepta = (EditText) findViewById(R.id.et_sadrzaj_recepta);
        btnDodajRecept = (Button) findViewById(R.id.btn_dodaj_recept);

        btnDodajRecept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                u ovom listeneru se poziva metoda addrecipe kojoj se prosledjuje recept koji se upisuje u bazu
                 */
                String ime = etNazivRecepta.getText().toString();
                String sadrzaj = etSadrzajRecepta.getText().toString();

                if (ime.equals("") || sadrzaj.equals("")) {
                    Toast.makeText(NewRecipeActivity.this, "Ispunite Sva Potrebna Polja!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Recept r = new Recept();
                    r.setIme(ime);
                    r.setSadrzaj(sadrzaj);
                    MainActivity.db.addRecipe(r);
                    etNazivRecepta.setText("");
                    etSadrzajRecepta.setText("");
                    etNazivRecepta.requestFocus();
                    Log.d("Info", "new recipe added " + r.toString());
                }
            }
        });
    }
}
