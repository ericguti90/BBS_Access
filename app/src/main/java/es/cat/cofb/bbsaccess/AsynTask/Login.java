package es.cat.cofb.bbsaccess.AsynTask;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.SystemClock;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.cat.cofb.bbsaccess.API.POST;
import es.cat.cofb.bbsaccess.Presentation.MainActivity;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 27/10/2015.
 */
public class Login extends AsyncTask<String, Integer, String>  {
    MainActivity DPActivity;

    public Login(MainActivity detallePreguntaActivity) {
        DPActivity = detallePreguntaActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String targetURL="http://xarxacd.cofb.net/app_accesscontrol/public/loginapi";
        String urlParameters = null;
        try {
            urlParameters =
                    "username=" + URLEncoder.encode(params[0]/*idUsuari*/, "UTF-8") +
                            "&password=" + URLEncoder.encode(params[1], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Login.this.cancel(true);
            //Toast.makeText(getApplicationContext(),"Error a l'enviar les respostes", Toast.LENGTH_SHORT).show();
        }
        int result = POST.excutePost(targetURL, urlParameters);
        String response = POST.response;
        //System.out.println("response:                     " + response);
        return response;
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
    protected void onPostExecute(String result) {
        result = String.valueOf(result).trim();
        DPActivity.pDialog.dismiss();
        int r = Integer.valueOf(result);
        if(r == -1) {
            Toast.makeText(DPActivity, R.string.usuariIncorrecte, Toast.LENGTH_SHORT).show();
        }
        else {DPActivity.login(r);}
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(DPActivity, R.string.errorDades, Toast.LENGTH_SHORT).show();
    }


}
