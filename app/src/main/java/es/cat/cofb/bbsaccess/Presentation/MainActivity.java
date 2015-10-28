package es.cat.cofb.bbsaccess.Presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.cat.cofb.bbsaccess.AsynTask.Login;
import es.cat.cofb.bbsaccess.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public ProgressDialog pDialog;
    Login login;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnSesion = (Button) findViewById(R.id.button);
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        btnSesion.setOnClickListener(this);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                pDialog = new ProgressDialog(this);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setMessage("Iniciant sessió...");
                pDialog.setCancelable(false);
                pDialog.setIndeterminate(true);
                pDialog.setProgressNumberFormat(null);
                pDialog.setProgressPercentFormat(null);

                login = new Login(this);
                login.execute(user.getText().toString().split("@")[0], pass.getText().toString());

                //Intent i = new Intent(this, ListActivity.class);

                //startActivity(i);
                //break;
        }
    }

    public void login(Integer result) {

        Bundle bundle = new Bundle();
        Intent i = null;
        if(result > 0 || result == -2)
            //Toast.makeText(this, "."+result+".", Toast.LENGTH_SHORT).show();
            i = new Intent(getApplicationContext(), ListActivity.class);
         else
            i = new Intent(getApplicationContext(), ListAdminActivity.class);
        bundle.putInt("idUsuari", result);
        bundle.putString("usuari",user.getText().toString().split("@")[0]);
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }
}
