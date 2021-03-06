package es.cat.cofb.bbsaccess.Presentation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import es.cat.cofb.bbsaccess.API.JSONCommentsParser;
import es.cat.cofb.bbsaccess.Adapters.EventoAdapter;
import es.cat.cofb.bbsaccess.Adapters.VotacionAdapter;
import es.cat.cofb.bbsaccess.Fragments.FragmentList;
import es.cat.cofb.bbsaccess.Listeners.MenuListener;
import es.cat.cofb.bbsaccess.Listeners.RecyclerItemClickListener;
import es.cat.cofb.bbsaccess.Model.ItemList;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.R;

public class ListActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    public static Resultado api;
    private boolean esEvento = true;
    private boolean esHistorico = false;
    //FragmentList frgListado;
    ImageView btnEvent, btnVotation, btnHistory;
    FrameLayout btnMenu;
    String actual = "event", userNom;
    static RecyclerView eventos;
    private LinearLayoutManager mLinearLayout;
    HttpURLConnection con;
    TextView txtError, textList;
    LinearLayout lyError;
    public static int user = 5;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);
        eventos = (RecyclerView) findViewById(R.id.LstListado);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        txtError = (TextView) findViewById(R.id.textView31);
        textList = (TextView) findViewById(R.id.textList);
        lyError = (LinearLayout) findViewById(R.id.avisError);
        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(new MenuListener(drawerLayout, this));
        /*navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.avis_legal:
                                Toast.makeText(getApplicationContext(),"avis legal", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.bustia:

                                break;
                            case R.id.contacte:

                                break;
                            case R.id.idioma:

                                break;
                            case R.id.perfil:
                                Toast.makeText(getApplicationContext(),"perfil", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.sessio:

                                break;
                        }

                        /*if (fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.FrgListado, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            //getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });*/

        //listener fragment
    //    frgListado = (FragmentList)getSupportFragmentManager().findFragmentById(R.id.FrgListado);
    //    frgListado.setListListener(this);

        //listeners menu
        btnEvent = (ImageView) findViewById(R.id.eventIcon);
        btnVotation = (ImageView) findViewById(R.id.votationIcon);
        btnHistory = (ImageView) findViewById(R.id.historyIcon);
        btnMenu = (FrameLayout) findViewById(R.id.menuBtn);
        btnEvent.setOnClickListener(this);
        btnVotation.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnMenu.setOnClickListener(this);

        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(this);
        //Asignamos el LinearLayoutManager al recycler:
        eventos.setLayoutManager(mLinearLayout);
        try {
            Bundle bundle = getIntent().getExtras();
            user = bundle.getInt("idUsuari");
            userNom = bundle.getString("usuari");
        }catch(Exception e){
            user = api.getIdUser();
            userNom = api.getUser();
        }
        TextView txt = (TextView) findViewById(R.id.headerUser);
        txt.setText(userNom);
        try {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                pDialog = new ProgressDialog(this);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setMessage(getString(R.string.carregantDades));
                pDialog.setCancelable(false);
                pDialog.setIndeterminate(true);
                pDialog.setProgressNumberFormat(null);
                pDialog.setProgressPercentFormat(null);
                pDialog.show();
                new GetCommentsTask().
                    execute(
                            new URL("http://xarxacd.cofb.net/app_accesscontrol/public/api/assistents/"+user));
            } else {
                Toast.makeText(this, R.string.errorConnexio, Toast.LENGTH_LONG).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        eventos.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        //Toast.makeText(getApplicationContext(),"Posicion: " + position,Toast.LENGTH_SHORT).show();
                        Intent i;
                        if (esEvento)
                            i = new Intent(getApplicationContext(), DetalleEventoActivity.class);
                        else i = new Intent(getApplicationContext(), DetalleVotacionActivity.class);
                        Bundle bundle = new Bundle();
                        if (esEvento) {
                            bundle.putInt("idEvento", position);
                            bundle.putInt("idUsuari", user);
                            bundle.putString("usuari", userNom);
                            //System.out.println("esHistorico? " + esHistorico);
                            bundle.putBoolean("esHistorico", esHistorico);
                        } else {
                            bundle.putString("usuari", userNom);
                            bundle.putInt("idVotacion", position);
                            bundle.putString("feta", "VotacioNoFeta");
                            bundle.putInt("idUsuari", user);
                        }
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                })
        );
    }


    /*@Override
    public void onItemSelected(ItemList c) {
        Intent i = new Intent(this, DetalleEventoActivity.class);

        startActivity(i);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuBtn:
                drawerLayout.openDrawer(navView);
                break;
            case R.id.eventIcon:
                if(actual != "event") {
                    actual = "event";
                    //frgListado.setList(datos);
                    textList.setText(R.string.esdeveniments);
                    setImgStatus(R.drawable.event_push, R.drawable.votation, R.drawable.history);
                    if(api.getEventos().size() == 0) {
                        eventos.setVisibility(View.GONE);
                        txtError.setText(R.string.EsdNoDisp);
                    }
                    else {
                        eventos.setVisibility(View.VISIBLE);
                        eventos.setAdapter(new EventoAdapter(api.getEventos()));
                    }
                    esEvento = true;
                    esHistorico = false;
                }
                break;
            case R.id.votationIcon:
                if(actual != "votation") {
                    actual = "votation";
                    textList.setText(R.string.votacions);
                    //frgListado.setList(datos);
                    setImgStatus(R.drawable.event, R.drawable.votation_push, R.drawable.history);
                    if(api.getVotaciones().size() == 0) {
                        eventos.setVisibility(View.GONE);
                        txtError.setText(R.string.VotaNoDisp);
                    }
                    else {
                        eventos.setVisibility(View.VISIBLE);
                        eventos.setAdapter(new VotacionAdapter(api.getVotaciones()));
                    }
                    esEvento = false;
                    esHistorico = false;
                }
                break;
            case R.id.historyIcon:
                if(actual != "history") {
                    actual = "history";
                    textList.setText(R.string.historics);
                    //frgListado.setList(datos);
                    setImgStatus(R.drawable.event, R.drawable.votation, R.drawable.history_push);
                    if(api.getHistorico().size() == 0) {
                        eventos.setVisibility(View.GONE);
                        txtError.setText(R.string.HisNoDisp);
                    }
                    else {
                        eventos.setVisibility(View.VISIBLE);
                        eventos.setAdapter(new EventoAdapter(api.getHistorico()));
                    }

                    esEvento = true;
                    esHistorico = true;
                }
                break;

        }

    }

    //cambia el icono del menu
    private void setImgStatus(int event, int votation, int history) {
        btnEvent.setImageResource(event);
        btnVotation.setImageResource(votation);
        btnHistory.setImageResource(history);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    La clase GetCommentsTask representa una tarea asincrona que realizará
    las operaciones de red necesarias en segundo plano para obtener toda la
    lista de comentarios alojada en el servidor.
     */
    public class GetCommentsTask extends AsyncTask<URL, Void, Resultado> {

        @Override
        protected Resultado doInBackground(URL... urls) {

            Resultado eventos = null;
            try {

                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();
                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();
                if(statusCode!=200) {
                    //eventos = new ArrayList<>();
                    //eventos.add("El recurso no está disponible");
                    //return eventos;
                }
                else{
                    /*
                    Parsear el flujo con formato JSON a una lista de Strings
                    que permitan crean un adaptador
                     */
                    InputStream in = new BufferedInputStream(con.getInputStream());
                    JSONCommentsParser parser = new JSONCommentsParser();
                    eventos = parser.readJsonStream(in);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                con.disconnect();
            }
            return eventos;
        }

        @Override
        protected void onPostExecute(Resultado s) {
            /*
            Se crea un adaptador con el el resultado del parsing
            que se realizó al arreglo JSON
             */
            pDialog.dismiss();
            if(s != null) {
                api = s;
                api.setUser(userNom);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                //        getBaseContext(),
                //        android.R.layout.simple_list_item_1,
                //        s);
                // Relacionar adaptador a la lista
                //s.add("hola");s.add("hola");s.add("hola");s.add("hola");s.add("hola");s.add("hola");s.add("hola");s.add("hola");
                //ArrayList<Evento> lstEvento = new ArrayList<Evento>();
                //Evento evento = new Evento(1, "titol1", "23-25-2015", "Col·legi", true, true);
                //Evento evento1 = new Evento(1, "titulillo", "212-05-2015", "COFB", true, true);
                //lstEvento.add(evento); lstEvento.add(evento1);
                api.setIdUser(user);
                api.setUser(userNom);
                if(esEvento && esHistorico) {
                    if(api.getHistorico().size() == 0) eventos.setVisibility(View.GONE);
                    else {
                        eventos.setVisibility(View.VISIBLE);
                        eventos.setAdapter(new EventoAdapter(api.getHistorico()));
                    }
                }
                else if(esEvento) {
                    if(api.getEventos().size() == 0) eventos.setVisibility(View.GONE);
                    else {
                        eventos.setVisibility(View.VISIBLE);
                        eventos.setAdapter(new EventoAdapter(s.getEventos()));
                    }
                }
                else {
                    if(api.getVotaciones().size() == 0) eventos.setVisibility(View.GONE);
                    else {
                        eventos.setVisibility(View.VISIBLE);
                        eventos.setAdapter(new VotacionAdapter(api.getVotaciones()));
                    }

                }


                //eventos.setAdapter(adapter);
            }
            else Toast.makeText(
                    getBaseContext(),
                    "Ocurrió un error de Parsing Json",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
