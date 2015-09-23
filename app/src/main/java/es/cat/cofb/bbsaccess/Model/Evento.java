package es.cat.cofb.bbsaccess.Model;

import java.util.ArrayList;

/**
 * Created by ericguti on 12/09/2015.
 */
public class Evento {
    private int id;
    private String titol;
    private String dataHora;
    private String lloc;
    private boolean inscripcio;
    private boolean presencial;
    private ArrayList<Votacion> votacions;

    public ArrayList<Votacion> getVotacions() {
        return votacions;
    }

    public void setVotacions(ArrayList<Votacion> votacions) {
        this.votacions = votacions;
    }

    public Evento(int id, String titol, String dataHora, String lloc, boolean inscripcio, boolean presencial) {
        this.id = id;
        this.titol = titol;
        this.dataHora = dataHora;
        this.lloc = lloc;
        this.inscripcio = inscripcio;
        this.presencial = presencial;
        this.votacions = new ArrayList<Votacion>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getLloc() {
        return lloc;
    }

    public void setLloc(String lloc) {
        this.lloc = lloc;
    }

    public boolean isInscripcio() {
        return inscripcio;
    }

    public void setInscripcio(boolean inscripcio) {
        this.inscripcio = inscripcio;
    }

    public boolean isPresencial() {
        return presencial;
    }

    public void setPresencial(boolean presencial) {
        this.presencial = presencial;
    }

    public void addVotacion(Votacion v) {
        votacions.add(v);
    }
}
