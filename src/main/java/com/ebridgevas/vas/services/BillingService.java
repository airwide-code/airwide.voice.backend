package com.ebridgevas.vas.services;

import com.ebridgevas.vas.dto.SubscriptionDTO;

import java.util.List;

import static com.ebridgevas.vas.dao.IVRSubscriptionDAO.unsubscribe;
import static com.ebridgevas.vas.dao.IVRSubscriptionDAO.updateSubscription;

/**
 * @author david@tekeshe.com
 */
public class BillingService {

    public static boolean bill(String serviceID,
                               String subscriberID,
                               Long trackDuration,
                               List<SubscriptionDTO> subscriptions) {

        for (SubscriptionDTO subscription : subscriptions) {
            if ( subscription.getBalanceSeconds() >= trackDuration ) {

                updateSubscription( serviceID,
                                    subscriberID,
                                    subscription.getId(),
                                    subscription.getBalanceSeconds() - trackDuration);
		subscription.setBalanceSeconds(subscription.getBalanceSeconds() - trackDuration);
                return true;
            } else {
                unsubscribe(subscriberID, serviceID, subscription.getId());
            }
        }

        return false;
    }
}
