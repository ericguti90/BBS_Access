package es.cat.cofb.bbsaccess.AsynTask;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.Presentation.DetallePreguntaActivity;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 29/10/2015.
 */
public class CrearAssistent extends AsyncTask<String, Integer, Boolean> {

    DetallePreguntaActivity DPActivity;
    String idUsuari;

    public CrearAssistent(DetallePreguntaActivity detallePreguntaActivity) {
        DPActivity = detallePreguntaActivity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String targetURL = "http://xarxacd.cofb.net/app_accesscontrol/public/esdeveniments/"+params[0]+"/assistents";
        String urlParameters = null;
        try {
            urlParameters =
                    "usuari=" + URLEncoder.encode(params[1]/*usuari*/, "UTF-8") +
                    "&assistit=" + URLEncoder.encode("true", "UTF-8") +
                    "&dataHora=" + URLEncoder.encode(POST.getDateTime(), "UTF-8") +
                    "&delegat=" + URLEncoder.encode("false", "UTF-8");
            //System.out.println("parametres: " + urlParameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            CrearAssistent.this.cancel(true);
            return false;
        }
        int result = POST.excutePost(targetURL, urlParameters);
        idUsuari = POST.response.trim();

        targetURL = "http://xarxacd.cofb.net/app_accesscontrol/public/api/vota-ass";
        urlParameters = null;
        try {
            urlParameters =
                    "assistent_id=" + URLEncoder.encode(idUsuari/*usuari*/, "UTF-8") +
                    "&votacio_id=" + URLEncoder.encode(params[2], "UTF-8");
            //System.out.println("parametres: " + urlParameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            CrearAssistent.this.cancel(true);
            return false;
        }
        POST.excutePost(targetURL, urlParameters);
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    @Override
    protected void onPreExecute() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DPActivity.pDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        DPActivity.pDialog.dismiss();
        if (result) {
            DPActivity.idUsuari = Integer.valueOf(idUsuari);
            DPActivity.sentRespostes();
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(DPActivity, R.string.errorCrearAssistent, Toast.LENGTH_SHORT).show();
    }
}