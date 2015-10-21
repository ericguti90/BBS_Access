package es.cat.cofb.bbsaccess.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import es.cat.cofb.bbsaccess.Model.Evento;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.Model.Votacion;
import es.cat.cofb.bbsaccess.R;

public class DetalleVotacionActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tituloVotacion, evento, dataHoraIni, dataHoraFin, VotacioFeta;
    Button btn;
    Resultado api;
    int idV, idUsuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_votacion);
        evento = (TextView) findViewById(R.id.textView19);
        dataHoraIni = (TextView) findViewById(R.id.textView21);
        dataHoraFin = (TextView) findViewById(R.id.textView23);
        VotacioFeta = (TextView) findViewById(R.id.textView25);
        tituloVotacion = (TextView) findViewById(R.id.textView28);
        btn = (Button) findViewById(R.id.buttonVotacio);
        btn.setOnClickListener(this);
        api = ListActivity.api;

        //cargamos datos
        Bundle bundle=getIntent().getExtras();
        idV = bundle.getInt("idVotacion");
        idUsuari = bundle.getInt("idUsuari");
        if(idV == -1) {
            Evento e = api.getHistoricoId(bundle.getInt("idEvento"));
            loadVotacion(e.getVotacions().get(bundle.getInt("position")));
        }
        else {
            loadVotacion(api.getVotacionPos(idV));
        }

    }

    private void loadVotacion(Votacion votacion) {
        tituloVotacion.setText(votacion.getTitol());
        evento.setText(votacion.getEvento());
        dataHoraIni.setText(votacion.getDataHoraIni());
        dataHoraFin.setText(votacion.getDataHoraFin());
        if(votacion.getFeta().equals("votacioNoFeta")) VotacioFeta.setText("No");
        else {
            VotacioFeta.setText("Si");
            btn.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_votacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonVotacio:
                Intent i = new Intent(getApplicationContext(), DetallePreguntaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idVotacion", idV);
                bundle.putInt("numPreg",1);
                bundle.putInt("idUsuari",idUsuari);
                i.putExtras(bundle);
                startActivity(i);
                break;
        }
    }
}
