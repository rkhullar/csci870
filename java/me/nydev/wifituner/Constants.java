package me.nydev.wifituner;

public final class Constants
{
    public interface NOTIFICATION_ID
    {
        int COUNTDOWN_BEGIN = 101;
        int COUNTDOWN_END = 102;
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
        String BUILDING = "me.nydev.wifituner.data.building";
        String FLOOR = "me.nydev.wifituner.data.floor";
        String ROOM = "me.nydev.wifituner.data.room";
    }
    public interface VAR
    {
        //int INTERVAL = 20;
        int INTERVAL = 1;
        String SSID = "NYIT";
        int BUFFER = 100;
        //int BUFFER = 10;
        String PROMPT = "[select]";
        int DEFAULT_SPINNER_ITEM = R.layout.support_simple_spinner_dropdown_item;
        int SPINNER_ITEM = R.layout.spinner_item;
        int SPINNER_DROPDOWN = R.layout.spinner_dropdown;
    }
    public interface REQUEST
    {
        int LOCATION = 1;
    }
}
