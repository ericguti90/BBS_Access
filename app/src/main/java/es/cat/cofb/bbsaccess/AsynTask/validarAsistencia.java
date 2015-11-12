package es.cat.cofb.bbsaccess.AsynTask;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.Presentation.DetalleEventoAdminActivity;
import es.cat.cofb.bbsaccess.R;

public class validarAsistencia extends AsyncTask<String, Integer, Boolean> {

    DetalleEventoAdminActivity DEAActivity;
    String total;

    public validarAsistencia(DetalleEventoAdminActivity detalleEventoAdminActivity) {
        DEAActivity = detalleEventoAdminActivity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String targetURL = "http://xarxacd.cofb.net/app_accesscontrol/public/api/validar-asistencia";
        String urlParameters = null;
        try {
            urlParameters =
                    "usuari=" + URLEncoder.encode(params[1].split("@")[0]/*usuari*/, "UTF-8") +
                            "&esdeveniment_id=" + URLEncoder.encode(params[0], "UTF-8") +
                            "&dataHora=" + URLEncoder.encode(POST.getDateTime(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            validarAsistencia.this.cancel(true);
            return false;
        }
        int result = POST.excutePost(targetURL, urlParameters);
        total = POST.response.trim();
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
            DEAActivity.respostaPOST(Integer.valueOf(total));
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(DEAActivity, R.string.errorValidar, Toast.LENGTH_SHORT).show();
    }
}
