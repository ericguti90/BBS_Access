package es.cat.cofb.bbsaccess.Listeners;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.cat.cofb.bbsaccess.R;

/**
 * Created by egutierrez on 03/11/2015.
 */
public class DialogValidarManual2 extends DialogFragment {
    private DialogListener2 listener;

    public interface DialogListener2 {
        public void onDialogValidar2(String correu);
    }

    TextView correu;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listener = (DialogListener2) getActivity();
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_validar_manual2, null);
        builder.setView(v);

        Button correcte = (Button) v.findViewById(R.id.correcteManual);
        Button incorrecte = (Button) v.findViewById(R.id.incorrecteManual);
        TextView nom = (TextView) v.findViewById(R.id.nomCorrecte);
        correu = (TextView) v.findViewById(R.id.correuCorrecte);
        Bundle bundle = getArguments();
        correu.setText(bundle.getString("emailAddress"));
        nom.setText(bundle.getString("firstName") + " " + bundle.getString("middleName") + " " + bundle.getString("lastName"));

        correcte.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onDialogValidar2(correu.getText().toString().split("@")[0]);
                        dismiss();
                    }
                }
        );

        incorrecte.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }

        );
        return builder.create();
    }
}
