package es.cat.cofb.bbsaccess.Listeners;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

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
                Toast.makeText(applicationContext, "avis legal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bustia:

                break;
            case R.id.contacte:

                break;
            case R.id.idioma:

                break;
            case R.id.perfil:
                Toast.makeText(applicationContext,"perfil", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sessio:

                break;
        }
        drawerLayout.closeDrawers();

        return true;
    }
}