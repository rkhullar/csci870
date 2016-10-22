package me.nydev.wifituner.model;

public class LocationBuilder extends Location
{
    public LocationBuilder(Location location)
    {
        this.building = location.building;
        this.floor = location.floor;
        this.room = location.room;
    }

    public LocationBuilder setBuilding(String building)
    {
        this.building = building;
        return this;
    }

    public LocationBuilder setFloor(int floor)
    {
        this.floor = floor;
        return this;
    }

    public LocationBuilder setRoom(String room)
    {
        this.room = room;
        return this;
    }

    public Location build()
    {
        return new Location(building, floor, room);
    }
}
