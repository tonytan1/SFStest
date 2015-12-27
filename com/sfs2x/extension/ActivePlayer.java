package com.sfs2x.extension;

/**
 * Created by tonytan on 28/12/2015.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.extensions.SFSExtension;

@SuppressWarnings("unused")
public class ActivePlayer
{
    private Channel channel;
    private User sfsUser; // SFS user that corresponds to this player
    public boolean useUDP = false;
    private boolean isDisconnecting = false;

    private int ID = 0;




    private int numBuilt = 0;

    public ActivePlayer(User _sfsUser, Channel _channel)
    {
        sfsUser = _sfsUser;
        channel = _channel;
        ID = sfsUser.getId();

    }

    public User getSfsUser()
    {
        return sfsUser;
    }

    public boolean isDisconnecting()
    {
        return isDisconnecting;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int _id)
    {
        ID = _id;
    }


}