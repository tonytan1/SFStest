
package com.sfs2x.extension;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

public class Receiver {
    private String name;
    private String description;

    public String getName()
    {
        return name;
    }

    public void setName(String _name)
    {
        name = _name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String _description)
    {
        description = _description;
    }
}