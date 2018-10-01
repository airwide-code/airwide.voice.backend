package com.ebridgevas.vas.dao;

import com.ebridgevas.vas.dto.BundleTypeDTO;
import com.ebridgevas.vas.dto.MediaTrackPathDTO;
import com.ebridgevas.vas.dto.ProductIDKey;
import com.ebridgevas.vas.dto.SubscriptionDTO;
import com.ebridgevas.vas.util.DataBaseConnectionPool;
import java.util.List;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IVRSubscriptionDAO {

    private static Connection connection;

    static {
        try {
            connection = DataBaseConnectionPool.getConnection();
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    public static List<SubscriptionDTO> subscriptionLookup(String serviceId,
                                                           String subscriberId) {
        System.out.println("subscriptionLookup ( serviceId = " + serviceId +
                                ", subscriberId : " + subscriberId + " )");
        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        // check if subscriber exists
        List<SubscriptionDTO> subscriptions
                = findById(serviceId, subscriberId);

//        SubscriptionDTO result
//                =  subscriptions == null || subscriptions.isEmpty()
//                        ? null : subscriptions.get(0);
//
//        System.out.println("subscriptionLookup result : " + result);

        return subscriptions;
    }

    public static int subscribe(String serviceId, String subscriberId, BundleTypeDTO bundleType) {

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
                " INSERT INTO ivr_subscription (" +
                "           service_id, subscriber_id, bundle_id, balance_seconds, " +
                "           balance_value, update_date, subscription_status ) " +
                " VALUES ( ?, ?, ?, ?, ?, ?, ? )";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, serviceId);
            stmt.setString(2, subscriberId);
            stmt.setString(3, bundleType.getId());
            stmt.setLong(4, bundleType.getBundleWindow());
            stmt.setBigDecimal(5, bundleType.getPrice());
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.setString(7, "active");

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


    public static int unsubscribe(String serviceId, String subscriberId) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        // check if subscriber exists
        List<SubscriptionDTO> subscriptions
                = findById(serviceId, subscriberId);

        if ( subscriptions.isEmpty() ) {
            return 1;
        }

//        String bundleId = subscriptions.get(0).getBundleId();

        String sql =
                " UPDATE ivr_subscription " +
                "     SET subscription_status = 'cancelled_by_user'," +
                "         update_date = ? " +
                "  WHERE service_id    = ? " +
                "    AND subscriber_id = ? ";
//                "    AND bundle_id     = ? ";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setString(2, serviceId);
            stmt.setString(3, subscriberId);
//            stmt.setString(4, bundleId);

            stmt.executeUpdate();

            resetCurrentTrackID(serviceId, subscriberId);

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {stmt.close();} catch (Exception e){}
        }

    }

    public static int unsubscribe(String serviceId, String subscriberId, Long id) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        // check if subscriber exists
        List<SubscriptionDTO> subscriptions
                = findById(serviceId, subscriberId);

        if ( subscriptions.isEmpty() ) {
            return 1;
        }

        String sql =
                " UPDATE ivr_subscription " +
                        "     SET subscription_status = 'cancelled_by_user'," +
                        "         update_date = ? " +
                        "  WHERE service_id    = ? " +
                        "    AND subscriber_id = ? ";
                //        "    AND id     = ? ";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setString(2, serviceId);
            stmt.setString(3, subscriberId);
            stmt.setLong(4, id);

            stmt.executeUpdate();

            resetCurrentTrackID(serviceId, subscriberId);

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {stmt.close();} catch (Exception e){}
        }

    }

    public static List<SubscriptionDTO> findById(String serviceId, String subscriberId) {

        System.out.println("findById ( serviceId : " + serviceId +
                                ", subscriberId " + subscriberId + ")");
        List<SubscriptionDTO> subscriptions
                = new ArrayList<SubscriptionDTO>();

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                " SELECT s.id, s.subscriber_id, s.service_id, s.bundle_id, s.balance_seconds, s.balance_value,      " +
                "        t.bundle_name, t.bundle_price                                                              " +
                "   FROM ivr_subscription s                                                                         " +
                "   JOIN bundle_types t " +
                "     ON ( s.service_id = t.service_id                      " +
                "    AND s.bundle_id = t.bundle_id)         " +
                "  WHERE s.service_id = ?                                                                          " +
                "    AND s.subscriber_id = ? " +
                "    AND s.subscription_status = ? " +
                "  ORDER BY balance_seconds ";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, serviceId);
            stmt.setString(2, subscriberId);
            stmt.setString(3, "active");

            ResultSet rs
                    = stmt.executeQuery();

            while (rs.next()) {
                subscriptions.add(
                        new SubscriptionDTO(
                                rs.getLong("id"),
                                rs.getString("subscriber_id"),
                                rs.getString("service_id"),
                                rs.getString("bundle_id"),
                                rs.getLong("balance_seconds"),
                                rs.getBigDecimal("balance_value"),
                                rs.getString("bundle_name"),
                                rs.getBigDecimal("bundle_price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error reading ivr_subscription: " + e.getMessage());
            // return null;
        } finally {
            try {stmt.close();} catch (Exception e){}
        }
        System.out.println("subscriptions : " + subscriptions.size());
        return subscriptions;
    }


    public static Map<String, BundleTypeDTO> findBundleTypes() {

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

    /*
    describe product_media_tracker;                                                                                                                                                     +------------------+-------------+------+-----+---------+-------+
| Field            | Type        | Null | Key | Default | Extra |
+------------------+-------------+------+-----+---------+-------+
| service_id       | varchar(20) | NO   | PRI | NULL    |       |
| subscriber_id    | varchar(60) | NO   | PRI | NULL    |       |
| category_id      | int(11)     | NO   | PRI | NULL    |       |
| product_id       | int(11)     | NO   | PRI | NULL    |       |
| current_track_id | int(11)     | NO   |     | 1       |       |
     */

    public static Integer currentTrackID(String serviceID,
                                         String subscriberID,
                                         int categoryID,
                                         int productID) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                " SELECT current_track_id                                                                       " +
                "   FROM product_media_tracker                                                                  " +
                "  WHERE service_id = ?                                                                         " +
                "    AND subscriber_id = ?                                                                      " +
                "    AND category_id = ?                                                                        " +
                "    AND product_id = ?                                                                         ";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, serviceID);
            stmt.setString(2, subscriberID);
            stmt.setInt(3, categoryID);
            stmt.setInt   (4, productID);

            ResultSet rs
                    = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("current_track_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }

        return null;
    }

    public static void saveCurrentTrackID(
                                         String serviceID,
                                         String subscriberID,
                                         int categoryID,
                                         int productID) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                " INSERT INTO product_media_tracker(                                                                " +
                "               service_id, subscriber_id, category_id, product_id, current_track_id )              " +
                "  VALUES (?, ?, ?, ?, ?)                                                                           " ;

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, serviceID);
            stmt.setString(2, subscriberID);
            stmt.setInt(3, categoryID);
            stmt.setInt(4, productID);
            stmt.setInt   (5, 1);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }
    }

    public static void saveCurrentTrackID(
            String serviceID,
            String subscriberID,
            int categoryID,
            int productID,
            Integer currentTrackID ) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
            " UPDATE product_media_tracker " +
            "   SET  current_track_id = ?                                                                   " +
            "  WHERE service_id = ?                                                                         " +
            "    AND subscriber_id = ?                                                                      " +
            "    AND category_id = ?                                                                        " +
            "    AND product_id = ?                                                                         ";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, currentTrackID);
            stmt.setString(2, serviceID);
            stmt.setString(3, subscriberID);
            stmt.setInt(4, categoryID);
            stmt.setInt(5, productID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }
    }

    public static void resetCurrentTrackID(
            String serviceID,
            String subscriberID) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                " UPDATE product_media_tracker " +
                "    SET  current_track_id = 1                                                                  " +
                "  WHERE service_id = ?                                                                         " +
                "    AND subscriber_id = ?                                                                      ";


        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, serviceID);
            stmt.setString(2, subscriberID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }
    }

    public static MediaTrackPathDTO mediaPath(
                                     String serviceID,
                                     int categoryID,
                                     int productID,
                                     int trackID) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                " SELECT track_path, track_title_path, track_duration                                           " +
                "   FROM product_media                                                                          " +
                "  WHERE service_id = ?                                                                         " +
                "    AND category_id = ?                                                                        " +
                "    AND product_id = ?                                                                         " +
                "    AND track_id = ?                                                                           ";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, serviceID);
            stmt.setInt(2, categoryID);
            stmt.setInt(3, productID);
            stmt.setInt   (4, trackID);

            ResultSet rs
                    = stmt.executeQuery();

            if (rs.next()) {
                return
                        new MediaTrackPathDTO(
                                rs.getString("track_path"),
                                rs.getString("track_title_path"),
                                rs.getLong("track_duration"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }

        return null;
    }

    public static MediaTrackPathDTO favouriteMediaPath(
            String serviceID,
            int categoryID,
            int productID) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                " SELECT track_path, track_title_path, track_duration, track_id                                " +
                "   FROM product_media                                                                          " +
                "  WHERE service_id = ?                                                                         " +
                "    AND category_id = ?                                                                        " +
                "    AND product_id = ?                                                                         " +
                "    AND play_count = (                                                                         " +
                "         SELECT MAX(play_count)                                                                " +
                "           FROM product_media                                                                  " +
                "          WHERE service_id = ?                                                                 " +
                "            AND category_id = ?                                                                " +
                "            AND product_id = ?                                                                 " +
                "       )                                                                                       ";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, serviceID);
            stmt.setInt(2, categoryID);
            stmt.setInt(3, productID);
            stmt.setString(4, serviceID);
            stmt.setInt(5, categoryID);
            stmt.setInt(6, productID);
            ResultSet rs
                    = stmt.executeQuery();

            if (rs.next()) {
                return
                        new MediaTrackPathDTO(
                                rs.getString("track_path"),
                                rs.getString("track_title_path"),
                                rs.getLong("track_duration"),
                                rs.getInt("track_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }

        return null;
    }

    public static void incrementPlayCounter(
            String serviceID,
            int categoryID,
            int productID,
            int trackID) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                    " UPDATE product_media                                                                          " +
                    "    SET play_count = play_count + 1                                                            " +
                    "  WHERE service_id = ?                                                                         " +
                    "    AND category_id = ?                                                                        " +
                    "    AND product_id = ?                                                                         " +
                    "    AND track_id = ?                                                                           ";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, serviceID);
            stmt.setInt(2, categoryID);
            stmt.setInt(3, productID);
            stmt.setInt   (4, trackID);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }
    }

    public static Map<ProductIDKey, Integer> maxTrackIDs() {

        Map<ProductIDKey, Integer> maxTrackIDs
                = new HashMap<ProductIDKey, Integer>();

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                "   SELECT service_id, category_id, product_id, MAX(track_id) AS maxTrackID                       " +
                "     FROM product_media                                                                          " +
                " GROUP BY category_id, product_id                                                                " ;

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);

            ResultSet rs
                    = stmt.executeQuery();

            while (rs.next()) {
                maxTrackIDs.put(
                        new ProductIDKey(
                                rs.getString("service_id"),
                                rs.getInt("category_id"),
                                rs.getInt("product_id")),
                        rs.getInt("maxTrackID") );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }

        return maxTrackIDs;
    }

    public static void updateSubscription(
            String serviceID,
            String subscriberID,
            Long id,
            long balanceSeconds ) {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql =
                " UPDATE ivr_subscription " +
                        "   SET  balance_seconds = ?                                                              " +
                        "  WHERE service_id = ?                                                                   " +
                        "    AND subscriber_id = ?                                                                " +
                        "    AND id = ?                                                                           " ;

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, balanceSeconds);
            stmt.setString(2, serviceID);
            stmt.setString(3, subscriberID);
            stmt.setLong(4, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }
    }

    /*
    SELECT category_id, product_id, MAX(track_id) FROM product_media WHERE service_id = '789' GROUP BY category_id,  product_id;
+-------------+------------+---------------+
| category_id | product_id | MAX(track_id) |
+-------------+------------+---------------+
| 1           | 1          |             8 |
| 2           | 1          |            12 |
+-------------+------------+---------------+
     */
    public static void main(String[] args) {

//        BundleTypeDTO bundleType
//                = new BundleTypeDTO();
//        subscribe("789", "263733661588", bundleType);

        /*
        SubscriptionDTO dto =
                new SubscriptionDTO(
                        "6001",
                        "agriculture",
                        "2",
                        300L,
                        new BigDecimal(10.00));

+-------------+-----------+-------------+--------------+---------------+
| service_id  | bundle_id | bundle_name | bundle_price | bundle_window |
+-------------+-----------+-------------+--------------+---------------+
| agriculture | 1         | monthly     |         1.00 |       2592000 |
| agriculture | 2         | weekly      |         0.35 |        604800 |
| agriculture | 3         | daily       |         0.10 |         86400 |
+-------------+-----------+-------------+--------------+---------------+
        */

        /*
        SubscriptionDTO result = findById("6001");
        System.out.println(result);

        Map<String, BundleTypeDTO> types
                = findBundleTypes();
        System.out.println(types.get("2"));
        */
    }
}
