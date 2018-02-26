/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;
import tikape.runko.domain.RaakaAine;

/**
 *
 * @author Kaisla
 */
public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {
    private Database database;

    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer annosId = rs.getInt("annosId");
        Integer raakaAineId = rs.getInt("raakaAineId");
        Integer jarjestys = rs.getInt("jarjestys");
        String maara = rs.getString("maara");
        String ohje = rs.getString("ohje");

        AnnosRaakaAine o = new AnnosRaakaAine(id, annosId, raakaAineId, jarjestys, maara, ohje);

        rs.close();
        stmt.close();
        connection.close();

        return o;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
        
        try(Connection conn = database.getConnection(); 

        ResultSet rs = conn.prepareStatement("SELECT id, nimi FROM AnnosRaakaAine").executeQuery()){
            while (rs.next()) {
                annosRaakaAineet.add(new AnnosRaakaAine(rs.getInt("id"), rs.getInt("annosId"), rs.getInt("raakaAineId"),
                rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("ohje")));
                //this.id = id;
        
            }
        }
        return annosRaakaAineet;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine ar) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO AnnosRaakaAine (annos_id, raakaAine_id, "
                        + "jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, ar.getAnnosId());
            stmt.setInt(2, ar.getRaakaAineId());
            stmt.setInt(3, ar.getJarjestys());
            stmt.setString(4, ar.getMaara());
            stmt.setString(5, ar.getOhje());
            
            stmt.executeUpdate();
        }

        return null;
    }
    
//    public List<Annos> findByIng(String ing){
//        
//    }
    
        public List<AnnosRaakaAine> findBySmoothieId(Integer id) throws SQLException {
        List<AnnosRaakaAine> ainekset = new ArrayList<>();
        
        try(Connection conn = database.getConnection(); 

        ResultSet rs = conn.prepareStatement("SELECT DISTINCT AnnosRaakaAine.id, AnnosRaakaAine.annos_id, "
                + "AnnosRaakaAine.raakaAine_id, AnnosRaakaAine.jarjestys, AnnosRaakaAine.maara, "
                + "AnnosRaakaAine.ohje "
                + "FROM AnnosRaakaAine, Annos, RaakaAine "
                + "WHERE Annos.id = " + id + " "
                + "AND Annos.id = AnnosRaakaAine.annos_id ").executeQuery()){
            while (rs.next()) {
                ainekset.add(new AnnosRaakaAine(rs.getInt("id"), rs.getInt("annos_id"), 
                        rs.getInt("raakaAine_id"), rs.getInt("jarjestys"), 
                        rs.getString("maara"), rs.getString("ohje")));
            }
        }
        return ainekset;
    } 

}
