package com.sfs2x.extension;

import java.sql.Timestamp;
import java.util.Date;

import com.sfs2x.extension.ChatSystemExtension;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
/**
 * Created by tonytan on 28/12/2015.
 *
 */



public class PublicMsgEventHandler extends BaseServerEventHandler
{
    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException
    {
        Room currentRoom = (Room) event.getParameter(SFSEventParam.ROOM);
        User user = (User) event.getParameter(SFSEventParam.USER);
        String message = (String) event.getParameter(SFSEventParam.MESSAGE);

        trace("[CHAT] [" + currentRoom.getName() + "] " + user.getName() + ": " + message);

        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        Chat newChat = new Chat(currentRoom.getName(), user.getName(), message, time);
        ((ChatSystemExtension) getParentExtension()).addToChatLog(newChat);

    }
}