package es.cat.cofb.bbsaccess.Presentation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import es.cat.cofb.bbsaccess.API.JSONCommentsParser;
import es.cat.cofb.bbsaccess.Adapters.EventoAdapter;
import es.cat.cofb.bbsaccess.Listeners.RecyclerItemClickListener;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 28/10/2015.
 */
public class ListAdminActivity extends AppCompatActivity {

    public static Resultado api;
    HttpURLConnection con;
    RecyclerView eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_admin);
        api = new Resultado();
        eventos = (RecyclerView) findViewById(R.id.LstListadoAdmin);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        eventos.setLayoutManager(mLinearLayout);
        try {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new GetCommentsTask().
                        execute(
                                new URL("http://xarxacd.cofb.net/app_accesscontrol/public/api/esdeveniments-accesibles"));
            } else {
                Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
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
                        Intent i = new Intent(getApplicationContext(), DetalleEventoAdminActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("idEvento", position);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                })
        );
    }


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
            if(s != null) {
                api = s;
                eventos.setAdapter(new EventoAdapter(s.getEventos()));
            }
            else Toast.makeText(
                    getBaseContext(),
                    "Ocurrió un error de Parsing Json",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
