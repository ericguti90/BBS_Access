package es.cat.cofb.bbsaccess.AsynTask;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.Presentation.PerfilActivity;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 11/11/2015.
 */
public class ObtenirNom extends AsyncTask<String, Integer, Boolean> {
    PerfilActivity PActivity;


    public ObtenirNom(PerfilActivity detalleEventoAdminActivity) {
        PActivity = detalleEventoAdminActivity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String targetURL = "http://xarxacd.cofb.net/app_accesscontrol/public/api/obtenir-usuari";
        String urlParameters = null;
        try {
            urlParameters =
                    "email=" + URLEncoder.encode(params[0], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ObtenirNom.this.cancel(true);
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
        PActivity.pDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        PActivity.pDialog.dismiss();
        if (result) {
            PActivity.respostaObtenirNom(POST.response);
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(PActivity, R.string.errorNom, Toast.LENGTH_SHORT).show();
    }
}
