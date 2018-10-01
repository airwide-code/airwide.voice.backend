
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
 * @param time a long.
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