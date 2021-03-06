package kuvarica.rade.cookingapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kuvarica.rade.cookingapp.R;
import model.Recept;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView tvImeRecepta;
    private TextView tvSadrzajRecepta;
    private FloatingActionButton fabSms;
    private FloatingActionButton fabEmail;
    private Intent intent;
    /*
    ovde ces videti 'detalje' za odabrani recept
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        intent = getIntent();
        String id = intent.getStringExtra("id");

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
                /*
                ovde ti je logika za slanje sms-a
                kad te pita gde ti je to za sms, pokazes mu ovo
                 */
                final Dialog d = new Dialog(RecipeDetailsActivity.this);
                d.setContentView(R.layout.dialog_sms);
                Button btnSendSms = d.findViewById(R.id.btn_sms_send);
                final EditText etSmsNumber = d.findViewById(R.id.et_sms_send_number);
                d.show();

                btnSendSms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String smsContent = tvImeRecepta.getText().toString() + " Instructions: \n" + tvSadrzajRecepta.getText().toString();
                        SmsManager sms = SmsManager.getDefault();
                        String number = etSmsNumber.getText().toString();

                        if (!number.equals("+381")) {
                            sms.sendTextMessage(number, null, smsContent, null, null);
                            d.cancel();
                        }
                        Toast.makeText(RecipeDetailsActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        fabEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                ovde ti je logika za slanje emaila
                kad te pita gde ti je to za email, pokazes mu ovo
                 */
                final Dialog d = new Dialog(RecipeDetailsActivity.this);
                d.setContentView(R.layout.dialog_email);
                Button btnSendEmail = d.findViewById(R.id.btn_email_send);
                final EditText etEmail = d.findViewById(R.id.et_email_send);
                d.show();

                btnSendEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = etEmail.getText().toString();

                        if (!email.equals("")) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null)); //intent koji se korist za slanje mejla
                            emailIntent.putExtra(Intent.EXTRA_TEXT, tvSadrzajRecepta.getText().toString()); //body tog maila
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, tvImeRecepta.getText().toString()); //subject maila
                            startActivity(Intent.createChooser(emailIntent, ""));
                            d.cancel();
                        }
                        else {
                            Toast.makeText(RecipeDetailsActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });
    }
}
