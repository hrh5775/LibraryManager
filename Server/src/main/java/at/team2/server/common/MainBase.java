package at.team2.server.common;

import at.team2.common.configuration.ConnectionInfo;

public class MainBase {
    public int doGetVersion() {
        return ConnectionInfo.version;
    }

    public void close() {
    }
}