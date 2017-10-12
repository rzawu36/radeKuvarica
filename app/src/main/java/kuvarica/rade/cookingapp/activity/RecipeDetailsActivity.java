package kuvarica.rade.cookingapp.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import kuvarica.rade.cookingapp.R;
import model.Recept;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView tvImeRecepta;
    private TextView tvSadrzajRecepta;
    private FloatingActionButton fabSms;
    private FloatingActionButton fabEmail;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        intent = getIntent();
        String id = intent.getStringExtra("id");
        Log.d("ID", id);

        Recept recept = MainActivity.db.getRecipeById(id);
        tvImeRecepta = (TextView) findViewById(R.id.tv_recept_ime_details);
        tvSadrzajRecepta = (TextView) findViewById(R.id.tv_recept_sadrzaj_details);
        fabSms = (FloatingActionButton) findViewById(R.id.fab_sms);
        fabEmail = (FloatingActionButton) findViewById(R.id.fab_email);


        tvImeRecepta.setText(recept.getIme());
        tvSadrzajRecepta.setText(recept.getSadrzaj());

        fabSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO intent za sms
            }
        });


        fabEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO intent za email
            }
        });
    }
}
