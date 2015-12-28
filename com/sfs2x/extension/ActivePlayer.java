package com.sfs2x.extension;

/**
 * Created by tonytan on 28/12/2015.
 */


import com.smartfoxserver.v2.entities.User;


public class ActivePlayer
{
    private Channel channel;
    private User sfsUser; // SFS user that corresponds to this player
    private int ID = 0;
    private String name = "";

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

    public int getID()
    {
        return ID;
    }

    public void setID(int _id)
    {
        ID = _id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String _name)
    {
        name = _name;
    }


}