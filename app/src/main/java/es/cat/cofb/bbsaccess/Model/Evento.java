package es.cat.cofb.bbsaccess.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ericguti on 12/09/2015.
 */
public class Evento implements Serializable {
    private int id;
    private String titol;
    private String dataHora;
    private String lloc;
    private boolean inscripcio;
    private boolean presencial;
    private boolean inscrit;
    private int numAss;
    private ArrayList<Votacion> votacions;
    private int idUsuari;

    public ArrayList<Votacion> getVotacions() {
        return votacions;
    }

    public void setVotacions(ArrayList<Votacion> votacions) {
        this.votacions = votacions;
    }



    public Evento(int id, String titol, String dataHora, String lloc, boolean inscripcio, boolean presencial, int numAss, boolean inscrit, int idUsuari) {
        this.id = id;
        this.titol = titol;
        this.dataHora = dataHora;
        this.lloc = lloc;
        this.inscripcio = inscripcio;
        this.presencial = presencial;
        this.numAss = numAss;
        this.inscrit = inscrit;
        this.votacions = new ArrayList<Votacion>();
        this.idUsuari = idUsuari;
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

    public int getNumAss() {
        return numAss;
    }

    public void setNumAss(int numAss) {
        this.numAss = numAss;
    }

    public boolean isInscrit() {
        return inscrit;
    }

    public void setInscrit(boolean inscrit) {
        this.inscrit = inscrit;
    }

    public int getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }
}
