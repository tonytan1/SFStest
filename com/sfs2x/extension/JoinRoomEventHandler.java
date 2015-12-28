package com.sfs2x.extension;

/**
 * Created by tonytan on 27/12/2015.
 *
 */
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class JoinRoomEventHandler extends BaseServerEventHandler {

    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException {
        Room room = (Room) event.getParameter(SFSEventParam.ROOM);
        User user = (User) event.getParameter(SFSEventParam.USER);

        trace(" JoinRoomEventHandler got room: " + room);
        trace(" JoinRoomEventHandler got user: " + user);

    }

}