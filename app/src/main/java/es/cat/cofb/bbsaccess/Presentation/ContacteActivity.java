package es.cat.cofb.bbsaccess.Presentation;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import es.cat.cofb.bbsaccess.Listeners.MenuListener;
import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 10/11/2015.
 */
public class ContacteActivity extends AppCompatActivity implements View.OnClickListener{

    NavigationView navView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis_legal);
        FrameLayout btnMenu = (FrameLayout) findViewById(R.id.menuBtn);
        btnMenu.setOnClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(new MenuListener(drawerLayout, this));
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://gis.cofb.net/apps_service/html/1002.html");
        TextView titol = (TextView) findViewById(R.id.titol);
        titol.setText(R.string.contacte);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuBtn:
                drawerLayout.openDrawer(navView);
                break;
        }
    }
}
