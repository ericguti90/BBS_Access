package es.cat.cofb.bbsaccess.Presentation;

import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import es.cat.cofb.bbsaccess.Listeners.MenuListener;
import es.cat.cofb.bbsaccess.QR.Contents;
import es.cat.cofb.bbsaccess.QR.QRCodeEncoder;
import es.cat.cofb.bbsaccess.R;

public class QRViewActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    NavigationView navView;
    DrawerLayout drawerLayout;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_view);
        imageView = (ImageView) findViewById(R.id.imageViewQR);
        txt = (TextView) findViewById(R.id.headerUser);
        FrameLayout btnMenu = (FrameLayout) findViewById(R.id.menuBtn);
        btnMenu.setOnClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(new MenuListener(drawerLayout, this));
        mostrarQR();
    }

    public void mostrarQR(){
        Bundle bundle=getIntent().getExtras();
        txt.setText(bundle.getString("usuari"));
        String qrData = bundle.getInt("idEvento") + ";" + bundle.getString("usuari") + "@cofb.net";
        int qrCodeDimention = 500;

        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null,
                Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
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
