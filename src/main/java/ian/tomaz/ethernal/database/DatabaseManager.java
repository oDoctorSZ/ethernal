package ian.tomaz.ethernal.database;

import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.models.RegionModel;
import ian.tomaz.ethernal.models.TransactionsModel;
import ian.tomaz.ethernal.utils.EthernalMaps;
import org.bukkit.Bukkit;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    static Connection connection;
    Statement statement;

    public DatabaseManager() {
        connection = new DatabaseConnection().getConnection();
    }

    public static void savePlayerData(PlayerModel model) {
        PreparedStatement stm = null;
        ResultSet resultSet = null;

        try {

            stm = connection.prepareStatement("select * from player where uuid = ?");
            stm.setString(1, model.getUuid());

            resultSet = stm.executeQuery();

            if (resultSet.next()) {

                stm = connection.prepareStatement("update player set name = ?, race = ?, balance = ?, profession = ?, regions = ? where uuid = ?");
                stm.setString(1, model.getName());
                stm.setString(2, model.getRace());
                stm.setDouble(3, model.getBalance());
                stm.setString(4, model.getProfession());
                stm.setString(5, model.getRegions());
                stm.setString(6, model.getUuid());

                stm.executeUpdate();

            } else {

                stm = connection.prepareStatement("insert into player (uuid, name, race, balance, profession, regions) values (?,?,?,?,?,?)");
                stm.setString(1, model.getUuid());
                stm.setString(2, model.getName());
                stm.setString(3, model.getRace());
                stm.setDouble(4, model.getBalance());
                stm.setString(5, model.getProfession());
                stm.setString(6, model.getRegions());

                stm.execute();
            }

            stm.close();

        } catch (Exception x) {
            Bukkit.getConsoleSender().sendMessage("Failed to save player database :'( ");
            x.printStackTrace();
        }

    }

    public static void saveRegionData(RegionModel model) {
        PreparedStatement stm = null;
        ResultSet resultSet = null;

        try {

            stm = connection.prepareStatement("select * from region where name = ?");
            stm.setString(1, model.getName());

            resultSet = stm.executeQuery();

            if (resultSet.next()) {

                stm = connection.prepareStatement("update region set tax = ?, price = ?, occupied = ?, payment = ?, owner_uuid = ?, members = ? where name = ?");
                stm.setDouble(1, model.getTax());
                stm.setDouble(2, model.getPrice());
                stm.setBoolean(3, model.isOccupied());
                stm.setString(4, model.getPayment());
                stm.setString(5, model.getOwner_uuid());
                stm.setString(6, model.getMembers());
                stm.setString(7, model.getName());

                stm.executeUpdate();

            } else {

                stm = connection.prepareStatement("insert into region (name, tax, price, occupied, payment, owner_uuid, members) values (?,?,?,?,?,?,?)");
                stm.setString(1, model.getName());
                stm.setDouble(2, model.getTax());
                stm.setDouble(3, model.getPrice());
                stm.setBoolean(4, model.isOccupied());
                stm.setString(5, model.getPayment());
                stm.setString(6, model.getOwner_uuid());
                stm.setString(7, model.getMembers());

                stm.execute();
            }

            stm.close();

        } catch (Exception x) {
            Bukkit.getConsoleSender().sendMessage("Failed to save region database :'( ");
            x.printStackTrace();
        }

    }

    public static void saveTransactions(TransactionsModel model) {
        PreparedStatement stm = null;
        ResultSet resultSet = null;

        try {

            stm = connection.prepareStatement("select * from transactions where id = ?");
            stm.setString(1, model.getUuid());

            stm = connection.prepareStatement("insert into transactions (uuid, uuid_target, value) values (?,?,?)");
            stm.setString(1, model.getUuid());
            stm.setString(2, model.getUuidTarget());
            stm.setDouble(3, model.getValue());

            stm.execute();
            stm.close();

        } catch (Exception x) {
            Bukkit.getConsoleSender().sendMessage("Failed to save player database :'( ");
            x.printStackTrace();
        }

    }

    public static void loadTransactionsList() throws SQLException {

        PreparedStatement stm = null;
        stm = connection.prepareStatement("SELECT uuid, uuid_target, value FROM transactions");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            String uuid = rs.getString(1);
            String uuid_target = rs.getString(2);
            double value = rs.getDouble(3);

            EthernalMaps.transactionsList.add(new TransactionsModel(uuid, uuid_target, value));
        }

        stm.close();

    }

    public static void loadPlayersOnHash() throws SQLException {

        PreparedStatement stm = null;
        stm = connection.prepareStatement("SELECT uuid, name, race, balance, profession, regions FROM player");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            String uuid = rs.getString(1);
            String name = rs.getString(2);
            String race = rs.getString(3);
            double balance = rs.getDouble(4);
            String profession = rs.getString(5);
            String regions = rs.getString(6);

            EthernalMaps.playerHash.put(uuid, new PlayerModel(uuid, name, race, balance, profession, regions));
        }

        stm.close();

    }

    public static void loadRegionsOnList() throws SQLException {

        PreparedStatement stm = null;
        stm = connection.prepareStatement("SELECT name, tax, price, occupied, payment, owner_uuid, members FROM region");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            String name = rs.getString(1);
            double tax = rs.getDouble(2);
            double price = rs.getDouble(3);
            boolean occupied = rs.getBoolean(4);
            String payment = rs.getString(5);
            String owner_uuid = rs.getString(6);
            String members = rs.getString(7);

            EthernalMaps.regions.add(new RegionModel(name, tax, price, occupied, payment, owner_uuid, members));
        }

        stm.close();

    }

    public void saveRegionData() {
        PreparedStatement stm = null;
        ResultSet resultSet = null;




    }



}
