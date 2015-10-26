package es.cat.cofb.bbsaccess.AsynTask;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.Model.Pregunta;
import es.cat.cofb.bbsaccess.Presentation.DetallePreguntaActivity;


/**
 * Created by egutierrez on 23/10/2015.
 */
public class DadesVotacio extends AsyncTask<String, Integer, Boolean> {

    DetallePreguntaActivity DPActivity;
    ArrayList<Pregunta> p;

    public DadesVotacio(DetallePreguntaActivity detallePreguntaActivity) {
        DPActivity = detallePreguntaActivity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        int enviats = 0;
        for(int i = 0; i < p.size() ; ++i) {
            enviats += 1;
            publishProgress(enviats);
            //System.out.println("i: " + i + " ;idV:" + v.getId() + " ;pregunta:" + p.get(i).getId() + " ;idU:" + String.valueOf(idUsuari) + " ;resposta:" + p.get(i).getResposta() + " ;data:"+ POST.getDateTime());
            String targetURL="http://xarxacd.cofb.net/app_accesscontrol/public/votacions/"+DPActivity.v.getId()+"/preguntes/"+ p.get(i).getId() +"/respostes";
            System.out.println("url:" +targetURL);
            String urlParameters = null;
            try {
                urlParameters =
                        "idUsuari=" + URLEncoder.encode(params[0]/*idUsuari*/, "UTF-8") +
                                "&resposta=" + URLEncoder.encode(p.get(i).getResposta(), "UTF-8") +
                                "&dataHora=" + URLEncoder.encode(POST.getDateTime(), "UTF-8");
                System.out.println("parametres: " + urlParameters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                DadesVotacio.this.cancel(true);
                //Toast.makeText(getApplicationContext(),"Error a l'enviar les respostes", Toast.LENGTH_SHORT).show();
            }
            int result = POST.excutePost(targetURL, urlParameters);
            System.out.println("Resultat: " + result);
        }
        /*for(int i=1; i<=10; i++) {
            tareaLarga();

            publishProgress(i*10);

            if(isCancelled())
                break;
        }

        return true;*/
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progreso = values[0].intValue();
        DPActivity.pDialog.setProgress(progreso);
    }

    @Override
    protected void onPreExecute() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        p = DPActivity.v.getPreguntes();
        DPActivity.pDialog.setMax(p.size());
        DPActivity.pDialog.setProgress(0);
        DPActivity.pDialog.show();
        /*pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                MiTareaAsincronaDialog.this.cancel(true);
            }
        });*/


    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result) {
            DPActivity.v.setFeta("votacioFeta");
            //Toast.makeText(DPActivity, "Enviat", Toast.LENGTH_SHORT).show();
        }
        DPActivity.pDialog.dismiss();
        DPActivity.fin();

        /*if(result)
        {
            pDialog.dismiss();
            Toast.makeText(MainActivity.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(DPActivity, "Error: no s'han enviat les respostes!", Toast.LENGTH_SHORT).show();
    }
}
