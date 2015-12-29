package com.sfs2x.extension;

/**
 * Created by tonytan on 25/12/2015.
 *
 */
import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;


public class Channel {
    private ChatSystemExtension extension;
    private HashMap<Integer, ActivePlayer> players = new HashMap<Integer, ActivePlayer>();


    public Channel(ChatSystemExtension _extension) {
        extension = _extension;
        extension.trace("channel created!");

        try {
            loadReceivers();
        } catch (Exception e) {
            extension.trace(e.getMessage());
        }
    }

    public ChatSystemExtension getExtension() {
        return extension;
    }


    public ActivePlayer addPlayer(User user) {
        ActivePlayer newPlayer = new ActivePlayer(user, this);
        players.put(user.getId(), newPlayer);
        return newPlayer;
    }

    // Gets the player corresponding to the specified SFS user
    public ActivePlayer getPlayer(User u) {
        return players.get(u.getId());
    }

    public ActivePlayer getPlayer(int serverID) {
        return players.get(serverID);
    }

    public Collection<ActivePlayer> getPlayers() {
        return players.values();
    }

    public boolean hasPlayer(String username) {
        Collection<ActivePlayer> allPlayers = players.values();
        for (ActivePlayer player : allPlayers)
            if (player.getName().equals(username))
                return true;

        return false;
    }

    public boolean hasPlayer(ActivePlayer player) {
        if (players.containsValue(player))
            return true;
        return false;
    }

    public boolean hasUser(User user) {
        ActivePlayer player = this.getPlayer(user);
        return hasPlayer(player);
    }

    // When user lefts the room or disconnects - removing him from the players list
    public ActivePlayer userLeft(User user) {
        ActivePlayer player = this.getPlayer(user);
        if (player == null)
            return null;
        players.remove(user.getId());
        return player;
    }

    public void loadReceivers() {

        IDBManager dbManager = extension.getParentZone().getDBManager();
        Connection connection;
        try {
            // Grab a connection from the DBManager connection pool
            connection = dbManager.getConnection();

            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT username FROM users "
            );
            // Execute query
            ResultSet result = stmt.executeQuery();

        } catch (SQLException e) {
            SFSErrorData errData = new SFSErrorData(SFSErrorCode.GENERIC_ERROR);
            errData.addParameter("SQL Error: " + e.getMessage());
            extension.trace("A SQL Error occurred: " + e.getMessage());
        }


    }
}