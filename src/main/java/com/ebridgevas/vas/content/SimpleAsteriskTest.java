//package com.ebridgevas.vas.content;
//
//import javax.sdp.Origin;
//
//import org.asteriskjava.fastagi.AgiChannel;
//import org.asteriskjava.fastagi.AgiException;
//import org.asteriskjava.fastagi.AgiRequest;
//import org.asteriskjava.fastagi.AgiServerThread;
//import org.asteriskjava.fastagi.BaseAgiScript;
//import org.asteriskjava.fastagi.ClassNameMappingStrategy;
//import org.asteriskjava.fastagi.DefaultAgiServer;
//import org.asteriskjava.manager.AuthenticationFailedException;
//import org.asteriskjava.manager.ManagerConnection;
//import org.asteriskjava.manager.ManagerConnectionFactory;
//import org.asteriskjava.manager.TimeoutException;
//import org.asteriskjava.manager.action.CommandAction;
//import org.asteriskjava.manager.action.OriginateAction;
//import org.asteriskjava.manager.response.ManagerResponse;
//
//import java.io.IOException;
//
//public class SimpleAsteriskTest extends BaseAgiScript {
//
//    public void service(AgiRequest request, AgiChannel channel)
//            throws AgiException {
//
//        answer();
//
//        int retCode = 0;
//
//        while (retCode == 0) {
//            retCode = exec("Festival", "This is a test of the festival text to speech system.");
//            System.out.println ("Read digit " + retCode);
//        }
//
//        hangup();
//
//    }
//
//    public static void main (String[] args)  {
//
//        DefaultAgiServer server = new DefaultAgiServer();
//        //server.setPort(5060);
//
//        ClassNameMappingStrategy strategy = new ClassNameMappingStrategy();
//        server.setMappingStrategy(strategy);
//        AgiServerThread thread = new AgiServerThread(server);
//        thread.startup();
//        System.out.println ("Server started");
//
//
//        // Do the outbound
//        ManagerConnection con = new ManagerConnectionFactory("10.10.10.1", "reliable", "reliable").createManagerConnection();
//        try {
//            con.login();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (AuthenticationFailedException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//
//        OriginateAction origAction = new OriginateAction();
//        origAction.setChannel("SIP/7205308877@spa3000");
//        origAction.setContext("outgoing");
//        origAction.setExten("999");
//        origAction.setPriority(new Integer(1));
//        ManagerResponse origResponse = con.sendAction(origAction);
//        if (origResponse.getResponse().toLowerCase().indexOf("error") >= 0) {
//            System.out.println ("Error: "+origResponse.getMessage());
//        }
//        System.out.println ("Originate Response = "+origResponse);
//        con.logoff();
//
//        while (true) {
//            Thread.sleep(100000);
//            System.out.println(".");
//        }
//    }
//
//}
//
