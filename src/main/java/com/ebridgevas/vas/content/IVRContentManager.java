package com.ebridgevas.vas.content;

import com.ebridgevas.vas.dao.IVRSubscriptionDAO;
//import com.ebridgevas.vas.dto.Authorisation;
import com.ebridgevas.vas.dao.MessageServiceDAO;
import com.ebridgevas.vas.dto.*;
import com.ebridgevas.vas.model.IVRSession;
import com.ebridgevas.vas.util.HttpClient;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ebridgevas.vas.dao.IVRSubscriptionDAO.*;
import static com.ebridgevas.vas.services.BillingService.bill;

/**
 * IVR Content Manager.
 *
 * @author david@tekeshe.com
 *
 *  Story 1:
 *
 *  1.1. We have a phone connected via SIP that is available at SIP/27729745087
 *  1.2. Subscriber dials *901#
 *  1.3. IVR Plaform Manager notes the mobile number and requested service id and disconnect the call
 *  1.4. The IVR Plaform Manager then initiate a call from subscriber's phone to IVR Platform, extention 1300
 *  1.5. The IVR Plaform redirects the call to Content Manager
 *  1.6. The Content Manager plays a Welcome Message for the requested service
 *
 * https://asterisk-java.org/tutorial/
 * https://www.callcentric.com/did/
 *
 * /etc/asterisk/extensions.conf
 * [default]
 *
 * ...
 *
 * extern => 1300,1,Agi(agi://localhost/hello.agi)
 *
 *  /etc/asterisk/manager.conf
 *
 *  [general]
 *  enabled = yes
 *  port = 5083
 *  bindaddr = 0.0.0.0
 *
 *  [manager]
 *  secret=pa55w0rd
 *  permit=0.0.0.0/0.0.0.0
 *  read=all
 *  write=all
 *
 */
@SuppressWarnings("unused")
public class IVRContentManager extends BaseAgiScript {

    private static final Map<SessionKeyDTO, IVRSession> IVR_USER_SESSIONS;

    static {
        IVR_USER_SESSIONS = new HashMap<SessionKeyDTO, IVRSession>();
    }

    private static Map<String, BundleTypeDTO> bundleTypes;

    private Map<String, List<SubscriptionDTO>> subscriptions
            = new HashMap<String, List<SubscriptionDTO>>();

    private Map<String, String> sessions
            = new HashMap<String, String>();

    private HttpClient httpClient
            = new HttpClient();

    private final static String MERCHANT_MOBILE_NUMBER = "263735962427";

    private final static Map<ProductIDKey, Integer> PRODUCT_MAX_TRACK_IDS;
    static {
        PRODUCT_MAX_TRACK_IDS =  IVRSubscriptionDAO.maxTrackIDs();
    }
    /**
     * Subscription Lookup
     *
     * @param callerId
     * @param calledId
     */
    private boolean subscriptionLookup(String callerId, String calledId) {

        System.out.println("calledId : " + calledId + ", callerId : " + callerId);

        List<SubscriptionDTO> subscriptionList
                = IVRSubscriptionDAO.subscriptionLookup(calledId, callerId);

        // System.out.println("IVRContentManager.subscriptionLookup : subscription : " + subscription);

        if (subscriptionList.size() > 0) {
            subscriptions.put(callerId, subscriptionList);
            return true;
        } else {
            return false;
        }
    }

    /**
     * subscriber
     *
     * @param callerId
     * @param calledId
     * @param bundleId
     */
    private boolean subscribe(String callerId, String calledId, String bundleId) {
        System.out.println("calledId : " + calledId + ", callerId : " + callerId + ", bundleId : " + bundleId );
        BundleTypeDTO bundleType
                = bundleTypes.get(bundleId);

        System.out.println("bundleType : " + bundleType );

        // Debit prepaid account
        BigDecimal purchaseAmount = bundleType.getPrice();
        try {
             boolean paid = httpClient.post("263" + callerId, MERCHANT_MOBILE_NUMBER, purchaseAmount);
//            boolean paid = true;
            if (paid) {
                IVRSubscriptionDAO.subscribe(calledId, callerId, bundleType);
                subscriptionLookup(callerId, calledId);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     */
    private void unsubscribe(String callerId, String calledId, String bundleId) {
        System.out.println("calledId : " + calledId + ", callerId : " + callerId + ", bundleId : " + bundleId );
        BundleTypeDTO bundleType
                = bundleTypes.get(bundleId);

        System.out.println("bundleType : " + bundleType );
        IVRSubscriptionDAO.unsubscribe(calledId, callerId);
    }

    /**
     *
     * exten => 789,n(agi_call_training),agi(agi://localhost/purchase_product.agi?
     * action_id=training&caller_id=${caller_id}&category_id=${category_id}&product_id=${product_id})
     *
     * @param agiRequest
     * @param agiChannel
     * @throws AgiException
     */
    public void service( AgiRequest agiRequest,
                         AgiChannel agiChannel ) throws AgiException {

        System.out.println("service()");

        String actionId = agiRequest.getParameter("action_id");

        if ("send_message".equals(actionId)) {

            String sourceId = parseNumber(agiRequest.getParameter("source_id"));
            String destinationId = parseNumber(agiRequest.getParameter("destination_id"));
            String payload = agiRequest.getParameter("payload");

            System.out.println("service.send_message, sourceId: " + sourceId +
                    "destinationId: " + destinationId +
                    "payload: " +payload);

            MessageServiceDAO.sendMessage(sourceId, destinationId, payload);

            return;
        }

        if (bundleTypes == null) {
            bundleTypes = IVRSubscriptionDAO.findBundleTypes();
        }

        String callId = agiRequest.getParameter("call_id");
        String calledId = "789"; //agiRequest.getParameter("called_id");
        String callerId = agiRequest.getParameter("caller_id");
        String bundleId = agiRequest.getParameter("bundle_id");

        if ("subscription_lookup".equals(actionId)) {
            System.out.println("subscription_lookup callerId : "
                    + callerId + ", calledId : " + calledId + ", callId : " + callId);
            boolean subscriptionFound
                    = subscriptionLookup(callerId, calledId);
            System.out.println("service.subscriptionFound : " + subscriptionFound );
            String result = subscriptionFound ? "1" : "0";
            System.out.println("subscription_lookup result : " + result);
            agiChannel.setVariable("subscription_found", result);

        } else if ("subscribe".equals(actionId)) {
            boolean subscribed = subscribe(callerId, calledId, bundleId);
            agiChannel.setVariable("is_subscription_successfull", subscribed ? "1" : "0");
        } else if ("unsubscribe".equals(actionId)) {
            unsubscribe(callerId, calledId, bundleId);
        } else if ("training".equals(actionId)) {
            int categoryID = Integer.parseInt(agiRequest.getParameter("category_id"));
            int productID = Integer.parseInt(agiRequest.getParameter("product_id"));
            int playCommandID = Integer.parseInt(agiRequest.getParameter("play_command_id"));

            System.out.println("training callerId : "
                    + callerId + ", calledId : " + calledId + ", callId : " + callId +
                    ", category_id : " + categoryID + ", productID : " + productID +
                    ", playCommandID : " + playCommandID);
            Integer currentTrackID =
                        currentTrackID(calledId, callerId, categoryID, productID);

            Integer maxTrackID
                    = PRODUCT_MAX_TRACK_IDS.get(new ProductIDKey(calledId, categoryID, productID));

            System.out.println("training currentTrackID : " + currentTrackID +
                                "max trackID : " + maxTrackID );

            // save current track id
            if (currentTrackID == null) {
                 saveCurrentTrackID(calledId, callerId, categoryID, productID);
                 currentTrackID = 1;
            }

            /*
             * Press 1 to listen to this session
             * press 2 to skip this show and go to the next section
             * press 3 to listen to the previous session
             * press 4 to repeat the session
             */
            switch (playCommandID) {
                case 4:
                    // listen to this session
                    break;
                case 2:
                    // skip this show and go to the next section
                    if (currentTrackID >= maxTrackID)  {
                        currentTrackID = 1;
                    } else {
                        ++currentTrackID;
                    }
                    saveCurrentTrackID(calledId, callerId, categoryID, productID, currentTrackID);
                    break;
                case 3:
                    // listen to the previous session
                    System.out.println("REWIND: playCommandID : " + playCommandID
                                        + ", Current Track : " + currentTrackID);
                    if (currentTrackID > 1) {
                        --currentTrackID;
                    }
                    System.out.println("REWIND: Going to play Track ID : " + currentTrackID);
                    saveCurrentTrackID(calledId, callerId, categoryID, productID, currentTrackID);
                    break;
                // case 4:
                    // repeat the session
                   // currentTrackID = 1;
                   // break;
            }
            // get media path
            MediaTrackPathDTO trackPath
                                = playCommandID == 1 ?   /* favourite */
                                        favouriteMediaPath(calledId, categoryID, productID)
                                        : mediaPath(calledId, categoryID, productID, currentTrackID);

//            MediaTrackPathDTO trackPath
//                    = mediaPath(calledId, categoryID, productID, currentTrackID);

            System.out.println("Going to play Track Path : " + trackPath);

//            if (playCommandID == 1) {
//                currentTrackID = trackPath.getTrackID();
//            }

            System.out.println("Going to play Track ID : " + currentTrackID);


            // billing
            boolean isBilled =
                    bill(calledId, callerId, trackPath.getTrackDuration(), subscriptions.get(callerId));

            if (isBilled) {
                // refresh balances
                subscriptionLookup(callerId, calledId);
                agiChannel.setVariable("is_billed","1");
            } else {
                agiChannel.setVariable("is_billed","0");
                agiChannel.setVariable("track_title_path","menu/minutes_expired");
                return;
            }
            // increment tracker
//             if (currentTrackID >= maxTrackID)  {
//                currentTrackID = 1;
//             } else {
//                ++currentTrackID;
//             }

            // save current tracker
            // saveCurrentTrackID(calledId, callerId, categoryID, productID, currentTrackID);

            // set result
            agiChannel.setVariable("current_track_id", "" + currentTrackID);
            agiChannel.setVariable("track_title_path",
                    ( trackPath == null ?
                            "menu/end_of_playlist" :
                            "menu/" + trackPath.getTrackTitlePath()) );
            agiChannel.setVariable("track_path",
                                    ( trackPath == null ?
                                             "menu/end_of_playlist" :
                                             "training/" + trackPath.getTrackPath()) );
            agiChannel.setVariable("is_end_of_playlist",
                                        trackPath ==  null ? "1" : "0");
        }  else if ("save_current_track_id".equals(actionId)) {
            // save current tracker
            int categoryID = Integer.parseInt(agiRequest.getParameter("category_id"));
            int productID = Integer.parseInt(agiRequest.getParameter("product_id"));
            int currentTrackID = Integer.parseInt(agiRequest.getParameter("current_track_id"));
            // increment tracker
            Integer maxTrackID
                    = PRODUCT_MAX_TRACK_IDS.get(new ProductIDKey(calledId, categoryID, productID));
            if (currentTrackID >= maxTrackID)  {
                currentTrackID = 1;
            } else {
                ++currentTrackID;
            }
            saveCurrentTrackID(calledId, callerId, categoryID, productID, currentTrackID);
            incrementPlayCounter(calledId, categoryID, productID, currentTrackID);
        }

        /*
        String[] payload = parse(ussdString);

        Authorisation authorisation;
        IVRSession ivrUserSession = IVR_USER_SESSIONS.get(uuid);
        if (userSession == null)
        {
            if (mobileNumber == null)
            {
                authorisation = new Authorisation(uuid);
                authorisation.setResultCode("096");
                authorisation.setNarrative1("Session terminated. Please short code dial again.");
                return authorisation;
            }
            else
            {
                authorisation = new Authorisation(uuid);
                authorisation.setFromAccountNumber( mobileNumber );
                authorisation.setToAccountNumber( mobileNumber );

                TreeNode<NodeData> shortCodeNode = findNode(ROOT_NODE, payload[0]);
                if (shortCodeNode == null)
                {
                    authorisation.setResultCode("096");
                    authorisation.setNarrative1("Short code " + payload[0] + " does not exist.");
                    return authorisation;
                }
                userSession = new UserSession(uuid, authorisation, shortCodeNode);
                USER_SESSIONS.put(authorisation.getUuid(), userSession);

                authorisation.setNarrative1(menu(payload[0]));
                authorisation.setResultCode("000");

                return authorisation;
            }
        }
        else
        {
            authorisation = userSession.getAuthorisation();
        }

//
//        if (! hasParameters(payload))
//        {
//            // Return service menu for this short code
//            authorisation.setNarrative1(menu(payload[0]));
//            authorisation.setResultCode("000");
//            return authorisation;
//        }

        TreeNode node = userSession.getTreeNode();

        if ( node == null)
        {
            authorisation = new Authorisation(uuid);
            authorisation.setResultCode("096");
            authorisation.setNarrative1("No active session. Please short code dial again.");
            return authorisation;
        }

//        if (node == null)
//        {
//            authorisation.setNarrative1("Invalid selection.\n" + ((NodeData) ROOT_NODE.data).getSubHeaderText());
//            authorisation.setResultCode("096");
//            return authorisation;
//        }

        // Process selected node
        TreeNode selectedNode = findNode(node, payload[0]);

        if (selectedNode == null)
        {
            authorisation.setNarrative1("Invalid selection.\n" + ((NodeData) node.data).getSubHeaderText());
            authorisation.setResultCode("096");
            return authorisation;
        }

        if (selectedNode.hasChildren())
        {
            // Node has children. Prompt user for selection.
            authorisation.setNarrative1(menu(selectedNode));
            authorisation.setResultCode("000");
            // update user session current TreeNode
            userSession.setTreeNode(selectedNode);
            return authorisation;
        }
        else
        {
            // Node has no children. Execute node processor
            return execute(selectedNode, authorisation );
        }
          */

        // sayAlpha(callerId);
        // sayAlpha(bundleId);
        // answer();

        // streamFile("welcomeAgricultureService");
//        System.out.println ("Dial-out AGI starting");
//        String text = "Press a number to test DTMF";
//        char digit = '0';
//        while (digit != '*') {
//            digit = playText(text);
//            if (digit != 0)
//                text = "You have pressed "+digit;
//        }
//
//        hangup();
        //Answer the channel... answer();
        //Play speed-enternumber sound while waiting for the user
        //to enter 4 digits. We wait for a 20 seconds timeout
        //in case digits are not dialed.
        // String numbers = getData("press_1&pass_1&press_1", 60000L, 1);
        // System.out.println("##### DIALED NUMBER : " + numbers);
        // hangup();
        //Work with dialed digits
        //If the user pressed 2-0-0-1, ask for another number and store it
        //If the user pressed 1-0-0-1, let the user leave a voice message
//        if(numbers.equal("2001")){
//            String finalNumbers = getData("vqplus-enter-number", 20000L, 2);

            //Store finalNumbers into your database or do something else
            //...
//        }else if(numbers.equal("1001")){
            //Record the voice for 10 seconds or until the user press 2
//            recordFile("voiceRecord","wav","2",10000);
//        }

//        hangup();
        /*
        System.out.println ("Dial-out AGI starting");
        String text = "Press a number to test DTMF";
        char digit = '0';
        while (digit != '*') {
            digit = playText(text);
            if (digit != 0)
                text = "You have pressed "+digit;
        }

        hangup();

        Map<String, String[]> parameterMap = agiRequest.getParameterMap();

//        for (String key : parameterMap.keySet()) {
//            String[] params = parameterMap.get(key);
//            switch (params[0]) {
//
//            }
//        }
        // Answer the channel ...
        answer();

         // ... and hangup.
        hangup();

        //Answer the channel... answer();
        //Play speed-enternumber sound while waiting for the user
        //to enter 4 digits. We wait for a 20 seconds timeout
        //in case digits are not dialed.
        String numbers = getData("speed-enternumber", 20000L, 4);

        //Work with dialed digits
        //If the user pressed 2-0-0-1, ask for another number and store it
        //If the user pressed 1-0-0-1, let the user leave a voice message
        if(numbers.equal("2001")){
            String finalNumbers = getData("vqplus-enter-number", 20000L, 2);

            //Store finalNumbers into your database or do something else
            //...
        }else if(numbers.equal("1001")){
            //Record the voice for 10 seconds or until the user press 2
            recordFile("voiceRecord","wav","2",10000);
        }

        //Terminate the phone call
        hangup();


        // http://www.programcreek.com/java-api-examples/index.php?api=org.asteriskjava.fastagi.AgiException

        // http://www.programcreek.com/java-api-examples/index.php?source_dir=onebusaway-application-modules-master/onebusaway-phone/src/main/java/org/asteriskjava/fastagi/DefaultAgiClient.java

        */
    }

    /*
    public void service( AgiRequest agiRequest,
                         AgiChannel agiChannel )
            throws AgiException {

        // answer the channel
        // answer();
        System.out.println("IVR Content Manager starting");
        */
        /*
        String payload = getData("play-main-menu", 20000L, 2);
        switch(payload) {
            case "01":
                streamFile("play-radio-menu");
                break;
        }
        // play welcome message
        streamFile("play-thank-you-message");
        */
        /*
        String text = "Press a number to test DTMF";
        char digit = '0';
        while (digit != '*') {

            digit = playText(text);
            if (digit != 0) {
                text = "You have pressed " + digit;
            }
        }
        // hangup
        hangup();
    }
    */

    /*
    public void service( AgiRequest request,
                         AgiChannel channel)
            throws AgiException {

        DialogicMessage dialogicMessage = new WelcomeDialogicMessage();
        String digits = "";
        char digit;

        while (true) {
            digits = "";
            if (dialogicMessage.getExpectedDigits() == 0) {
                // If we're not expecting digits, just play the wave
                digit = streamFile(dialogicMessage.getAsteriskFilename(), "0123456789*#");
                if (digit != 0) {
                    digits = ""+digit;
                }
            } else {
                digits = getData(dialogicMessage.getAsteriskFilename(), 20000, dialogicMessage.getExpectedDigits());
            }
            BrokerFactory.getLoggingBroker().logDebug("DTMF="+digits);
            if ((digits == null) || (digits.toLowerCase().indexOf("timeout")>=0)) {
                return;
            } else if (digits.indexOf("*")>=0) {
                dialogicMessage = new WelcomeDialogicMessage();
            } else {
                dialogicMessage = dialogicMessage.getNextMessage(digits);
            }
        }

    }
    */

    /*
    private char playText ( String text )
            throws AgiException {

        String waveName = "RRN_Sound_" + System.currentTimeMillis();
        int retCode
                = exec(
                "System",
                "echo " + text +
                        "|/usr/bin/text2wave -F 8000 -o /var/lib/asterisk/sounds/tts/" + waveName + ".wav" );

        char digit = streamFile( "tts/" + waveName, "0123456789*#" );

        retCode = exec("System", "rm /var/lib/asterisk/sounds/tts/" + waveName+".wav" );

        return digit;
    }
    */

    /**
     * This replaces the super getData.  It returns on a '*', so we
     * can do the whole "press * at any time to return to the main menu"
     * thing
     */
    /*
    protected String getData(String file, int timeout, int maxdigits) throws AgiException {
        StringBuffer digits = new StringBuffer();
        char digit;

        digit = streamFile(file, "0123456789#*");
        if (digit == '*') {
            return "*";
        };
        digits.append (digit);

        while ((digits.length() < maxdigits) &&
                ((digit = waitForDigit(timeout)) != '#') ) {
            BrokerFactory.getLoggingBroker().logDebug("Got digit "+digit);
            if (digit == '*') {
                return "*";
            }
            if (digit == 0) {
                return null;
            }
            digits.append(digit);
        }
        return digits.toString();
    }
    */

    /**
     * <p>sayDateTimeInterruptible</p>
     *
     * @return a char.
     * @throws org.asteriskjava.fastagi.AgiException if any.
     */
    /*
    protected char sayDateTimeInterruptible(long time) throws AgiException {
        if (! "".equals(getVariable(VAR_INTERRUPT_DIGITS))) {
            return sayDateTime(time, getVariable(VAR_INTERRUPT_DIGITS));
        } else {
            sayDateTime(time);
            return 0x0;
        }
    }
    */

//    private static void process(SessionKeyDTO key, String payload) {
//        System.out.println(key + ", payload : " + payload);
//    }
//

//    public static void main(String[] args) {
//        System.out.println("Services ...");
//
//        SessionKeyDTO key
//                = new SessionKeyDTO(
//                "6001",
//                "789",
//                "b539tZmVi1abmAUWjQ4Chf4wUJ-DJdGT\n");
//
//        System.out.println(parseNumber("6001 <sip:6001@10.0.0.4>"));
//        System.out.println(parseNumber("sip:27644303883@10.0.0.4"));

//    }

    public static String parseNumber(String input) {
        // service.send_message, sourceId: 6001 <sip:6001@10.0.0.4>destinationId: sip:27644303883@10.0.0.4payload: Test
        Pattern pattern = Pattern.compile("sip:(.*?)@");
        Matcher matcher = pattern.matcher(input);
        if ( matcher.find() ) {
            String result = matcher.group(1);
            return result;
        }
        return null;
    }
}
