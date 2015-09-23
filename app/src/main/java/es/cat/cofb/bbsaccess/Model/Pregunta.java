package es.cat.cofb.bbsaccess.Model;

import java.util.ArrayList;

/**
 * Created by egutierrez on 14/09/2015.
 */
public class Pregunta {
    private int id;
    private String titol;
    private ArrayList<String> opcions;
    private boolean obligatoria;

    public Pregunta(int id, String titol, ArrayList<String> opcions, boolean obligatoria) {
        this.id = id;
        this.titol = titol;
        this.opcions = opcions;
        this.obligatoria = obligatoria;
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

    public ArrayList<String> getOpcions() {
        return opcions;
    }

    public void setOpcions(ArrayList<String> opcions) {
        this.opcions = opcions;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }
}
