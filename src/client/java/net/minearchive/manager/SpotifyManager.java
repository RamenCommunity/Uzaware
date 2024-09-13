package net.minearchive.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SpotifyManager {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    private String clientID;

    public void setClient(String clientID) {
        this.clientID = clientID;
    }



}
