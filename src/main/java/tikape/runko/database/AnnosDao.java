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

public class AnnosDao implements Dao<Annos, Integer> {

    private Database database;

    public AnnosDao(Database database) {
        this.database = database;
    }

    @Override
    public Annos findOne(Integer key) throws SQLException {
        Annos o;
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos WHERE id = ?");
            stmt.setObject(1, key);
            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            o = new Annos(id, nimi);
            rs.close();
            stmt.close();
        }

        return o;
    }

    @Override
    public List<Annos> findAll() throws SQLException {
        List<Annos> annokset = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet rs = conn.prepareStatement("SELECT id, nimi FROM Annos").executeQuery()) {
            while (rs.next()) {
                annokset.add(new Annos(rs.getInt("id"), rs.getString("nimi")));
            }
        }
        return annokset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = ?");
            stmt2.setInt(1, key);
            stmt2.executeUpdate();

            stmt.close();
            stmt2.close();
        }

    }

    @Override
    public Annos saveOrUpdate(Annos a) throws SQLException {
        Annos byName = findByName(a.getNimi());

        if (byName != null) {
            return byName;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos (nimi) VALUES (?)");
            stmt.setString(1, a.getNimi());
            stmt.executeUpdate();
        }

        return findByName(a.getNimi());
    }

    public Annos findByName(String nimi) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi FROM Annos WHERE nimi = ?");
            stmt.setString(1, nimi);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Annos(result.getInt("id"), result.getString("nimi"));
        }
    }

    public List<Annos> findByRaakaAineId(Integer id) throws SQLException {
        List<Annos> annokset = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet rs = conn.prepareStatement("SELECT Annos.id, Annos.nimi FROM RaakaAine, Annos, "
                        + "AnnosRaakaAine WHERE RaakaAine.id = " + id + " "
                        + "AND RaakaAine.id = AnnosRaakaAine.RaakaAine_id "
                        + "AND Annos.id = AnnosRaakaAine.Annos_id").executeQuery()) {

            while (rs.next()) {
                annokset.add(new Annos(rs.getInt("id"), rs.getString("nimi")));
            }
        }
        return annokset;
    }

}
