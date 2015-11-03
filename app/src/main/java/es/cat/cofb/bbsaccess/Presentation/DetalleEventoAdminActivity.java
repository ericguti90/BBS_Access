package es.cat.cofb.bbsaccess.Presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import es.cat.cofb.bbsaccess.AsynTask.ObtenirCorreu;
import es.cat.cofb.bbsaccess.AsynTask.ValidarAsistencia;
import es.cat.cofb.bbsaccess.Listeners.DialogValidarManual;
import es.cat.cofb.bbsaccess.Listeners.DialogValidarManual2;
import es.cat.cofb.bbsaccess.Model.Evento;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.QR.IntentIntegrator;
import es.cat.cofb.bbsaccess.QR.IntentResult;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 28/10/2015.
 */
public class DetalleEventoAdminActivity extends FragmentActivity implements View.OnClickListener, DialogValidarManual.DialogListener, DialogValidarManual2.DialogListener2{

    TextView dataHora, lloc, inscripcio, numAss, tituloEvento, textKO, textOK;
    Button btnQR, btnNFC, btnMANUAL;
    Resultado api;
    Bundle bundle;
    int id;
    String numAssTotal;
    private NfcAdapter mNfcAdapter;
    LinearLayout lyOK, lyKO;
    public ProgressDialog pDialog;
    ValidarAsistencia va;
    ObtenirCorreu oc;

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
        textKO = (TextView) findViewById(R.id.textKO);
        textOK = (TextView) findViewById(R.id.textOK);
        btnNFC = (Button) findViewById(R.id.buttonValidarNFC);
        btnQR = (Button) findViewById(R.id.buttonValidarQR);
        btnMANUAL = (Button) findViewById(R.id.buttonValidarManual);
        btnQR.setOnClickListener(this);
        btnNFC.setOnClickListener(this);
        btnMANUAL.setOnClickListener(this);
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
        numAssTotal = String.valueOf(evento.getNumAss());
        numAss.setText(String.valueOf(evento.getNumAssAct()) + "/" + numAssTotal);
        if (mNfcAdapter == null) btnNFC.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        lyOK.setVisibility(View.GONE);
        lyKO.setVisibility(View.GONE);
        switch (v.getId()) {
            case R.id.buttonValidarQR:
                IntentIntegrator integrator = new IntentIntegrator(DetalleEventoAdminActivity.this);
                integrator.initiateScan();
                break;
            case R.id.buttonValidarNFC:
                break;
            case R.id.buttonValidarManual:
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogValidarManual dialogo = new DialogValidarManual();
                dialogo.show(fragmentManager, "tagAlerta");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //System.out.println("requestCode: " + requestCode);
        if(requestCode == 49374) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanResult.getContents() != null) {
                // handle scan result
                //Toast.makeText(getApplicationContext(), scanResult.getContents(), Toast.LENGTH_SHORT).show();
                if(id == Integer.valueOf(scanResult.getContents().toString().split(";")[0])) {
                    validarAcces(scanResult.getContents().toString().split(";")[0],scanResult.getContents().toString().split(";")[1]);
                }
                else Toast.makeText(getApplicationContext(), "L'entrada no pertany a aquest esdeveniment", Toast.LENGTH_SHORT).show();
            }
        }// else continue with any other code you need in the method
        else {

        }


    }

    private void validarAcces(String idV, String correu) {
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setMessage("Validant accès...");
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);
        pDialog.setProgressNumberFormat(null);
        pDialog.setProgressPercentFormat(null);
        va = new ValidarAsistencia(this);
        va.execute(idV, correu);

    }

    public void respostaPOST(Integer resp) {
        if(resp == -1) {
            lyOK.setVisibility(View.GONE);
            lyKO.setVisibility(View.VISIBLE);
            textKO.setText("L'assistent no s'ha apuntat a l'esdeveniment");
            //Toast.makeText(getApplicationContext(), "L'assistent no s'ha apuntat a l'esdeveniment", Toast.LENGTH_SHORT).show();
        }
        else if(resp == 0){
            lyOK.setVisibility(View.GONE);
            lyKO.setVisibility(View.VISIBLE);
            textKO.setText("L'assistent ja ha accedit a l'esdeveniment");
            //Toast.makeText(getApplicationContext(), "L'assistent ja ha accedit", Toast.LENGTH_SHORT).show();
        }
        else {
            numAss.setText(resp + "/" + numAssTotal);
            lyOK.setVisibility(View.VISIBLE);
            lyKO.setVisibility(View.GONE);
            textOK.setText("pot accedir a l'esdeveniment");
        }
    }

    public void onDialogValidar(String nif, String numF){
        //Toast.makeText(getApplicationContext(),"validar", Toast.LENGTH_SHORT).show();
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setMessage("Enviant dades...");
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);
        pDialog.setProgressNumberFormat(null);
        pDialog.setProgressPercentFormat(null);
        oc = new ObtenirCorreu(this);
        oc.execute(nif, numF);
    }

    public void respostaObtenirCorreu(String dades) {
        try {
            JSONObject jsonObj = new JSONObject(dades);
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogValidarManual2 dialogo = new DialogValidarManual2();
            Bundle bundle = new Bundle();
            bundle.putString("firstName",jsonObj.get("firstName").toString());
            bundle.putString("middleName",jsonObj.get("middleName").toString());
            bundle.putString("lastName",jsonObj.get("lastName").toString());
            bundle.putString("emailAddress",jsonObj.get("emailAddress").toString());
            dialogo.setArguments(bundle);
            dialogo.show(fragmentManager, "tagAlerta");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogValidar2(String correu) {
        validarAcces(String.valueOf(id),correu);
    }
}
