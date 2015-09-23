package es.cat.cofb.bbsaccess.Model;

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
}
