package es.cat.cofb.bbsaccess.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import es.cat.cofb.bbsaccess.Fragments.FragmentList;
import es.cat.cofb.bbsaccess.Model.ItemList;
import es.cat.cofb.bbsaccess.R;

public class ListActivity extends AppCompatActivity implements FragmentList.ListListener, View.OnClickListener{

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ItemList[] datos =
            new ItemList[]{
                    new ItemList("Persona 11", "Asunto del correo 11"),
                    new ItemList("Persona 21", "Asunto del correo 21"),
                    new ItemList("Persona 31", "Asunto del correo 31"),
                    new ItemList("Persona 41", "Asunto del correo 41"),
                    new ItemList("Persona 51", "Asunto del correo 51")};

    FragmentList frgListado;
    ImageView btnEvent, btnVotation, btnHistory, btnMenu;
    String actual = "event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            /*case R.id.menu_seccion_1:
                                fragment = new Fragment11();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Fragment22();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_3:
                                fragment = new Fragment33();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_1:
                                Log.i("NavigationView", "Pulsada opción 1");
                                break;
                            case R.id.menu_opcion_2:
                                Log.i("NavigationView", "Pulsada opción 2");
                                break;*/
                        }

                        if (fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.FrgListado, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            //getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

        //listener fragment
        frgListado = (FragmentList)getSupportFragmentManager().findFragmentById(R.id.FrgListado);
        frgListado.setListListener(this);

        //listeners menu
        btnEvent = (ImageView) findViewById(R.id.eventIcon);
        btnVotation = (ImageView) findViewById(R.id.votationIcon);
        btnHistory = (ImageView) findViewById(R.id.historyIcon);
        btnMenu = (ImageView) findViewById(R.id.iconMenu);
        btnEvent.setOnClickListener(this);
        btnVotation.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(ItemList c) {
        Intent i = new Intent(this, DetalleActivity.class);
        //i.putExtra(DetalleActivity.EXTRA_TEXTO, c.getTitle());
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iconMenu:
                drawerLayout.openDrawer(navView);
                break;
            case R.id.menuBtn:
                drawerLayout.openDrawer(navView);
                break;
            case R.id.eventIcon:
                if(actual != "event") {
                    actual = "event";
                    frgListado.setList(datos);
                    setImgStatus(R.drawable.event_push, R.drawable.votation, R.drawable.history);
                }
                break;
            case R.id.votationIcon:
                if(actual != "votation") {
                    actual = "votation";
                    frgListado.setList(datos);
                    setImgStatus(R.drawable.event, R.drawable.votation_push, R.drawable.history);
                }
                break;
            case R.id.historyIcon:
                if(actual != "history") {
                    actual = "history";
                    frgListado.setList(datos);
                    setImgStatus(R.drawable.event, R.drawable.votation, R.drawable.history_push);
                }
                break;

        }

    }

    //cambia el icono del menu
    private void setImgStatus(int event, int votation, int history) {
        btnEvent.setImageResource(event);
        btnVotation.setImageResource(votation);
        btnHistory.setImageResource(history);
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

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
