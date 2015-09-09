package es.cat.cofb.bbsaccess.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import es.cat.cofb.bbsaccess.Model.ItemList;
import es.cat.cofb.bbsaccess.R;

public class FragmentList extends Fragment {
	
	private ItemList[] datos =
	    	new ItemList[]{
	    		new ItemList("Persona 1", "Asunto del correo 1"),
	    		new ItemList("Persona 2", "Asunto del correo 2"),
	    		new ItemList("Persona 3", "Asunto del correo 3"),
	    		new ItemList("Persona 4", "Asunto del correo 4"),
	    		new ItemList("Persona 5", "Asunto del correo 5")};
	
	private ListView lstListado;
	private ListListener listener;


	//inflamos la vista para que se vea en el fragment
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list, container, false);
	}

	//cargamos los datos (el listado)
	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		lstListado = (ListView)getView().findViewById(R.id.LstListado);
		setList(datos);
	}

	//el adaptador sirve para mostrar un listado con chicha, no solamente el titulo, sino una estrucutra xml
	class AdaptaderList extends ArrayAdapter<ItemList> {
    	Activity context;
    	AdaptaderList(Fragment context) {
    		super(context.getActivity(), R.layout.item_fragment_list, datos);
    		this.context = context.getActivity();
    	}

		//va cargando los datos visibles (los que caben en pantalla)
    	public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.item_fragment_list, null);
			TextView lblDe = (TextView)item.findViewById(R.id.LblTitle);
			lblDe.setText(datos[position].getTitle());
			TextView lblAsunto = (TextView)item.findViewById(R.id.LblDate);
			lblAsunto.setText(datos[position].getDate());
			return(item);
		}
    }

	//interface para crear el listener
	public interface ListListener {
		void onItemSelected(ItemList c);
	}
	
	public void setListListener(ListListener listener) {
		this.listener=listener;
	}

	//para mostrar otra lista en el fragment
	public void setList(ItemList[] newDatos) {
		datos = newDatos;
		lstListado.setAdapter(new AdaptaderList(this));
		lstListado.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
				if (listener!=null) {
					listener.onItemSelected(
                            (ItemList) lstListado.getAdapter().getItem(pos));
				}
			}
		});
	}
}
