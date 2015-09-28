package es.cat.cofb.bbsaccess.Presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.cat.cofb.bbsaccess.R;

public class DetalleActivity extends AppCompatActivity {
	
	public static final String EXTRA_TEXTO = 
			"net.sgoliver.android.fragments.EXTRA_TEXTO";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_evento);
	}
}
