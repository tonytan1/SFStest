/**
 * Created by tonytan on 22/12/2015.
 *
 */
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.core.

public class test extends SFSExtension{
    @Override
    public void init(){
        initEventHandlers();
        //
    }

    public void destory(){
        super.destroy();
    }



    // 绑定sfs系统事件
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
        this.addEventHandler(SFSEventType.USER_DISCONNECT, LeaveRoomEventHandler.class);
    }

    // 初始化过滤器
    private void initFilters(){

    }
}
