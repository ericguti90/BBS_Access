package es.cat.cofb.bbsaccess.Model;

import java.util.ArrayList;

/**
 * Created by ericguti on 12/09/2015.
 */
public class Votacion {
    private int id;
    private String titol;
    private String dataHoraIni;
    private String dataHoraFin;
    private String evento;
    private ArrayList<Pregunta> preguntes;

    public ArrayList<Pregunta> getPreguntes() {
        return preguntes;
    }

    public void setPreguntes(ArrayList<Pregunta> preguntes) {
        this.preguntes = preguntes;
    }

    public Votacion(int id, String titol, String dataHoraIni, String dataHoraFin, String evento) {
        this.id = id;
        this.titol = titol;
        this.dataHoraIni = dataHoraIni;
        this.dataHoraFin = dataHoraFin;
        this.evento = evento;
        this.preguntes = new ArrayList<Pregunta>();
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

    public String getDataHoraIni() {
        return dataHoraIni;
    }

    public void setDataHoraIni(String dataHoraIni) {
        this.dataHoraIni = dataHoraIni;
    }

    public String getDataHoraFin() {
        return dataHoraFin;
    }

    public void setDataHoraFin(String dataHoraFin) {
        this.dataHoraFin = dataHoraFin;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public void addPreguntes(Pregunta p) {
        preguntes.add(p);
    }
}
