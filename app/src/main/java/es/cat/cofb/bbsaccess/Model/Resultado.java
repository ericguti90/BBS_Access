package es.cat.cofb.bbsaccess.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by egutierrez on 22/09/2015.
 */
public class Resultado {
    private ArrayList<Evento> eventos;
    private ArrayList<Votacion> votaciones;
    private ArrayList<Evento> historico;

    public Resultado() {
        this.eventos = new ArrayList<Evento>();
        this.votaciones = new ArrayList<Votacion>();
        this.historico = new ArrayList<Evento>();
    }

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    public Evento getEvento(int id) {
        for(int i = 0; i < eventos.size();++i) {
            if(eventos.get(i).getId() == id) return eventos.get(i);
        }
        return null;
    }

    public Evento getEventoPos(int pos) {
        return eventos.get(pos);
    }

    public Votacion getVotacionPos(int pos) {
        return votaciones.get(pos);
    }

    public Evento getHistoricoPos(int pos) {
        return historico.get(pos);
    }

    public void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }

    public ArrayList<Votacion> getVotaciones() {
        return votaciones;
    }

    public void setVotaciones(ArrayList<Votacion> votaciones) {
        this.votaciones = votaciones;
    }

    public ArrayList<Evento> getHistorico() {
        return historico;
    }

    public Evento getHistoricoId(int id) {
        for(int i = 0; i < historico.size(); ++i){
            if(historico.get(i).getId() == id) return historico.get(i);
        }
        return null;
    }

    public void setHistorico(ArrayList<Evento> historico) {
        this.historico = historico;
    }

    public void addEvento(Evento e) {
        eventos.add(e);
    }

    public void addVotacion(Votacion v) {
        votaciones.add(v);
    }

    public void addHistorico(Evento e) {
        historico.add(e);
    }

    public void addVotacionEvento(String titol, Votacion v, boolean assistit) {
        boolean trobat = false;
        if(!assistit) {
            for (int i = 0; i < eventos.size() && !trobat; ++i) {
                if (((eventos.get(i)).getTitol()).equals(titol)) {
                    (eventos.get(i)).addVotacion(v);
                    trobat = true;
                }
            }
        }
        else {
            for (int i = 0; i < historico.size() && !trobat; ++i) {
                if (((historico.get(i)).getTitol()).equals(titol)) {
                    (historico.get(i)).addVotacion(v);
                    trobat = true;
                }
            }
        }
    }

    public boolean existeEvento(int id) {
        for(int i = 0; i < eventos.size(); ++i){
            if(eventos.get(i).getId() == id) return true;
        }
        for(int i = 0; i < historico.size(); ++i){
            if(historico.get(i).getId() == id) return true;
        }
        return false;
    }

    public int getPosVotacion(int id) {
        for(int i = 0; i < votaciones.size(); ++i) {
            if(votaciones.get(i).getId() == id) return i;
        }
        return -1;
    }

    public boolean isHistoricoAsistido (String evento, String vota) {
        for(int i = 0; i < historico.size(); ++i){
            if(historico.get(i).getTitol().equals(evento)) {
                ArrayList<Votacion> aux = historico.get(i).getVotacions();
                for(int j = 0; j < aux.size(); ++j) {
                    if(aux.get(j).getTitol().equals(vota)) {
                        if(aux.get(j).getFeta().equals("votacioFeta")) return true;
                        else return false;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
