package com.sfs2x.extension;

/**
 * Created by tonytan on 25/12/2015.
 */
import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import com.smartfoxserver.bitswarm.sessions.ISession;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;

import com.sfs2x.extension.*;


public class Channel {
    private ChatSystemExtension extension;

    private HashMap<Integer, ActivePlayer> players = new HashMap<Integer, ActivePlayer>();
    private HashSet<Recipe> recipes = new HashSet<Recipe>();


    public Channel(ChatSystemExtension _extension) {
        extension = _extension;

        extension.trace("channel created!.");

        try {
            loadRecipes();
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
}
