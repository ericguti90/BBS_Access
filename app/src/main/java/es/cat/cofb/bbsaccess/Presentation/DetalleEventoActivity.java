package es.cat.cofb.bbsaccess.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;

import es.cat.cofb.bbsaccess.Model.Evento;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.R;

public class DetalleEventoActivity extends AppCompatActivity implements View.OnClickListener{
	
	TextView dataHora, lloc, inscripcio, presencial, numAss, tituloEvento;
	Button btn;
	Resultado api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_evento);
		dataHora = (TextView) findViewById(R.id.textView12);
		lloc = (TextView) findViewById(R.id.textView13);
		inscripcio = (TextView) findViewById(R.id.textView14);
		presencial = (TextView) findViewById(R.id.textView15);
		numAss = (TextView) findViewById(R.id.textView16);
		tituloEvento = (TextView) findViewById(R.id.tituloEvento);
		btn = (Button) findViewById(R.id.buttonAccedir);
		btn.setOnClickListener(this);
		RelativeLayout rel = (RelativeLayout) findViewById(R.id.BtnVotaciones);
		rel.setOnClickListener(this);

		//cargamos datos
		Bundle bundle=getIntent().getExtras();
		//int pos = bundle.getInt("idEvento");
		//System.out.println(pos);
		//api.getEventoPos(pos);
        api = ListActivity.api;
		loadEvento(api.getEventoPos(bundle.getInt("idEvento")));
	}

	private void loadEvento(Evento evento) {
		tituloEvento.setText(evento.getTitol());
		dataHora.setText(evento.getDataHora());
		if(evento.getLloc().equals("")) lloc.setText("Online");
		else lloc.setText(evento.getLloc());
		if(evento.isInscripcio()) inscripcio.setText("Sí");
		else inscripcio.setText("No");
		if(evento.isPresencial()) presencial.setText("Sí");
		else presencial.setText("No");
		numAss.setText(String.valueOf(evento.getNumAss()));
		if(evento.isInscrit()) btn.setText("Accedir");
		else btn.setText("Inscriure");
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonAccedir:
				//Intent i = new Intent(this, ListActivity.class);

				//startActivity(i);
				break;
			case R.id.BtnVotaciones:
				break;

		}
	}
}
