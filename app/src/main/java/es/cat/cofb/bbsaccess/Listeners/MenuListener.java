package es.cat.cofb.bbsaccess.Listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import es.cat.cofb.bbsaccess.Presentation.AvisoLegalActivity;
import es.cat.cofb.bbsaccess.Presentation.ContacteActivity;
import es.cat.cofb.bbsaccess.Presentation.MainActivity;
import es.cat.cofb.bbsaccess.Presentation.PerfilActivity;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 04/11/2015.
 */
public class MenuListener implements NavigationView.OnNavigationItemSelectedListener{

    Context applicationContext;
    DrawerLayout drawerLayout;
    Activity activity;
    int idUsuari = 0;

    public MenuListener(DrawerLayout drawerLayout, Activity activity) {
        this.applicationContext = activity.getApplicationContext();
        this.drawerLayout = drawerLayout;
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.avis_legal:
                Intent intentAL = new Intent(applicationContext, AvisoLegalActivity.class);
                intentAL.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                applicationContext.startActivity(intentAL);
                break;
            case R.id.bustia:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"informatica@cofb.net" });
                emailIntent.putExtra(Intent.EXTRA_CC, "");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Aplicació COFB accés");
                //emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
                emailIntent.setType("message/rfc822");
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                applicationContext.startActivity(emailIntent);
                break;
            case R.id.contacte:
                Intent intentC = new Intent(applicationContext, ContacteActivity.class);
                intentC.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                applicationContext.startActivity(intentC);
                break;
            case R.id.idioma:
                Toast.makeText(applicationContext,"proximament", Toast.LENGTH_SHORT).show();
                break;
            case R.id.perfil:
                Intent intentP = new Intent(applicationContext, PerfilActivity.class);
                intentP.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                applicationContext.startActivity(intentP);
                break;
            case R.id.sessio:
                String user = "user";
                String pass = "pass";
                SharedPreferences prefs = applicationContext.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(user);
                editor.remove(pass);
                editor.apply();
                Intent intentS = new Intent(applicationContext, MainActivity.class);
                intentS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                applicationContext.startActivity(intentS);
                activity.finish();
                break;
        }
        drawerLayout.closeDrawers();

        return true;
    }
}