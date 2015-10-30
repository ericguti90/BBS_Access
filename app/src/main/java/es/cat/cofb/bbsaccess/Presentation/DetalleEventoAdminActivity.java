package es.cat.cofb.bbsaccess.Presentation;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.Model.Evento;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.QR.IntentIntegrator;
import es.cat.cofb.bbsaccess.QR.IntentResult;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 28/10/2015.
 */
public class DetalleEventoAdminActivity extends AppCompatActivity implements View.OnClickListener{

    TextView dataHora, lloc, inscripcio, numAss, tituloEvento;
    Button btnQR, btnNFC;
    Resultado api;
    Bundle bundle;
    int id, idUsuari;
    private NfcAdapter mNfcAdapter;
    LinearLayout lyOK, lyKO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        setContentView(R.layout.activity_detalle_evento_admin);
        dataHora = (TextView) findViewById(R.id.textView12);
        lloc = (TextView) findViewById(R.id.textView13);
        inscripcio = (TextView) findViewById(R.id.textView14);
        numAss = (TextView) findViewById(R.id.textView16);
        tituloEvento = (TextView) findViewById(R.id.tituloEvento);
        btnNFC = (Button) findViewById(R.id.buttonValidarNFC);
        btnQR = (Button) findViewById(R.id.buttonValidarQR);
        btnQR.setOnClickListener(this);
        btnNFC.setOnClickListener(this);
        lyOK = (LinearLayout) findViewById(R.id.lytGreenEvento);
        lyKO = (LinearLayout) findViewById(R.id.lytRedEvento);

        //cargamos datos
        bundle=getIntent().getExtras();
        //int pos = bundle.getInt("idEvento");
        //System.out.println(pos);
        //api.getEventoPos(pos);
        api = ListAdminActivity.api;
        //idUsuari = bundle.getInt("idUsuari");
        loadEvento(api.getEventoPos(bundle.getInt("idEvento")));
    }

    private void loadEvento(Evento evento) {
        lyOK.setVisibility(View.GONE);
        lyKO.setVisibility(View.GONE);
        id = evento.getId();
        tituloEvento.setText(evento.getTitol());
        dataHora.setText(evento.getDataHora());
        if(evento.getLloc().equals("")) lloc.setText("Online");
        else lloc.setText(evento.getLloc());
        if(evento.isInscripcio()) inscripcio.setText("Sí");
        else inscripcio.setText("No");
        numAss.setText(String.valueOf(evento.getNumAssAct()) + "/" + String.valueOf(evento.getNumAss()));
        if (mNfcAdapter == null) btnNFC.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonValidarQR:
                IntentIntegrator integrator = new IntentIntegrator(DetalleEventoAdminActivity.this);
                integrator.initiateScan();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            Toast.makeText(getApplicationContext(), scanResult.getContents(), Toast.LENGTH_SHORT).show();
        }
        // else continue with any other code you need in the method
    }
}
