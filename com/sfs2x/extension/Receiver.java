
package com.sfs2x.extension;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

public class Receiver {
    private int dbID;
    private int templateID;
    private String name;
    private String description;


    public int getDBID()
    {
        return dbID;
    }

    public void setDBID(int _dbID)
    {
        dbID = _dbID;
    }

    public int getTemplateID()
    {
        return templateID;
    }

    public void setTemplateID(int _templateID)
    {
        templateID = _templateID;
    }

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



    public void toSFSObject(ISFSObject data)
    {
        toSFSObject(data, true);
    }

    public void toSFSObject(ISFSObject data, boolean verbose)
    {
        data.putInt("TemplateID", templateID);
        if (verbose)
        {
            data.putUtfString("Name", name);
            data.putUtfString("Description", description);
        }
    }


    public void possibleToSFSArray(ISFSArray data)
    {
        ISFSObject subData = new SFSObject();
        toSFSObject(subData, false);
        subData.putBool("Possible", true);
        data.addSFSObject(subData);
    }

    public void changedToSFSArray(ISFSArray data, boolean possible)
    {
        ISFSObject subData = new SFSObject();
        toSFSObject(subData, false);
        if (possible)
            subData.putBool("Available", false);
        else
            subData.putBool("Possible", false);
        data.addSFSObject(subData);
    }
}