package com.ebridgevas.vas.dao;

import com.ebridgevas.vas.dto.BundleTypeDTO;
import com.ebridgevas.vas.util.DataBaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author david@tekeshe.com
 */
public class MessageServiceDAO {

    private static Connection connection;

    static {
        try {
            connection = DataBaseConnectionPool.getConnection();
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    public static int sendMessage(String sourceID, String destinationID, String payload) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        // check if subscriber exists
        // if ( findById(serviceId, subscriberId).isEmpty() ) {
        //    return 1;
        //}


        String sql =
                " INSERT INTO outbound_msg ( message_id, " +
                        "           source_id, destination_id, payload, channel_type, message_status ) " +
                        " VALUES ( ?, ?, ?, ?, ?, ? )";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, System.currentTimeMillis());
            stmt.setString(2, sourceID);
            stmt.setString(3, destinationID);
            stmt.setString(4, payload);
            stmt.setString(5, "SMS");
            stmt.setString(6, "QUEUED");

            stmt.executeUpdate();

            return 0;
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR WHILE CREATING SUBSCRIPTION: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } finally {
            try {stmt.close();} catch (Exception e){}
        }

    }

}
