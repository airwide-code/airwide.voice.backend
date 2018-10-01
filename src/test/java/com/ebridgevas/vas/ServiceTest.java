package com.ebridgevas.vas;

/**
 * @author david@tekeshe.com
 */
public class ServiceTest {

    public void test() {

        // Story 1 - Subscriber Dials 789
        //      Step 1.1 - Get Subscription
        //          Step 1.1.1  if not - Goto Story 2
        //          Step 1.1.2  if found - Goto Story 3

        // Story 2 - Has No Subscription

        //      Step 1.1 - Answer answer = welcomeToAgriculturePortal.wav
        //              Welcome to the Agriculture Portal,
        //              which educates farmers to improve their productivity of crops, and help in producing with great gain and efficiency
        //              Press 1 for English
        //              Press 2 for Shona
        //              Press 3 for Ndebele
        //              Press 0 for Help Menu
        //                 Goto Story 9
        //              Press* to repeat the Menu

        //      Step 1.1.1 - If answer = 1, Press 1 for English
        //          Goto Story 1.1.1

        //      Step 1.1.2 - If answer = 2, Press 2 for Shona
        //          Goto Story 1.1.2

        //      Step 1.1.3 - If answer = 3, Press 3 for Ndebele
        //          Goto Story 1.1.3

        //      Step 1.1.4 - If answer = 0, Press 0 for Help Menu
        //          Goto Story 1.1.4

        //      Step 1.1.5 - If answer = *, Press* to repeat the Menu
        //          Goto Story 1.1.5

        // Story 1.1.1 - Press 1 for English

        //      Step 1.1.1.1 - Answer answer = trafficInfomation.wav
        //          Now purchase any of the following Agriculture Pack and start listening
        //          Press 1 for Monthly Pack, at just $1
        //          Press 2 for Weekly Pack, at 35 cents
        //          Press 3 for Daily Pack, at just 10 cents
        //      Step 1.1.1.1.1 - If answer = 1, Press 1 for Monthly Pack, at just $1
        //          Goto Story 1.1.1.1.1
        //      Step 1.1.1.1.2 - If answer = 2, Press 2 for Weekly Pack, at 35 cents
        //      Step 1.1.1.1.3 - If answer = 3, Press 3 for Daily Pack, at just 10 cents

        // Story 1.1.2 - If answer = 2, Press 2 for Shona
        //      Program ino haisati yava kuwanikwa
        //      SIP_BYE

        // Story 1.1.3 - If answer = 3, Press 3 for Ndebele
        //     This program is not yet available
        //      SIP_BYE

        // Story 1.1.4 - If answer = 0, Press 0 for Help Menu
        //      Help not yet available
        //      SIP_BYE
        // Story 1.1.5 - If answer = *, Press* to repeat the Menu
        //    Goto Step 1.1

        // Story 1.1.1.1.1 - Answer answer = confirmPurchase
        //      Press 1 to confirm your subscription.
        //      This service will auto renew after validity expiry.

        //      Step 1.1.1.1.1.1 - Press 1 to confirm your subscription
        //          purchase (pack, 1|2|3)
        //          Goto Story 1.1.1.1.1.1.1

        // Story 1.1.1.1.1.1.1 - purchase Response
        //      You are successfully subscribed to the Agriculture Portal Dial 789 to listen to Agricultural Tips
        //      SIP_BYE

        // Story 3 - Has Subscription

        //      Step 3.1 - Answer answer = playCropSubscription.wav
        //          Press 1 for crops
        //              Goto 3.1.1
        //          Press 2 for poultry
        //              Goto 3.1.2
        //          Press 3 training
        //              Goto 3.1.3
        //          Press 4 beef production
        //              Goto 3.1.4
        //          Press 5 for honey production
        //              Goto 3.1.5
        //          Press 6 for more categories
        //              Goto 3.1.6
        //          press 9 to unsubscribe and help menu
        //              Goto 3.1.7
        //          Press * to repeat this menu
        //              Goto 3.1

        // Story 3.1.1
        //          Press 1 for Potatoes
        //              Goto Story 3.1.1.1
        //          Press 2 for Soya beans
        //              Goto Story 3.1.1.2
        //          Press 6 for more categories
        //              Goto Story 3.1.1.3
        //          Press 9 to unsubscribe and help menu press
        //              Goto Story 3.1.1.4
        //          Press * to repeat this menu
        //              Goto Story 3.1.1.5

        // Story 3.1.1.1
        //      Press 1 Guiding literature
        //          Goto Story 3.1.1.1.1
        //      Press 2 Weekly guide
        //      Press 8 for more categories
        //      press 9 to unsubscribe and help menu
        //      press* to repeat this menu

        //  Story 3.1.1.1.1 - Guiding Literature
        //      Press 1 introduction
        //          Goto Story 3.1.1.1.1.1
        //      Press 2 cultural menu
        //      Press 3 growing crops
        //      Press 4 fertilizers
        //      Press 5 pest management
        //      Press 7 for more categories
        //      press 9 to unsubscribe and help menu press
        //      * to repeat this menu

        //      Story 3.1.1.1.1.1 - Guiding Literature - introduction
        //          Press 1 to listen to this session
        //              SIP_BYE
        //          press 2 to skip this show and go to the next section press 3 to listen to the previous session
        //              SIP_BYE
        //          press 4 to repeat the session
        //              SIP_BYE
        //          press 0 for main menu
        //              SIP_BYE
        //          press * to repeat this menu again
        //              Goto Story 3.1.1.1.1.1

        // Story 9 - playHelpMenu.wav

        //      welcome to help section of agricultural portal service and for to unsubscribe from this service
        //      SIP_BYE
        //      press 1 to know features of this service
        //      SIP_BYE
        //      press 2 to change your language
        //      SIP_BYE
        //      press 3 for ... information and
        //      SIP_BYE
        //      press 4 to unsubscribe to these service and 4 to unsubscribe to these service press # to go back to the main menu
        //      SIP_BYE

    }
}
