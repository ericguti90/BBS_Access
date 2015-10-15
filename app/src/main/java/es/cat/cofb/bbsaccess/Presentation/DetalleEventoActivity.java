package es.cat.cofb.bbsaccess.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.Model.Evento;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.R;

public class DetalleEventoActivity extends AppCompatActivity implements View.OnClickListener{
	
	TextView dataHora, lloc, inscripcio, presencial, numAss, tituloEvento;
	Button btn;
	Resultado api;
	Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

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
		bundle=getIntent().getExtras();
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
				if(btn.getText().toString().equals("Accedir")) {

				} else {
					String targetURL="http://xarxacd.cofb.net/app_accesscontrol/public/esdeveniments/7/assistents";
					String urlParameters = null;
					try {
						urlParameters =
								"usuari=" + URLEncoder.encode("egutierrezS", "UTF-8") +
										"&assistit=" + URLEncoder.encode("false", "UTF-8") +
										"&dataHora=" + URLEncoder.encode("2001-09-28 24:12:60", "UTF-8") +
										"&delegat=" + URLEncoder.encode("true", "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					int result = POST.excutePost(targetURL, urlParameters);
					if(result == 201) {
						Evento e = api.getEventoPos(bundle.getInt("idEvento"));
						e.setInscrit(true);
						btn.setText("Accedir");
					}
				}
				//Intent i = new Intent(this, ListActivity.class);

				//startActivity(i);
				break;
			case R.id.BtnVotaciones:
				break;

		}
	}
}
