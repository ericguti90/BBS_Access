package es.cat.cofb.bbsaccess.Presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import java.util.ArrayList;

import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.Model.Votacion;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 30/09/2015.
 */
public class DetallePreguntaActivity extends AppCompatActivity implements View.OnClickListener {
    TextView contador, pregunta, titulo;
    EditText resposta;
    Button btn;
    ViewGroup vg;
    Resultado api;
    int idV, numP;

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
        btn.setOnClickListener(this);
        api = ListActivity.api;
        
        //cargamos datos
        Bundle bundle=getIntent().getExtras();
        idV = bundle.getInt("idVotacion");
        numP = bundle.getInt("numPreg");
        loadPregunta(api.getVotacionPos(idV),numP);
    }

    private void loadPregunta(Votacion v, int numP) {
        titulo.setText(v.getTitol());
        int total = v.getPreguntes().size();
        contador.setText(numP+"/"+total);
        pregunta.setText(v.getPreguntes().get(numP-1).getTitol());
        if(numP == total) btn.setText("Finalitzar");
        else btn.setText("Continuar");
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
                }
            });
            //button.setChecked(i == currentHours); // Only select button with same index as currently selected number of hours
            //button.setBackgroundResource(R.drawable.item_selector); // This is a custom button drawable, defined in XML
            vg.addView(button);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
