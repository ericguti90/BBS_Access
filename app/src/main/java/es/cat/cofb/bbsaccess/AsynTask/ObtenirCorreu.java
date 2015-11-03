package es.cat.cofb.bbsaccess.AsynTask;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.Presentation.DetalleEventoAdminActivity;

/**
 * Created by egutierrez on 03/11/2015.
 */
public class ObtenirCorreu extends AsyncTask<String, Integer, Boolean> {
    DetalleEventoAdminActivity DEAActivity;


    public ObtenirCorreu(DetalleEventoAdminActivity detalleEventoAdminActivity) {
        DEAActivity = detalleEventoAdminActivity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String targetURL = "http://xarxacd.cofb.net/app_accesscontrol/public/api/obtenir-correu";
        String urlParameters = null;
        try {
            urlParameters =
                    "nif=" + URLEncoder.encode(params[0], "UTF-8") +
                            "&numF=" + URLEncoder.encode(params[1], "UTF-8");
            //System.out.println("parametres: " + urlParameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ObtenirCorreu.this.cancel(true);
            return false;
        }
        int result = POST.excutePost(targetURL, urlParameters);
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    @Override
    protected void onPreExecute() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DEAActivity.pDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        DEAActivity.pDialog.dismiss();
        if (result) {
            DEAActivity.respostaObtenirCorreu(POST.response);
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(DEAActivity, "Error: no es pot validar", Toast.LENGTH_SHORT).show();
    }
}
