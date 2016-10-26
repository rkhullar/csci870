package me.nydev.wifituner.model;


public class UserBuilder extends User
{
    public UserBuilder()
    {
        super();
    }

    public UserBuilder(User x)
    {
        fname = x.fname; lname = x.lname;
        email = x.email; token = x.token;
    }

    public UserBuilder setFirstName(String x)
    {
        fname = x;
        return this;
    }

    public UserBuilder setLastName(String x)
    {
        lname = x;
        return this;
    }

    public UserBuilder setEmail(String x)
    {
        email = x;
        return this;
    }

    public UserBuilder setToken(String x)
    {
        token = x;
        return this;
    }

    public User build()
    {
        return new User(fname, lname, email, token);
    }
}
