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
        throw new UnsupportedOperationException("Not supported yet.");
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement(
//                "SELECT Annos.id AS annos, RaakaAine.nimi AS raakaAine "
//                        + "FROM Annos, AnnosRaakaAine, RaakaAine "
//                        + "WHERE Annos.id = ? "
//                        + "AND AnnosRaakaAine.annos_id = Annos.id "
//                        + "AND AnnosRaakaAine.raakaAine_id = raakaAine.id ");
//        stmt.setObject(1, key);
//
//        ResultSet rs = stmt.executeQuery();
//        boolean hasOne = rs.next();
//        if (!hasOne) {
//            return null;
//        }
//
//        Integer id = rs.getInt("raakaAine_id");
//        String nimi = rs.getString("nimi");
//        Integer annosId = rs.getInt("annos_id");
//
//        Annos o = new Annos(id, nimi, annosId,);
//
//        rs.close();
//        stmt.close();
//        connection.close();
//
//        return o;
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//        List<AnnosRaakaAine> annosainekset = new ArrayList<>();
//
//        try (Connection conn = database.getConnection();
//                ResultSet rs = conn.prepareStatement("SELECT id, nimi FROM RaakaAine").executeQuery()) {
//            while (rs.next()) {
//                annosainekset.add(new AnnosRaakaAine(rs.getInt("id"), rs.getString("nimi")));
//            }
//        }
//        return annosainekset;
//    }

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

}
