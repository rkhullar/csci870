package me.nydev.wifituner.model;

public class Location
{
    protected String building, room;
    protected int floor;

    public Location() {}

    public Location(String building, int floor, String room)
    {
        this.building = building;
        this.floor = floor;
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public int getFloor()
    {
        return floor;
    }

    public String getRoom()
    {
        return room;
    }
}
