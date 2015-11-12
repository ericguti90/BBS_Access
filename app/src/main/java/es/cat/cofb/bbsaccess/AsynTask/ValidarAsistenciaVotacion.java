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
 * Created by egutierrez on 30/10/2015.
 */
public class ValidarAsistenciaVotacion extends AsyncTask<String, Integer, Boolean> {

    DetallePreguntaActivity DPActivity;
    String total;

    public ValidarAsistenciaVotacion(DetallePreguntaActivity detallePreguntaActivity) {
        DPActivity = detallePreguntaActivity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String targetURL = "http://xarxacd.cofb.net/app_accesscontrol/public/api/validar-asistencia";
        String urlParameters = null;
        try {
            urlParameters =
                    "usuari=" + URLEncoder.encode(params[1]/*usuari*/, "UTF-8") +
                            "&esdeveniment_id=" + URLEncoder.encode(params[0], "UTF-8") +
                            "&dataHora=" + URLEncoder.encode(POST.getDateTime(), "UTF-8");
            //System.out.println("parametres: " + urlParameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ValidarAsistenciaVotacion.this.cancel(true);
            return false;
        }
        int result = POST.excutePost(targetURL, urlParameters);
        total = POST.response.trim();
        //System.out.println("totaaaaaaaaaaaaaal:"+ total);
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
            DPActivity.sentRespostes();
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(DPActivity, R.string.errorValidar, Toast.LENGTH_SHORT).show();
    }
}
