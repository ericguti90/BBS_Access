package es.cat.cofb.bbsaccess.API;

/**
 * Created by egutierrez on 10/09/2015.
 */
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import es.cat.cofb.bbsaccess.Model.Evento;
import es.cat.cofb.bbsaccess.Model.Pregunta;
import es.cat.cofb.bbsaccess.Model.Resultado;
import es.cat.cofb.bbsaccess.Model.Votacion;


public class JSONCommentsParser {
    Resultado result;

    public Resultado readJsonStream(InputStream in) throws IOException {
        // Nueva instancia JsonReader

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readCommentsArray(reader);
        }
        finally{
            reader.close();
        }
    }

    public Resultado readCommentsArray(JsonReader reader) throws IOException {
        result = new Resultado();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("data")) readArrayData(reader);
            else if(name.equals("insOb")) readArrayInsOb(reader);
            else reader.skipValue();
        }
        reader.endObject();
        return result;
    }

    private void readArrayInsOb(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            readObjectEsd(reader, false, false, 0);
        }
        reader.endArray();
    }

    private void readArrayData(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
           readObjectUser(reader);
        }
        reader.endArray();
    }

    private void readObjectUser(JsonReader reader) throws IOException {
        String aux = null;
        Evento e = null;
        int idUsuari = -1;
        boolean assistit = false;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("assistit")) {
                aux = reader.nextString();
                if (aux.equals("0")) assistit = false;
                else assistit = true;
            }
            else if(name.equals("id")) {
                aux = reader.nextString();
                idUsuari = Integer.parseInt(aux);
            }
            else if(name.equals("esd")) e = readObjectEsd(reader, assistit, true, idUsuari);
            else if(name.equals("vota")) readArrayVota(reader,e,assistit, idUsuari);
            else reader.skipValue();
        }
        reader.endObject();
    }

    private Evento readObjectEsd(JsonReader reader, boolean assistit, boolean inscrit, int idUsuari) throws IOException {
        String titol = null, dataHora = null, lloc = null, aux;
        Evento e = null;
        int id = 0, numAss = 0, numAssAct = 0;
        boolean inscripcio = false, presencial = false, esVota = false;
        reader.beginObject();
        JsonReader JRaux = null;
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("id")) id = Integer.parseInt(reader.nextString());
            else if(name.equals("titol")) titol = reader.nextString();
            else if(name.equals("dataHora")) dataHora = reader.nextString();
            else if(name.equals("lloc")) lloc = reader.nextString();
            else if(name.equals("inscripcioOberta")) {
                aux = reader.nextString();
                if (aux.equals("0")) inscripcio = false;
                else inscripcio = true;
            }
            else if(name.equals("presencial")) {
                aux = reader.nextString();
                if (aux.equals("0")) presencial = false;
                else presencial = true;
            }
            else if(name.equals("numAss")) numAss = Integer.parseInt(reader.nextString());
            else if(name.equals("numAssAct")) numAssAct = Integer.parseInt(reader.nextString());
            else if(name.equals("vota")) {
                esVota = true;
                //JRaux = reader;
                //reader.skipValue();
                e = new Evento(id, titol, dataHora, lloc, inscripcio, presencial, numAss, inscrit, -2);
                readArrayVota(reader,e,false,-2);
            }
            else reader.skipValue();
        }
        reader.endObject();
        if(inscrit || !result.existeEvento(id)) {
            if(!esVota) e = new Evento(id, titol, dataHora, lloc, inscripcio, presencial, numAss, inscrit, idUsuari);
            if(numAssAct != 0) e.setNumAssAct(numAssAct);
            if (assistit) {
                result.addHistorico(e);
            } else if(presencial)result.addEvento(e);
        }
        //if(esVota) readArrayVota(JRaux,e,false,-2);
        return e;
    }

    private void readArrayVota(JsonReader reader, Evento e, boolean assistit, int idUsuari) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            readObjectVota(reader, e, assistit, idUsuari);
        }
        reader.endArray();
    }

    private void readObjectVota(JsonReader reader, Evento evento, boolean assistit, int idUsuari) throws IOException {
        String titol = null, dataHoraIni = null, dataHoraFin = null, aux;
        int id = 0;
        String feta = "votacioNoFeta";
        ArrayList<Pregunta> preg = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("id")) id = Integer.parseInt(reader.nextString());
            else if(name.equals("titol")) titol = reader.nextString();
            else if(name.equals("dataHoraIni")) dataHoraIni = reader.nextString();
            else if(name.equals("dataHoraFin")) dataHoraFin = reader.nextString();
            else if(name.equals("feta")) {
                aux = reader.nextString();
                feta = aux;
            }
            else if(name.equals("preguntes")) preg = readArrayPreg(reader);
            else reader.skipValue();
        }
        reader.endObject();
        if(!result.existeVotacion(titol)) {
            boolean votaFeta = result.isHistoricoAsistido(evento.getTitol(), titol);
            if (votaFeta) feta = "votacioFeta";
            Votacion v = new Votacion(id, titol, dataHoraIni, dataHoraFin, evento.getTitol(), evento.getId(), feta, idUsuari);
            v.setPreguntes(preg);
            if (feta.equals("votacioNoFeta") && (assistit || !evento.isPresencial()) && !votaFeta)
                result.addVotacion(v);
            result.addVotacionEvento(evento.getTitol(), v, assistit);
        }
    }

    private ArrayList<Pregunta> readArrayPreg(JsonReader reader) throws IOException {
        ArrayList<Pregunta> preg = new ArrayList<Pregunta>();
        reader.beginArray();
        while (reader.hasNext()) {
            preg.add(readObjectPreg(reader));
        }
        reader.endArray();
        return preg;
    }

    private Pregunta readObjectPreg(JsonReader reader) throws IOException {
        int id = 0; boolean obligatoria = false;
        String titol = null, opcions = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("id")) id = Integer.parseInt(reader.nextString());
            else if(name.equals("titol")) titol = reader.nextString();
            else if(name.equals("opcions")) opcions = reader.nextString();
            else if(name.equals("obligatoria")) {
                String aux = reader.nextString();
                if (aux.equals('0')) obligatoria = false;
                else obligatoria = true;
            }
            else reader.skipValue();
        }
        reader.endObject();
        ArrayList<String> vOpcions = new ArrayList<String>();
        if(!opcions.equals("")) {
            String[] lOpcions = opcions.split(", ");
            vOpcions = new ArrayList<String>(Arrays.asList(lOpcions));
        }
        Pregunta p = new Pregunta(id,titol,vOpcions,obligatoria);
        return p;
    }


}
