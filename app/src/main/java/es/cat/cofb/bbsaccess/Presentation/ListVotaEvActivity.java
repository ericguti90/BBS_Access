package es.cat.cofb.bbsaccess.Presentation;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import es.cat.cofb.bbsaccess.Adapters.VotacionAdapter;
import es.cat.cofb.bbsaccess.Listeners.DialogValidarManual;
import es.cat.cofb.bbsaccess.Listeners.MenuListener;
import es.cat.cofb.bbsaccess.Listeners.RecyclerItemClickListener;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.Model.Votacion;
import es.cat.cofb.bbsaccess.QR.IntentIntegrator;
import es.cat.cofb.bbsaccess.R;

public class ListVotaEvActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView votacions;
    LinearLayoutManager mLinearLayout;
    ArrayList<Votacion> lstVota;
    Resultado api;
    int idUsuari, idEvento;
    NavigationView navView;
    DrawerLayout drawerLayout;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vota_ev);
        votacions = (RecyclerView) findViewById(R.id.LstVotaEv);
        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(this);
        //Asignamos el LinearLayoutManager al recycler:
        votacions.setLayoutManager(mLinearLayout);
        bundle=getIntent().getExtras();
        TextView txt = (TextView) findViewById(R.id.headerUser);
        txt.setText(bundle.getString("usuari"));
        api = ListActivity.api;
        if(bundle.getBoolean("esHistorico")) {
            lstVota = api.getHistoricoPos(bundle.getInt("idEvento")).getVotacions();
            idEvento = api.getHistoricoPos(bundle.getInt("idEvento")).getId();
        } else {
            lstVota = api.getEventoPos(bundle.getInt("idEvento")).getVotacions();
            idEvento = api.getEventoPos(bundle.getInt("idEvento")).getId();
        }
        idUsuari = bundle.getInt("idUsuari");
        votacions.setAdapter(new VotacionAdapter(lstVota));
        votacions.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        //Toast.makeText(getApplicationContext(),"Posicion: " + position,Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), DetalleVotacionActivity.class);
                        Bundle sent = new Bundle();
                        sent.putInt("idVotacion", api.getPosVotacion(lstVota.get(position).getId()));
                        sent.putInt("idUsuari", idUsuari);
                        sent.putInt("idEvento", idEvento);
                        sent.putInt("position", position);
                        sent.putString("usuari", bundle.getString("usuari"));
                        //sent.putString("feta", "VotacioNoFeta");
                        i.putExtras(sent);
                        startActivity(i);
                    }
                })
        );
        FrameLayout btnMenu = (FrameLayout) findViewById(R.id.menuBtn);
        btnMenu.setOnClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(new MenuListener(getApplicationContext(), drawerLayout));
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
