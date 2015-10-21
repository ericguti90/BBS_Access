package es.cat.cofb.bbsaccess.Presentation;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import es.cat.cofb.bbsaccess.QR.Contents;
import es.cat.cofb.bbsaccess.QR.QRCodeEncoder;
import es.cat.cofb.bbsaccess.R;

public class QRViewActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_view);
        imageView = (ImageView) findViewById(R.id.imageViewQR);
        mostrarQR();
    }

    public void mostrarQR(){
        Bundle bundle=getIntent().getExtras();
        String qrData = bundle.getInt("idEvento") + ";" + bundle.getInt("idUsuari");
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
}
