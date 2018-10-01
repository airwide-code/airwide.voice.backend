package com.ebridgevas.vas.dao;

import com.ebridgevas.vas.util.DataBaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
 * @author david@tekeshe.com
 */
public class RegistrationServiceDAO {

    private static Connection connection;

    static {
        try {
            connection = DataBaseConnectionPool.getConnection("jdbc:mysql://localhost:3306/ebridge");
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    private static String generateSecurityToken() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // first not 0 digit
        sb.append(random.nextInt(9) + 1);

        // rest of 11 digits
        for (int i = 0; i < 11; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static int register(String mobileNumber, String accountName) {

        System.out.println("REGISTERING " + mobileNumber + " TO " + accountName);

        // 263732050288
        // format mobile number

        if (mobileNumber.length() != 10) {
            System.out.println(mobileNumber + " IS INVALID. MUST BE 10 DIGITS LONG");
            return -1;
        }

        mobileNumber = "263" + mobileNumber.substring(1);

        String DELETE_SIP_USER =
                "   DELETE FROM sip_buddies                                                                     " +
                "   WHERE defaultuser = ?                                                                       ";

        String CREATE_SIP_USER =
                "   INSERT INTO sip_buddies                                                                     " +
                "                   ( subscriber_name, name, callerid, defaultuser, regexten,                   " +
                "                     secret, context,host,nat, port,type, regseconds, ipaddr,lastms )          " +
                "        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )                                     ";

//                "   ('David','263737482087','263737482087','0','1234','internet_inbound_calls'," +
//                "'dynamic','no','5060','friend', 60, 'dynamic',600000);   ";

        String OUTBOUND_MSG_SQL =
                " INSERT INTO outbound_msg ( message_id, " +
                "           source_id, destination_id, payload, channel_type, message_status ) " +
                " VALUES ( ?, ?, ?, ?, ?, ? )";

        String securityToken = generateSecurityToken();
        String shortMessage = "Telecel Internet Calling Security Code is " + securityToken;

        PreparedStatement stmt = null;
        try {
            connection = DataBaseConnectionPool.getConnection("jdbc:mysql://127.0.0.1:3306/ebridge");

            // delete sip user if exists
            System.out.println("CHECKING IF " + mobileNumber + " EXISTS ");
            stmt = connection.prepareStatement(DELETE_SIP_USER);
            stmt.setString(1, mobileNumber);
            stmt.executeUpdate();

            // create sip user
            System.out.println("REGISTERING " + mobileNumber);
            stmt = connection.prepareStatement(CREATE_SIP_USER);
            stmt.setString(1, accountName);
            stmt.setString(2, mobileNumber);
            stmt.setString(3, mobileNumber);
            stmt.setString(4, mobileNumber);
            stmt.setString(5, "0");
            stmt.setString(6, securityToken);
            stmt.setString(7, "internet_inbound_calls");
            stmt.setString(8, "dynamic");
            stmt.setString(9, "no");
            stmt.setString(10, "5060");
            stmt.setString(11, "friend");
            stmt.setInt(12, 600);
            stmt.setString(13, "dynamic");
            stmt.setString(14, "600000");

            stmt.executeUpdate();

            // send sms
            System.out.println("SENDING SECURITY CODE TO " + mobileNumber + " VIA SMS ");
            connection = DataBaseConnectionPool.getConnection("jdbc:mysql://192.1.1.57:3306/vas");
            stmt = connection.prepareStatement(OUTBOUND_MSG_SQL);
            stmt.setLong(1, System.currentTimeMillis());
            stmt.setString(2, "TelecelVOIP");
            stmt.setString(3, mobileNumber);
            stmt.setString(4, shortMessage);
            stmt.setString(5, "SMS");
            stmt.setString(6, "QUEUED");

            stmt.executeUpdate();

            System.out.println("REGISTERING OF MOBILE NUMBER " + mobileNumber +
                                " TO " + accountName + " COMPLETED SUCCESSFULLY.");

            return 0;
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR WHILE CREATING SUBSCRIPTION: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {stmt.close();} catch (Exception e){}
        }

        return -1;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: register mobileNumber subscriberName");
            System.exit(1);
        }
        String mobileNumber = args[0];
        String accountName = args[1];
        register(mobileNumber, accountName);
    }
}
