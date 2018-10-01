/*
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
    subscribe(callerId, calledId, bundleId);
} else if ("unsubscribe".equals(actionId)) {
    unsubscribe(callerId, calledId, bundleId);
}
*/
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
