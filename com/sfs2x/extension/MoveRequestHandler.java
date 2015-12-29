package com.sfs2x.extension;

import java.util.List;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

/**
 * Created by tonytan on 29/12/2015.
 *
 */

public class MoveRequestHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User user, ISFSObject params) {

        trace("MoveRequestHandler got : " + user);

        Room room = user.getLastJoinedRoom();

        trace("got move:" + user);

        List<User> users = room.getUserList();

        //remove the sender
        users.remove(user);

        send("move", params, users);
    }

}