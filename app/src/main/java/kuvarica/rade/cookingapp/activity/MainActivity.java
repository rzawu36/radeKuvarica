package kuvarica.rade.cookingapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import db.DatabaseUtility;
import kuvarica.rade.cookingapp.R;
import model.Recept;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnAdd;
    static DatabaseUtility db;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv_content);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewRecipeActivity.class);
                startActivity(i);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO logika za novi intent - detalje
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.menu_erase) {
            db.deleteRecipes();
            Toast.makeText(MainActivity.this, "Svi Recepti Su Obrisani!", Toast.LENGTH_SHORT).show();
            adapter = null;
            makeAdapter();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseUtility(MainActivity.this);
        populateListView();

    }

    public void populateListView() {
        //TODO popraviti
        makeAdapter();
    }

    public void makeAdapter() {
        ArrayList<Recept> recepti = db.getAllRecipes();
        Log.d("Recepti", recepti.toString());
        ArrayList<String> nazivi = new ArrayList<>();
        for (Recept r: recepti) {
            String recept = r.getIme();
            nazivi.add(recept);
        }
        Log.d("nazivi", nazivi.toString());
        adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, nazivi);
        listView.setAdapter(adapter);
    }
}
