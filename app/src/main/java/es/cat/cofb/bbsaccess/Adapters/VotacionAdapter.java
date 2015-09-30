package es.cat.cofb.bbsaccess.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import es.cat.cofb.bbsaccess.Model.Evento;
import es.cat.cofb.bbsaccess.Model.Votacion;
import es.cat.cofb.bbsaccess.R;

public class VotacionAdapter extends RecyclerView.Adapter<VotacionAdapter.AdapterViewHolderVotacion>{
    ArrayList<Votacion> votacion;

    public VotacionAdapter(ArrayList<Votacion> list){
        votacion = new ArrayList<Votacion>();
        votacion = list;
    }

    @Override
    public AdapterViewHolderVotacion onCreateViewHolder(ViewGroup parent, int viewType) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.row_list, parent, false);
        return new AdapterViewHolderVotacion(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolderVotacion holder, int position) {
        holder.titol.setText(votacion.get(position).getTitol());
        holder.data.setText(votacion.get(position).getDataHoraIni()+"");
    }

    @Override
    public int getItemCount() {
        return votacion.size();
    }

    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolderVotacion extends RecyclerView.ViewHolder {
        public TextView titol;
        public TextView data;

        public AdapterViewHolderVotacion(View itemView) {
            super(itemView);
            this.titol = (TextView) itemView.findViewById(R.id.textTitol);
            this.data = (TextView) itemView.findViewById(R.id.textData);
        }
    }
}

