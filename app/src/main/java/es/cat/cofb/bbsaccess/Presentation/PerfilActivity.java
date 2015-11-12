package es.cat.cofb.bbsaccess.Presentation;

import android.app.ProgressDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.cat.cofb.bbsaccess.AsynTask.ObtenirNom;
import es.cat.cofb.bbsaccess.Listeners.DialogValidarManual2;
import es.cat.cofb.bbsaccess.Listeners.MenuListener;
import es.cat.cofb.bbsaccess.R;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener{

    public ProgressDialog pDialog;
    NavigationView navView;
    DrawerLayout drawerLayout;
    TextView usuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        usuari = (TextView) findViewById(R.id.textView36);
        TextView correu = (TextView) findViewById(R.id.textView37);
        TextView esdDisp = (TextView) findViewById(R.id.textView39);
        TextView votaDisp = (TextView) findViewById(R.id.textView41);
        TextView esdAcc = (TextView) findViewById(R.id.textView43);
        TextView votaFet = (TextView) findViewById(R.id.textView45);
        try {
            correu.setText(ListActivity.api.getUser()+"@cofb.net");
            esdDisp.setText(String.valueOf(ListActivity.api.getEventos().size()));
            votaDisp.setText(String.valueOf(ListActivity.api.getVotaciones().size()));
            esdAcc.setText(String.valueOf(ListActivity.api.getHistorico().size()));
            votaFet.setText(String.valueOf(ListActivity.api.getVotacionesHechas()));
            obtenirNom();
            FrameLayout btnMenu = (FrameLayout) findViewById(R.id.menuBtn);
            btnMenu.setOnClickListener(this);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            navView = (NavigationView)findViewById(R.id.navview);
            navView.setNavigationItemSelectedListener(new MenuListener(drawerLayout, this));
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(),R.string.errorPerfil, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void obtenirNom() {
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setMessage(getString(R.string.obtenirDades));
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);
        pDialog.setProgressNumberFormat(null);
        pDialog.setProgressPercentFormat(null);
        Bundle bundle=getIntent().getExtras();
        ObtenirNom on = new ObtenirNom(this);
        on.execute(ListActivity.api.getUser() + "@cofb.net");
    }

    public void respostaObtenirNom(String response) {
        try {
            JSONObject jsonObj = new JSONObject(response);
            usuari.setText(toProperCase(jsonObj.get("firstName").toString()+" "+jsonObj.get("middleName").toString()+" "+jsonObj.get("lastName").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String toProperCase(String inputString) {
        String ret = "";
        StringBuffer sb = new StringBuffer();
        Matcher match = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(inputString);
        while (match.find()) {
            match.appendReplacement(sb, match.group(1).toUpperCase() + match.group(2).toLowerCase()) ;
        }
        ret = match.appendTail(sb).toString();
        return ret;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuBtn:
                drawerLayout.openDrawer(navView);
                break;
        }
    }
}
