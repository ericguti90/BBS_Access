package es.cat.cofb.bbsaccess.Listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import es.cat.cofb.bbsaccess.Presentation.MainActivity;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 04/11/2015.
 */
public class MenuListener implements NavigationView.OnNavigationItemSelectedListener{

    Context applicationContext;
    DrawerLayout drawerLayout;

    public MenuListener(Context applicationContext, DrawerLayout drawerLayout) {
        this.applicationContext = applicationContext;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.avis_legal:
                Toast.makeText(applicationContext, "proximament", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bustia:
                Toast.makeText(applicationContext,"proximament", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contacte:
                Toast.makeText(applicationContext,"proximament", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idioma:
                Toast.makeText(applicationContext,"proximament", Toast.LENGTH_SHORT).show();
                break;
            case R.id.perfil:
                Toast.makeText(applicationContext,"proximament", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sessio:
                String user = "user";
                String pass = "pass";
                SharedPreferences prefs = applicationContext.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(user);
                editor.remove(pass);
                editor.apply();
                Intent intent = new Intent(applicationContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                applicationContext.startActivity(intent);
                ((Activity)applicationContext).finish();
                break;
        }
        drawerLayout.closeDrawers();

        return true;
    }
}