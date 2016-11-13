package me.nydev.wifituner;

public final class Constants
{
    public interface NOTIFICATION_ID
    {
        int WIFI_SCAN_SERVICE = 101;
    }
    public interface ACTION
    {
        String MAIN = "me.nydev.wifituner.action.main";
        String START = "me.nydev.wifituner.action.start";
        String STOP = "me.nydev.wifituner.action.stop";
        String DONE = "me.nydev.wifituner.action.done";
    }
    public interface DATA
    {
        String DURATION = "me.nydev.wifituner.data.duration";
        String TIMELEFT = "me.nydev.wifituner.data.timeleft";
    }
    public interface VAR
    {
        int INTERVAL = 1;
    }
}
