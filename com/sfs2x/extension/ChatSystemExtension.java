package com.sfs2x.extension;

/**
 * Created by tonytan on 23/12/2015.
 *
 */
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

import java.util.ArrayList;
import java.util.List;

public class ChatSystemExtension extends SFSExtension {

    private List<Chat> chatLog = new ArrayList<Chat>();
    private List<Chat> pmLog = new ArrayList<Chat>();
    private Channel channel;
    @Override
    public void init(){
        channel = new Channel(this);
        initEventHandlers();

    }


    public void destory(){
        super.destroy();
    }

    private void initEventHandlers() {
        //move to another channel
        addRequestHandler("move", MoveRequestHandler.class);

        //Login event
        this.addEventHandler(SFSEventType.USER_LOGIN,      LoginEventHandler.class);
        //join zone event
        this.addEventHandler(SFSEventType.USER_JOIN_ZONE, JoinZoneEventHandler.class);
        //join room event
        this.addEventHandler(SFSEventType.USER_JOIN_ROOM,  JoinRoomEventHandler.class);
        //leave room event
        this.addEventHandler(SFSEventType.USER_LEAVE_ROOM, LeaveRoomEventHandler.class);
        //disconnect event
        this.addEventHandler(SFSEventType.USER_DISCONNECT, LeaveRoomEventHandler.class);
        //Private message event
        this.addEventHandler(SFSEventType.PRIVATE_MESSAGE, PrivateMsgEventHandler.class);
        //Public message event
        this.addEventHandler(SFSEventType.PUBLIC_MESSAGE, PublicMsgEventHandler.class);

    }


    public void addToChatLog(Chat newChat){
        chatLog.add(newChat);
    }

    public void addToPMLog(Chat newChat) {
        pmLog.add(newChat);
    }

    public List<Chat> getChatLog()
    {
        return chatLog;
    }

    public List<Chat> getPMLog()
    {
        return pmLog;
    }

    public void resetChatLog()
    {
        chatLog.clear();
    }

    public void resetPMLog()
    {
        pmLog.clear();
    }

    public Channel getChannel() {
        return channel;
    }
}







