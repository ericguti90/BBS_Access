package es.cat.cofb.bbsaccess.Presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.AsynTask.DadesVotacio;
import es.cat.cofb.bbsaccess.Model.Pregunta;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.Model.Votacion;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 30/09/2015.
 */
public class DetallePreguntaActivity extends AppCompatActivity implements View.OnClickListener {
    TextView contador, pregunta, titulo, resOb;
    EditText resposta;
    Button btn;
    ViewGroup vg;
    Resultado api;
    int idV, numP, idUsuari;
    public Votacion v;
    String respostaRB = "";
    public ProgressDialog pDialog;
    DadesVotacio dv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pregunta);
        contador = (TextView)findViewById(R.id.textView27);
        pregunta = (TextView)findViewById(R.id.textView26);
        titulo = (TextView)findViewById(R.id.textView28);
        resposta = (EditText)findViewById(R.id.editText3);
        btn = (Button)findViewById(R.id.buttonResposta);
        vg = (ViewGroup)findViewById(R.id.radioGroup);
        resOb = (TextView)findViewById(R.id.RObligatoria);
        btn.setOnClickListener(this);
        api = ListActivity.api;
        //cargamos datos
        Bundle bundle=getIntent().getExtras();
        idV = bundle.getInt("idVotacion");
        numP = bundle.getInt("numPreg");
        idUsuari = bundle.getInt("idUsuari");
        System.out.println("usuariP:"+idUsuari);
        v = api.getVotacionPos(idV);
        loadPregunta();
    }

    private void loadPregunta() {
        resOb.setVisibility(View.GONE);
        titulo.setText(v.getTitol());
        int total = v.getPreguntes().size();
        contador.setText(numP+"/"+total);
        pregunta.setText(v.getPreguntes().get(numP-1).getTitol());
        if(numP == total) btn.setText("Finalitzar");
        else btn.setText("Continuar");
        System.out.println("opcions:   " + v.getPreguntes().get(numP - 1).getOpcions().size());
        if(v.getPreguntes().get(numP-1).getOpcions().size() == 0) vg.setVisibility(View.GONE);
        else loadOpcions(v.getPreguntes().get(numP-1).getOpcions());
    }

    private void loadOpcions(ArrayList<String> opcions) {
        resposta.setVisibility(View.GONE);
        for(int i = 0; i < opcions.size(); ++i) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            button.setText(opcions.get(i));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((RadioGroup) view.getParent()).check(view.getId());
                    respostaRB = ((RadioButton) findViewById(view.getId())).getText().toString();
                    //((RadioButton) findViewById(view.getId())).getText();
                }
            });
            //button.setChecked(i == currentHours); // Only select button with same index as currently selected number of hours
            //button.setBackgroundResource(R.drawable.item_selector); // This is a custom button drawable, defined in XML
            vg.addView(button);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonResposta:
                if(v.getPreguntes().get(numP - 1).isObligatoria()){
                    if(v.getPreguntes().get(numP-1).getOpcions().size() == 0) {
                        if(resposta.getText().toString().equals("")) {
                            resOb.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    else {
                        if(respostaRB.equals("")) {
                            resOb.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
                if (btn.getText().equals("Continuar")) {
                    if(v.getPreguntes().get(numP-1).getOpcions().size() == 0) {
                        v.getPreguntes().get(numP-1).setResposta(resposta.getText().toString());

                    }else{
                        v.getPreguntes().get(numP-1).setResposta(respostaRB);
                    }
                    Bundle sent = new Bundle();
                    sent.putInt("idVotacion",idV);
                    sent.putInt("numPreg", numP + 1);
                    sent.putInt("idUsuari", idUsuari);
                    Intent i = new Intent(getApplicationContext(), DetallePreguntaActivity.class);
                    i.putExtras(sent);
                    startActivityForResult(i, 1);

                }else{
                    if(v.getPreguntes().get(numP-1).getOpcions().size() == 0)
                        v.getPreguntes().get(numP-1).setResposta(resposta.getText().toString());
                    else
                        v.getPreguntes().get(numP-1).setResposta(respostaRB);
                    enviarRespostes();
                }
                break;
            }
    }

    private void enviarRespostes() {
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setMessage("Enviant dades...");
        pDialog.setCancelable(false);
        //pDialog.setMax(100);
        dv = new DadesVotacio(this);
        dv.execute(String.valueOf(idUsuari));



/*
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ArrayList<Pregunta> p = v.getPreguntes();
        for(int i = 0; i < p.size() ; ++i) {
            System.out.println("i: " + i + " ;idV:" + v.getId() + " ;pregunta:" + p.get(i).getId() + " ;idU:" + String.valueOf(idUsuari) + " ;resposta:" + p.get(i).getResposta() + " ;data:"+POST.getDateTime());
            String targetURL="http://xarxacd.cofb.net/app_accesscontrol/public/votacions/"+v.getId()+"/preguntes/"+ p.get(i).getId() +"/respostes";
            System.out.println("url:" +targetURL);
            String urlParameters = null;
            try {
                urlParameters =
                        "idUsuari=" + URLEncoder.encode(String.valueOf(idUsuari), "UTF-8") +
                                "&resposta=" + URLEncoder.encode(p.get(i).getResposta(), "UTF-8") +
                                "&dataHora=" + URLEncoder.encode(POST.getDateTime(), "UTF-8");
                System.out.println("parametres: " + urlParameters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error a l'enviar les respostes", Toast.LENGTH_SHORT).show();
            }
            int result = POST.excutePost(targetURL, urlParameters);
            System.out.println("Resultat: " + result);
        }
        v.setFeta("votacioFeta");
        Toast.makeText(getApplicationContext(),"Enviat", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        if ((requestCode == 1) && (resultCode == RESULT_OK)){
            Intent data = new Intent();
            data.setData(Uri.parse("RESULT_OK"));
            setResult(RESULT_OK, data);
        }
        finish();
    }

    public void fin() {
        Intent data = new Intent();
        data.setData(Uri.parse("RESULT_OK"));
        setResult(RESULT_OK, data);
        finish();
    }
}
