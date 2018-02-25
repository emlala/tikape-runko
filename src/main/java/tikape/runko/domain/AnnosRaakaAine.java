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
    private Integer annosId;
    private Integer raakaAineId;
    private Integer jarjestys;
    private String maara;
    private String ohje;
    
    public AnnosRaakaAine(Integer id, Integer annosId, Integer raakaAineId, Integer jarj, String maara, String ohje) {
        this.id = id;
        this.annosId = annosId;
        this.raakaAineId = raakaAineId;
        this.jarjestys = jarj;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getRaakaAineId() {
        return this.raakaAineId;
    }
    public void setRaakaAine(Integer raId) {
        this.raakaAineId = raId;
    }
    public Integer getAnnosId() {
        return this.annosId;
    }
    public void setAnnos(Integer annId) {
        this.annosId = annId;
    }
    
    public Integer getJarjestys(){
        return this.jarjestys;
    }
    
    public void setJarjestys(Integer jarj){
        this.jarjestys = jarj;
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
