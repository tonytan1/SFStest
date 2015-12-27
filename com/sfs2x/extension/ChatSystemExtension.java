package com.sfs2x.extension; /**
 * Created by tonytan on 23/12/2015.
 */
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Room;
import java.lang.Object.*;
import com.smartfoxserver.v2.*;
import com.smartfoxserver.*;
import com.smartfoxserver.v2.extensions.SFSExtension;

import java.nio.channels.SocketChannel;
import java.net.InetAddress;
import java.util.LinkedList;

public class ChatSystemExtension extends SFSExtension {

    //private ExtensionHelper helper;
    private Zone currentZone;
    private String publicMessage = "public";//公共聊天信息
    private String privateMessage = "private";//私人聊天信息
    LinkedList<SocketChannel> recipients;

    @Override
    public void init(){
        Channel channel = new Channel(this);
        recipients=new LinkedList<SocketChannel>();
        initEventHandlers();

    }


    public void destory(){
        super.destroy();
    }

    private void initEventHandlers() {
        //注册登录事件
        this.addEventHandler(SFSEventType.USER_LOGIN,      LoginEventHandler.class);
        //注册加入zone事件
        this.addEventHandler(SFSEventType.USER_JOIN_ZONE,  JoinZoneEventHandler.class);
        //注册加入房间事件
        this.addEventHandler(SFSEventType.USER_JOIN_ROOM,  JoinRoomEventHandler.class);
        //注册离开房间事件
        this.addEventHandler(SFSEventType.USER_LEAVE_ROOM, LeaveRoomEventHandler.class);
        //注册断开连接事件
        this.addEventHandler(SFSEventType.USER_DISCONNECT, LeaveRoomEventHandler.class);
        //Private message event
        this.addEventHandler(SFSEventType.PRIVATE_MESSAGE, PrivateMsgEventHandler.class);
        //Public message event
        this.addEventHandler(SFSEventType.PUBLIC_MESSAGE, PublicMsgEventHandler.class);

    }

    @Override
    public void handleRequest(String cmd, String[] params, User user, int roomId) {

        if(params.length!=0){
            if(cmd.equals(publicMessage)){
                handlePublicMessage(params,user,roomId);
            }else if(cmd.equals(privateMessage)){
                handlePrivateMessage(params,user,roomId);
            }
        }else{
            return;//如果用户传递的聊天信息为空时，服务器端不做任何操作，即params对象为空。
        }

    }

    @Override
    public void handleRequest(String cmd, String[] params, User user, int roomId) {

        if(params.length!=0){
            if(cmd.equals(publicMessage)){
                handlePublicMessage(params,user,roomId);
            }else if(cmd.equals(privateMessage)){
                handlePrivateMessage(params,user,roomId);
            }
        }else{
            return;//如果用户传递的聊天信息为空时，服务器端不做任何操作，即params对象为空。
        }

    }

    /**
     * process private message
     * @param username+消息
     * @param target user
     * @param roomId
     */
    private void handlePrivateMessage(String[] params, User user, int roomId) {

        LinkedList<SocketChannel> privateChannel=new LinkedList<SocketChannel>();
        Room myRoom = currentZone.getRoomById(roomId);

        //接收信息的用户昵称，是客户端传递过来的
        String recipientsName = params[0];

        User receiveUser=myRoom.getUserByName(recipientsName);

        //接收信息用户的Sockect通道
        SocketChannel acceptUserChannel=receiveUser.getSocketChannel();

        //发送信息用户的Sockect通道
        String senderChannel=receiveUser.getIpAddress();

        ActionscriptObject response=new ActionscriptObject();

        //发送者向接收者发送的聊天信息
        String message=params[1];

        int userId=user.getId();
        response.put("_cmd", privateMessage);
        response.put("userId", userId);
        response.put("username", user.getName());
        response.put("privateMessages", message);

        response.put("acceptUser", acceptUser);

        privateChannel.add(senderChannel);
        privateChannel.add(acceptUserChannel);

        this.sendResponse(response, roomId, user, privateChannel);
    }

    public Channel getChannel(){
        return channel;
    }

}







