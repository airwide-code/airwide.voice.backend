//package com.ebridgevas.vas.content;
//
//import com.ebridgevas.vas.dao.ServiceDAO;
//import com.ebridgevas.vas.dto.SessionKeyDTO;
//import com.ebridgevas.vas.dto.SubscriptionDTO;
//import com.ebridgevas.vas.model.IVRSession;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.ebridgevas.vas.dao.IVRSubscriptionDAO.lookup;
//
///**
// *
// */
//public class ContentManager {
//
//    private static final Map<SessionKeyDTO, IVRSession> IVR_USER_SESSIONS;
//
//    static {
//        IVR_USER_SESSIONS = new HashMap<SessionKeyDTO, IVRSession>();
//    }
//
//    public void process(SessionKeyDTO key, String payload) {
//        System.out.println(key + ", payload : " + payload);
//
//        // get session
//        IVRSession session
//                = IVR_USER_SESSIONS.get(key);
//        if (session == null) {
//            List<SubscriptionDTO> subscriptions
//                    = lookup(key.getCalledId(), key.getCallerId());
//            if (subscriptions.isEmpty()) {
//                // processing code 100001 - SUBSCRIPTION_REQUEST
//                ServiceDAO.service("100002");
//                IVR_USER_SESSIONS.put(key, new IVRSession("01"));
//                // set media path to SUBSCRIPTION_REQUEST_MEDIA_PATH
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        ContentManager contentManager
//                = new ContentManager();
//        SessionKeyDTO key
//                = new SessionKeyDTO(
//                "6001",
//                "789",
//                "b539tZmVi1abmAUWjQ4Chf4wUJ-DJdGT\n");
//
//        contentManager.process(key, null);
//    }
//}
