package com.ebridgevas.vas.dao;

import com.ebridgevas.vas.dto.BundleTypeDTO;
import com.ebridgevas.vas.util.DataBaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ServiceDAO {


    private static Connection connection;

    static {
        try {
            connection = DataBaseConnectionPool.getConnection();
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }
    public static Map<String, BundleTypeDTO> service(String serviceID) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                " SELECT bundle_id, bundle_name, bundle_price, bundle_window                                  " +
                        "   FROM bundle_types                                                                         ";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);

            ResultSet rs
                    = stmt.executeQuery();
            Map<String, BundleTypeDTO> types
                    = new HashMap<String, BundleTypeDTO>();
            while (rs.next()) {
                types.put(
                        rs.getString("bundle_id"),
                        new BundleTypeDTO(
                                rs.getString("bundle_id"),
                                rs.getString("bundle_name"),
                                rs.getBigDecimal("bundle_price"),
                                rs.getLong("bundle_window")));
            }

            return types;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }

        return null;
    }

}
