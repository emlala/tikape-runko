package tikape.runko.domain;

import java.util.ArrayList;


public class Annos {

    private Integer id;
    private String nimi;    
    private ArrayList<AnnosRaakaAine> raakaAineitaAnnoksessa;
    
    public Annos(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public Annos(Integer id, String nimi, ArrayList<AnnosRaakaAine> raakaAineitaAnnoksessa) {
        this.id = id;
        this.nimi = nimi;
        this.raakaAineitaAnnoksessa = new ArrayList<AnnosRaakaAine>();
        
        
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
