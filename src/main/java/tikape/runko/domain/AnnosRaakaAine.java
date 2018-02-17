/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Kaisla
 */
public class AnnosRaakaAine {
    private Integer id;
    private RaakaAine raakaAine;
    private Annos annos;   
    private String maara;
    private String ohje;
    
    public AnnosRaakaAine(Integer id, RaakaAine raakaAine, Annos annos, String maara, String ohje) {
        this.id = id;
        this.raakaAine = raakaAine;
        this.annos = annos;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public RaakaAine getRaakaAine() {
        return this.raakaAine;
    }
    public void setRaakaAine(RaakaAine raakaAine) {
        this.raakaAine = raakaAine;
    }
    public Annos getAnnos() {
        return this.annos;
    }
    public void setAnnos(Annos annos) {
        this.annos = annos;
    }
    public String getMaara() {
        return this.maara;
    }
    public void setMaara(String maara) {
        this.maara = maara;
    }
    public String getOhje() {
        return this.ohje;
    }
    public void setOhje(String ohje) {
        this.ohje = ohje;
    }
    
    
    
    
}
