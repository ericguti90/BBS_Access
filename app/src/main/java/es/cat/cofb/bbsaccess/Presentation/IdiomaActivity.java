package es.cat.cofb.bbsaccess.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Locale;

import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.R;

public class IdiomaActivity extends AppCompatActivity implements View.OnClickListener {

    String idioma = "ca";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idioma);
        Button idioma = (Button) findViewById(R.id.buttonIdioma);
        RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton);
        RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
        idioma.setOnClickListener(this);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonIdioma:
                changeLang();
                Intent intent;
                if(Resultado.getIdUser() > 0 || Resultado.getIdUser() == -2) intent = new Intent(getApplicationContext(), ListActivity.class);
                else intent = new Intent(getApplicationContext(), ListAdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.radioButton:
                idioma = "ca";
                break;
            case R.id.radioButton2:
                idioma = "es";
                break;
        }
    }

    private void changeLang() {
        if (idioma.equalsIgnoreCase("")) return;
        Locale myLocale = new Locale(idioma);
        saveLocale();
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //updateTexts();
    }

    public void saveLocale()
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, idioma);
        editor.apply();
    }
}
