package com.sfs2x.extension;

/**
 * Created by tonytan on 27/12/2015.
 *
 */
    import com.sfs2x.extension.utils.RoomHelper;
    import com.smartfoxserver.v2.core.ISFSEvent;
    import com.smartfoxserver.v2.entities.*;
    import com.smartfoxserver.v2.entities.data.SFSObject;
    import com.smartfoxserver.v2.exceptions.SFSException;
    import com.smartfoxserver.v2.core.SFSEventParam;
    import com.smartfoxserver.bitswarm.sessions.ISession;
    import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

    public class JoinZoneEventHandler extends BaseServerEventHandler {
        @Override
        public void handleServerEvent(ISFSEvent event) throws SFSException {
            Channel channel = RoomHelper.getChannel(this);
            User currentUser = (User) event.getParameter(SFSEventParam.USER);
            ISession session = currentUser.getSession();
            String username = currentUser.getName();
            Zone zone = currentUser.getZone();

            trace(" JoinZoneEventHandler got room: " + zone);
            trace(" JoinZoneEventHandler got user: " + username);
        }


        // Send the transform to all the clients
        private void updateUser(User user, SFSObject data) {
            this.send("zoneJoinResponse", data, user, true); // Use UDP = true

            trace("Sent user back information retrieved from database!");
        }
    }

