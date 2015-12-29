package com.sfs2x.extension.utils;

/**
 * Created by tonytan on 25/12/2015.
 *
 */

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.sfs2x.extension.ChatSystemExtension;
import com.sfs2x.extension.Channel;


// Helper methods to easily get current room or zone and precache the link to ExtensionHelper
public class RoomHelper {

    public static Room getCurrentRoom(BaseClientRequestHandler handler) {
        return handler.getParentExtension().getParentRoom();
    }

    public static Room getCurrentRoom(SFSExtension extension) {
        return extension.getParentRoom();
    }

    public static Room getCurrentRoom(BaseServerEventHandler handler) {
        return handler.getParentExtension().getParentRoom();
    }

    public static Channel getChannel(BaseClientRequestHandler handler) {
        ChatSystemExtension chatSys = (ChatSystemExtension) handler.getParentExtension();
        return chatSys.getChannel();
    }

    public static Channel getChannel(BaseServerEventHandler handler) {
        ChatSystemExtension ext = (ChatSystemExtension) handler.getParentExtension();
        return ext.getChannel();
    }


}