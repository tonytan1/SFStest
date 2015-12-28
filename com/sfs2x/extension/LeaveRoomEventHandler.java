package com.sfs2x.extension;

/**
 * Created by tonytan on 28/12/2015.
 */
import java.util.List;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class LeaveRoomEventHandler extends BaseServerEventHandler {

    public void handleServerEvent(ISFSEvent event) throws SFSException {

        User user = (User) event.getParameter(SFSEventParam.USER);

        trace("ChatSystemExtension LeaveRoomEventHandler got user: " + user);

        UserVariable userVariable = user.getVariable("room");

        if (userVariable == null) {
            return;
        }

        Room room = (Room) getParentExtension().getParentZone().getRoomByName(userVariable.getStringValue());

        trace("ChatSystemExtension LeaveRoomEventHandler got room: " + room);

        //send the note to all other players
        List<User> users = room.getUserList();
        ISFSObject resObj = SFSObject.newInstance();
        resObj.putUtfString("message", "A player has disconnected from the server.");
        send("leaving or disconnected", resObj, users);

    }

}