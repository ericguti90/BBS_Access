package es.cat.cofb.bbsaccess.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.Listeners.MenuListener;
import es.cat.cofb.bbsaccess.Model.Evento;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.R;

public class DetalleEventoActivity extends AppCompatActivity implements View.OnClickListener{
	
	TextView dataHora, lloc, inscripcio, presencial, numAss, tituloEvento;
	Button btnQR, btnNFC;
	Resultado api;
	Bundle bundle;
	int id, idUsuari;
	private NfcAdapter mNfcAdapter;
	LinearLayout lyOK;
	NavigationView navView;
	DrawerLayout drawerLayout;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		setContentView(R.layout.activity_detalle_evento);
		dataHora = (TextView) findViewById(R.id.textView12);
		lloc = (TextView) findViewById(R.id.textView13);
		inscripcio = (TextView) findViewById(R.id.textView14);
		presencial = (TextView) findViewById(R.id.textView15);
		numAss = (TextView) findViewById(R.id.textView16);
		tituloEvento = (TextView) findViewById(R.id.tituloEvento);
		btnQR = (Button) findViewById(R.id.buttonAccedirQR);
		btnNFC = (Button) findViewById(R.id.buttonAccedirNFC);
		lyOK = (LinearLayout) findViewById(R.id.lytGreenEvento);
		FrameLayout btnMenu = (FrameLayout) findViewById(R.id.menuBtn);
		btnMenu.setOnClickListener(this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		navView = (NavigationView)findViewById(R.id.navview);
		navView.setNavigationItemSelectedListener(new MenuListener(drawerLayout, this));
		btnQR.setOnClickListener(this);
		btnNFC.setOnClickListener(this);
		RelativeLayout rel = (RelativeLayout) findViewById(R.id.BtnVotaciones);
		rel.setOnClickListener(this);

		//cargamos datos
		bundle=getIntent().getExtras();
		//int pos = bundle.getInt("idEvento");
		//System.out.println(pos);
		//api.getEventoPos(pos);
		TextView txt = (TextView) findViewById(R.id.headerUser);
		txt.setText(bundle.getString("usuari"));
        api = ListActivity.api;
		idUsuari = bundle.getInt("idUsuari");
		if(bundle.getBoolean("esHistorico")) {
			loadEvento(api.getHistoricoPos(bundle.getInt("idEvento")), true);
		}
		else {
			loadEvento(api.getEventoPos(bundle.getInt("idEvento")), false);
			rel.setVisibility(View.GONE);
		}
	}

	private void loadEvento(Evento evento, boolean assistit) {
		lyOK.setVisibility(View.GONE);
		id = evento.getId();
		tituloEvento.setText(evento.getTitol());
		dataHora.setText(evento.getDataHora());
		if(evento.getLloc().equals("")) lloc.setText("Online");
		else lloc.setText(evento.getLloc());
		if(evento.isInscripcio()) inscripcio.setText("Sí");
		else inscripcio.setText("No");
		if(evento.isPresencial()) presencial.setText("Sí");
		else presencial.setText("No");
		numAss.setText(String.valueOf(evento.getNumAss()));
		if(evento.isInscrit()) {
			if(!assistit) {
				btnQR.setText(R.string.accedirQR);
				if (mNfcAdapter == null) btnNFC.setVisibility(View.GONE);
			}
			else {
				btnNFC.setVisibility(View.GONE);
				btnQR.setVisibility(View.GONE);
			}
		}
		else {
			btnQR.setText(R.string.inscriu);
			btnNFC.setVisibility(View.GONE);
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonAccedirQR:
				if(btnQR.getText().toString().equals(getString(R.string.accedirQR))) {
					Bundle bundleQR = new Bundle();
					Intent i = new Intent(getApplicationContext(), QRViewActivity.class);
					bundleQR.putInt("idEvento", id);
					bundleQR.putString("usuari", bundle.getString("usuari"));
					i.putExtras(bundleQR);
					startActivity(i);
				} else {
					String targetURL="http://xarxacd.cofb.net/app_accesscontrol/public/esdeveniments/"+id+"/assistents";
					String urlParameters = null;
					try {
						urlParameters =
								"usuari=" + URLEncoder.encode(bundle.getString("usuari"), "UTF-8") +
										"&assistit=" + URLEncoder.encode("false", "UTF-8") +
										"&dataHora=" + URLEncoder.encode("0000-00-00 00:00:00", "UTF-8") +
										"&delegat=" + URLEncoder.encode("false", "UTF-8");
						//System.out.println(urlParameters);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					int result = POST.excutePost(targetURL, urlParameters);
					if(result == 200) {
						Evento e = api.getEventoPos(bundle.getInt("idEvento"));
						e.setInscrit(true);
						btnQR.setText(R.string.accedirQR);
						if (mNfcAdapter != null) btnNFC.setVisibility(View.VISIBLE);
						lyOK.setVisibility(View.VISIBLE);
						if(idUsuari<0)actualitzar_id(POST.response);
					}
				}

				//Intent i = new Intent(this, ListActivity.class);

				//startActivity(i);
				break;
			case R.id.BtnVotaciones:
				Bundle bundleV = new Bundle();
				Intent i = new Intent(getApplicationContext(), ListVotaEvActivity.class);
				bundleV.putInt("idEvento", bundle.getInt("idEvento"));
				bundleV.putInt("idUsuari", idUsuari);
				bundleV.putString("usuari", bundle.getString("usuari"));
				//System.out.println("esHistorico? " + bundle.getBoolean("esHistorico"));
				bundleV.putBoolean("esHistorico", bundle.getBoolean("esHistorico"));
				i.putExtras(bundleV);
				startActivity(i);
				break;
			case R.id.menuBtn:
				drawerLayout.openDrawer(navView);
				break;
		}
	}

	private void actualitzar_id(String response) {
		response = String.valueOf(response).trim();
		idUsuari = Integer.parseInt(response);
		ListActivity.user = idUsuari;
	}
}
