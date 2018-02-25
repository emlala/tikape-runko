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
import tikape.runko.domain.RaakaAine;

/**
 *
 * @author Kaisla
 */
public class RaakaAineDao implements Dao<RaakaAine, Integer> {
    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        List<RaakaAine> ainekset = new ArrayList<>();
        
        try(Connection conn = database.getConnection(); 

        ResultSet rs = conn.prepareStatement("SELECT id, nimi FROM RaakaAine").executeQuery()){
            while (rs.next()) {
                ainekset.add(new RaakaAine(rs.getInt("id"), rs.getString("nimi")));
            }
        }
        return ainekset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RaakaAine saveOrUpdate(RaakaAine r) throws SQLException {
        RaakaAine byName = findByName(r.getNimi());

        if (byName != null) {
            return byName;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
            stmt.setString(1, r.getNimi());
            stmt.executeUpdate();
        }

        return findByName(r.getNimi());
    }

    public RaakaAine findByName(String nimi) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi FROM RaakaAine WHERE nimi = ?");
            stmt.setString(1, nimi);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new RaakaAine(result.getInt("id"), result.getString("nimi"));
        }
    }

    public List<RaakaAine> findBySmoothie(Integer id) throws SQLException {
        List<RaakaAine> ainekset = new ArrayList<>();
        
        try(Connection conn = database.getConnection(); 

        ResultSet rs = conn.prepareStatement("SELECT RaakaAine.id, RaakaAine.nimi FROM RaakaAine, Annos, "
                + "AnnosRaakaAine WHERE Annos.id = " + id + " "
                + "AND Annos.id = AnnosRaakaAine.annos_id "
                + "AND RaakaAine.id = AnnosRaakaAine.raakaAine_id").executeQuery()){
            while (rs.next()) {
                ainekset.add(new RaakaAine(rs.getInt("id"), rs.getString("nimi")));
            }
        }
        return ainekset;
    }
    
    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
