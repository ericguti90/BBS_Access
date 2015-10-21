package es.cat.cofb.bbsaccess.API;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class POST {

    public static int excutePost(String targetURL, String urlParameters)
    {
        System.out.println("executePost");
        URL url;
        HttpURLConnection connection = null;
        try {
            //System.out.println("createConnection");
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            //connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setFixedLengthStreamingMode(urlParameters.getBytes().length);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            //connection.setRequestProperty("Content-Length", "" +
            //       Integer.toString(urlParameters.getBytes().length));
            //connection.setRequestProperty("Content-Language", "en-US");

            //connection.setUseCaches(false);

            //connection.setDoOutput(true);

            //System.out.println("sendRequest");
            //Send request
            //DataOutputStream wr = new DataOutputStream (
            //        connection.getOutputStream ());
            //wr.writeBytes (urlParameters);
            //wr.flush();
            //wr.close();
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(urlParameters);
            out.close();

            //System.out.println("getResponse");

            // handle issues
            int statusCode = connection.getResponseCode();
            //if (statusCode != 201) {
                System.out.println("status: " + statusCode);
            //}
            //else System.out.println("status: " + statusCode + "(creada)");
            //return "fin";
            return statusCode;


            //Get Response
            /*InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            System.out.println("resposta: " + response.toString());
            return "fin";//response.toString();*/

        } catch (Exception e) {

            //System.out.println("----------------------------");
            e.printStackTrace();
            //System.out.println("----------------------------");
            return -1;


        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
        //return "errorFin";
    }

    public static String getDateTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        String localTime = date.format(currentLocalTime);
        return localTime;
    }

}
