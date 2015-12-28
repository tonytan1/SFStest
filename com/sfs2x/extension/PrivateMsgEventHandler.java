package com.sfs2x.extension;

/**
 * Created by tonytan on 28/12/2015.
 *
 */


import java.sql.Timestamp;
import java.util.Date;

import com.sfs2x.extension.ChatSystemExtension;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class PrivateMsgEventHandler extends BaseServerEventHandler
{
    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException
    {
        User user = (User) event.getParameter(SFSEventParam.USER);
        User recipient = (User) event.getParameter(SFSEventParam.RECIPIENT);
        String message = (String) event.getParameter(SFSEventParam.MESSAGE);

        trace("[PM] " + user.getName() + " to " + recipient.getName() + ": " + message);

        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        Chat newChat = new Chat(user.getName(), recipient.getName(), message, time, true);
        ((ChatSystemExtension) getParentExtension()).addToPMLog(newChat);

    }
}