package es.cat.cofb.bbsaccess.Listeners;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.cat.cofb.bbsaccess.R;

public class DialogValidarManual extends DialogFragment {
    private DialogListener listener;

        public interface DialogListener {
        public void onDialogValidar(String nif, String numF);
    }

    private EditText nif, numF;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listener = (DialogListener) getActivity();
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_validar_manual, null);
        builder.setView(v);

        Button validar = (Button) v.findViewById(R.id.validarManual);
        Button cancelar = (Button) v.findViewById(R.id.cancelarManual);
        nif = (EditText) v.findViewById(R.id.nif);
        numF = (EditText) v.findViewById(R.id.numF);

        validar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(nif.getText().toString()!="" || numF.getText().toString()!=""){
                            listener.onDialogValidar(nif.getText().toString(), numF.getText().toString());
                            dismiss();
                        }
                        else Toast.makeText(getActivity().getApplicationContext(),R.string.noDadesValidar,Toast.LENGTH_SHORT).show();
                    }
                }
        );

        cancelar.setOnClickListener(
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