package com.sfs2x.extension;

/**
 * Created by tonytan on 25/12/2015.
 *
 * Aims to check username, id and zone to login in.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.smartfoxserver.bitswarm.sessions.ISession;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

import com.sfs2x.extension.utils.RoomHelper;

public class LoginEventHandler extends BaseServerEventHandler
{
    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException
    {
        // Grab parameters from client request
        String userName = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
        String encryptedPwd = (String) event.getParameter(SFSEventParam.LOGIN_PASSWORD);
        ISession session = (ISession) event.getParameter(SFSEventParam.SESSION);

        // Get password from DB
        IDBManager dbManager = getParentExtension().getParentZone().getDBManager();
        Connection connection;

        try {
            // Grab a connection from the DBManager connection pool
            connection = dbManager.getConnection();

            // Build a prepared statement
            PreparedStatement preStatement = connection.prepareStatement(
                    "SELECT Password, Zone FROM player_info WHERE Username = ?"
            );
            preStatement.setString(1, userName);

            // Execute query
            ResultSet result = preStatement.executeQuery();

            // Verify that one record was found
            if (!result.first()) {
                // This is the part that goes to the client
                SFSErrorData errData = new SFSErrorData(SFSErrorCode.LOGIN_BAD_USERNAME);
                errData.addParameter(userName);

                // This is logged on the server side
                throw new SFSLoginException("Bad username: " + userName, errData);
            }

            String dbpassword = result.getString("Password");

            if (dbpassword.equals(encryptedPwd)) {
                trace("password is correct!");
            } else {
                SFSErrorData errData = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
                errData.addParameter(userName);

                throw new SFSLoginException("login failed: " + userName, errData);
            }

            // check the existence of user for the specific zone
            String zone = result.getString("Zone");
            String thisZone = getParentExtension().getParentZone().getName();
            if ((zone.equals("ChannelOne") && !zone.equals(thisZone)) ||
                    (!zone.equals("ChannelOne") && thisZone.equals("ChannelOne"))) {
                SFSErrorData data = new SFSErrorData(SFSErrorCode.JOIN_GAME_ACCESS_DENIED);
                data.addParameter(thisZone);

                throw new SFSLoginException("Login failed. User " + userName +
                        " is not a member of Server " + thisZone, data);
            }

            // Return connection to the DBManager connection pool
            connection.close();

            Channel channel = RoomHelper.getChannel(this);
            if (channel.hasPlayer(userName)) {
                SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_ALREADY_LOGGED);
                String[] params = {userName, thisZone};
                data.setParams(Arrays.asList(params));

                throw new SFSLoginException("Login failed: " + userName +
                        " is already logged in!", data);
            }


            // Verify the secure password ????
            if (!getApi().checkSecurePassword(session, dbpassword, encryptedPwd)) {
                    SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
                    data.addParameter(userName);

                    throw new SFSLoginException("Login failed for user: " + userName, data);

            }
        }
        catch (SQLException e){ // User name was not found
            SFSErrorData errData = new SFSErrorData(SFSErrorCode.GENERIC_ERROR);
            errData.addParameter("SQL Error: " + e.getMessage());

            throw new SFSLoginException("A SQL Error occurred: " + e.getMessage(), errData);
        }
    }
}