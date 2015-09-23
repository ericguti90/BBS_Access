package es.cat.cofb.bbsaccess.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import es.cat.cofb.bbsaccess.Model.Evento;
import es.cat.cofb.bbsaccess.R;


/**
 * Created by egutierrez on 21/09/2015.
 */
public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.AdapterViewHolderEvento>{
    ArrayList<Evento> evento;

    public EventoAdapter(ArrayList<Evento> list){
        evento = new ArrayList<Evento>();
        evento = list;
    }

    @Override
    public AdapterViewHolderEvento onCreateViewHolder(ViewGroup parent, int viewType) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.row_list, parent, false);
        return new AdapterViewHolderEvento(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolderEvento holder, int position) {
        holder.titol.setText(evento.get(position).getTitol());
        holder.data.setText(evento.get(position).getDataHora()+"");
    }

    @Override
    public int getItemCount() {
        return evento.size();
    }

    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolderEvento extends RecyclerView.ViewHolder {
        public TextView titol;
        public TextView data;

        public AdapterViewHolderEvento(View itemView) {
            super(itemView);
            this.titol = (TextView) itemView.findViewById(R.id.textTitol);
            this.data = (TextView) itemView.findViewById(R.id.textData);
        }
    }
}
