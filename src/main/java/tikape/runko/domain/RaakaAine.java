/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.ArrayList;

/**
 *
 * @author sperande
 */
public class RaakaAine {
    private Integer id;
    private String nimi;
    private ArrayList<AnnosRaakaAine> raakaAineitaAnnoksessa;
    

    public RaakaAine(Integer id, String nimi, ArrayList<AnnosRaakaAine> raakaAineitaAnnoksessa) {
        this.id = id;
        this.nimi = nimi;
        this.raakaAineitaAnnoksessa = raakaAineitaAnnoksessa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    public ArrayList<AnnosRaakaAine> getRaakaAineitaAnnoksessa() {
        return this.raakaAineitaAnnoksessa;
    }
    public void setRaakaAineitaAnnoksessa(ArrayList<AnnosRaakaAine> lista) {
        this.raakaAineitaAnnoksessa = lista;
    }

}
