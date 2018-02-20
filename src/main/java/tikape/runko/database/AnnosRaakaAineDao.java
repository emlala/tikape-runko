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
        throw new UnsupportedOperationException("Not supported yet.");
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine");
//
//        ResultSet rs = stmt.executeQuery();
//        List<Annos> annokset = new ArrayList<>();
//        while (rs.next()) {
//            Integer id = rs.getInt("id");
//            String nimi = rs.getString("nimi");
//
//            annokset.add(new Annos(id, nimi));
//        }
//
//        rs.close();
//        stmt.close();
//        connection.close();
//
//        return annokset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
