package es.cat.cofb.bbsaccess.Presentation;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import es.cat.cofb.bbsaccess.Listeners.MenuListener;
import es.cat.cofb.bbsaccess.R;

public class AvisoLegalActivity extends AppCompatActivity implements View.OnClickListener{

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
        webView.loadUrl("http://gis.cofb.net/apps_service/html/1003.html");
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
